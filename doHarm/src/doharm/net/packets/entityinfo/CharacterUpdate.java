package doharm.net.packets.entityinfo;

import java.nio.ByteBuffer;

public class CharacterUpdate extends EntityUpdate
{
	public final int lvl;
	
	public CharacterUpdate(int id, ByteBuffer buff)
	{
		super(id, buff);
		lvl = buff.getInt();
	}
}