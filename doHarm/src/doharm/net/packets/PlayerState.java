package doharm.net.packets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import doharm.logic.entities.characters.players.Player;

/**
 * Contains all the updating information (info only the Client, not everyone else, needs to know) regarding the player the client controls.
 * @author Adam McLaren (300248714)
 */
public class PlayerState {
	
	public final float health, mana, rage, exp;
	
	/**
	 * Create a PlayerState from the given Player Entity.
	 * @param playerEntity Player to compile State from.
	 */
	public PlayerState(Player player)
	{
		this.health = player.getHealth();
		this.mana = player.getMana();
		this.rage = player.getRage();
		this.exp = player.getExperienceRatio();
	}
	
	/**
	 * Read in a PlayerState from Bytes. This should only be called via the PlayerStates getPlayerState method!
	 * @param buff Buffer to read the state from.
	 */
	protected PlayerState(ByteBuffer buff)
	{
		health = buff.getFloat();
		mana = buff.getFloat();
		rage = buff.getFloat();
		exp = buff.getLong();
	}

	/**
	 * Translates the PlayerState object into a byte-array for transmission.
	 * @return Byte-array form of the PlayerState.
	 */
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

	/**
	 * Translates the PlayerState stored in a Byte-array form of a Snapshot into a PlayerState object.
	 * Will return a Full PlayerState should the PlayerState contained be of the full type.
	 * @param buff ByteBuffer to read the PlayerState from. The Buffer must be at the position where the PlayerState starts.
	 * @return
	 */
	public static PlayerState getPlayerState(ByteBuffer buff)
	{
		switch (buff.get())
		{
		case 0:	// No PlayerState
			return null;
		case 1:	// Regular PlayerState
			return new PlayerState(buff);
		case 2:	// Full PlayerState
			return new PlayerStateFull(buff);
		}
		return null;
	}

}
