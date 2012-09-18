package doharm.logic.gameobjects.entities.characters;

import doharm.logic.gameobjects.entities.Entity;
import doharm.logic.gameobjects.entities.characters.attributes.Level;
import doharm.logic.gameobjects.entities.characters.attributes.classes.CharacterClass;
import doharm.logic.world.Tile;

public abstract class Character extends Entity
{
	private int id; //unique id used for networking
	private String name;
	private CharacterClass characterClass;
	private Level level;
	
	protected Character(Tile spawnTile, String name, int id) 
	{
		super(spawnTile);
	}
	
	
	
	
	
}
