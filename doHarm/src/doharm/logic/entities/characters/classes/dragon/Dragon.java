package doharm.logic.entities.characters.classes.dragon;

import java.awt.Dimension;

import doharm.logic.entities.characters.Character;
import doharm.logic.entities.characters.classes.CharacterClass;
import doharm.logic.entities.characters.classes.CharacterClassType;

public class Dragon extends CharacterClass
{
	public Dragon(Character character)
	{
		super(character,CharacterClassType.DRAGON);
		
		setAttributes(new DragonAttributes());
		setLevelupAttributes(new DragonLevelupAttributes());
		setSize(new Dimension(64,64));
	}
}
