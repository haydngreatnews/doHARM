package doharm.logic.entities.items.misc;

import doharm.logic.entities.items.Item;
import doharm.logic.entities.items.ItemType;

public abstract class MiscItem extends Item
{
	private MiscItemType miscItemType;

	protected MiscItem(MiscItemType miscItemType, String imageName) 
	{
		super(ItemType.MISC,imageName);
		this.miscItemType = miscItemType;
	}

	public MiscItemType getMiscItemType() {
		return miscItemType;
	}

	
}
