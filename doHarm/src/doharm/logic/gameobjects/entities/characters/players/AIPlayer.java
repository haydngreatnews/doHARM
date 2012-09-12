package doharm.logic.gameobjects.entities.characters.players;


public class AIPlayer extends Player
{
	protected AIPlayer(String name, int id) 
	{
		super(name, id, PlayerType.AI);
	}
}
