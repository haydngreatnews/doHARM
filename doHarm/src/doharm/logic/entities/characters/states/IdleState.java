package doharm.logic.entities.characters.states;

import doharm.logic.entities.characters.Character;
import doharm.logic.physics.Vector;

public class IdleState extends CharacterState
{
	private static final float IDLE_FRICTION = 0.1f;
	
	public IdleState()
	{
		super(CharacterStateType.IDLE);
	}

	@Override
	public void process(Character character) 
	{
		Vector velocity = character.getVelocity();
		
		velocity.multiply(IDLE_FRICTION);

		character.setVelocity(velocity);
	}
}