package doharm.logic.entities.items.wearable;

import doharm.logic.entities.items.Item;
import doharm.logic.entities.items.ItemType;
import doharm.logic.inventory.SlotType;

public abstract class WearableItem extends Item
{
	private SlotType slotType;
	private float durability;
	private float decayRate;
	
	
	public WearableItem(SlotType slotType) 
	{
		super(ItemType.WEARABLE);
		this.slotType = slotType;
	}
	
	public SlotType getSlotType()
	{
		return slotType;
	}
	
}
