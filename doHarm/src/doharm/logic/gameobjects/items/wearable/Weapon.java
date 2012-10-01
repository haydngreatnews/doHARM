package doharm.logic.gameobjects.items.wearable;

import doharm.logic.gameobjects.entities.inventory.SlotType;




public abstract class Weapon extends WearableItem
{
	private float damage;
	private float attackSpeed;
	
	public Weapon(int width, int height, int imageID) 
	{
		super(SlotType.WEAPON, width, height, imageID);
		
	}

}
