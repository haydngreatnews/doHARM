package doharm.logic.entities.characters.states;

import doharm.logic.entities.characters.Character;
import doharm.logic.physics.Vector;

/**
 * A state where you do nothing - but respond to attacks.
 * @author Roland
 */

public class IdleState extends CharacterState
{
	private static final float IDLE_FRICTION = 0.5f;
	
	public IdleState()
	{
		super(CharacterStateType.IDLE);
	}

	@Override
	public void process(Character character) 
	{
		if (character.getAttackedBy() != null)
		{
			character.setState(new AttackState(character.getAttackedBy()));
			character.resetAttackedBy();
			return;
		}
		
		Vector velocity = character.getVelocity();
		
		velocity.multiply(IDLE_FRICTION);

		character.setVelocity(velocity);
	}
}