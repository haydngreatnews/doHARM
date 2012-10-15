package doharm.logic.entities.characters.classes.warrior;

import doharm.logic.entities.characters.Character;
import doharm.logic.entities.characters.classes.CharacterClass;
import doharm.logic.entities.characters.classes.CharacterClassType;

/**
 * A warrior is the master of melee combat. Starts off with a sword.
 * 
 * @author Roland
 */

public class Warrior extends CharacterClass
{

	public Warrior(Character character) 
	{
		super(character,CharacterClassType.WARRIOR);
		
		setAttributes(new WarriorAttributes());
		setLevelupAttributes(new WarriorLevelupAttributes());
		
	}

}
