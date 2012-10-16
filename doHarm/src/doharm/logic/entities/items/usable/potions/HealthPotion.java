package doharm.logic.entities.items.usable.potions;

import doharm.logic.entities.characters.Character;
import doharm.logic.entities.items.usable.UsableItem;
import doharm.logic.entities.items.usable.UsableItemType;

public class HealthPotion extends UsableItem
{
	public HealthPotion() 
	{
		super(UsableItemType.HEALTH_POTION, "hp.png");
	}

	@Override
	protected boolean use(Character character) 
	{
		if (character.getHealthRatio() > 0.95f)
			return false;
		
		switch(getQuality())
		{
			default:
				character.increaseHealth(character.getMaxHealth());
				break;
		}
		return true;
	}

}
