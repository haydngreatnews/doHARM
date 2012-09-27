package doharm.logic.gameobjects.entities.characters.players;

import doharm.logic.gameobjects.entities.characters.classes.CharacterClassType;
import doharm.logic.world.tiles.Tile;


public class NetworkPlayer extends Player
{
	protected NetworkPlayer(Tile spawnTile, String name, CharacterClassType classType, int id) 
	{
		super(spawnTile, name, id,classType, PlayerType.AI);
	}
	
}
