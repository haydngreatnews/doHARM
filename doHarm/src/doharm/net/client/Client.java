package doharm.net.client;

import java.awt.Color;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

import doharm.logic.entities.AbstractEntity;
import doharm.logic.entities.EntityFactory;
import doharm.logic.entities.characters.classes.CharacterClassType;
import doharm.logic.entities.characters.players.Player;
import doharm.logic.entities.characters.players.PlayerType;
import doharm.logic.entities.projectiles.Projectile;
import doharm.logic.world.World;
import doharm.net.UDPReceiver;
import doharm.net.packets.Action;
import doharm.net.packets.Gamestate;
import doharm.net.packets.Join;
import doharm.net.packets.ServerPacket;
import doharm.net.packets.Snapshot;
import doharm.net.packets.entityinfo.CharacterCreate;
import doharm.net.packets.entityinfo.CharacterUpdate;
import doharm.net.packets.entityinfo.EntType;
import doharm.net.packets.entityinfo.EntityCreate;
import doharm.net.packets.entityinfo.EntityUpdate;
import doharm.net.packets.entityinfo.FurnitureCreate;
import doharm.net.packets.entityinfo.FurnitureUpdate;
import doharm.net.packets.entityinfo.ItemCreate;
import doharm.net.packets.entityinfo.ProjectileCreate;
import doharm.net.packets.entityinfo.ProjectileUpdate;

public class Client {

	private UDPReceiver receiver;
	private DatagramSocket udpSock;

	private InetSocketAddress serverAddress;

	private int playerEntID;

	private Snapshot snapCurrent, snapNext;

	private int latestSeqSent = 0;

	private static int RETRY_COUNT = 4;
	private static int RETRY_DELAY = 20;
	private int counter;	

	/** Holds on to all unack'd CommandLists we've sent the server. */
	private HashMap<Integer,ArrayList<String>> commandsBuffer = new HashMap<Integer,ArrayList<String>>();

	public Client()
	{	
		// Setup the UDP socket.
		try {
			udpSock = new DatagramSocket(null);
			InetSocketAddress address = new InetSocketAddress(0);
			udpSock.bind(address);
			receiver = new UDPReceiver(udpSock);
			receiver.start();
		
		} catch (SocketException e) { e.printStackTrace(); }
	}

	/** 
	 * Attempt to connect to a Server.
	 * @param address Server address to connect to.
	 * @return If the connection was successful.
	 */
	public String connect(InetSocketAddress address, String name, Color colour, CharacterClassType classType)
	{
		serverAddress = address;
		byte[] join = new Join(name,colour,classType).toBytes();

		for (int i=0; i<RETRY_COUNT; ++i)
		{
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
							serverAddress = null;
							switch (data[1])	// rejected
							{
							case 1:
								return "Server is full.";
							case 2:
								return "Name already in use.";
							}
						}
						else
							return null;	// Good to go.
					}
				}
			}
		}
		serverAddress = null;
		return "Connection attempt timed out.";
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
		if ( (snapNext != null && timestamp <= snapNext.serverTime) || (snapCurrent != null && timestamp <= snapCurrent.serverTime) )
			return;

		if (isGameState)
			snapNext = new Gamestate(data);
		else
			snapNext = new Snapshot(data);
	}

	/**
	 * Builds a new Action and sends it out to the server we're connected to.
	 */
	public void dispatchAction(World world)
	{
		// Remove all acknowledged commands from the Command buffer.
		ArrayList<Integer> toRemove = new ArrayList<Integer>();
		for (int i : commandsBuffer.keySet())
			if ( i <= snapNext.seqAckd )
				toRemove.add(i);
		for (int i : toRemove)
			commandsBuffer.remove(i);

		int time;
		if (snapCurrent != null)
			time = snapCurrent.serverTime;
		else
			time = 0;

		Action action = new Action(++latestSeqSent, time, world.getHumanPlayer() );

		if (!commandsBuffer.isEmpty())
			action.addCommands(commandsBuffer);

		transmit(action.convertToBytes());
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

	/* Fake main method, placeholder used so coding on the flow of operations can be done.
	private void main()
	{
		while (true)
		{
			// process incoming packets
			processIncomingPackets();

			// update gamestate based on snapshots
			updateWorld();

			// perform client-side game logics

			// render

			// create then send command packet
			dispatchCommand();

			// wait for next tick
		}
	}*/

	/** 
	 * Updates the client view of the world.
	 * @param world World to update.
	 */
	public void updateWorld(World world)
	{	
		// We don't have a snapshot to update our world with.
		if (snapNext == null)
			return;
		
		if (snapNext instanceof Gamestate)
		{
			Gamestate gamestate = (Gamestate) snapNext;
			// TODO update year month day.
			
			playerEntID = gamestate.playerEntityID;
			CharacterCreate pc = (CharacterCreate) snapNext.getECreates().get(playerEntID);
			CharacterClassType pClass = null;
			switch (EntType.to(pc.type))
			{
			case PLAYER_RANGER:
				pClass = CharacterClassType.RANGER;
				break;
			case PLAYER_WARRIOR:
				pClass = CharacterClassType.WARRIOR;
				break;
			case PLAYER_WIZARD:
				pClass = CharacterClassType.WIZARD;
			default:
				throw new RuntimeException("Invalid character class type for creating local player.");
			}
			world.createHumanPlayer(world.getRandomEmptyTile(), pClass, pc.name, pc.colour, playerEntID);
			CharacterUpdate pu = (CharacterUpdate) snapNext.getEUpdates().get(playerEntID);
			//world.getHumanPlayer().update(pu);
		}
		else
		{
			if (snapNext.timeOfDay < snapCurrent.timeOfDay)
				world.getTime().nextDay();
		}
		world.getTime().setTimeOfDay(snapNext.timeOfDay);
		world.getWeather().setConditions(snapNext.weather);

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

			// TODO TODO TODO TODO
			if (c instanceof CharacterCreate)
			{
				CharacterCreate cc = (CharacterCreate) c;
				//AbstractEntity newEnt = world.getPlayerFactory().createPlayer(world.getLayers()[0].getTiles()[5][5],cc.name,CharacterClassType.WARRIOR, 0,PlayerType.HUMAN, true);
				//ents.addEntity(newEnt, c.id, true);
			}
			else if (c instanceof FurnitureCreate)
			{
				FurnitureCreate fc = (FurnitureCreate) c;
			}
			else if (c instanceof ItemCreate)
			{
				ItemCreate ic = (ItemCreate) c;
			}
			else if (c instanceof ProjectileCreate)
			{
				ProjectileCreate pc = (ProjectileCreate) c;
			}
		}

		for (EntityUpdate u : snapNext.getEUpdates().values())
		{
			AbstractEntity e = ents.getEntity(u.id);
			if (e == null)	// skip if we don't have this entity or this entity is our player.
				continue;

			if (u instanceof CharacterUpdate)
			{
				Player p = (Player) e;

				if (u.id == world.getHumanPlayer().getID())		// if this is our player, don't bother with it (for now. TODO)
					continue;					

				//p.update((CharacterUpdate)u);
			}
//			else if (u instanceof FurnitureUpdate)
//			{
//				Furniture f = (Furniture) e;
//				f.update((FurnitureUpdate)u);
//			}
			else if (u instanceof ProjectileUpdate)
			{
				Projectile p = (Projectile) e;
				//p.update((ProjectileUpdate)u);
				
			}
		}

		// Get commands to execute.
		HashMap<Integer,ArrayList<String>> givenCmds = snapNext.getCommands();
		ArrayList<String> cmds = new ArrayList<String>();
		if (snapCurrent == null)
			for (ArrayList<String> list : givenCmds.values())
				cmds.addAll(list);
		else
			for (int i : givenCmds.keySet())
				if ( i > snapCurrent.serverTime )
					cmds.addAll(givenCmds.get(i));
		
		// TODO execute cmd list.

		snapCurrent = snapNext;
		snapNext = null;
	}

}
