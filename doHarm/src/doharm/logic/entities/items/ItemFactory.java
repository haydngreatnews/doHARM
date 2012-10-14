package doharm.logic.entities.items;

import doharm.logic.entities.AbstractEntityFactory;
import doharm.logic.entities.EntityFactory;
import doharm.logic.entities.inventory.DragonRadar;
import doharm.logic.entities.inventory.ItemContainer;
import doharm.logic.entities.items.misc.DragonBall;
import doharm.logic.entities.items.misc.MiscItemType;
import doharm.logic.world.World;

public class ItemFactory extends AbstractEntityFactory<Item>
{
	private DragonRadar dragonRadar;
	private int dragonBallStar;
	
	public ItemFactory(World world, EntityFactory observer, DragonRadar dragonRadar) 
	{
		super(world, observer);
		this.dragonRadar = dragonRadar;
	}
	
	public void setDragonBallStar(int star)
	{
		dragonBallStar = star;
	}
	
	/**
	 * Add an item to a container.
	 * @param type
	 * @param container where to add the item to (could be a tile, player inventory, chest, etc.)
	 * @return the created item
	 */
	public Item createItem(ItemType type, int subtypeOrdinal, ItemQuality quality, int id, ItemContainer container, boolean fromNetwork)
	{
		Item item = null;
		switch (type)
		{
		case MISC:
			item = createMiscItem(subtypeOrdinal, quality);
			break;
		default:
			throw new UnsupportedOperationException("ItemType not implemented");
		}
		
		
		addEntity(item, id,fromNetwork);
		return item;
	}
	
	private Item createMiscItem(int subtypeOrdinal, ItemQuality quality) 
	{
		MiscItemType type = MiscItemType.values()[subtypeOrdinal];
		Item item = null;
		switch (type)
		{
		case COIN:
			//item = new Coin();
			break;
		case DRAGONBALL:	
			item = new DragonBall(dragonBallStar);
			
			dragonRadar.add((DragonBall)item);
			break;
		default:
			throw new UnsupportedOperationException("MiscItemType not implemented");
		}
		
		return item;
	}

	public void removeItem(Item item)
	{
		removeEntity(item);
	}
	
	

}
