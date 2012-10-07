package doharm.logic.entities.characters.players;

import doharm.logic.entities.characters.classes.CharacterClassType;
import doharm.logic.entities.characters.players.ai.AIState;
import doharm.logic.entities.characters.players.ai.WanderState;
import doharm.logic.world.tiles.Tile;


public class AIPlayer extends Player
{
	private AIState state;
	
	protected AIPlayer() 
	{
		super(PlayerType.AI);
		
		state = new WanderState();
	}
	
	@Override
	public void move()
	{
		super.move();
		state.process(this);
	}
	
}
