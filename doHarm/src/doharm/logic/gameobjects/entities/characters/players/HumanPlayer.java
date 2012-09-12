package doharm.logic.gameobjects.entities.characters.players;


public class HumanPlayer extends Player
{
	protected HumanPlayer(String name, int id) 
	{
		super(name, id, PlayerType.AI);
	}
}
