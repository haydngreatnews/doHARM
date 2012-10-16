package doharm.logic.entities.characters.classes.spider;

import doharm.logic.entities.characters.classes.CharacterClass;
import doharm.logic.entities.characters.classes.CharacterClassType;
import doharm.logic.entities.characters.classes.ranger.RangerAttributes;
import doharm.logic.entities.characters.classes.ranger.RangerLevelupAttributes;
import doharm.logic.entities.characters.monsters.Monster;
import doharm.logic.entities.characters.Character;

public class Spider extends CharacterClass
{
	public Spider(Character character)
	{
		super(character,CharacterClassType.DRAGON);
		
		setAttributes(new SpiderAttributes());
		setLevelupAttributes(new SpiderLevelupAttributes());
	}
}
