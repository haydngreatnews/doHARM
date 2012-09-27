package doharm.logic.gameobjects.entities.characters.players;

import doharm.logic.gameobjects.entities.characters.classes.CharacterClass;
import doharm.logic.gameobjects.entities.characters.classes.CharacterClassType;
import doharm.logic.world.tiles.Tile;

/**
 * Player constructors are protected so that they can only be created via this PlayerFactory class.
 * 
 * @author bewickrola
 */
public class PlayerFactory 
{	
	public static HumanPlayer createHumanPlayer(Tile spawnTile, String name, CharacterClassType classType, int id)
	{
		return new HumanPlayer(spawnTile, name,classType, id);
	}
	public static NetworkPlayer createNetworkPlayer(Tile spawnTile, String name, CharacterClassType classType, int id)
	{
		return new NetworkPlayer(spawnTile, name,classType, id);
	}
	public static AIPlayer createAIPlayer(Tile spawnTile, String name, CharacterClassType classType, int id)
	{
		return new AIPlayer(spawnTile, name,classType, id);
	}
}
