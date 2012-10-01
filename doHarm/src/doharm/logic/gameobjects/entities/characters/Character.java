package doharm.logic.gameobjects.entities.characters;

import doharm.logic.gameobjects.entities.Entity;
import doharm.logic.gameobjects.entities.characters.attributes.Level;
import doharm.logic.gameobjects.entities.characters.classes.CharacterClass;
import doharm.logic.gameobjects.entities.characters.classes.CharacterClassType;
import doharm.logic.gameobjects.entities.characters.classes.Warrior;
import doharm.logic.gameobjects.entities.inventory.Inventory;
import doharm.logic.world.tiles.Tile;

public abstract class Character extends Entity
{
	private int id; //unique id used for networking
	private String name;
	private CharacterClass characterClass;
	private Level level;
	private Inventory inventory;
	
	protected Character(Tile spawnTile, String name, CharacterClassType classType, int id) 
	{
		super(spawnTile);
		
		switch(classType)
		{
		case WARRIOR:
			characterClass = new Warrior();
			break;
		default:
			throw new UnsupportedOperationException("Character class type not implemented: " + classType);
		}
		
		inventory = new Inventory();
		
	}

	public Inventory getInventory()
	{
		return inventory;
	}
	
	
	public void Process()
	{
		
	}
	
	
	
}
