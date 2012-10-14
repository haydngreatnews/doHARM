package doharm.logic.entities.items.misc;

import doharm.logic.entities.items.Item;
import doharm.logic.entities.items.ItemType;

public abstract class MiscItem extends Item
{
	private MiscItemType miscItemType;

	protected MiscItem(MiscItemType miscItemType) 
	{
		super(ItemType.MISC);
		this.miscItemType = miscItemType;
	}

	
}
