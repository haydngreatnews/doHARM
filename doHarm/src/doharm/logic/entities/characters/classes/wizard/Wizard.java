package doharm.logic.entities.characters.classes.wizard;

import doharm.logic.entities.characters.Character;
import doharm.logic.entities.characters.classes.CharacterClass;
import doharm.logic.entities.characters.classes.CharacterClassType;

public class Wizard extends CharacterClass
{

	public Wizard(Character character) 
	{
		super(character,CharacterClassType.WIZARD);
		
		setAttributes(new WizardAttributes());
		setLevelupAttributes(new WizardLevelupAttributes());
		
	}

}
