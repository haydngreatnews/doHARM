package doharm.net.packets.entityinfo;

/**
 * Enum for translating Entity Types to and from a Byte for transmitting in packets.
 * @author Adam McLaren (300248714)
 */
public enum EntType
{
	PLAYER_RANGER,
	PLAYER_WARRIOR,
	PLAYER_WIZARD,
	END_CHARACTER,
	END_FURNITURE,
	ITEM_DRAGONBALL,
	END_ITEM,
	END_PROJECTILE;

	public byte toByte() { return (byte) this.ordinal(); }
	
	public static EntType to(byte b)
	{
		return EntType.values()[(int)b];
	}

}