package doharm.logic.entities.characters.states;

import java.awt.Color;

import doharm.logic.chat.Message;
import doharm.logic.chat.MessagePart;
import doharm.logic.entities.characters.Character;
import doharm.logic.entities.items.Item;
import doharm.logic.entities.items.ItemQuality;
import doharm.logic.entities.items.ItemType;
import doharm.logic.entities.items.misc.MiscItem;
import doharm.logic.entities.items.misc.MiscItemType;

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
			boolean pickedUp = itemToPickup.getCurrentTile().drop(itemToPickup, character.getInventory());
			String pickupString = " picked up "+(itemToPickup.isUnique()?"the ":"a ");
			String exclamation = itemToPickup.getQuality() == ItemQuality.LEGENDARY?"!":"";
			
			if (character.isHumanPlayer())
			{
				if (pickedUp)
				{
					Message message = new Message(character.getID(),false, new MessagePart("you"+ pickupString +itemToPickup.toString()+exclamation,Color.yellow));
					character.getWorld().addMessage(message);
				}
				else
				{
					Message message = new Message(character.getID(),false, new MessagePart("INVENTORY FULL",Color.red));
					character.getWorld().addMessage(message);
				}
			}
			
			if (itemToPickup.getItemType() == ItemType.MISC)
			{
				MiscItem misc = (MiscItem)itemToPickup;
				if (misc.getMiscItemType() == MiscItemType.DRAGONBALL)
				{
					int numDragonBalls = character.getNumDragonBalls();
					Color colour = Color.green;
					
					
					if (numDragonBalls == 7)
						colour = Color.yellow;
					else if (numDragonBalls > 4)
						colour = Color.red;
					else if (numDragonBalls > 2)
						colour = Color.orange;
					
					
					MessagePart part = new MessagePart(character.getName() + pickupString + itemToPickup.toString()+"<br/>",Color.white);
					MessagePart part2 = new MessagePart(character.getName() + " now has " + numDragonBalls + " dragon balls!",colour);
					
					
					Message message = new Message(-1,true, part,part2);
					character.getWorld().addMessage(message);
					
					
					if (numDragonBalls == 7)
					{
						if (character.isHumanPlayer())
						{
							message = new Message(-1,false, new MessagePart("YOU WON THE GAME!",colour));
							character.getWorld().addMessage(message);
						}
						
						message = new Message(-1, true, new MessagePart(character.getName() +" WON THE GAME!",colour));
						character.getWorld().addMessage(message);
						
						character.getWorld().getGame().end(character);
					}
					
				}
			}
			
			
			
		}
		else
		{
			moveState.setDestination(itemToPickup.getCurrentTile());
			moveState.process(character);
		}
		
		
	}
}
