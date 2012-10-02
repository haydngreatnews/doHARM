package doharm.logic.entities.characters.players;

import doharm.logic.entities.characters.classes.CharacterClassType;
import doharm.logic.entities.inventory.DragonRadar;
import doharm.logic.world.tiles.Tile;


public class HumanPlayer extends Player
{
	private DragonRadar dragonRadar;
	
	protected HumanPlayer() 
	{
		super(PlayerType.HUMAN);
	}
	
	@Override
	public void move()
	{
		super.move();
	}

	
}
