package doharm.net.packets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import doharm.logic.entities.characters.players.HumanPlayer;

/** Struct representing a Client Command, which is then converted into a packet to send over the wire. */
public class Command {

	public int seqNum;
	public final int serverTimeAckd;
	
	// HumanPlayer movement.
	public final float posX, posY, angle;
	public final int layer;
	
	public Command(int seq, int time, HumanPlayer player)
	{
		seqNum = seq;
		serverTimeAckd = time;
		
		posX = player.getPosition().getX();
		posY = player.getPosition().getY();
		layer = player.getCurrentLayer().getLayerNumber();
		angle = player.getAngle();
	}
	
	public Command(byte[] packet)
	{		
		ByteBuffer buff = ByteBuffer.wrap(packet);
		
		buff.position(1);	// Skip packet type, as we obviously already know what it is.
		
		seqNum = buff.getInt();
		serverTimeAckd = buff.getInt();
		
		posX = buff.getFloat();
		posY = buff.getFloat();
		layer = buff.getInt();
		angle = buff.getFloat();
	}
	
	/**
	 * Translates the Command object into a byte-array for transmission.
	 * @param cmd Command object to convert
	 * @return 
	 */
	public byte[] convertToBytes()
	{
		ByteArrayOutputStream buff = new ByteArrayOutputStream();
		
		try {
		// Packet type
		buff.write((byte) ClientPacket.COMMAND.ordinal());		// Need the byte cast otherwise it'll write it as a 4-byte int
		// SeqNum
		 buff.write(Bytes.setInt(seqNum)); 
		// ServertimeAckd
		buff.write(Bytes.setInt(serverTimeAckd));
		
		// my desired viewing direction
		// my desired movement
		// my selected weapon
		// my commands
		buff.write(Bytes.setFloat(posX));
		buff.write(Bytes.setFloat(posY));
		buff.write(Bytes.setInt(layer));
		buff.write(Bytes.setFloat(angle));
		
		
		} catch (IOException e) {	e.printStackTrace(); }
		
		return buff.toByteArray();
	}
	
	/**
	 * Extracts the timestamp from the byte-array form of a Command packet.
	 * @param data Command byte-array packet.
	 * @return
	 */
	public static int getSeqNum(byte[] data)
	{
		ByteBuffer buff = ByteBuffer.wrap(data);
		buff.position(1);
		return buff.getInt();
	}

}
