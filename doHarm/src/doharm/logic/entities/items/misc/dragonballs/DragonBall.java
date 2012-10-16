package doharm.logic.entities.items.misc.dragonballs;

import doharm.logic.entities.items.misc.MiscItem;
import doharm.logic.entities.items.misc.MiscItemType;

public class DragonBall extends MiscItem 
{
	public static final int NUM_DRAGONBALLS = 7;
	private int star;
	public DragonBall(int star) 
	{
		super(MiscItemType.DRAGONBALL,"db.jpg");
		this.star = star;
	}
	
	//TODO on pickup say "Player X picked up the X star dragonball!"

	public int getStar() 
	{
		return star;
	}
	
	@Override
	public String toString()
	{
		return star +" star dragonball";
	}
	
}
