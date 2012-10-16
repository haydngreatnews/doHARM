package doharm.logic.entities.items;

import java.awt.Dimension;


import doharm.logic.entities.AbstractEntity;
import doharm.logic.entities.EntityType;
import doharm.logic.entities.characters.classes.attributes.Attributes;
import doharm.logic.entities.characters.Character;
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
	private Dimension stashSize; 
	private ItemImage inventoryImage;
	private ItemImage droppedImage;
	private ItemImage holdingImage;
	
	
	private ItemType itemType;

	private boolean onGround;
	private boolean unique;
	
	public boolean canUse(Character character)
	{
		return true;
	}
	
	protected Item(ItemType type)
	{
		super(EntityType.ITEM);
		this.itemType = type;
		setSize(new Dimension(6,6));
		onGround = false;
		stashSize = new Dimension(1, 1);
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

	public ItemImage getInventoryImage() {
		return inventoryImage;
	}

	public void setInventoryImage(ItemImage inventoryImage) {
		this.inventoryImage = inventoryImage;
	}

	public ItemImage getDroppedImage() {
		return droppedImage;
	}

	public void setDroppedImage(ItemImage droppedImage) {
		this.droppedImage = droppedImage;
	}

	public ItemImage getHoldingImage() {
		return holdingImage;
	}

	public void setHoldingImage(ItemImage holdingImage) {
		this.holdingImage = holdingImage;
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
