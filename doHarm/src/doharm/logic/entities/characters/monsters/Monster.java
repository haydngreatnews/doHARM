package doharm.logic.entities.characters.monsters;
import java.util.HashMap;
import java.util.Map;

import doharm.logic.entities.characters.Character;
import doharm.logic.entities.characters.CharacterType;
import doharm.logic.entities.characters.players.ai.AIAttackState;
import doharm.logic.entities.characters.players.ai.AIIdleState;
import doharm.logic.entities.characters.players.ai.AIMoveState;
import doharm.logic.entities.characters.players.ai.AIPickupState;
import doharm.logic.entities.characters.players.ai.AIState;
import doharm.logic.entities.characters.states.CharacterStateType;

public abstract class Monster extends Character
{
	public Monster()
	{
		super(CharacterType.MONSTER);
	}
	
	@Override
	public void process()
	{
		if (!isAlive())
			return;
		super.process();

	}
}
