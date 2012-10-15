package doharm.logic.entities.items.usable;

import doharm.logic.entities.items.Item;
import doharm.logic.entities.items.ItemType;

public class UsableItem extends Item
{
	private UsableItemType usableItemType;

	protected UsableItem(UsableItemType usableItemType) 
	{
		super(ItemType.USABLE);
		this.usableItemType = usableItemType;
	}
	
	public UsableItemType getUsableItemType()
	{
		return usableItemType;
	}
}
