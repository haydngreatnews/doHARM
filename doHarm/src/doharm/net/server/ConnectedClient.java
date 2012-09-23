package doharm.net.server;

import java.net.SocketAddress;
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
	private Queue<Snapshot> snapsBuffer;
	
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
	
	public ClientState getState()
	{
		return state;
	}
}
