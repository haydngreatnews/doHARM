package doharm.logic.entities.projectiles;

import doharm.logic.entities.AbstractEntity;
import doharm.logic.entities.EntityType;

public abstract class Projectile extends AbstractEntity 
{
	private Character attacker;

	public Projectile(Character attacker) 
	{
		super(EntityType.PROJECTILE);
		this.attacker = attacker;
	}
	
	@Override
	public void process()
	{
		
		
		super.process();
	}
	
}
