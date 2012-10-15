package doharm.logic.entities.characters.classes.attributes;

import java.awt.Color;

/**
 * points can be used to increase one of the below attributes.
 * 
 * @author Roland
 */

public enum AttributeType 
{
	INTELLIGENCE, //mana
	VITALITY, //health
	STRENGTH, //damage
	DEXTERITY; //accuracy and movement speed

	public Color getColour() 
	{
		switch(this)
		{
		case DEXTERITY:
			return Color.green;
		case INTELLIGENCE:
			return Color.blue;
		case STRENGTH:
			return Color.yellow;
		case VITALITY:
			return Color.red;
		default:
			throw new UnsupportedOperationException("Unknown AttributePointType: " + this);
		}
	}
	
	/**
	 * @return a string that describes this attribute type.
	 */
	public String getDescription() 
	{
		switch(this)
		{
		case DEXTERITY:
			return "Increases accuracy, movement speed and attack rate";
		case INTELLIGENCE:
			return "Increases maximum mana and mana regeneration";
		case STRENGTH:
			return "Increases attack damage";
		case VITALITY:
			return "Increases maximum health and health regeneration";
		default:
			throw new UnsupportedOperationException("Unknown AttributePointType: " + this);
		}
	}
}
