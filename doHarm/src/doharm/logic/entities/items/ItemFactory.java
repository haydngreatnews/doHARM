package doharm.logic.entities.items;

import doharm.logic.entities.AbstractEntityFactory;
import doharm.logic.entities.EntityFactory;
import doharm.logic.entities.items.misc.MiscItemType;
import doharm.logic.entities.items.misc.dragonballs.DragonBall;
import doharm.logic.entities.items.misc.dragonballs.DragonRadar;
import doharm.logic.entities.items.usable.UsableItemType;
import doharm.logic.entities.items.usable.potions.HealthPotion;
import doharm.logic.entities.items.wearable.WearableItemType;
import doharm.logic.entities.objects.furniture.Chest;
import doharm.logic.inventory.ItemContainer;
import doharm.logic.world.World;
import doharm.logic.world.tiles.Tile;

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
	 * Add a new item to a container.
	 * @param type the type of item to create, either misc, wearable or usable.
	 * @param subtype the subtype of item, eg. dragonball, sword, health potion, etc.
	 * @param quality the quality of the item
	 * @param id the unique entity id.
	 * @param container where to add the item to (could be a tile, player inventory, chest, etc.)
	 * @param fromNetwork
	 * @return the created item
	 */
	public Item createItem(ItemType type, int subtype, ItemQuality quality, int id, ItemContainer container, boolean fromNetwork)
	{
		Item item = null;
		switch (type)
		{
		case MISC:
			item = createMiscItem(subtype, quality);
			break;
		case USABLE:
			item = createUsableItem(subtype,quality);
			break;
		case WEARABLE:
			item = createWearableItem(subtype, quality);
			break;
		default:
			throw new UnsupportedOperationException("ItemType not implemented");
		}
		
		
		
		item.setQuality(quality);
		
		container.pickup(item);
		if (container instanceof Tile)
		{
			item.spawn((Tile)container);
		}
		
		
		
		addEntity(item, id,fromNetwork);
		return item;
	}
	
	private Item createWearableItem(int subtype, ItemQuality quality) 
	{
		WearableItemType type = WearableItemType.values()[subtype];
		Item item = null;
		switch (type)
		{
		//case SWORD:
			//item = new Sword();
			
			//break;
		default:
			throw new UnsupportedOperationException("UsableItemType not implemented");
		}
		
		//return item;
	}

	private Item createUsableItem(int subtype, ItemQuality quality) 
	{
		UsableItemType type = UsableItemType.values()[subtype];
		Item item = null;
		switch (type)
		{
		case HEALTH_POTION:
			item = new HealthPotion();
			
			break;
		default:
			throw new UnsupportedOperationException("UsableItemType not implemented");
		}
		
		return item;
	}

	private Item createMiscItem(int subtype, ItemQuality quality) 
	{
		MiscItemType type = MiscItemType.values()[subtype];
		Item item = null;
		switch (type)
		{
		//case COIN:
			//item = new Coin();
			//break;
		case DRAGONBALL:	
			item = new DragonBall(dragonBallStar);
			item.setUnique(true);
			
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

	public Item createRandomItem(ItemQuality minimum, int id, ItemContainer container) 
	{
		int randomOrdinal = (int)(Math.random()* (ItemQuality.values().length -1-minimum.ordinal()));
		ItemQuality quality = ItemQuality.values()[minimum.ordinal()+randomOrdinal];
		ItemType type = ItemType.values()[(int)(Math.random()*ItemType.values().length-1)];
		int subtype = 0;
		return createItem(type,subtype, quality,id,container,false);
	}
	
	

}
