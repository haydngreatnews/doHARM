package doharm.net.packets;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import doharm.logic.entities.characters.classes.CharacterClassType;

/**
 * Represents a Join packet for sending to a server to join a game.
 * @author Adam McLaren (300248714)
 */
public class Join
{
	/** Desired Player Name */
	public final String name;
	/** Desired Player Colour */
	public final Color colour;
	/** Desired Player Class */
	public final CharacterClassType classType; 
	
	/**
	 * Creates a Join packet object from the given parameters.
	 * @param name Desired Name
	 * @param colour Desired Colour
	 * @param classType Desired Class
	 */
	public Join(String name, Color colour, CharacterClassType classType)
	{
		this.name = name;
		this.colour = colour;
		this.classType = classType;
	}
	
	/**
	 * Constructs the object form of the Join packet out of the byte-array.
	 * @param data Byte-array Join packet.
	 */
	public Join(byte[] data)
	{
		ByteBuffer buff = ByteBuffer.wrap(data);
		buff.position(1); // skip first byte, as we already know what type of packet this is.
		this.name = Bytes.getString(buff);
		this.colour = new Color(buff.get()&0xff, buff.get()&0xff, buff.get()&0xff);
		this.classType = CharacterClassType.values()[buff.get()&0xff]; 
	}
	
	/**
	 * Translates the Join object out into a byte-array for transmission.
	 * @return Byte-array Join packet.
	 */
	public byte[] toBytes()
	{
		ByteArrayOutputStream buff = new ByteArrayOutputStream();
		
		buff.write((byte) ClientPacket.JOIN.ordinal());
		try
		{
			buff.write(Bytes.setString(name));
			buff.write((byte)colour.getRed());
			buff.write((byte)colour.getGreen());
			buff.write((byte)colour.getBlue());
			buff.write((byte)classType.ordinal());
		}
		catch (IOException e) { e.printStackTrace(); }
		
		return buff.toByteArray();
	}
}
