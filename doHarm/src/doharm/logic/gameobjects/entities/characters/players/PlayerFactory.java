package doharm.logic.gameobjects.entities.characters.players;

import doharm.logic.world.tiles.Tile;

/**
 * Player constructors are protected so that they can only be created via this PlayerFactory class.
 * 
 * @author bewickrola
 */
public class PlayerFactory 
{	
	public static HumanPlayer createHumanPlayer(Tile spawnTile, String name, int id)
	{
		return new HumanPlayer(spawnTile, name, id);
	}
	public static NetworkPlayer createNetworkPlayer(Tile spawnTile, String name, int id)
	{
		return new NetworkPlayer(spawnTile, name, id);
	}
	public static AIPlayer createAIPlayer(Tile spawnTile, String name, int id)
	{
		return new AIPlayer(spawnTile, name, id);
	}
}
