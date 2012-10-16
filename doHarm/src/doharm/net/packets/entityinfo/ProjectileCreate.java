package doharm.net.packets.entityinfo;

import java.nio.ByteBuffer;

/**
 * @author Adam McLaren (300248714)
 */
public class ProjectileCreate extends EntityCreate {

	public ProjectileCreate(int id, EntType type, ByteBuffer buff)
	{
		super(id, type);
	}
	
	@Override
	public byte[] toBytes() {
		// TODO Auto-generated method stub
		return null;
	}

}
