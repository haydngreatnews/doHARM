package doharm.net.packets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import doharm.logic.entities.characters.players.Player;

/**
 * Full PlayerState. Contains updated attributes that only change on level up.
 * @author Adam McLaren (300248714)
 */
public class PlayerStateFull extends PlayerState {

	public final float maxHealth, maxMana, maxRage;
	
	/**
	 * Create a PlayerState from the given Player Entity.
	 * @param playerEntity Player to compile State from.
	 */
	public PlayerStateFull(Player playerEntity)
	{
		super(playerEntity);
		this.maxHealth = playerEntity.getMaxHealth();
		this.maxMana = playerEntity.getMaxMana();
		this.maxRage = playerEntity.getMaxRage();
	}
	
	/**
	 * Read in a Full PlayerState from Bytes. This should only be called by the PlayerStates getPlayerState method!
	 * @param buff Buffer to read the state from.
	 */
	protected PlayerStateFull(ByteBuffer buff)
	{
		super(buff);
		maxHealth = buff.getFloat();
		maxMana = buff.getFloat();
		maxRage = buff.getFloat();
	}

	/**
	 * Translates the PlayerState object into a byte-array for transmission.
	 * @return Byte-array form of the Full PlayerState.
	 */
	public byte[] convertToBytes()
	{
		byte[] pState = super.convertToBytes();
		pState[0] = (byte)2;	// 2 = Full Player State.
		ByteArrayOutputStream buff = new ByteArrayOutputStream();
		try
		{
			buff.write(pState);	// Copy the playerstate over into our new full playerstate array.
			buff.write(Bytes.setFloat(maxHealth));
			buff.write(Bytes.setFloat(maxMana));
			buff.write(Bytes.setFloat(maxRage));
		} catch (IOException e) { e.printStackTrace(); }
		
		return buff.toByteArray();
	}
}
