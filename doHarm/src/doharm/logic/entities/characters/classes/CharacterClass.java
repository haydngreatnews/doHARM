package doharm.logic.entities.characters.classes;

import doharm.logic.entities.AbstractEntity;
import doharm.logic.entities.characters.classes.attributes.Attributes;
import doharm.logic.entities.characters.classes.attributes.LevelupAttributes;


public class CharacterClass 
{
	private Attributes attributes;
	private LevelupAttributes levelupAttributes;
	
	private long experience;
	private long nextLevelExperience;
	private int level;
	
	
	
	public CharacterClass(AbstractEntity entity, CharacterClassType type)
	{
		
		
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
	public long getExperience()
	{
		return experience;
	}
	
	public void addExperience(int amount)
	{
		experience += amount;
		if (experience > nextLevelExperience)
		{
			levelup();
		}
	}
	
	private void levelup()
	{
		nextLevelExperience = (nextLevelExperience+1)*2;
		level++;
		attributes.levelup(levelupAttributes);
	}
	
}
