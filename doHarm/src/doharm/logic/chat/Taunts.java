package doharm.logic.chat;

import java.util.HashMap;
import java.util.Map;

import doharm.logic.entities.characters.Character;

/**
 * If you can't be bothered chatting properly, just hit ALT + any letter, and you will say a predefined "taunt".
 * 
 * @author Roland
 */

public class Taunts 
{
	private Character character; //the character who is making the taunt (could be an AI or human player!)
	private static Map<java.lang.Character, String> taunts; //map of possible taunts.
	
	//Use of the flyweight pattern...
	static
	{
		taunts = new HashMap<java.lang.Character, String>();
		taunts.put('a', "Hello!");
		taunts.put('b', "Yes.");
		taunts.put('c', "No.");
		taunts.put('d', "Join my alliance!");
		taunts.put('e', "Die!");
		taunts.put('f', "Maybe.");
		taunts.put('g', "Wow.");
		taunts.put('h', "");
		taunts.put('i', "");
		taunts.put('j', "");
		taunts.put('k', "");
		taunts.put('l', "");
		taunts.put('m', "");
		taunts.put('n', "");
		taunts.put('o', "");
		taunts.put('p', "");
		taunts.put('q', "");
		taunts.put('r', "");
		taunts.put('s', "");
		taunts.put('t', "");
		taunts.put('u', "");
		taunts.put('v', "");
		taunts.put('w', "");
		taunts.put('x', "");
		taunts.put('y', "");
		taunts.put('z', "");
	}
	
	
	public Taunts(Character character)
	{
		this.character = character;
	}
	
	/**
	 * Taunt - create a message and say it.
	 * @param key the key that corresponds to a taunt.
	 */
	public void taunt(char key) 
	{
		String taunt = taunts.get(key);
		if (taunt == null)
			return;
		
		Message message = new Message(character.getID(),true, new MessagePart(taunt));
		
		character.getWorld().addMessage(message);
	}
	
}
