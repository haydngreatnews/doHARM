package doharm.logic.entities.characters.states;

import doharm.logic.entities.characters.Character;

public class AttackState extends CharacterState
{
	private MoveState moveState;
	public AttackState()
	{
		super(CharacterStateType.ATTACK);
		//moveState = new MoveState(destination)
	}

	@Override
	public void process(Character character) 
	{
		
	}
}
