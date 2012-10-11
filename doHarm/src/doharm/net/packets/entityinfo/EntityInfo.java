package doharm.net.packets.entityinfo;

import java.nio.ByteBuffer;

import doharm.net.packets.Bytes;

/** Holds changes in entity state */
abstract public class EntityInfo
{
	public final int id;
		
	protected EntityInfo(int id)
	{
		this.id = id;
	}
	
	protected void toBytes(ByteBuffer buff)
	{
		buff.putInt(id);
	}
	
	public abstract byte[] toBytes();
}