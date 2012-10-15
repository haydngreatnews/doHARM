package doharm.logic.entities.characters.players;


import java.awt.Color;

import doharm.logic.entities.characters.Character;
import doharm.logic.entities.characters.CharacterType;

/**
 * A player is a type of character that is able to win the game.
 * 
 * @author Roland
 */

public abstract class Player extends Character
{
	private PlayerType playerType;
	private Color colour; //the player's unique colour
	
	
	protected Player(PlayerType playerType)
	{
		super(CharacterType.PLAYER);
		this.playerType = playerType;
	}

	public Color getColour()
	{
		return colour;
	}
	
	public void setColour(Color colour)
	{
		this.colour = colour;
	}
	
	public PlayerType getPlayerType()
	{
		return playerType;
	}
	
	
	public void process()
	{
		if (!isAlive())
			return;
		super.process();
	}	
}
