package doharm.net.packets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import doharm.logic.entities.characters.players.Player;

/** Full PlayerState. Contains the Network ID of the Player and all attributes that only change on level up. */
public class PlayerStateFull extends PlayerState {

	/** Entity ID number for the current Player this client controls. */
	public final float maxHealth, maxMana, maxRage;
	
	public PlayerStateFull(Player playerEntity)
	{
		super(playerEntity);
		this.maxHealth = playerEntity.getMaxHealth();
		this.maxMana = playerEntity.getMaxMana();
		this.maxRage = playerEntity.getMaxRage();
	}
	
	protected PlayerStateFull(ByteBuffer buff)
	{
		super(buff);
		maxHealth = buff.getFloat();
		maxMana = buff.getFloat();
		maxRage = buff.getFloat();
	}

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
