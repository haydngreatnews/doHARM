package doharm.logic.entities.characters.classes.troll;

import java.awt.Dimension;

import doharm.logic.entities.characters.Character;
import doharm.logic.entities.characters.classes.CharacterClass;
import doharm.logic.entities.characters.classes.CharacterClassType;

public class Troll extends CharacterClass
{
	public Troll(Character character)
	{
		super(character,CharacterClassType.DRAGON);
		
		setAttributes(new TrollAttributes());
		setLevelupAttributes(new TrollLevelupAttributes());
		setSize(new Dimension(48,48));
	}
}
