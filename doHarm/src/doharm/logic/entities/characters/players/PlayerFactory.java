package doharm.logic.entities.characters.players;

import doharm.logic.entities.AbstractEntity;
import doharm.logic.entities.characters.classes.CharacterClassType;
import doharm.logic.entities.characters.Character;
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
	
	public Player createPlayer(Tile spawnTile, String name, CharacterClassType classType, int id, PlayerType playerType)
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
		
		((Character)player).setName(name);
		player.spawn(spawnTile);
		((AbstractEntity)player).setWorld(world);
		player.setID(id);
		
		
		return player;
	}

}
