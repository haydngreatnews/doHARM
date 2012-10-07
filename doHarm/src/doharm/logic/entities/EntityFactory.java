package doharm.logic.entities;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import doharm.logic.world.World;

public class EntityFactory extends AbstractEntityFactory<AbstractEntity>
{
	private IDManager idManager;
	private Set<AbstractEntity> addedEntities;
	private Set<AbstractEntity> removedEntities;
	
	public EntityFactory(World world, IDManager idManager) 
	{
		super(world, null);
		this.idManager = idManager;
		addedEntities = new HashSet<AbstractEntity>();
		removedEntities = new HashSet<AbstractEntity>();
	}
	
	@Override
	public void removeEntity(AbstractEntity entity)
	{
		super.removeEntity(entity);
		idManager.freeID(entity.getID());
		removedEntities.add(entity);
	}
	@Override
	public void addEntity(AbstractEntity entity, int id)
	{
		entity.setID(id);
		addedEntities.add(entity);
	}
	
	public Set<AbstractEntity> getAddedEntities()
	{
		return Collections.unmodifiableSet(addedEntities);
	}
	
	public Set<AbstractEntity> getRemovedEntities()
	{
		return Collections.unmodifiableSet(removedEntities);
	}
	public void clearAddedEntities()
	{
		addedEntities.clear();
	}
	public void clearRemovedEntities()
	{
		removedEntities.clear();
	}
	
}
