package doharm.logic.gameobjects.entities.players;

public class PlayerFactory 
{
	public static Player createPlayer(int id, PlayerType type)
	{
		switch (type)
		{
		case HUMAN:
			return new HumanPlayer(id);
		case NETWORK:
			return new NetworkPlayer(id);
		case NPC:
			return new NPC(id);
		default:
				throw new UnsupportedOperationException("Unknown player type");
		}
		
	}
}
