package doharm.logic.entities.characters.monsters;

import java.awt.Color;

import doharm.logic.entities.AbstractEntityFactory;
import doharm.logic.entities.EntityFactory;
import doharm.logic.entities.characters.classes.CharacterClassType;
import doharm.logic.world.World;

public class MonsterFactory extends AbstractEntityFactory<Monster>
{
	public MonsterFactory(World world, EntityFactory entityFactory) 
	{
		super(world,entityFactory);
	}
	
	public Monster createMonster(CharacterClassType classType, int id, boolean fromNetwork)
	{
		Monster monster = new Monster();
		
		//Set important variables for each type of player
		monster.setName(classType.toString());
		monster.setWorld(getWorld());
		monster.setCharacterClass(classType);
		monster.setColour(Color.white);
		monster.spawnEventually();
		
		
		//add this player to the global list of entities.
		addEntity(monster,id,fromNetwork);
		return monster;
	}
	
}
