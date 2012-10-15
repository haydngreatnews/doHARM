package doharm.net.packets.entityinfo;

import java.nio.ByteBuffer;

import doharm.net.packets.Bytes;

public class FurnitureCreate extends EntityCreate {

public final String name;
	
	public FurnitureCreate(int id, EntType type, ByteBuffer buff)
	{
		super(id, type);
		name = Bytes.getString(buff);
	}
	
	@Override
	public byte[] toBytes() {
		// TODO Auto-generated method stub
		return null;
	}

}
