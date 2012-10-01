package doharm.logic.gameobjects.entities.characters.players.ai;

import doharm.logic.gameobjects.entities.characters.players.AIPlayer;

public class WanderState implements AIState
{

	@Override
	public void process(AIPlayer player) 
	{
		if (player.getPath().isEmpty())
		{
			
			
			player.moveTo(goal);
			
			
		}
	}
	
}
