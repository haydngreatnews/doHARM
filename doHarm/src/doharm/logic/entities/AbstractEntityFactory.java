package doharm.logic.entities;

import java.util.Collections;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

import doharm.logic.world.World;

public abstract class AbstractEntityFactory<T extends AbstractEntity>
{
	private World world;
	private Set<T> entities;
	private EntityFactory observer;

	public AbstractEntityFactory(World world, EntityFactory observer)
	{
		this.world = world;
		this.observer = observer;
		entities = new HashSet<T>();
	}
	
	public World getWorld()
	{
		return world;
	}
	
	public void addEntity(T entity, int id)
	{
		entities.add(entity);
		if (observer != null)
			observer.addEntity(entity,id);
	}
	public void removeEntity(T entity)
	{
		entities.remove(entity);
		if (observer != null)
			observer.removeEntity(entity);
	}
	
	public Set<T> getEntities()
	{
		return Collections.unmodifiableSet(entities);
	}
}
