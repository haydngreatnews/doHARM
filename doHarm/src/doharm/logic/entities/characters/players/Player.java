package doharm.logic.entities.characters.players;


import doharm.logic.entities.characters.Character;

public abstract class Player extends Character
{
	private PlayerType playerType;
	
	protected Player(PlayerType playerType)
	{
		super();
		this.playerType = playerType;
	}

	
	public PlayerType getPlayerType()
	{
		return playerType;
	}
	
	
	public void move()
	{
		super.move();
	}
}
