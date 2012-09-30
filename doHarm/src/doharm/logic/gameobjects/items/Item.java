package doharm.logic.gameobjects.items;

import doharm.logic.gameobjects.entities.characters.attributes.Attributes;

/**
 * 
 * Uses the visitor pattern 
 * 
 * 
 * @author bewickrola
 */


public abstract class Item 
{
	private Attributes minimumAttributes;
	private int width; //in stash
	private int height; //in stash
	private int imageID;
	private ItemType itemType;
	
	
	/*public boolean canUse(Player player)
	{
		
	}*/
	
	public Item(ItemType type, int width, int height, int imageID)
	{
		this.itemType = type;
		this.width = width;
		this.height = height;
		this.imageID = imageID; 
	}
	
	public ItemType getItemType()
	{
		return itemType;
	}

	public int getHeight() {
		return height; //TODO
	}

	public int getWidth() {
		return width; //TODO
	}
	
	/**
	 * NOTE: this will map to two different images (Each in an array):
	 * -The inventory image, and
	 * -The world image (when the item is lying on the ground)
	 * 
	 * @return the the image id of this item.
	 */
	public int getImageID()
	{
		return imageID;
	}
}
