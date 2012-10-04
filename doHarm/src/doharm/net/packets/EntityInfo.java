package doharm.net.packets;

import java.nio.ByteBuffer;

/** Holds changes in entity state */
abstract public class EntityInfo
{
	public final int id;
		
	protected EntityInfo(int id)
	{
		this.id = id;
	}
}

/** Holds updates to an entity */
abstract class EntityUpdate extends EntityInfo
{
	public final int position, angle, velocity, health;
	
	protected EntityUpdate(int id, ByteBuffer buff)
	{
		super(id);
		position = buff.getInt();
		angle = buff.getInt();
		velocity = buff.getInt();
		health = buff.getInt();
	}
}

class CharacterUpdate extends EntityUpdate
{
	public final int lvl;
	
	public CharacterUpdate(int id, ByteBuffer buff)
	{
		super(id, buff);
		lvl = buff.getInt();
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

/** Holds creation of an entity */
abstract class EntityCreate extends EntityInfo
{
	public final int type;
	
	protected EntityCreate(int id, ByteBuffer buff)
	{
		super(id);
		type = buff.getInt();
	}
}

class CharacterCreate extends EntityCreate
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