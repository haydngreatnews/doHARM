package doharm.logic.entities.characters.players;

import java.awt.Color;

import doharm.logic.entities.AbstractEntityFactory;
import doharm.logic.entities.EntityFactory;
import doharm.logic.entities.characters.classes.CharacterClassType;
import doharm.logic.entities.items.ItemQuality;
import doharm.logic.entities.items.ItemType;
import doharm.logic.entities.items.usable.UsableItemType;
import doharm.logic.world.World;
import doharm.logic.world.tiles.Tile;

/**
 * Player constructors are protected so that they can only be created via this PlayerFactory class.
 * This factory creates, stores and removes players from the game.
 * 
 * @author bewickrola
 */
public class PlayerFactory extends AbstractEntityFactory<Player>
{	
	public PlayerFactory(World world, EntityFactory entityFactory) 
	{
		super(world,entityFactory);
	}

	/**
	 * 
	 * @param spawnTile the tile to spawn the player in
	 * @param name the unique name of the player
	 * @param classType the player's class (Warrior,etc)
	 * @param id the unique id of the player
	 * @param playerType AI, Human or Network
	 * @param colour the unique colour of the player
	 * @param fromNetwork will be true if this player is not created by this machine.
	 * @return
	 */
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
		default:
			throw new UnsupportedOperationException("Unknown player type: " + playerType.toString());
		}
		
		
		//Set important variables for each type of player
		player.setName(name);
		player.setWorld(getWorld());
		player.setCharacterClass(classType);
		player.setColour(colour);
		
		
		//Give each player 2 health potions... 
		
		for (int i = 0; i < 2; i++)
		{
			getWorld().getItemFactory().createItem(ItemType.USABLE, UsableItemType.HEALTH_POTION.ordinal(), ItemQuality.POOR, getWorld().getIDManager().takeID(), player.getInventory().getBelt(), false);
		}
		
		//actually spawn the player
		player.spawn(spawnTile);
		
		//add this player to the global list of entities.
		addEntity(player,id,fromNetwork);
		return player;
	}

	/**
	 * Completely remove a player from the game.
	 * @param player
	 */
	public void removePlayer(Player player)
	{
		removeEntity(player);
	}
	
}
