package doharm.net.packets.entityinfo;

import java.nio.ByteBuffer;

/** Holds updates to an entity */
public abstract class EntityUpdate extends EntityInfo
{
	public final int layer, velocity, health;
	public final float posX, posY, angle;
	
	protected EntityUpdate(int id, ByteBuffer buff)
	{
		super(id);
		posX = buff.getFloat();
		posY = buff.getFloat();
		layer = buff.getInt();
		angle = buff.getFloat();
		velocity = buff.getInt();
		health = buff.getInt();
	}
}