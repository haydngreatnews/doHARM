package doharm.net.packets.entityinfo;

import java.nio.ByteBuffer;

import doharm.logic.entities.AbstractEntity;

/** Holds updates to an entity */
public abstract class EntityUpdate extends EntityInfo
{
	public final int layer; //, velocity, health;
	public final float posX, posY, angle;
	
	protected EntityUpdate(int id, ByteBuffer buff)
	{
		super(id);
		posX = buff.getFloat();
		posY = buff.getFloat();
		layer = buff.getInt();
		angle = buff.getFloat();
		//velocity = buff.getInt();
		//health = buff.getInt();
	}

	public EntityUpdate(AbstractEntity ent) {
		super(ent.getID());
		posX = ent.getPosition().getX();
		posY = ent.getPosition().getY();
		layer = ent.getCurrentLayer().getLayerNumber();
		angle = ent.getAngle();
	}
	
	protected void toBytes(ByteBuffer buff) {
		super.toBytes(buff);
		buff.putFloat(posX);
		buff.putFloat(posY);
		buff.putInt(layer);
		buff.putFloat(angle);
	}
}