package doharm.logic.entities.characters.classes.spider;

import java.awt.Dimension;

import doharm.logic.entities.characters.Character;
import doharm.logic.entities.characters.classes.CharacterClass;
import doharm.logic.entities.characters.classes.CharacterClassType;

public class Spider extends CharacterClass
{
	public Spider(Character character)
	{
		super(character,CharacterClassType.DRAGON);
		
		setAttributes(new SpiderAttributes());
		setLevelupAttributes(new SpiderLevelupAttributes());
		setSize(new Dimension(20,20));
	}
}
