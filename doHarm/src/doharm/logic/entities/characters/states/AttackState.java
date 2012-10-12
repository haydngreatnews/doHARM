package doharm.logic.entities.characters.states;

import doharm.logic.entities.characters.Character;

public class AttackState extends CharacterState
{
	private MoveState moveState;
	private Character victim;
	public AttackState(Character victim)
	{
		super(CharacterStateType.ATTACK);
		
		this.victim = victim;
		
		moveState = new MoveState(victim.getCurrentTile(),false);
	}

	@Override
	public void process(Character character) 
	{
		
		
		if (!victim.isAlive())
		{
			character.setState(new IdleState());
			return;
		}
		
		if (character.fromNetwork())
			return;
			
		
		float distance = character.getCurrentTile().distanceToTile(victim.getCurrentTile());
		
		//TODO increased distance for ranged attack.
		
		if (distance < 3)
		{
			float damage = 1;
			victim.receiveDamage(damage,character);
		}
		else
		{
			moveState.setDestination(victim.getCurrentTile());
			moveState.process(character);
		}
		
		
	}
}
