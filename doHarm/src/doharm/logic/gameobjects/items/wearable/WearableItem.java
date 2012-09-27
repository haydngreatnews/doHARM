package doharm.logic.gameobjects.items.wearable;

import doharm.logic.gameobjects.entities.inventory.SlotType;
import doharm.logic.gameobjects.items.Item;
import doharm.logic.gameobjects.items.ItemType;

public abstract class WearableItem extends Item
{
	private SlotType slotType;
	private float durability;
	private float decayRate;
	
	
	public WearableItem(SlotType slotType, int width, int height, int imageID) 
	{
		super(ItemType.WEARABLE, width, height, imageID);
	}
	
	public SlotType getSlotType()
	{
		return slotType;
	}
	
}
