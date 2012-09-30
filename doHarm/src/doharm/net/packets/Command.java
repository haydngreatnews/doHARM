package doharm.net.packets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/** Struct representing a Client Command, which is then converted into a packet to send over the wire. */
public class Command {

	public final int serverTimeAckd;
	public int repeat;	// Number of times we've sent a packet with this serverTimeAck already.
	
	public Command(int time)
	{
		serverTimeAckd = time;
	}
	
	public Command(byte[] packet)
	{		
		ByteBuffer buff = ByteBuffer.wrap(packet);
		
		buff.position(1);	// Skip packet type, as we obviously already know what it is.
		
		serverTimeAckd = buff.getInt();
	}
	
	/**
	 * Translates the Command object into a byte-array for transmission.
	 * @param cmd Command object to convert
	 * @return 
	 */
	public byte[] convertToBytes()
	{
		ByteArrayOutputStream buff = new ByteArrayOutputStream();
		// Packet type
		buff.write((byte) ClientPacket.COMMAND.ordinal());		// TODO Do we need the byte cast?
		// ServertimeAckd
		try { buff.write(ByteBuffer.allocate(4).putInt(serverTimeAckd).array()); }
		catch (IOException e) {	e.printStackTrace(); }
		
		// my desired viewing direction
		// my desired movement
		// my selected weapon
		// my commands
		
		return buff.toByteArray();
	}
	
	/**
	 * Extracts the timestamp from the byte-array form of a Command packet.
	 * @param data Command byte-array packet.
	 * @return
	 */
	public static int getTimestamp(byte[] data)
	{
		ByteBuffer buff = ByteBuffer.wrap(data);
		buff.position(1);
		return buff.getInt();
	}

}
