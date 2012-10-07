package doharm.logic.entities.characters.players.ai;

import doharm.logic.entities.characters.players.AIPlayer;

public class WanderState implements AIState
{

	@Override
	public void process(AIPlayer player) 
	{
		if (player.getPath().isEmpty())
		{
			
			if (Math.random() > 0.998f)
				player.moveTo(player.getWorld().getRandomEmptyTile());
			
			
		}
	}
	
}
