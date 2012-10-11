package doharm.logic.entities.characters.players.ai;

import doharm.logic.entities.characters.players.AIPlayer;
import doharm.logic.entities.characters.states.AttackState;

public abstract class AIState 
{
	protected abstract void internalProcess(AIPlayer player);
	public void process(AIPlayer player)
	{
		internalProcess(player);
		
		
	}
}
