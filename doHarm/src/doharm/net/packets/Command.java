package doharm.net.packets;

/** Struct representing a Client Command, which is then converted into a packet to send over the wire. */
public class Command {

	public final int serverTimeAckd;
	public int repeat;	// Number of times we've sent a packet with this serverTimeAck already.
	
	public Command(byte[] packet)
	{
		serverTimeAckd = 8*((int)packet[1]) + 4*((int)packet[2]) + 2*((int)packet[3]) + ((int)packet[4]);
	}
	
	/**
	 * Translates the Command object into a byte-array for transmission.
	 * @param cmd Command object to convert
	 * @return 
	 */
	public byte[] convertToBytes()
	{
		byte[] temp = new byte[1024];
		temp[0] = (byte) ServerPacket.SNAPSHOT.ordinal();
		// temp[1-4] server time of last snapshot/gamestate we received.
		// my desired viewing direction
		// my desired movement
		// my selected weapon
		// my commands
		return null;
	}
	
	/**
	 * Extracts the timestamp from the byte-array form of a Command packet.
	 * @param data Command byte-array packet.
	 * @return
	 */
	public static int getTimestamp(byte[] data)
	{
		return 8*((int)data[1]) + 4*((int)data[2]) + 2*((int)data[3]) + ((int)data[4]);
	}

}
