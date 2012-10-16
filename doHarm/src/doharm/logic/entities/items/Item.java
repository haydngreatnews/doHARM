package doharm.logic.entities.items;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import doharm.logic.entities.AbstractEntity;
import doharm.logic.entities.EntityType;
import doharm.logic.entities.characters.Character;
import doharm.logic.entities.characters.classes.attributes.Attributes;
import doharm.logic.physics.Vector;
import doharm.logic.world.Layer;
import doharm.logic.world.tiles.Tile;

/**
 * 
 * Uses the visitor pattern 
 * 
 * An item is an entity as it can be dropped on the ground
 * 
 * 
 * @author bewickrola
 */


public abstract class Item extends AbstractEntity
{
	private Attributes minimumAttributes;
	
	private ItemQuality quality;
	private Dimension stashSize; //in grid units.
	
	
	private ItemType itemType;

	private boolean onGround;
	private boolean unique;
	
	
	
	protected Item(ItemType type, String imageName)
	{
		super(EntityType.ITEM);
		this.itemType = type;
		setSize(new Dimension(8,8));
		onGround = false;
		stashSize = new Dimension(1, 1);
		loadImage(imageName);
	}
	
	public boolean canUse(Character character)
	{
		return true;
	}
	
	@Override
	public void loadImage(String imageName)
	{
		super.loadImage("res/items/"+imageName);
	}
	
	@Override
	public void setPosition(float x, float y, Layer layer)
	{
		if (!onGround)
			return;
		
		Tile old = getCurrentTile();
		super.setPosition(x, y, layer);
		if (old == null || old == getCurrentTile())
			return;
		
		old.drop(this, getCurrentTile());
	}
	
	@Override
	public void process()
	{
		super.process();
		
		if (!onGround)
			return;
		
		for (Item item: getCurrentTile().getItems())
		{
			if (item == this || !item.onGround)
				continue;
			
			float distance = distanceTo(item);
			
			if (distance < getSize().width*getSize().height*2)
			{
				Vector direction = item.getPosition().subtract(getPosition());
				
				if (direction.getLength() == 0)
				{
					direction.set((float)Math.random(), (float)Math.random());
					if (direction.getLength() == 0)
						direction.set(1,0);
				}
				direction.normalize();
				direction.multiply(1);
				
				Vector velocity = item.getVelocity();
				velocity.add(direction.getX(), direction.getY());
				item.setVelocity(velocity);
				
				
				velocity = getVelocity();
				velocity.add(-direction.getX(), -direction.getY());
				
				setVelocity(velocity);
			}
			
		}
		
	}
	
	@Override
	public void spawn(Tile spawnTile)
	{
		super.spawn(spawnTile);
	}
	
	@Override 
	public String toString()
	{
		return "Item["+itemType.toString()+"]";
	}
	
	public boolean isUnique()
	{
		return unique;
	}
	public void setUnique(boolean unique)
	{
		this.unique = unique;
	}
	
	public ItemType getItemType()
	{
		return itemType;
	}

	public Attributes getMinimumAttributes() {
		return minimumAttributes;
	}

	public void setMinimumAttributes(Attributes minimumAttributes) {
		this.minimumAttributes = minimumAttributes;
	}

	public Dimension getStashSize() {
		return stashSize;
	}

	public void setStashSize(Dimension stashSize) {
		this.stashSize = stashSize;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public boolean isOnGround() 
	{
		return onGround;
	}
	public void setOnGround(boolean onGround)
	{
		this.onGround = onGround;
	}

	public ItemQuality getQuality() 
	{
		return quality;
	}

	public void setQuality(ItemQuality quality) 
	{
		this.quality = quality;
	}
	
	
	
	
	
	
}
