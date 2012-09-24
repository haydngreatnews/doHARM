package doharm.logic.gameobjects.entities.characters.players;


import doharm.logic.gameobjects.entities.characters.Character;
import doharm.logic.world.tiles.Tile;

public abstract class Player extends Character
{
	private PlayerType type;
	protected Player(Tile spawnTile, String name, int id, PlayerType type)
	{
		super(spawnTile, name, id);
		this.type = type;
	}
	
	protected void abstractMove()
	{
		
		
	}

}
