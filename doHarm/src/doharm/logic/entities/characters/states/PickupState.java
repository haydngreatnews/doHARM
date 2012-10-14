package doharm.logic.entities.characters.states;

import doharm.logic.entities.characters.Character;
import doharm.logic.entities.items.Item;

public class PickupState extends CharacterState
{
	private MoveState moveState;
	private Item itemToPickup;
	
	public PickupState(Item itemToPickup)
	{
		super(CharacterStateType.PICKUP);
		this.itemToPickup = itemToPickup;
		moveState = new MoveState(itemToPickup.getCurrentTile(),false);
	}

	@Override
	public void process(Character character) 
	{
		
		
		/*if (!itemToPickup.isAlive())
		{
			character.setState(new IdleState());
			return;
		}
		
		if (character.fromNetwork())
			return;
			
		
		float distance = character.getCurrentTile().distanceToTile(itemToPickup.getCurrentTile());
		
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
		}*/
		
		
	}
}
