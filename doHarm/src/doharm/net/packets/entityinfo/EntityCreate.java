package doharm.net.packets.entityinfo;

import java.nio.ByteBuffer;

import doharm.logic.entities.AbstractEntity;

/** Holds creation of an entity */
public abstract class EntityCreate extends EntityInfo
{
	//public final int type;
	
	protected EntityCreate(int id, ByteBuffer buff)
	{
		super(id);
		//type = buff.getInt();
	}

	protected EntityCreate(AbstractEntity ent) {
		super(ent.getID());
		//
	}
	
	protected void toBytes(ByteBuffer buff) {
		
		super.toBytes(buff);
		
	}
}