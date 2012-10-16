package doharm.net.packets;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import doharm.logic.entities.characters.classes.CharacterClassType;

public class Join
{
	public final String name;
	public final Color colour;
	public final CharacterClassType classType; 
	
	public Join(String name, Color colour, CharacterClassType classType)
	{
		this.name = name;
		this.colour = colour;
		this.classType = classType;
	}
	
	public Join(byte[] data)
	{
		ByteBuffer buff = ByteBuffer.wrap(data);
		buff.position(1); // skip first byte, as we already know what type of packet this is.
		this.name = Bytes.getString(buff);
		this.colour = new Color(buff.get()&0xff, buff.get()&0xff, buff.get()&0xff);
		this.classType = CharacterClassType.values()[buff.get()&0xff]; 
	}
	
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
