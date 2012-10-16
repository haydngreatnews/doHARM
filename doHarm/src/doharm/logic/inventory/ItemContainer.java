package doharm.logic.inventory;

import java.util.Set;

import doharm.logic.entities.items.Item;
import doharm.logic.world.tiles.Tile;

/**
 * This interface is simply a container for items. 
 * Items can be dropped, deleted or picked up.
 * @author bewickrola
 */
public interface ItemContainer 
{
	/**
	 * 
	 * @param item the item to pickup
	 * @return true if the item was picked up
	 */
	public boolean pickup(Item item);
	
	/**
	 * 
	 * @param item the item to drop
	 * @param destination where to drop it
	 * @return true if the item was dropped.
	 */
	public boolean drop(Item item, ItemContainer destination);
	
	/**
	 * Drop all items into a tile.
	 * @param dropTile the tile to drop the items into
	 */
	public void dropAll(Tile dropTile);
	
	/**
	 * Delete an item completely from the game.
	 * @param item the item to delete
	 */
	public void deleteItem(Item item);

	
}
