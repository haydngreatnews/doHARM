package doharm.net.packets.entityinfo;

import java.nio.ByteBuffer;

import doharm.net.packets.Bytes;

public class ProjectileUpdate extends EntityUpdate {

	protected ProjectileUpdate(int id, ByteBuffer buff)
	{
		super(id, buff);
	}
	
	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[1024];
		ByteBuffer buff = ByteBuffer.wrap(bytes);
		super.toBytes(EntityUpdate.PROJECTILE, buff);
		return Bytes.compress(buff);
	}

}
