package doharm.logic.gameobjects.entities.inventory;

import java.util.ArrayList;
import java.util.List;

import doharm.logic.gameobjects.items.Item;
import doharm.logic.gameobjects.items.ItemType;
import doharm.logic.gameobjects.items.wearable.WearableItem;

public class Inventory 
{
	public static final int STASH_ROWS = 6;
	public static final int STASH_COLS = 10;
	
	private Item[] slots;
	private Item[][] stash;
	

	public Inventory()
	{
		slots= new Item[SlotType.values().length];
		stash = new Item[STASH_ROWS][STASH_COLS];
	}
	
	
	/**
	 * @param item
	 * @return true if the item was picked up. Otherwise print inventory full!
	 */
	public boolean pickup(Item item)
	{
		//First check if can add to a slot.
		
		if (item.getItemType() == ItemType.WEARABLE)
		{
			addItemToSlot((WearableItem)item);
			return true;
		}
		
		return addItemToStash(item);
	}

	public void reorderStash()
	{
		//TODO
	}

	private boolean addItemToStash(Item item) 
	{
		
		for (int row = 0; row < STASH_ROWS; row++)
		{
			for (int col = 0; col < STASH_COLS; col++)
			{
				if (addItemToStash(item, row,col))
					return true;
			}
		}
		
		return false;
	}


	private boolean addItemToStash(Item item, int row, int col) 
	{
		for (int y = row; y < row+item.getHeight(); y++)
		{
			for (int x = col; x < col+item.getWidth(); x++)
			{
				if (y >= STASH_ROWS || x >= STASH_COLS)
					return false;
				if (stash[y][x] != null)
					return false;
			}
		}
		
		stash[row][col] = item;
		return true;
	}


	private boolean addItemToSlot(WearableItem item) 
	{
		//special case for weapon slots
		if (item.getSlotType() == SlotType.WEAPON || item.getSlotType() == SlotType.WEAPON2)
		{
			
			//TODO two handed weapons and shield in slot 2
			if (slots[SlotType.WEAPON.ordinal()] == null)
			{
				slots[SlotType.WEAPON.ordinal()] = item;
				return true;
			}
			else if (slots[SlotType.WEAPON2.ordinal()] == null)
			{
				slots[SlotType.WEAPON2.ordinal()] = item;
				return true;
			}
		}
		else if (item.getSlotType() == SlotType.RING1)
		{
			if (slots[SlotType.RING1.ordinal()] == null)
			{
				slots[SlotType.RING1.ordinal()] = item;
				return true;
			}
			else if (slots[SlotType.RING2.ordinal()] == null)
			{
				slots[SlotType.RING2.ordinal()] = item;
				return true;
			}
		}
		else if (slots[item.getSlotType().ordinal()] == null)
		{
			slots[item.getSlotType().ordinal()] = item;
			return true;
		}
		
		
		return false;
	}
	
}
