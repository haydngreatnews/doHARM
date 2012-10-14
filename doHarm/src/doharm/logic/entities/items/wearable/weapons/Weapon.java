package doharm.logic.entities.items.wearable.weapons;

import doharm.logic.entities.inventory.SlotType;
import doharm.logic.entities.items.wearable.WearableItem;




public abstract class Weapon extends WearableItem
{
	private float damage;
	private float attackSpeed;
	
	public Weapon() 
	{
		super(SlotType.WEAPON);
		
	}

}
