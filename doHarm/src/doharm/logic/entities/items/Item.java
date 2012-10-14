package doharm.logic.entities.items;

import java.awt.Dimension;


import doharm.logic.entities.AbstractEntity;
import doharm.logic.entities.EntityType;
import doharm.logic.entities.characters.classes.attributes.Attributes;
import doharm.logic.entities.characters.Character;

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
	
	private Dimension stashSize; 
	private ItemImage inventoryImage;
	private ItemImage droppedImage;
	private ItemImage holdingImage;
	
	private ItemType itemType;
	
	public boolean canUse(Character character)
	{
		return true;
	}
	
	protected Item(ItemType type)
	{
		super(EntityType.ITEM);
		this.itemType = type;
		
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

	
	
	
	
	
	
}
