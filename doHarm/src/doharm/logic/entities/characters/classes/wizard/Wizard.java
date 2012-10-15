package doharm.logic.entities.characters.classes.wizard;

import doharm.logic.entities.characters.Character;
import doharm.logic.entities.characters.classes.CharacterClass;
import doharm.logic.entities.characters.classes.CharacterClassType;

/**
 * The wizard is weak but uses his vast intelligence to overcome his foes.
 * Starts with a staff. Right click to cast spell
 * 
 * @author Roland
 */

public class Wizard extends CharacterClass
{
	public Wizard(Character character) 
	{
		super(character,CharacterClassType.WIZARD);
		
		setAttributes(new WizardAttributes());
		setLevelupAttributes(new WizardLevelupAttributes());
		
	}

}
