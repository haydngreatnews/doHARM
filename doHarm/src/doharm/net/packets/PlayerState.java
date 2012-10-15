package doharm.net.packets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import doharm.logic.entities.characters.players.Player;

/** Contains all updating information regarding the player the client controls. */
public class PlayerState {
	
	public final float health, mana, rage, exp;
	
	public PlayerState(Player player)
	{
		this.health = player.getHealth();
		this.mana = player.getMana();
		this.rage = player.getRage();
		this.exp = player.getExperienceRatio();
	}
	
	protected PlayerState(ByteBuffer buff)
	{
		health = buff.getFloat();
		mana = buff.getFloat();
		rage = buff.getFloat();
		exp = buff.getLong();
	}

	public byte[] convertToBytes()
	{
		ByteArrayOutputStream buff = new ByteArrayOutputStream();
		buff.write((byte)1);	// 1 = Normal Player State.
		try {
			buff.write(Bytes.setFloat(health));
			buff.write(Bytes.setFloat(mana));
			buff.write(Bytes.setFloat(rage));
			buff.write(Bytes.setFloat(exp));			
		} catch (IOException e) {	e.printStackTrace(); }
		return buff.toByteArray();
	}

	public static PlayerState getPlayerState(ByteBuffer buff)
	{
		switch (buff.get())
		{
		case 0:
			return null;
		case 1:
			return new PlayerState(buff);
		case 2:
			return new PlayerStateFull(buff);
		}
		return null;
	}

}
