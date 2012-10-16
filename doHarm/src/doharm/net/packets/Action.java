package doharm.net.packets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import doharm.logic.entities.characters.players.HumanPlayer;

/**
 * Represents a Client Action Update, which is then able to be converted to or from a packet for sending/receiving over the net.
 * @author Adam McLaren (300248714)
 */
public class Action extends Update {

	/** Sequence number of the Action. */
	public final int seqNum;
	/** Latest Servertime from a Server Snapshot packet the client has received. */
	public final int serverTimeAckd;
	
	/** Player Coordinates Position */
	public final float posX, posY;
	/** Player Angle */
	public final float angle;
	/** Player Layer Position */
	public final int layer;
	
	/**
	 * Construct the Action packet from the Client.
	 * @param seq Sequence number for this packet.
	 * @param time Server time the Client is up to.
	 * @param player Player the Client controls.
	 */
	public Action(int seq, int time, HumanPlayer player)
	{
		seqNum = seq;
		serverTimeAckd = time;
		
		posX = player.getPosition().getX();
		posY = player.getPosition().getY();
		layer = player.getCurrentLayer().getLayerNumber();
		angle = player.getAngle();
	}
	
	/**
	 * Constructs an Action object out of an Action packet byte array.
	 * @param packet Raw byte array form of the Action to convert from.
	 * @return Action generated from the packet.
	 */
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
	 * @return Byte-array form of the Action packet.
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
