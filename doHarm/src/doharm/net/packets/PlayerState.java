package doharm.net.packets;

public class PlayerState {
	
	/** Entity ID number for the current Player this client controls. */
	public final int id;
	public final float health, mana, rage;
	public final long exp;
	
	public PlayerState(int id)
	{
		this.id = id;
		this.health = 0;
		this.mana = 0;
		this.rage = 0;
		this.exp = 0;
	}
	
	public byte[] convertToBytes()
	{
		return null;
	}

}
