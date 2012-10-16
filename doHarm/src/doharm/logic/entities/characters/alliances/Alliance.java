package doharm.logic.entities.characters.alliances;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import doharm.logic.chat.Message;
import doharm.logic.chat.MessagePart;
import doharm.logic.entities.characters.Character;
import doharm.logic.world.World;

/**
 * When in an alliance, you share XP but also get bonuses.
 * Your rage slowly increases, when group rage > threshold, alliance breaks
 * A player can join / leave an alliance at any time, if their rage is low.
 * Rage can be decreased by leveling up or killing enemies.
 * Rage is increased when a player is damaged.
 * Can request alliances with other players rather than fighting them, if both rage values are low.
 * 
 * 
 * TO JOIN AN ALLIANCE: Hold J and press 1-9.
 * TO CHANGE / LEAVE an alliance: Hold J and press 1-9.
 * 
 * @author Roland
 */

public class Alliance 
{
	private Set<Character> characters; //set of characters in this alliance
	private AllianceName name;
	private World world;
	
	
	/**
	 * Someone starts an alliance and other players can join.
	 * 
	 * @param firstCharacter
	 * @param secondCharacter
	 */
	public Alliance(World world,AllianceName name)
	{
		this.world = world;
		this.name = name;
		characters = new HashSet<Character>();
	}

	public AllianceName getName()
	{
		return name;
	}
	
	public void process()
	{
		if (characters.size() < 1)
			return;
		

		
		float combinedRage = 0;
				
		for (Character character: characters)
		{
			character.increaseRage();
			combinedRage += character.getRageRatio();
		}
		combinedRage /= characters.size();
		
		
		
		if (combinedRage > 0.7f)
		{
			explode();
		}
		
	}


	private void explode() 
	{
		world.addMessage(new Message(-1, true, new MessagePart(name.toString()+" alliance broke!")));
		
		while (!characters.isEmpty())
		{
			removeCharacter(characters.iterator().next());
		}
	}

	public Set<Character> getCharacters() 
	{
		return Collections.unmodifiableSet(characters);
	}

	public void removeCharacter(Character c) 
	{
		c.setAlliance(null);
		characters.remove(c);
		world.addMessage(new Message(-1, true, new MessagePart(c.getName()+ " left the "+name.toString()+" alliance.")));
	}
	
	public void addCharacter(Character c) 
	{
		c.setAlliance(this);
		characters.add(c);
		world.addMessage(new Message(-1, true, new MessagePart(c.getName()+ " joined the "+name.toString()+" alliance!")));
	}
	
	
}






