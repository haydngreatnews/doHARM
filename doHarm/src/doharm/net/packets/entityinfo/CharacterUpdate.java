package doharm.net.packets.entityinfo;

import java.nio.ByteBuffer;

import doharm.logic.entities.characters.players.HumanPlayer;
import doharm.net.packets.Bytes;

public class CharacterUpdate extends EntityUpdate
{
	public final int lvl;
	
	protected CharacterUpdate(int id, ByteBuffer buff)
	{
		super(id, buff);
		lvl = buff.getInt();
	}

	public CharacterUpdate(HumanPlayer player) {
		super(player);
		lvl = player.getLevel();
	}
	
	public byte[] toBytes()
	{
		byte[] bytes = new byte[1024];
		ByteBuffer buff = ByteBuffer.wrap(bytes);
		super.toBytes(EntityUpdate.CHARACTER, buff);
		buff.putInt(lvl);
		return Bytes.compress(buff);
	}
}