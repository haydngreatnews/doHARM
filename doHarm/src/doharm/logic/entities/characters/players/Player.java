package doharm.logic.entities.characters.players;


import java.awt.Color;

import doharm.logic.entities.characters.Character;

public abstract class Player extends Character
{
	private PlayerType playerType;
	private Color colour;
	
	
	protected Player(PlayerType playerType)
	{
		super();
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
