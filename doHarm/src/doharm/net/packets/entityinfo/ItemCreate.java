package doharm.net.packets.entityinfo;

import java.nio.ByteBuffer;

import doharm.net.packets.Bytes;

public class ItemCreate extends EntityUpdate {

	public float posX, posY;
	public int layer;
	public int colour;
	public String name;
	
	public ItemCreate(int id, ByteBuffer buff)
	{
		super(id, buff);
		posX = buff.getFloat();
		posY = buff.getFloat();
		layer = buff.getInt();
		colour = buff.getInt();
		name = Bytes.getString(buff);
	}
	
	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[1024];
		ByteBuffer buff = ByteBuffer.wrap(bytes);
		super.toBytes(buff);
		buff.putFloat(posX);
		buff.putFloat(posY);
		buff.putInt(layer);
		buff.putInt(colour);
		buff.put(Bytes.setString(name));
		return Bytes.compress(buff);
	}

}
