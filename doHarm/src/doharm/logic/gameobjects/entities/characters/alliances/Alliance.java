package doharm.logic.gameobjects.entities.characters.alliances;

import java.util.Set;

public class Alliance {
	
	//When in an alliance, you share XP but also get bonuses.
	//your rage constantly increases, when group rage > threshold, alliance breaks
	//Can leave alliance at any time.
	//Can request alliances with other players rather than fighting them, if both rage values are low.
	//If you move too far away you will leave your alliance.
	//Right click on player to ask them to join alliance.
	
	
	public Set<Character> characters;
	
	
	/**
	 * Someone starts an alliance and other players can join.
	 * 
	 * @param firstCharacter
	 * @param secondCharacter
	 */
	public Alliance(Character firstCharacter, Character secondCharacter)
	{
		//TODO create message "... joined an alliance!"
		
	}
}






