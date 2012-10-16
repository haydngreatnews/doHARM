package doharm.logic.entities.characters.classes.troll;

import doharm.logic.entities.characters.classes.CharacterClass;
import doharm.logic.entities.characters.classes.CharacterClassType;
import doharm.logic.entities.characters.classes.ranger.RangerAttributes;
import doharm.logic.entities.characters.classes.ranger.RangerLevelupAttributes;
import doharm.logic.entities.characters.monsters.Monster;
import doharm.logic.entities.characters.Character;

public class Troll extends CharacterClass
{
	public Troll(Character character)
	{
		super(character,CharacterClassType.DRAGON);
		
		setAttributes(new TrollAttributes());
		setLevelupAttributes(new TrollLevelupAttributes());
	}
}
