package doharm.net.packets.entityinfo;

import java.nio.ByteBuffer;

import doharm.logic.entities.characters.players.HumanPlayer;
import doharm.logic.entities.characters.players.Player;
import doharm.net.packets.Bytes;

public class CharacterUpdate extends EntityUpdate
{
	public final float health;
	public final int lvl;
	
	protected CharacterUpdate(int id, ByteBuffer buff)
	{
		super(id, buff);
		health = buff.getFloat();
		lvl = buff.getInt();
	}

	public CharacterUpdate(Player player) {
		super(player);
		health = player.getHealth();
		lvl = player.getLevel();
	}
	
	public byte[] toBytes()
	{
		byte[] bytes = new byte[1024];
		ByteBuffer buff = ByteBuffer.wrap(bytes);
		super.toBytes(EntityUpdate.CHARACTER, buff);
		buff.putFloat(health);
		buff.putInt(lvl);
		return Bytes.compress(buff);
	}
}