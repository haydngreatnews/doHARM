package doharm.logic.entities.characters.classes.dragon;

import doharm.logic.entities.characters.classes.CharacterClass;
import doharm.logic.entities.characters.classes.CharacterClassType;
import doharm.logic.entities.characters.classes.ranger.RangerAttributes;
import doharm.logic.entities.characters.classes.ranger.RangerLevelupAttributes;
import doharm.logic.entities.characters.monsters.Monster;
import doharm.logic.entities.characters.Character;

public class Dragon extends CharacterClass
{
	public Dragon(Character character)
	{
		super(character,CharacterClassType.DRAGON);
		
		setAttributes(new DragonAttributes());
		setLevelupAttributes(new DragonLevelupAttributes());
	}
}
