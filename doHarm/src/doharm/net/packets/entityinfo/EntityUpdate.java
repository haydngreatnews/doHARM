package doharm.net.packets.entityinfo;

import java.nio.ByteBuffer;

import doharm.logic.entities.AbstractEntity;

/**
 * Holds information for the update of an entity to send to Clients.
 * @author Adam McLaren (300248714)
 */
public abstract class EntityUpdate extends EntityInfo
{
	public final int layer; //velocity, 
	public final float posX, posY, angle;
	protected static final byte CHARACTER = 0, FURNITURE = 1, PROJECTILE = 2;
	
	protected EntityUpdate(int id, ByteBuffer buff)
	{
		super(id);
		posX = buff.getFloat();
		posY = buff.getFloat();
		layer = buff.getInt();
		angle = buff.getFloat();
		//velocity = buff.getInt();
	}

	public EntityUpdate(AbstractEntity ent) {
		super(ent.getID());
		posX = ent.getPosition().getX();
		posY = ent.getPosition().getY();
		layer = ent.getCurrentLayer().getLayerNumber();
		angle = ent.getAngle();
		//velocity = ent.getVelocity();
	}
	
	protected void toBytes(byte type, ByteBuffer buff) {
		super.toBytes(buff);
		buff.put(type);
		buff.putFloat(posX);
		buff.putFloat(posY);
		buff.putInt(layer);
		buff.putFloat(angle);
	}

	public static EntityUpdate newEntityUpdate(int id, ByteBuffer buff) {
		byte type = buff.get();
		if (type == CHARACTER)
			return new CharacterUpdate(id,buff);
		else if (type == FURNITURE)
			return new FurnitureUpdate(id,buff);
		else if (type == PROJECTILE)
			return new ProjectileUpdate(id,buff);
		return null;
	}
}