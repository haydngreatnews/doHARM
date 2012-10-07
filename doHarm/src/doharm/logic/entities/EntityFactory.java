package doharm.logic.entities;

import doharm.logic.world.World;

public class EntityFactory extends AbstractEntityFactory<AbstractEntity>
{
	private IDManager idManager;
	public EntityFactory(World world, IDManager idManager) 
	{
		super(world, null);
		this.idManager = idManager;
	}
	
	@Override
	public void removeEntity(AbstractEntity entity)
	{
		super.removeEntity(entity);
		idManager.freeID(entity.getID());
	}
	@Override
	public void addEntity(AbstractEntity entity, int id)
	{
		entity.setID(id);
	}
}
