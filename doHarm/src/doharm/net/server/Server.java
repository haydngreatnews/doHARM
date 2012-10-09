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
import doharm.logic.world.World;
import doharm.net.ClientState;
import doharm.net.UDPReceiver;
import doharm.net.packets.ClientPacket;
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
			case COMMAND:
				for (ConnectedClient c : clients)
					if ( c.getAddress().equals(packet.getSocketAddress()) )
					{
						c.updateClientCommandPacket(data);
						break;
					}
				break;
				
			case JOIN:
				if (clients.size() < maxPlayers)
				{
					// TODO check if player is already connected with that address.
					// If so, they must've dropped, so kill them and start them along the joining process again. 
					sendGamestate(createClient(packet));
				}
				else
				{
					// Reject, inform them so.
					byte[] response = new byte[2];
					response[0] = (byte) ServerPacket.RESPONSE.ordinal();
					response[1] = 1;	// 1 = NO at this point in time.
					transmit(response, new InetSocketAddress(packet.getAddress(), packet.getPort()));
				}
				break;
				
			case OKACK:
				for (ConnectedClient c : clients)
					if ( c.getAddress().equals(packet.getSocketAddress()) )
					{
						// blah
					}
				break;
				
			case READY:
				for (ConnectedClient c : clients)
					if ( c.getAddress().equals(packet.getSocketAddress()) )
					{
						// ready state transition?
						// send gamestate
					}
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
	
	private ConnectedClient createClient(DatagramPacket packet)
	{
		ConnectedClient client = new ConnectedClient(new InetSocketAddress(packet.getAddress(), packet.getPort()));
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
				Snapshot snap = new Snapshot(serverTime, c.latestCommandPacket.seqNum);
				
				// TODO temp setup is that the entire game change snap shot is sent to all clients, 
				// eventually when we add local area only snapshots, will need to build independently.
				
				for (int eID : entityDeletes)
				{
					// if (relavent)
					snap.addEDelete(eID);
				}
				
				for (int eID : entityCreates.keySet())
				{
					// if (relavent)
					snap.addECreate(entityCreates.get(eID));
				}
				
				for (int eID : entityUpdates.keySet())
				{
					// if (relavent)
					snap.addEUpdate(entityUpdates.get(eID));
				}
				
				// add snapshot to client
				c.addSnapshot(snap);
				// build n send
				transmit( c.buildTransmissionSnapshot().convertToBytes() , c.getAddress() );
			}
		}
	}
	
	/**
	 * Sends out a "Gamestate" snapshot, it contains all the information.
	 * @param client
	 */
	private void sendGamestate(ConnectedClient client)
	{
		Snapshot gamestate = new Snapshot(serverTime, -1);
		
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
		while (true)
		{
			// process incoming packets
			processIncomingPackets();
			
			// perform game logics
			
			
			// create then send snapshots
			dispatchSnapshots();
			
			// wait for next tick
		}
	}
}
