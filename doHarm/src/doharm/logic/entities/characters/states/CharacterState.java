package doharm.logic.entities.characters.states;

import doharm.logic.entities.characters.Character;

/**
 * 
 * @author bewickrola
 */

public abstract class CharacterState 
{
	private CharacterStateType type;
	
	public abstract void process(Character character);
	
	public CharacterState(CharacterStateType type)
	{
		this.type = type;
	}
	
	
	public CharacterStateType getStateType()
	{
		return type;
	}


	
}
