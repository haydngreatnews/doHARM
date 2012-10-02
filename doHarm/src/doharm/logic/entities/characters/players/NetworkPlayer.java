package doharm.logic.entities.characters.players;

import doharm.logic.entities.characters.classes.CharacterClassType;
import doharm.logic.world.tiles.Tile;


public class NetworkPlayer extends Player
{
	protected NetworkPlayer() 
	{
		super(PlayerType.AI);
	}
	
	@Override
	public void move()
	{
		super.move();
	}
}
