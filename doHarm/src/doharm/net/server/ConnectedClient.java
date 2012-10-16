package doharm.net.server;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import doharm.logic.entities.characters.players.Player;
import doharm.logic.world.World;
import doharm.net.ClientState;
import doharm.net.packets.Action;
import doharm.net.packets.PlayerState;
import doharm.net.packets.PlayerStateFull;
import doharm.net.packets.Snapshot;

/**
 * The Servers view of a Client.
 * @author Adam McLaren (300248714)
 */
public class ConnectedClient
{
	private InetSocketAddress address;
	private Action latestActionPacket;
	private int gamestateCounter;
	private static int RESEND_DELAY = 40;
	private Player playerEntity;
	private String name;
	private int lastFullPlayerState;	// Server time at which we created the latest FullPlayerState, so keep sending PlayerStateFull until it's been ack'd.
	
	// Last time we received a packet from this client.
	private int latestTime;
	
	private ClientState state;
	
	// Holds on to all unack'd Snapshots we've sent the client.
	private LinkedList<Snapshot> snapsBuffer = new LinkedList<Snapshot>();
	
	// Holds on to all unack'd CommandLists we've sent the client.
	private HashMap<Integer,ArrayList<String>> commandsBuffer = new HashMap<Integer,ArrayList<String>>();
	
	public ConnectedClient(InetSocketAddress address, Player player, int time)
	{
		this.playerEntity = player;
		this.name = player.getName();
		this.address = address;
		setState(ClientState.READY);
		latestTime = time;
	}
	
	/** 
	 * @return Address the Client is connected from.
	 */
	public InetSocketAddress getAddress() {	return address; }
	
	/**
	 * @return Current state the Client is in.
	 */
	public ClientState getState() { return state; }
	
	/**
	 * @return Last time we received a packet from this Client.
	 */
	public int getLatestTime() { return latestTime; }

	/**
	 * Changes the state of the Client.
	 * @param newState State to set the Client to.
	 */
	public void setState(ClientState newState)
	{
		state = newState;
		switch (state)
		{
		case READY:
			gamestateCounter = 20;
		}
	}
	
	/**
	 * Update what the latest action packet from the client is.
	 * @param data Packet in raw byte array form.
	 * @param time Server time we received this packet at.
	 */
	public void updateClientActionPacket(byte[] data, int time)
	{
		if (state == ClientState.READY)		// TODO can probably optimise this by having a special kind of action packet sent on first try.
			setState(ClientState.INGAME);
		
		// Extract the timestamp from the packet.
		int seqnum = Action.getTimestamp(data);
		
		// If this packet isn't more recent than the latest action we've received, discard.
		if ( latestActionPacket != null && seqnum <= latestActionPacket.seqNum )
			return;
		
		latestTime = time;
		
		latestActionPacket = new Action(data);
	}
	
	/**
	 * Add a new snapshot to the snap buffer.
	 * @param snap Snapshot to add.
	 */
	public void addSnapshot(Snapshot snap) { snapsBuffer.add(snap); }
	
	/**
	 * Builds the Snapshot to actually transmit to the client.
	 * Combines all unack'd snapshots into one.
	 * @return The Snapshot to transmit.
	 */
	public Snapshot buildTransmissionSnapshot()
	{
		// Remove all acknowledged snaps from the Snapshot buffer.
		while (snapsBuffer.peek().serverTime <= latestActionPacket.serverTimeAckd)
			snapsBuffer.poll();
		
		// Remove all acknowledged commands from the Command buffer.
		ArrayList<Integer> toRemove = new ArrayList<Integer>();
		for (int i : commandsBuffer.keySet())
			if ( i <= latestActionPacket.serverTimeAckd )
				toRemove.add(i);
		for (int i : toRemove)
			commandsBuffer.remove(i);
		
		/* Build the snapshot to send.
		So we use the latest snapshot as a base, and from there we go through the rest in order from newest to oldest,
		and if entities from the snap we are looking at aren't in our transmission snap, add them.	*/
		Iterator<Snapshot> iter = snapsBuffer.descendingIterator();
		
		Snapshot transSnap = new Snapshot(iter.next());	// will never be null, a snapshot was just added earlier in the thread of execution
		
		// if the latest full player state hasn't been ack'd send the full state.
		if (lastFullPlayerState > latestActionPacket.serverTimeAckd)
			transSnap.setPlayerState(new PlayerStateFull(playerEntity));
		else
			transSnap.setPlayerState(new PlayerState(playerEntity));
		
		while (iter.hasNext())
			transSnap.addMissingEntities(iter.next());
		
		transSnap.addCommands(commandsBuffer);
		
		return transSnap;
	}

	/**
	 * Removes all the unack'd snaps from the snapsBuffer.
	 * Used when sending a full GameState. 
	 */
	public void flushSnaps() { snapsBuffer.clear(); }

	/**
	 * Check if it is time to resend the Gamestate.
	 * @return If we should resend the gamestate.
	 */
	public boolean resendGamestate()
	{
		if (--gamestateCounter == 0)
		{
			gamestateCounter = RESEND_DELAY;
			return true;
		}
		return false;
	}

	/**
	 * @return Player entity this Client controls. 
	 */
	public Player getPlayerEntity() { return playerEntity; }

	/**
	 * @return Name of this Client.
	 */
	public String getName() { return name; }

	/**
	 * Remove this Clients player from the world.
	 * @param world World to remove the player from.
	 */
	public void kill(World world)
	{
		world.getPlayerFactory().removePlayer(getPlayerEntity());
	}
	
	/**
	 * @return The latest Action packet receieved.
	 */
	public Action getLatestActionPacket()
	{
		return latestActionPacket;
	}
}
