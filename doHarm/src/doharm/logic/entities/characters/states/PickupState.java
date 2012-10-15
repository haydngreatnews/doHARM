package doharm.logic.entities.characters.states;

import java.awt.Color;

import doharm.logic.chat.Message;
import doharm.logic.chat.MessagePart;
import doharm.logic.entities.characters.Character;
import doharm.logic.entities.items.Item;
import doharm.logic.entities.items.ItemQuality;

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
		
		
		if (!itemToPickup.isAlive() || !itemToPickup.isOnGround())
		{
			character.setState(new IdleState());
			return;
		}
		
		if (character.fromNetwork())
			return;
			
		
		float distance = character.getCurrentTile().distanceToTile(itemToPickup.getCurrentTile());
		
		//TODO increased distance for ranged attack.
		
		if (distance < 2)
		{
			boolean pickedUp = character.getInventory().pickup(itemToPickup);
			String pickupString = "picked up "+(itemToPickup.isUnique()?"the ":"a ");
			String exclamation = itemToPickup.getQuality() == ItemQuality.LEGENDARY?"!":"";
			
			if (character.isHumanPlayer())
			{
				if (pickedUp)
				{
					Message message = new Message(character.getID(), new MessagePart("you "+ pickupString +itemToPickup.toString()+exclamation,Color.yellow));
					character.getWorld().addMessage(message);
				}
				else
				{
					Message message = new Message(character.getID(), new MessagePart("INVENTORY FULL",Color.red));
					character.getWorld().addMessage(message);
				}
			}
			else
			{
				
			}
			
			
			
		}
		else
		{
			moveState.setDestination(itemToPickup.getCurrentTile());
			moveState.process(character);
		}
		
		
	}
}
