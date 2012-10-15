package doharm.logic.inventory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import doharm.logic.entities.items.Item;
import doharm.logic.world.tiles.Tile;

/**
 * This class stores a set of items.
 * For example, a bookcase can have a set of items. Also tiles contain a set of items...
 * @author bewickrola
 */
public class ItemSet implements ItemContainer
{
	private Set<Item> items;
	
	public ItemSet()
	{
		items = new HashSet<Item>();
	}

	@Override
	public boolean pickup(Item item) {
		return items.add(item); //will always return true! as items are unique
	}

	@Override
	public boolean drop(Item item, ItemContainer destination) 
	{
		if (destination.pickup(item))
		{
			deleteItem(item);
			return true;
		}
		return false;
	}

	@Override
	public void deleteItem(Item item) 
	{
		items.remove(item);
	}

	@Override
	public void dropAll(Tile dropTile) 
	{
		while (!items.isEmpty())
		{
			Iterator<Item> it = items.iterator();
			drop(it.next(), dropTile);
		}
	}

	
	
	
	
}
