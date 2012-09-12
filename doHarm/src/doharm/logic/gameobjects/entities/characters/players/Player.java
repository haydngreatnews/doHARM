package doharm.logic.gameobjects.entities.characters.players;


import doharm.logic.gameobjects.entities.characters.Character;

public abstract class Player extends Character
{
	private PlayerType type;
	protected Player(String name, int id, PlayerType type)
	{
		super(name, id);
		this.type = type;
	}
	
	
	protected void abstractMove()
	{
		
		
	}

}
