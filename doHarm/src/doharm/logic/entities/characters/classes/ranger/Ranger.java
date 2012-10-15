package doharm.logic.entities.characters.classes.ranger;

import doharm.logic.entities.characters.Character;
import doharm.logic.entities.characters.classes.CharacterClass;
import doharm.logic.entities.characters.classes.CharacterClassType;

/**
 * A ranger. Excels in ranged attack. Starts off with a bow.
 * 
 * @author Roland
 */

public class Ranger extends CharacterClass
{

	public Ranger(Character character) 
	{
		super(character,CharacterClassType.RANGER);
		
		setAttributes(new RangerAttributes());
		setLevelupAttributes(new RangerLevelupAttributes());
		
	}

}
