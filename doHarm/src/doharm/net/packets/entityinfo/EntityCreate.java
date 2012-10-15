package doharm.net.packets.entityinfo;

import java.nio.ByteBuffer;

import doharm.logic.entities.AbstractEntity;
import doharm.logic.entities.characters.players.Player;
import doharm.logic.entities.items.Item;

/** Holds creation of an entity */
public abstract class EntityCreate extends EntityInfo
{
	public final byte type;
	
	protected EntityCreate(int id, EntType type)
	{
		super(id);
		this.type = (byte)type.ordinal();
	}

	protected EntityCreate(AbstractEntity ent)
	{
		super(ent.getID());
		type = getByteRepresentationOfTheType(ent).toByte();
	}
	
	protected void toBytes(ByteBuffer buff)
	{
		super.toBytes(buff);
		buff.put(type);
	}

	public static EntityCreate newEntityCreate(int id, ByteBuffer buff)
	{
		EntType t = EntType.to(buff.get());
		if (t.ordinal() < EntType.END_CHARACTER.ordinal())
			return new CharacterCreate(id,t,buff);
		else if (t.ordinal() < EntType.END_FURNITURE.ordinal())
			return new FurnitureCreate(id,t,buff);
		else if (t.ordinal() < EntType.END_ITEM.ordinal())
			return new ItemCreate(id,t,buff);
		else
			return new ProjectileCreate(id,t,buff);
	}
	
	private EntType getByteRepresentationOfTheType(AbstractEntity ent)
	{
		if (ent instanceof Player)
		{
			Player p = (Player)ent;
			switch (p.getCharacterClass().getClassType())
			{
			case RANGER:
				return EntType.PLAYER_RANGER;
			case WIZARD:
				return EntType.PLAYER_WIZARD;
			case WARRIOR:
				return EntType.PLAYER_WARRIOR;
			}
		}
		else if (ent instanceof Item)
		{
			return null;
		}
		return null;
	}
}