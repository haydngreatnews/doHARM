package doharm.net.packets.entityinfo;

import java.nio.ByteBuffer;

import doharm.logic.entities.characters.players.HumanPlayer;
import doharm.net.packets.Bytes;

public class CharacterUpdate extends EntityUpdate
{
	//public final int lvl;
	
	public CharacterUpdate(int id, ByteBuffer buff)
	{
		super(id, buff);
		//lvl = buff.getInt();
	}

	public CharacterUpdate(HumanPlayer player) {
		super(player);
	}
	
	public byte[] toBytes()
	{
		byte[] bytes = new byte[1024];
		ByteBuffer buff = ByteBuffer.wrap(bytes);
		super.toBytes(buff);
		return Bytes.compress(buff);
	}
}