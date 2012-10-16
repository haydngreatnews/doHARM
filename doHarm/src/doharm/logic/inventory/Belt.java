package doharm.logic.inventory;

import doharm.logic.entities.items.Item;
import doharm.logic.entities.items.ItemQuality;
import doharm.logic.entities.items.ItemType;
import doharm.logic.entities.items.usable.UsableItem;
import doharm.logic.entities.items.usable.UsableItemType;
import doharm.logic.world.World;

public class Belt extends Stash
{
	private static final int MAX_ITEM_AREA = 1;
	public static final int NUM_ROWS = 1;
	public static final int NUM_COLS = 8;
	
	public Belt()
	{
		super(NUM_ROWS, NUM_COLS);
		
		
		
	}
	
	@Override
	public boolean pickup(Item item)
	{
		if (item.getItemType() != ItemType.USABLE)
			return false;
		
		if (item.getSize().width > MAX_ITEM_AREA || item.getSize().height > MAX_ITEM_AREA)
			return false;
		return super.pickup(item);
	}

	public UsableItem getItem(int i) 
	{
		if (i < 0 || i >= NUM_COLS)
			throw new UnsupportedOperationException("Accessing outside belt slot range!");
		
		Item item = getItems()[0][i];
		if (item != null)
		{
			return (UsableItem)item;
		}
		return null;
	}
	
}
