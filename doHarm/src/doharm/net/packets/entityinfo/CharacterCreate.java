package doharm.net.packets.entityinfo;

import java.nio.ByteBuffer;

import doharm.net.packets.Bytes;

public class CharacterCreate extends EntityCreate
{
	public final int colour;
	public final String name;
	
	public CharacterCreate(int id, ByteBuffer buff)
	{
		super(id, buff);
		colour = buff.getInt();
		name = Bytes.getString(buff);
	}
}
