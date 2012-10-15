package doharm.net.packets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import doharm.logic.entities.characters.players.HumanPlayer;

/** Struct representing a Clients Actions, which is then converted into a packet to send over the wire. */
public class Action extends Update {

	public final int seqNum;
	public final int serverTimeAckd;
	
	// HumanPlayer movement.
	public final float posX, posY, angle;
	public final int layer;
	
	public Action(int seq, int time, HumanPlayer player)
	{
		seqNum = seq;
		serverTimeAckd = time;
		
		posX = player.getPosition().getX();
		posY = player.getPosition().getY();
		layer = player.getCurrentLayer().getLayerNumber();
		angle = player.getAngle();
	}
	
	public Action(byte[] packet)
	{		
		ByteBuffer buff = ByteBuffer.wrap(packet);
		
		buff.position(1);	// Skip packet type, as we obviously already know what it is.
		seqNum = buff.getInt();
		serverTimeAckd = buff.getInt();
		
		posX = buff.getFloat();
		posY = buff.getFloat();
		layer = buff.getInt();
		angle = buff.getFloat();
		
		readCommands(buff);
	}
	
	/**
	 * Translates the Action object into a byte-array for transmission.
	 * @return 
	 */
	public byte[] convertToBytes()
	{
		ByteArrayOutputStream buff = new ByteArrayOutputStream();
		
		try
		{
			buff.write((byte) ClientPacket.ACTION.ordinal());	// Packet type
			buff.write(Bytes.setInt(seqNum));			
			buff.write(Bytes.setInt(serverTimeAckd));

			buff.write(Bytes.setFloat(posX));
			buff.write(Bytes.setFloat(posY));
			buff.write(Bytes.setInt(layer));
			buff.write(Bytes.setFloat(angle));

			writeCommands(buff);
		}
		catch (IOException e) { e.printStackTrace(); }
		
		return buff.toByteArray();
	}
}
