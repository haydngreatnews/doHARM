package doharm.net.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import doharm.logic.entities.AbstractEntity;
import doharm.logic.entities.EntityFactory;
import doharm.logic.entities.characters.classes.CharacterClassType;
import doharm.logic.entities.characters.players.PlayerType;
import doharm.logic.world.World;
import doharm.net.ClientState;
import doharm.net.UDPReceiver;
import doharm.net.packets.ClientPacket;
import doharm.net.packets.Command;
import doharm.net.packets.ServerPacket;
import doharm.net.packets.Snapshot;
import doharm.net.packets.entityinfo.CharacterCreate;
import doharm.net.packets.entityinfo.CharacterUpdate;
import doharm.net.packets.entityinfo.EntityCreate;
import doharm.net.packets.entityinfo.EntityUpdate;

public class Client {
	
	private UDPReceiver receiver;
	private DatagramSocket udpSock;
	
	private InetSocketAddress serverAddress;
	
	private ClientState state;
	
	private Snapshot snapCurrent, snapNext;
	
	private int latestSeqSent;
	
	private static int RETRY_COUNT = 4;
	private static int RETRY_DELAY = 20;
	private int counter;
	
	World world;
	
	
	/** Holds on to all unack'd Commands we've sent the server. */
	private LinkedList<Command> cmdsBuffer = new LinkedList<Command>();
	
	public Client(World world) throws IOException
	{
		this.world = world;
		
		// Setup the UDP socket.
		udpSock = new DatagramSocket(null);
		
		InetSocketAddress address = new InetSocketAddress(0);
		 
		udpSock.bind(address);
		
		receiver = new UDPReceiver(udpSock);
		receiver.start();
		
		state = ClientState.NONE;
	}
	
	/**
	 * Changes the Client state and execute any needed code for switching to the state.
	 * @param newState
	 */
	private void setState(ClientState newState)
	{
		state = newState;
	}
	/** 
	 * Attempt to connect to a Server.
	 * @param address Server address to connect to.
	 * @return If the connection was successful.
	 */
	public boolean connect(InetSocketAddress address)
	{
		serverAddress = address;
		setState(ClientState.CONTACTING);
		
		for (int i=0; i<RETRY_COUNT; ++i)
		{
			byte[] join = new byte[1];
			join[0] = (byte) ClientPacket.JOIN.ordinal();
			transmit(join);
			counter = 0;
			while (++counter < RETRY_DELAY);
			{
				while (!receiver.isEmpty())
				{
					DatagramPacket packet = receiver.poll();
					
					// If the packet isn't from the game server we are connected/talking to, discard.
					if (!packet.getSocketAddress().equals(serverAddress))	// TODO Potentially doesn't work, may need to getAddress, then comapre by IP and port seperately. or something.
						continue;
					
					byte[] data = packet.getData();
				
					if (ServerPacket.values()[data[0]] == ServerPacket.RESPONSE)
					{
						if (data[1] != 0)	// Response something other than OK.
						{
							System.out.println("Didn't let you in. For some reason."); // rejected
							setState(ClientState.NONE);
							return false;
						}
						else
						{
							// Good to go.
							setState(ClientState.INGAME);
							return true;
						}
					}
				}
			}
		}
		System.out.println("Connection attempt timed out.");
		setState(ClientState.NONE);
		return false;
	}

	public void processIncomingPackets()
	{		
		// TODO Might want to eventually add to the condition "|| !runningLate" for if we're taking too long. Probably won't care about this for quite a while.
		while (!receiver.isEmpty())
		{
			DatagramPacket packet = receiver.poll();
			
			// If the packet isn't from the game server we are connected/talking to, discard.
			if (!packet.getSocketAddress().equals(serverAddress))	// TODO Potentially doesn't work, may need to getAddress, then comapre by IP and port seperately. or something.
				continue;
			
			byte[] data = packet.getData();
			
			// Check what type of packet it is.
			switch (ServerPacket.values()[data[0]])
			{
			case SNAPSHOT:
				updateSnapshotPacket(data, false);
				break;
				
			case GAMESTATE:
				updateSnapshotPacket(data, true);
				break;
				
			case RESPONSE:
				// THIS CASE DOESN'T CHECK WHAT STATE WE ARE IN (ie we could be INGAME then get forced back into LOADING cause of receiving a packet of this type. This should be changed by only listening for certain packets at certain points of time, not sure how exactly want to do that at the mo.
				// if OK, [create TCP connection] and then load the static game into memory.
				if (data[1] == 0)	// response code 0 = OK
				{
					System.out.println("Server responded with OK, moving to LOADING state...");
					state = ClientState.LOADING;
					/* TODO Possibly run the loading in a separate thread so the queue continue to be looped thru.
					Then again, there is only one thing the client needs to be worrying about, 
					so he doesn't need to be listening for anything else at this point? 
					unless we want the server to ask us if were still here every once in a while.. */
				}
				else	// it's an error, print the message and return to menu or w/e
				{
					System.out.println("Server responded with NO.");
				}
				break;
			}
		}
	}
	
	/**
	 * Update what the latest snapshot packet from the server is.
	 * @param data
	 * @param isGameState is this packet a Gamestate packet.
	 */
	public void updateSnapshotPacket(byte[] data, boolean isGameState)
	{
		// Extract the timestamp from the packet.
		int timestamp = Snapshot.getTimestamp(data);
		
		// If this packet isn't more recent than the latest snapshot we've received, discard.
		if ( snapNext != null && timestamp <= snapNext.serverTime )
			return;
		
		snapNext = new Snapshot(data);
	}
	
	/**
	 * Builds a new Command and sends it out to the server we're connected to.
	 */
	public void dispatchCommand()
	{
		// Remove all acknowledged commands from the Command buffer.
		while (!cmdsBuffer.isEmpty() && cmdsBuffer.peek().seqNum <= snapNext.seqAckd)
			cmdsBuffer.poll();
		
		int time;
		if (snapCurrent != null)
			time = snapCurrent.serverTime;
		else
			time = 0;
		
		Command cmd = new Command(++latestSeqSent, time, world.getHumanPlayer() );
		
		// TODO
		
		transmit(cmd.convertToBytes());
	}
	
	/**
	 * Sends a UDP Packet out to the desired address.
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
	
	/**
	 * Sends a UDP Packet to the server we are connected to.
	 * REQUIRES: serverAddress equals valid address.
	 * @param data Packet contents.
	 * @return
	 */
	public boolean transmit(byte[] data)
	{
		return transmit(data, serverAddress);
	}
	
	// Fake main method, placeholder used so coding on the flow of operations can be done.
	private void main()
	{
		while (true)
		{
			// process incoming packets
			processIncomingPackets();
			
			if (state == ClientState.INGAME)
			{
				// update gamestate based on snapshots
				updateWorld();
				
				// perform client-side game logics
				
				// render
				
				// create then send command packet
				dispatchCommand();
				
				// wait for next tick
			}
			else
			{
				
				
				try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
			}
		}
	}
	
	private void begin()
	{
		// load up static world 
		
		// 
	}
	
	private void updateWorld()
	{	
		// We don't have a snapshot to update our world with.
		if (snapNext == null)
			return;
		
		EntityFactory ents = world.getEntityFactory();
		
		for (int i : snapNext.getEDeletes())
		{
			// skip if we've already deleted this entity (since the server will keep sending us the delete until our ack reaches them)
			AbstractEntity e = ents.getEntity(i);
			if (e == null)
				continue;
			
			ents.removeEntity(e);
		}
		
		for (EntityCreate c : snapNext.getECreates().values())
		{
			// skip if we've already created this entity (since the server will keep sending us the create until our ack reaches them)
			AbstractEntity e = ents.getEntity(c.id);
			if (e != null)
				continue;
			
			if (c instanceof CharacterCreate)
			{
				CharacterCreate cc = (CharacterCreate) c;
				AbstractEntity newEnt = world.getPlayerFactory().createPlayer(world.getLayers()[0].getTiles()[5][5],cc.name,CharacterClassType.WARRIOR, 0,PlayerType.HUMAN, true);
				ents.addEntity(newEnt, c.id, true);
			}
		}
		
		for (EntityUpdate u : snapNext.getEUpdates().values())
		{
			AbstractEntity e = ents.getEntity(u.id);
			if (e == null)
				continue;
			
			if (u instanceof CharacterUpdate)
			{
				CharacterUpdate cu = (CharacterUpdate) u;
				
				if (u.id == world.getHumanPlayer().getID())		// if this is our player, don't bother with it (for now. TODO)
					break;					
				
				e.setPosition(cu.posX, cu.posY, world.getLayer(cu.layer));
				e.setAngle(cu.angle);
			}
		}
		
		if (world.getHumanPlayer() == null)
		{
			//world.setHumanPlayer((HumanPlayer) world.getPlayerFactory().getEntity(GAMESTATE_ID));
		}
		
		snapCurrent = snapNext;
		snapNext = null;
	}

}
