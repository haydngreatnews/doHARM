package doharm.logic.entities.characters.players.ai;

import doharm.logic.entities.characters.players.AIPlayer;
import doharm.logic.entities.characters.states.AttackState;
import doharm.logic.entities.characters.states.CharacterStateType;
import doharm.logic.entities.characters.states.MoveState;

public class IdleState implements AIState
{

	@Override
	public void process(AIPlayer player) 
	{
		if (player.getStateType() == CharacterStateType.IDLE)
		{
			if (player.getAttackedBy() != null)
			{
				player.setState(new AttackState(player.getAttackedBy()));
			}
			
			
			
			if (Math.random() > 0.998f)
				player.setState(new MoveState(player.getWorld().getRandomEmptyTile(),true));
			
			
		}
	}
	
}
