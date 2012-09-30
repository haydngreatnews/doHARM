package doharm.logic.gameobjects.items.wearable;

import doharm.logic.gameobjects.entities.inventory.SlotType;




public abstract class Weapon extends WearableItem
{

	public Weapon(int width, int height, int imageID) 
	{
		super(SlotType.WEAPON, width, height, imageID);
		
	}

}
