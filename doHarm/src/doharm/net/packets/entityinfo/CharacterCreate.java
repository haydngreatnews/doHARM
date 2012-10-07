package doharm.net.packets.entityinfo;

import java.nio.ByteBuffer;

import doharm.logic.entities.characters.players.HumanPlayer;
import doharm.net.packets.Bytes;

public class CharacterCreate extends EntityCreate
{
	//public final int colour;
	public final String name;
	
	public CharacterCreate(int id, ByteBuffer buff)
	{
		super(id, buff);
		//colour = buff.getInt();
		name = Bytes.getString(buff);
	}

	public CharacterCreate(HumanPlayer player) {
		super(player);
		name = player.toString();
	}
	
	public byte[] toBytes()
	{
		byte[] bytes = new byte[1024];
		ByteBuffer buff = ByteBuffer.wrap(bytes);
		super.toBytes(buff);
		buff.put(Bytes.setString(name));
		return Bytes.compress(buff);
	}
}
