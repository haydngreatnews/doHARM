package doharm.logic.entities.items;

import doharm.logic.entities.AbstractEntityFactory;
import doharm.logic.entities.EntityFactory;
import doharm.logic.entities.inventory.ItemContainer;
import doharm.logic.entities.items.wearable.Weapon;
import doharm.logic.entities.items.wearable.temp.Sword;
import doharm.logic.world.World;

public class ItemFactory extends AbstractEntityFactory<Item>
{

	public ItemFactory(World world, EntityFactory observer) 
	{
		super(world, observer);
	}
	
	/**
	 * Add an item to a container.
	 * @param type
	 * @param container
	 */
	public void AddItem(ItemType type, int id, ItemContainer container)
	{
		Item item = new Sword();
		addEntity(item, id);
	}
	
	public void RemoveItem(Item item)
	{
		removeEntity(item);
	}
	
	

}
