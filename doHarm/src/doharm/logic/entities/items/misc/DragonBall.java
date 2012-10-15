package doharm.logic.entities.items.misc;

public class DragonBall extends MiscItem 
{
	private int star;
	public DragonBall(int star) 
	{
		super(MiscItemType.DRAGONBALL);
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
