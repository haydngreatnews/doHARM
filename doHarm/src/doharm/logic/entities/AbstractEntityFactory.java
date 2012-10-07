package doharm.logic.entities;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import doharm.logic.world.World;

public abstract class AbstractEntityFactory<T extends AbstractEntity>
{
	private World world;
	private Map<Integer,T> entities;
	private EntityFactory observer;

	public AbstractEntityFactory(World world, EntityFactory observer)
	{
		this.world = world;
		this.observer = observer;
		entities = new HashMap<Integer, T>();
	}
	
	public World getWorld()
	{
		return world;
	}
	
	public void addEntity(T entity, int id)
	{
		entities.put(id, entity);
		if (observer != null)
			observer.addEntity(entity,id);
	}
	public void removeEntity(T entity)
	{
		entities.remove(entity);
		if (observer != null)
			observer.removeEntity(entity);
	}
	
	public Collection<T> getEntities()
	{
		return Collections.unmodifiableCollection(entities.values());
	}
	public T getEntity(int id)
	{
		return entities.get(id);
	}
}
