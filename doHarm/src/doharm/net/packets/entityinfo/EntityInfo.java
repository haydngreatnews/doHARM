package doharm.net.packets.entityinfo;

import java.nio.ByteBuffer;

/**
 * Holds changes of an entity state to send to Clients.
 * @author Adam McLaren (300248714)
 */
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