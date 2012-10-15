package doharm.logic.entities.items.misc.dragonballs;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import doharm.logic.entities.characters.players.HumanPlayer;

public class DragonRadar 
{
	private Set<DragonBall> balls;

	public DragonRadar()
	{
		balls = new HashSet<DragonBall>();
	}
	public void add(DragonBall ball) 
	{
		balls.add(ball);
	}
	
	public Set<DragonBall> getBalls()
	{
		return Collections.unmodifiableSet(balls);
	}
	
	

}
