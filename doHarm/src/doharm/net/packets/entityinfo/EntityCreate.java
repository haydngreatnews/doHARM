package doharm.net.packets.entityinfo;

import java.nio.ByteBuffer;

/** Holds creation of an entity */
public abstract class EntityCreate extends EntityInfo
{
	public final int type;
	
	protected EntityCreate(int id, ByteBuffer buff)
	{
		super(id);
		type = buff.getInt();
	}
}