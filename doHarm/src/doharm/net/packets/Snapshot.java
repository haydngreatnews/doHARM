package doharm.net.packets;

import java.nio.ByteBuffer;

/** Struct representing a Server Snapshot, which is then converted into a packet to send over the wire. */
public class Snapshot implements Cloneable {

	public final int serverTime;
	
	public Snapshot(int serverTime)
	{
		this.serverTime = serverTime;
	}
	
	/**
	 * Constructs a Snapshot object out of a Snapshot packet byte array.
	 * @param packet
	 * @return
	 */
	public Snapshot(byte[] packet)
	{
		serverTime = 8*((int)packet[1]) + 4*((int)packet[2]) + 2*((int)packet[3]) + ((int)packet[4]);
	}
	
	/**
	 * Translates the Snapshot object into a byte-array for transmission.
	 * @param snap Snapshot object to convert
	 * @return 
	 */
	public byte[] convertToBytes()
	{
		byte[] temp = new byte[1024];
		temp[0] = (byte) ServerPacket.SNAPSHOT.ordinal();
		byte[] serverTimeBytes = ByteBuffer.allocate(4).putInt(serverTime).array();
		return null;
	}
	
	/** Extracts the timestamp from the byte array form of a Snapshot */
	public static int getTimestamp(byte[] data)
	{
		return 8*((int)data[1]) + 4*((int)data[2]) + 2*((int)data[3]) + ((int)data[4]);
	}
	
	public Snapshot clone()
	{
		return this.clone();
	}

	/**
	 * Add any fields that are in the given snapshot that are not present in this snapshot.
	 * This WILL NOT overwrite any fields that this snapshot already has (even if their values differ). 
	 * @param next
	 */
	public void addMissing(Snapshot next) {
		// TODO Auto-generated method stub		
	}

}

