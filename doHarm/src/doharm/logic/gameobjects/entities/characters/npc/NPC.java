package doharm.logic.gameobjects.entities.characters.npc;

import doharm.logic.gameobjects.entities.characters.Character;
import doharm.logic.world.tiles.Tile;

public abstract class NPC extends Character
{
	public NPC(Tile spawnTile, String name, int id) 
	{
		super(spawnTile, name, id);
	}

}
