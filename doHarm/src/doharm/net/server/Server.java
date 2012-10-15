package doharm.net.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

import doharm.logic.entities.AbstractEntity;
import doharm.logic.entities.characters.players.HumanPlayer;
import doharm.logic.entities.characters.players.Player;
import doharm.logic.entities.characters.players.PlayerType;
import doharm.logic.world.World;
import doharm.net.ClientState;
import doharm.net.UDPReceiver;
import doharm.net.packets.ClientPacket;
import doharm.net.packets.Gamestate;
import doharm.net.packets.Join;
import doharm.net.packets.ServerPacket;
import doharm.net.packets.Snapshot;
import doharm.net.packets.entityinfo.CharacterCreate;
import doharm.net.packets.entityinfo.CharacterUpdate;
import doharm.net.packets.entityinfo.EntityCreate;
import doharm.net.packets.entityinfo.EntityInfo;
import doharm.net.packets.entityinfo.EntityUpdate;

public class Server {

	private final int maxPlayers = 64;
	private ArrayList<ConnectedClient> clients = new ArrayList<ConnectedClient>();
	private UDPReceiver receiver;
	private DatagramSocket udpSock;
	private int serverTime;
	private int frameTime;	// Time between frames.
	private static int CLIENT_CHECK_INTERVAL = 60, TIMEOUT_DELAY = 200;
	
	private World world;
	
	public Server(int port, World world) throws IOException
	{
		this.world = world;
		
		// Setup the UDP socket.
		udpSock = new DatagramSocket(null);
		
		InetSocketAddress address = new InetSocketAddress(port);
		
		udpSock.bind(address);
		
		receiver = new UDPReceiver(udpSock);
		receiver.start();
	}
	
	public void processIncomingPackets()
	{		
		while (!receiver.isEmpty())
		{
			DatagramPacket packet = receiver.poll();
			byte[] data = packet.getData();
			
			// Check what type of packet it is.			
			switch (ClientPacket.values()[data[0]])
			{
			case ACTION:
				for (ConnectedClient c : clients)
					if ( c.getAddress().equals(packet.getSocketAddress()) )
					{
						c.updateClientActionPacket(data);
						break;
					}
				break;
				
			case JOIN:	// TODO TODO TODO TODO TODO TODO
				Join request = new Join(data);
				byte[] response = new byte[2];
				response[0] = (byte) ServerPacket.RESPONSE.ordinal();
				
				if (clients.size() < maxPlayers)
				{
					for (ConnectedClient c : clients)
					{
						if (c.getName().equals(request.name))
						{
							createClient(new InetSocketAddress(packet.getAddress(), packet.getPort()), request);
							break;
						}
					}
				}
				else
				{
					// Reject, inform them so.
					response[1] = 1;	// 1 = NO server is full.
					transmit(response, new InetSocketAddress(packet.getAddress(), packet.getPort()));
				}
				break;
			}
		}
	}
	
	/**
	 * Sends a UDP Packet out.
	 * @param data Packet contents.
	 * @param address IP and Port to send to.
	 * @return
	 */
	public boolean transmit(byte[] data, InetSocketAddress address)
	{
		try {
			udpSock.send(new DatagramPacket(data, data.length, address.getAddress(), address.getPort()));
			return true;
		}
		catch (SocketException e) { e.printStackTrace(); }
		catch (IOException e) {	e.printStackTrace(); }
		return false;
	}
	
	private ConnectedClient createClient(InetSocketAddress address, Join settings)
	{
		ConnectedClient oldClient = null;
		for (ConnectedClient c : clients)
		{
			if (c.getAddress().equals(address))
			{
				world.getPlayerFactory().removeEntity(c.getPlayerEntity());
				oldClient = c;
				break;
			}
		}
		if (oldClient != null)
		{
			oldClient.kill(world);
			clients.remove(oldClient);
		}
		
		Player player = (HumanPlayer) world.getPlayerFactory().createPlayer(world.getRandomEmptyTile(), settings.name, 
				settings.classType, world.getIDManager().takeID(), PlayerType.NETWORK, settings.colour, true);
		ConnectedClient client = new ConnectedClient(address, player);
		clients.add(client);
		return client;
	}
	
	/**
	 * Builds new snapshots from the game state then sends them out.
	 */
	private void dispatchSnapshots()
	{	
		HashMap<Integer,EntityUpdate> entityUpdates = new HashMap<Integer,EntityUpdate>();
		HashMap<Integer,EntityCreate> entityCreates = new HashMap<Integer,EntityCreate>();
		ArrayList<Integer> entityDeletes = new ArrayList<Integer>();
		
		// get game changes.
		
		// Created Entities.
		for (AbstractEntity e : world.getEntityFactory().getAddedEntities() )
		{
			if (e instanceof HumanPlayer)
			{
				entityCreates.put(e.getID(), new CharacterCreate((HumanPlayer)e));	// TODO
			}
		}
		world.getEntityFactory().clearAddedEntities();
		
		// Updated Entities (presently is just ALL entities)
		for (AbstractEntity e : world.getEntityFactory().getEntities() )
		{
			if (e instanceof HumanPlayer)
			{
				entityUpdates.put(e.getID(), new CharacterUpdate((HumanPlayer)e));	// TODO
			}
		}
		
		// Removed Entities.
		for (AbstractEntity e : world.getEntityFactory().getRemovedEntities() )
			entityDeletes.add(e.getID());
		world.getEntityFactory().clearRemovedEntities();
		
		
		for (ConnectedClient c : clients)
		{
			if (c.getState() == ClientState.INGAME)
			{
				buildSnapshot(c, new Snapshot(serverTime, c.latestActionPacket.seqNum, world), entityUpdates, entityCreates, entityDeletes);
				
				// build transmission snap and send
				transmit( c.buildTransmissionSnapshot().convertToBytes() , c.getAddress() );
			}
			else if ( c.getState() == ClientState.READY )
			{
				if (c.resendGamestate())
					sendGamestate(c);
				else
					buildSnapshot(c, new Snapshot(serverTime, -1, world), entityUpdates, entityCreates, entityDeletes);
			}
		}
	}
	
	/**
	 * Adds entity deletes, creates and updates to a Snapshot, and then adds it to the clients snap buffer.
	 * @param client
	 * @param snap Freshly constructed snapshot (does not include entity info)
	 * @param entityUpdates
	 * @param entityCreates
	 * @param entityDeletes
	 */
	private void buildSnapshot(ConnectedClient client, Snapshot snap, HashMap<Integer,EntityUpdate> entityUpdates, HashMap<Integer,EntityCreate> entityCreates, ArrayList<Integer> entityDeletes)
	{		
		for (int eID : entityDeletes)
			snap.addEDelete(eID);
		
		for (int eID : entityCreates.keySet())
			snap.addECreate(entityCreates.get(eID));
		
		for (int eID : entityUpdates.keySet())
			snap.addEUpdate(entityUpdates.get(eID));
		
		client.addSnapshot(snap);
	}
	
	/**
	 * Sends out a "Gamestate" snapshot, it contains all the information.
	 * @param client Client to send Gamestate to.
	 */
	private void sendGamestate(ConnectedClient client)
	{
		client.flushSnaps();
		
		Snapshot gamestate = new Gamestate(serverTime, -1, world);
		
		for (AbstractEntity e : world.getEntityFactory().getEntities() )
		{
			if (e instanceof HumanPlayer)
			{
				gamestate.addECreate(new CharacterCreate((HumanPlayer)e));	// TODO
				gamestate.addEUpdate(new CharacterUpdate((HumanPlayer)e));	// TODO
			}
		}
		transmit(gamestate.convertToBytes(), client.getAddress() );
	}
	
	
	// Fake main method, placeholder used so coding on the flow of operations can be done.
	private void main()
	{
		long start, sleepTime;
		int checkClientsCounter = 0;
		while (true)
		{
			start = System.currentTimeMillis();
			++checkClientsCounter;
			// process incoming packets
			processIncomingPackets();
			
			// get client actions; update pos based on client fields, and then perform commands.
			
			// perform moves
			
			// periodically check if we should drop clients (because we haven't heard from them in a while).
			if (checkClientsCounter > CLIENT_CHECK_INTERVAL)
			{
				checkClientsCounter = 0;
				ArrayList<ConnectedClient> toRemove = new ArrayList<ConnectedClient>(2);
				for ( ConnectedClient c : clients )
				{
					if (serverTime - c.getLatestTime() > TIMEOUT_DELAY);
						toRemove.add(c);
				}
				for ( ConnectedClient c : toRemove )
				{
					clients.remove(c);
				}
			}
			// create then send snapshots
			dispatchSnapshots();
			
			// wait for next tick
			sleepTime = frameTime - (System.currentTimeMillis()-start);
			if (sleepTime > 0)
			{
				try {Thread.sleep(sleepTime);} catch (InterruptedException e) {e.printStackTrace();}
			}
		}
	}
}
