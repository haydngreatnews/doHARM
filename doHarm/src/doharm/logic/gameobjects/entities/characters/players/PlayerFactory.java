package doharm.logic.gameobjects.entities.characters.players;



/**
 * Player constructors are protected so that they can only be created via this PlayerFactory class.
 * 
 * @author bewickrola
 */
public class PlayerFactory 
{	
	public static HumanPlayer createHumanPlayer(String name, int id)
	{
		return new HumanPlayer(name, id);
	}
	public static NetworkPlayer createNetworkPlayer(String name, int id)
	{
		return new NetworkPlayer(name, id);
	}
	public static AIPlayer createAIPlayer(String name, int id)
	{
		return new AIPlayer(name, id);
	}
}
