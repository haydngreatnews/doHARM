package doharm.logic.gameobjects.entities.characters.players;

import doharm.logic.gameobjects.entities.characters.classes.CharacterClass;
import doharm.logic.gameobjects.entities.characters.classes.CharacterClassType;
import doharm.logic.world.tiles.Tile;


public class HumanPlayer extends Player
{
	protected HumanPlayer(Tile spawnTile, String name, CharacterClassType type, int id) 
	{
		super(spawnTile, name, id, type, PlayerType.HUMAN);
	}
}
