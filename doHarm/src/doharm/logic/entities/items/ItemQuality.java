package doharm.logic.entities.items;

import java.awt.Color;

/**
 * Possible qualities of an item.
 * @author Roland
 */

public enum ItemQuality {
	POOR, //grey
	COMMON, //white
	UNCOMMON, //green
	RARE, //blue
	EPIC, //purple
	LEGENDARY; //orange
	
	public Color getColour()
	{
		switch(this)
		{
		case POOR:
			return Color.gray;
		case COMMON:
			return Color.white;
		case UNCOMMON:
			return Color.green;
		case RARE:
			return Color.blue;
		case EPIC:
			return Color.magenta.darker();
		case LEGENDARY:
			return Color.orange;
		default:
			throw new UnsupportedOperationException("UNKNOWN ITEM QUALITY COLOUR");
		}
		
	}
}


