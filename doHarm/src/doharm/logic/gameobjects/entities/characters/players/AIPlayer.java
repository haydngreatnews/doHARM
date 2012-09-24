package doharm.logic.gameobjects.entities.characters.players;

import doharm.logic.world.tiles.Tile;


public class AIPlayer extends Player
{
	protected AIPlayer(Tile spawnTile, String name, int id) 
	{
		super(spawnTile, name, id, PlayerType.AI);
	}
}
