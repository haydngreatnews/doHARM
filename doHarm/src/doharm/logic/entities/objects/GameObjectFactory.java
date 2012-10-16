package doharm.logic.entities.objects;

import doharm.logic.entities.AbstractEntityFactory;
import doharm.logic.entities.EntityFactory;
import doharm.logic.entities.objects.doors.Door;
import doharm.logic.entities.objects.furniture.Chest;
import doharm.logic.entities.objects.scenery.Tree;
import doharm.logic.world.World;
import doharm.logic.world.tiles.Tile;

public class GameObjectFactory extends AbstractEntityFactory<GameObject>
{
	public GameObjectFactory(World world, EntityFactory entityFactory) 
	{
		super(world,entityFactory);
	}
	
	public GameObject createObject(ObjectType type, Tile spawnPos, int id, boolean fromNetwork)
	{
		GameObject object = null;
		switch(type)
		{
		case CHEST:
			object = new Chest();
			break;
		case DOOR:
			object = new Door();
			break;
		case TREE:
			object = new Tree();
			break;
		default:
			throw new UnsupportedOperationException("Unknown GameObjectType");
		}

		object.spawn(spawnPos);
		//add this entity to the global list of entities.
		addEntity(object,id,fromNetwork);
		return object;
	}
	
}
