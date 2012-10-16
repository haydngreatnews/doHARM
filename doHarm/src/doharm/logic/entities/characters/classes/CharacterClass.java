package doharm.logic.entities.characters.classes;

import java.awt.Dimension;

import doharm.logic.chat.Message;
import doharm.logic.chat.MessagePart;
import doharm.logic.chat.Taunts;
import doharm.logic.entities.characters.Character;
import doharm.logic.entities.characters.classes.attributes.AttributeType;
import doharm.logic.entities.characters.classes.attributes.Attributes;
import doharm.logic.entities.characters.classes.attributes.LevelupAttributes;

/**
 * A character class stores all the properties of a character - their attributes 
 * @author Roland
 */

public class CharacterClass 
{
	private Attributes attributes;
	private LevelupAttributes levelupAttributes;
	private static final int INITIAL_EXPERIENCE = 10;
	private static final int ATTRIBUTEPOINTS_PER_LEVEL = 3;
	
	private float experience;
	private float experienceToAdd;
	private float lastLevelExperience;
	private float nextLevelExperience;
	private int level;
	
	private Character character;
	private int attributePoints;
	private CharacterClassType classType;
	private Dimension size = new Dimension();
	
	
	
	
	public CharacterClass(Character character, CharacterClassType classType)
	{
		this.character = character;
		this.classType = classType;
		
		experience = INITIAL_EXPERIENCE;
		lastLevelExperience = INITIAL_EXPERIENCE;
		nextLevelExperience = INITIAL_EXPERIENCE*2;
		experienceToAdd = 0;
		level = 1;
		
		attributePoints = ATTRIBUTEPOINTS_PER_LEVEL;
		
	}
	
	public void setSize(Dimension size)
	{
		this.size = size;
	}
	
	public void process()
	{
		if (experienceToAdd > 0)
		{
			float delayedExperience = Math.max(experienceToAdd * 0.075f,0.01f);
			experienceToAdd = Math.max(experienceToAdd-delayedExperience,0);
			
			experience += delayedExperience;
			if (experience >= nextLevelExperience)
			{
				levelup();
			}
		}
	}
	
	public Attributes getAttributes()
	{
		return attributes;
	}
	
	public void setAttributes(Attributes attributes)
	{
		this.attributes = attributes;
	}
	public void setLevelupAttributes(LevelupAttributes attributes)
	{
		this.levelupAttributes = attributes;
	}
	
	public int getLevel()
	{
		return level;
	}
	public float getExperience()
	{
		return experience;
	}
	public float getNextLevelExperience()
	{
		return nextLevelExperience;
	}
	
	public void addExperience(float experience)
	{
		experienceToAdd += experience;
		
	}
	
	private void levelup()
	{
		lastLevelExperience = nextLevelExperience;
		nextLevelExperience = (nextLevelExperience+1)*2;
		level++;
		attributes.levelup(levelupAttributes,character);
		attributePoints += ATTRIBUTEPOINTS_PER_LEVEL + level;
	}

	public int getAttributePoints()
	{
		return attributePoints;
	}
	
	public void addPoint(AttributeType type)
	{
		if (attributePoints <= 0)
		{
			throw new UnsupportedOperationException("No attribute points left");
		}
		switch(type)
		{
		case DEXTERITY:
			attributes.increaseDexterity();
			break;
		case INTELLIGENCE:
			attributes.increaseIntelligence();
			break;
		case STRENGTH:
			attributes.increaseStrength();
			break;
		case VITALITY:
			attributes.increaseVitality();
			break;
		default:
			throw new UnsupportedOperationException("Unknown AttributePointType: " + type);
		}
		
		if (character.isHumanPlayer())
		{
			character.getWorld().addMessage(new Message(character.getID(), false, new MessagePart(type.toString() + " increased!",type.getColour())));
		}
		
		attributePoints--;
	}

	public float getExperienceRatio() 
	{
		float relativeExperience = experience - lastLevelExperience;
		float difference = nextLevelExperience-lastLevelExperience;
		
		float ratio = relativeExperience / difference;
		return ratio;
	}
	
	
	

	public CharacterClassType getClassType() {
		return classType;
	}

	public Dimension getSize() 
	{
		return size;
	}
}
