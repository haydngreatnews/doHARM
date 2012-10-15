package doharm.logic.entities.inventory;

import doharm.logic.entities.items.Item;
import doharm.logic.world.tiles.Tile;

/**
 * This interface is simply a container for items. 
 * Items can be dropped or picked up.
 * @author bewickrola
 */
public interface ItemContainer 
{
	public boolean pickup(Item item);
	public boolean drop(Item item, ItemContainer destination);
}
