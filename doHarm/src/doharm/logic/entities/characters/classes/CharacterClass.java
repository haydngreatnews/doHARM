package doharm.logic.entities.characters.classes;

import doharm.logic.chat.Taunts;
import doharm.logic.entities.AbstractEntity;
import doharm.logic.entities.characters.classes.attributes.Attributes;
import doharm.logic.entities.characters.classes.attributes.LevelupAttributes;
import doharm.logic.entities.characters.Character;

public class CharacterClass 
{
	private Attributes attributes;
	private LevelupAttributes levelupAttributes;
	private static final int INITIAL_EXPERIENCE = 10;
	
	private float experience;
	private float experienceToAdd;
	private float lastLevelExperience;
	private float nextLevelExperience;
	private int level;
	private Taunts taunts;
	private Character character;
	
	
	
	
	public CharacterClass(Character character, CharacterClassType type)
	{
		this.character = character;
		experience = INITIAL_EXPERIENCE;
		lastLevelExperience = INITIAL_EXPERIENCE;
		nextLevelExperience = INITIAL_EXPERIENCE*2;
		experienceToAdd = 0;
		level = 1;
		taunts = new Taunts(character);
	}
	
	public void process()
	{
		if (experienceToAdd > 0)
		{
			float delayedExperience = Math.max(experienceToAdd * 0.075f,0.01f);
			experienceToAdd = Math.max(experienceToAdd-delayedExperience,0);
			
			experience += delayedExperience;
			if (experience > nextLevelExperience)
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
	
	public void addExperience(int amount)
	{
		experienceToAdd += amount;
		
	}
	
	private void levelup()
	{
		lastLevelExperience = nextLevelExperience;
		nextLevelExperience = (nextLevelExperience+1)*2;
		level++;
		attributes.levelup(levelupAttributes,character);
	}


	public float getExperienceRatio() 
	{
		float relativeExperience = experience - lastLevelExperience;
		float difference = nextLevelExperience-lastLevelExperience;
		
		float ratio = relativeExperience / difference;
		return ratio;
	}
	
	

	public Taunts getTaunts() {
		return taunts;
	}
}
