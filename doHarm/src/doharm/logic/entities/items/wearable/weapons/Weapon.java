package doharm.logic.entities.items.wearable.weapons;

import doharm.logic.entities.items.wearable.WearableItem;
import doharm.logic.inventory.SlotType;




public abstract class Weapon extends WearableItem
{
	private float damage;
	private float attackSpeed;
	
	public Weapon() 
	{
		super(SlotType.WEAPON, "weapon.png");
		
	}

}
