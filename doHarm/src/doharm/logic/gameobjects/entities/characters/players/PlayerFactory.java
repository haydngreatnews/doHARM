package doharm.logic.gameobjects.entities.characters.players;

import doharm.logic.gameobjects.entities.characters.classes.CharacterClassType;
import doharm.logic.world.World;
import doharm.logic.world.tiles.Tile;

/**
 * Player constructors are protected so that they can only be created via this PlayerFactory class.
 * 
 * @author bewickrola
 */
public class PlayerFactory 
{	
	private World world;

	public PlayerFactory(World world)
	{
		this.world = world;
	}
	
	public static Player createPlayer(Tile spawnTile, String name, CharacterClassType classType, int id, PlayerType playerType)
	{
		Player player = null;
		switch(playerType)
		{
		case AI:
			player = new AIPlayer();
			break;
		case HUMAN:
			player = new HumanPlayer();
			break;
		case NETWORK:
			player = new NetworkPlayer();
		}
		
		
		player.spawn(spawnTile);
		player.setWorld(world);
		
		Tile spawnTile, String name, int id, CharacterClassType classType, PlayerType playerType
		
		return player;
	}

}
