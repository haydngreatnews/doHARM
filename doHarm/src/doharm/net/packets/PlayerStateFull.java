package doharm.net.packets;

import java.nio.ByteBuffer;

import doharm.logic.entities.characters.players.HumanPlayer;

/** Full PlayerState. Contains the Network ID of the Player and all attributes that only change on level up. */
public class PlayerStateFull extends PlayerState {

	/** Entity ID number for the current Player this client controls. */
	public final int id;
	public final float maxHealth;
	
	public PlayerStateFull(HumanPlayer player)
	{
		super(player);
		this.id = player.getID();
		this.maxHealth = player.getMaxHealth();
	}
	
	public PlayerStateFull(ByteBuffer buff) {
		super(buff);
		id = buff.getInt();
		maxHealth = buff.getFloat();
	}

	public byte[] convertToBytes()
	{
		return null;
	}
}
