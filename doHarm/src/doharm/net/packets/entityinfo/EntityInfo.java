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
}



class FurnitureUpdate extends EntityUpdate
{
	public FurnitureUpdate(int id, ByteBuffer buff)
	{
		super(id, buff);
	}
}

class ProjectileUpdate extends EntityUpdate
{
	// ALWAYS TRANSMITTED
	public ProjectileUpdate(int id, ByteBuffer buff)
	{
		super(id, buff);
	}
}



class FurnitureCreate extends EntityCreate
{
	public final String name;
	
	public FurnitureCreate(int id, ByteBuffer buff)
	{
		super(id, buff);
		name = Bytes.getString(buff);
	}
}

class ProjectileCreate extends EntityCreate
{
	public ProjectileCreate(int id, ByteBuffer buff)
	{
		super(id, buff);
	}
}

class ItemCreate extends EntityCreate
{
	public int position;
	public int colour;
	public String name;
	
	public ItemCreate(int id, ByteBuffer buff)
	{
		super(id, buff);
		position = buff.getInt();
		colour = buff.getInt();
		name = Bytes.getString(buff);
	}
}