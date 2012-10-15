package doharm.net.packets.entityinfo;

import java.nio.ByteBuffer;

import doharm.logic.entities.items.Item;
import doharm.net.packets.Bytes;

public class ItemCreate extends EntityCreate {

	public float posX, posY;
	public int layer;
	public String name;
	
	public ItemCreate(Item item)
	{
		super(item);
		posX = item.getPosition().getX();
		posY = item.getPosition().getY();
		layer = item.getCurrentLayer().getLayerNumber();
		name = item.toString();
	}
	
	public ItemCreate(int id, EntType type, ByteBuffer buff)
	{
		super(id, type);
		posX = buff.getFloat();
		posY = buff.getFloat();
		layer = buff.getInt();
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
		buff.put(Bytes.setString(name));
		return Bytes.compress(buff);
	}

}
