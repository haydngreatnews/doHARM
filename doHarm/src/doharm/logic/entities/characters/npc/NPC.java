package doharm.logic.entities.characters.npc;

import doharm.logic.entities.characters.Character;
import doharm.logic.entities.characters.CharacterType;
import doharm.logic.entities.characters.classes.CharacterClassType;
import doharm.logic.world.tiles.Tile;

public abstract class NPC extends Character
{
	public NPC() 
	{
		super(CharacterType.NPC);
	}

}
