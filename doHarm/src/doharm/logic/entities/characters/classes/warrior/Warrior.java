package doharm.logic.entities.characters.classes.warrior;

import doharm.logic.entities.AbstractEntity;
import doharm.logic.entities.characters.classes.CharacterClass;
import doharm.logic.entities.characters.classes.CharacterClassType;

public class Warrior extends CharacterClass
{

	public Warrior(AbstractEntity e,CharacterClassType characterType) 
	{
		super(e,characterType);
		
		setAttributes(new WarriorAttributes());
		setLevelupAttributes(new WarriorLevelupAttributes());
		
	}

}
