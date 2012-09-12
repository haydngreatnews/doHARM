package doharm.logic.gameobjects.entities.characters;

import doharm.logic.gameobjects.entities.Entity;
import doharm.logic.gameobjects.entities.characters.attributes.Level;
import doharm.logic.gameobjects.entities.characters.attributes.classes.CharacterClass;

public abstract class Character extends Entity
{
	private int id; //unique id used for networking
	private String name;
	private CharacterClass characterClass;
	private Level level;
	
	protected Character(String name, int id) 
	{
		
	}
	
	
	
	
	
}
