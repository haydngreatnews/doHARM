package doharm.net.server;

import java.net.SocketAddress;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import doharm.net.ClientState;
import doharm.net.packets.Command;
import doharm.net.packets.Snapshot;

/**
 * The servers view of a Client
 */
public class ConnectedClient {
	private SocketAddress address;
	private Command latestCommandPacket;
	/** Last time we received a packet from this client. */
	private int latestTime;
	private ClientState state;
	/** Holds on to all unack'd Snapshots we've sent the client. */
	private LinkedList<Snapshot> snapsBuffer;
	
	public ConnectedClient(SocketAddress address)
	{
		this.address = address;
		state = ClientState.LOADING;
	}
	
	public SocketAddress getAddress()
	{
		return address;
	}
	
	/**
	 * Update what the latest command packet from the client is.
	 * @param data
	 */
	public void updateClientCommandPacket(byte[] data)
	{
		// Extract the timestamp from the packet.
		int timestamp = Command.getTimestamp(data);
		
		// If this packet isn't more recent than the latest command we've received, discard.
		// TODO needs to take into account the 2-B-Implemented repeat field. 
		if ( timestamp <= latestTime )
			return;
		
		latestTime = timestamp;
		latestCommandPacket = new Command(data);
	}
	
	
	public void addSnapshot(Snapshot snap)
	{
		snapsBuffer.add(snap);
	}
	
	/**
	 * Builds the Snapshot to actually transmit to the client.
	 * Combines all unack'd snapshots into one.
	 * @return
	 */
	public Snapshot buildTransmissionSnapshot()
	{
		// Remove all acknowledged snaps from the Snapshot buffer.
		while (snapsBuffer.peek().serverTime <= latestCommandPacket.serverTimeAckd)
		{
			snapsBuffer.poll();
		}
		
		/* Build the snapshot.
		
		So we use the latest snapshot as a base, and from there we go through the rest in order from newest to oldest,
		and if fields from the snap we are looking at isnt in our transmission snap, add them.
		
		TODO not the most efficient way at the moment; eventually should keep the latest transmission packet 
		and just make changes to it based on what has been removed and what has been added, not going thru all of them.
		
		*/
		
		Iterator<Snapshot> iter = snapsBuffer.descendingIterator();
		
		Snapshot transSnap = iter.next().clone();
		
		while (iter.hasNext())
			transSnap.addMissing(iter.next());
		
		return transSnap;
	}
	
	public ClientState getState()
	{
		return state;
	}

	public void setState(ClientState newState) {
		state = newState;
	}
}
