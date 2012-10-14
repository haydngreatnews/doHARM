package doharm.net.packets;

import java.nio.ByteBuffer;

import doharm.logic.entities.characters.players.HumanPlayer;

/** Contains all updating information regarding the player the client controls. */
public class PlayerState {
	
	public final float health, mana, rage, exp;
	
	public PlayerState(HumanPlayer player)
	{
		this.health = player.getHealth();
		this.mana = player.getMana();
		this.rage = player.getRage();
		this.exp = player.getExperienceRatio();
	}
	
	public PlayerState(ByteBuffer buff)
	{
		health = buff.getFloat();
		mana = buff.getFloat();
		rage = buff.getFloat();
		exp = buff.getLong();
	}

	public byte[] convertToBytes()
	{
		return null;
	}

}
