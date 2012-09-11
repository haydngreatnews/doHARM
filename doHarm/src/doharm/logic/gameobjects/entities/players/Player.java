package doharm.logic.gameobjects.entities.players;

import doharm.logic.gameobjects.entities.Entity;

public abstract class Player extends Entity
{
	private int id; //unique id used for networking
	protected Player(int id, PlayerType type) //only allow players to be created via the PlayerFactory
	{
		
	}
}
