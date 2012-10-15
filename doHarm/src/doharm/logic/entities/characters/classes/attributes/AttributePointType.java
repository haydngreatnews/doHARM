package doharm.logic.entities.characters.classes.attributes;

import java.awt.Color;

public enum AttributePointType 
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
