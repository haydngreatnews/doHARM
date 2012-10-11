package doharm.logic.chat;

import doharm.logic.entities.characters.Character;

public class Taunts 
{
	private Character character;
	public Taunts(Character character)
	{
		this.character = character;
	}
	public void taunt(char key) 
	{
		Message message = new Message(character.getID(), new MessagePart("TAUNT"));
		
		character.getWorld().addMessage(message);
	}
	
}
