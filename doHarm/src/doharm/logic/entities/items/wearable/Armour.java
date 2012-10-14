package doharm.logic.entities.items.wearable;

import doharm.logic.entities.inventory.SlotType;


/**
 * Armour permanently increases attributes while worn.
 * 
 * 
 * 
 * @author bewickrola
 */

public abstract class Armour extends WearableItem
{

	public Armour() 
	{
		super(SlotType.TORSO);
	}

}
