package doharm.logic.entities.characters.players;

import java.awt.Color;

import doharm.logic.entities.AbstractEntityFactory;
import doharm.logic.entities.EntityFactory;
import doharm.logic.entities.characters.classes.CharacterClassType;
import doharm.logic.world.World;
import doharm.logic.world.tiles.Tile;

/**
 * Player constructors are protected so that they can only be created via this PlayerFactory class.
 * 
 * @author bewickrola
 */
public class PlayerFactory extends AbstractEntityFactory<Player>
{	
	public PlayerFactory(World world, EntityFactory entityFactory) 
	{
		super(world,entityFactory);
	}

	public Player createPlayer(Tile spawnTile, String name, CharacterClassType classType, int id, PlayerType playerType, Color colour, boolean fromNetwork)
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
		
		player.setName(name);
		player.setWorld(getWorld());
		player.setCharacterClass(classType);
		player.setColour(colour);
		
		player.spawn(spawnTile);
		
		addEntity(player,id,fromNetwork);
		return player;
	}

	public void removePlayer(Player player)
	{
		
		removeEntity(player);
	}
	
}
