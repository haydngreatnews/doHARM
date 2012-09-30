package doharm.logic.gameobjects.entities.characters.players;


import doharm.logic.gameobjects.entities.characters.Character;
import doharm.logic.gameobjects.entities.characters.classes.CharacterClassType;
import doharm.logic.world.tiles.Tile;

public abstract class Player extends Character
{
	private PlayerType playerType;
	protected Player(Tile spawnTile, String name, int id, CharacterClassType classType, PlayerType playerType)
	{
		super(spawnTile, name,classType, id);
		this.playerType = playerType;
	}
	
	protected void abstractMove()
	{
		
		
	}

	
	public PlayerType getPlayerType()
	{
		return playerType;
	}
}
