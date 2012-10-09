package doharm.logic.entities.characters.classes;

import doharm.logic.entities.AbstractEntity;
import doharm.logic.entities.characters.classes.attributes.Attributes;
import doharm.logic.entities.characters.classes.attributes.LevelupAttributes;


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
	
	
	
	public CharacterClass(AbstractEntity entity, CharacterClassType type)
	{
		experience = INITIAL_EXPERIENCE;
		lastLevelExperience = INITIAL_EXPERIENCE;
		nextLevelExperience = INITIAL_EXPERIENCE*2;
		experienceToAdd = 0;
		level = 1;
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
	
	public void addExperience(int amount)
	{
		experienceToAdd += amount;
		
	}
	
	private void levelup()
	{
		lastLevelExperience = nextLevelExperience;
		nextLevelExperience = (nextLevelExperience+1)*2;
		level++;
		attributes.levelup(levelupAttributes);
	}


	public float getExperienceRatio() 
	{
		float relativeExperience = experience - lastLevelExperience;
		float difference = nextLevelExperience-lastLevelExperience;
		
		float ratio = relativeExperience / difference;
		return ratio;
	}
}
