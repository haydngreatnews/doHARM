package doharm.logic.gameobjects.entities.characters.players;

import doharm.logic.gameobjects.entities.characters.classes.CharacterClassType;
import doharm.logic.gameobjects.entities.inventory.DragonRadar;
import doharm.logic.world.tiles.Tile;


public class HumanPlayer extends Player
{
	private DragonRadar dragonRadar;
	
	protected HumanPlayer(Tile spawnTile, String name, CharacterClassType type, int id) 
	{
		super(spawnTile, name, id, type, PlayerType.HUMAN);
	}
}
