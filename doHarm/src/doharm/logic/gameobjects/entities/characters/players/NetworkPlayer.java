package doharm.logic.gameobjects.entities.characters.players;


public class NetworkPlayer extends Player
{
	protected NetworkPlayer(String name, int id) 
	{
		super(name, id, PlayerType.AI);
	}
	
}
