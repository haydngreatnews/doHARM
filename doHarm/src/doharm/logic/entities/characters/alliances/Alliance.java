package doharm.logic.entities.characters.alliances;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import doharm.logic.entities.characters.Character;

public class Alliance 
{
	
	//When in an alliance, you share XP but also get bonuses.
	//your rage constantly increases, when group rage > threshold, alliance breaks
	//Can leave alliance at any time.
	//Rage can be decreased by leveling up or killing enemies.
	//Rage is increased when a player is damaged.
	//Can request alliances with other players rather than fighting them, if both rage values are low.
	//If you move too far away you will leave your alliance.
	//Right click on player to ask them to join alliance.
	
	
	private Set<Character> characters;
	private float combinedRage;
	private float maxRage;
	
	
	/**
	 * Someone starts an alliance and other players can join.
	 * 
	 * @param firstCharacter
	 * @param secondCharacter
	 */
	public Alliance(Character firstCharacter, Character secondCharacter)
	{
		//TODO create message "... joined an alliance!"
		combinedRage = 0;
		characters = new HashSet<Character>();
		
	}
	
	public void process()
	{
		//increase player rage
		
		//increase total rage
		combinedRage += 0.1f;
		combinedRage *= 1.01f;
		
		//calculate total rage
		
		//calculate max rage
		
		if (combinedRage > maxRage)
		{
			explode();
			
		}
		
	}


	private void explode() 
	{
		// TODO Auto-generated method stub
		
	}

	public Set<Character> getCharacters() 
	{
		return Collections.unmodifiableSet(characters);
	}

	public void addExperience(int exp) 
	{
		
		for (Character ally: characters)
		{
			ally.addExperience(exp);
		}
		
	}
	
	
}






