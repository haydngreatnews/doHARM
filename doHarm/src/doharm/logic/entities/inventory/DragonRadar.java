package doharm.logic.entities.inventory;

import java.util.HashSet;
import java.util.Set;

import doharm.logic.entities.characters.players.HumanPlayer;
import doharm.logic.entities.items.misc.DragonBall;

public class DragonRadar 
{
	private HumanPlayer player;
	private Set<DragonBall> balls;

	public DragonRadar()
	{
		balls = new HashSet<DragonBall>();
	}
	public void add(DragonBall ball) 
	{
		balls.add(ball);
	}
	
	public void setPlayer(HumanPlayer player)
	{
		this.player = player;
	}
	
	

}
