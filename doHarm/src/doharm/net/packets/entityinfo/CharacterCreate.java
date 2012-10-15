package doharm.net.packets.entityinfo;

import java.awt.Color;
import java.nio.ByteBuffer;

import doharm.logic.entities.characters.players.Player;
import doharm.net.packets.Bytes;

public class CharacterCreate extends EntityCreate
{
	public final Color colour;
	public final String name;
	
	public CharacterCreate(int id, EntType type, ByteBuffer buff)
	{
		super(id, type);
		colour = new Color(buff.get()&0xff, buff.get()&0xff, buff.get()&0xff);
		name = Bytes.getString(buff);
	}

	public CharacterCreate(Player player)
	{
		super(player);
		colour = player.getColour();
		name = player.getName();
	}
	
	public byte[] toBytes()
	{
		byte[] bytes = new byte[1024];
		ByteBuffer buff = ByteBuffer.wrap(bytes);
		super.toBytes(buff);
		buff.put((byte)colour.getRed());
		buff.put((byte)colour.getGreen());
		buff.put((byte)colour.getBlue());
		buff.put(Bytes.setString(name));
		return Bytes.compress(buff);
	}
}
