package doharm.logic.entities.characters.players.ai;

import doharm.logic.entities.characters.players.AIPlayer;
import doharm.logic.entities.characters.players.Player;
import doharm.logic.entities.characters.states.AttackState;
import doharm.logic.entities.characters.states.MoveState;

public class AIIdleState extends AIState
{

	@Override
	public void internalProcess(AIPlayer player) 
	{
		if (Math.random() > 0.998f)
		{
			if (Math.random() > 0.5)
				player.setState(new MoveState(player.getWorld().getRandomEmptyTile(),true));
			else
			{
				Player victim = null;
				do
				{
					victim = player.getWorld().getRandomPlayer();
				} while(victim == player);
				
				player.setState(new AttackState(victim));
			}
			
			
		}
	}
	
}
