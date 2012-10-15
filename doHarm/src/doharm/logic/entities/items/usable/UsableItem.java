package doharm.logic.entities.items.usable;

import doharm.logic.chat.Message;
import doharm.logic.chat.MessagePart;
import doharm.logic.entities.characters.Character;
import doharm.logic.entities.items.Item;
import doharm.logic.entities.items.ItemType;

public abstract class UsableItem extends Item
{
	private UsableItemType usableItemType;
	
	protected abstract boolean use(Character character);

	protected UsableItem(UsableItemType usableItemType) 
	{
		super(ItemType.USABLE);
		this.usableItemType = usableItemType;
	}
	
	public UsableItemType getUsableItemType()
	{
		return usableItemType;
	}
	
	public void useItem(Character character)
	{
		if (!isAlive())
			return;
		
		if (!use(character))
		{
			getWorld().addMessage(new Message(-1, false, new MessagePart("Could not use " + usableItemType.toString())));
			return;
		}
		
		character.getInventory().deleteItem(this);
		getWorld().addMessage(new Message(-1, false, new MessagePart("Used " + usableItemType.toString())));
		die();
	}

	
	
}
