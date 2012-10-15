package doharm.logic.inventory;

import doharm.logic.entities.items.Item;
import doharm.logic.entities.items.ItemType;

public class Belt extends Stash
{
	private static final int MAX_ITEM_AREA = 1;
	private static final int NUM_ROWS = 1;
	private static final int NUM_COLS = 8;
	
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
	
}
