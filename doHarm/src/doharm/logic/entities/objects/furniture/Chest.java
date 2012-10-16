package doharm.logic.entities.objects.furniture;

import doharm.logic.entities.items.Item;
import doharm.logic.entities.objects.GameObject;
import doharm.logic.entities.objects.ObjectType;
import doharm.logic.inventory.ItemContainer;
import doharm.logic.inventory.Stash;
import doharm.logic.world.tiles.Tile;

public class Chest extends GameObject implements ItemContainer
{
	private static final int NUM_ROWS = 6;
	private static final int NUM_COLS = 10;
	private Stash stash;
	public Chest() 
	{
		super(ObjectType.CHEST, "chest_closed.png");
		stash = new Stash(NUM_ROWS, NUM_COLS);
	}
	
	public Stash getStash()
	{
		return stash;
	}

	@Override
	public boolean pickup(Item item) {
		return stash.pickup(item);
	}

	@Override
	public boolean drop(Item item, ItemContainer destination) {
		return stash.drop(item, destination);
	}

	@Override
	public void dropAll(Tile dropTile) {
		stash.dropAll(dropTile);
	}

	@Override
	public void deleteItem(Item item) {
		stash.deleteItem(item);
	}
}
