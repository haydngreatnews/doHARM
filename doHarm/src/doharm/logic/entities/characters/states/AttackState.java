package doharm.logic.entities.characters.states;

import doharm.logic.entities.characters.Character;
import doharm.logic.entities.items.wearable.WearableItem;
import doharm.logic.inventory.SlotType;

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
		
		float minDistance = 3;
		
		
		WearableItem weapon = character.getInventory().getSlots()[SlotType.WEAPON.ordinal()];
		
		if (weapon != null)
		{
			
		}
		
		
		float distance = character.getCurrentTile().distanceToTile(victim.getCurrentTile());
		
		//TODO increased distance for ranged attack.
		
		if (distance < minDistance)
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
