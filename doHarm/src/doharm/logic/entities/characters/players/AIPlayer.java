package doharm.logic.entities.characters.players;

import doharm.logic.entities.characters.players.ai.AIState;
import doharm.logic.entities.characters.players.ai.IdleState;
import doharm.logic.world.tiles.Tile;


public class AIPlayer extends Player
{
	private AIState state;
	
	protected AIPlayer() 
	{
		super(PlayerType.AI);
		
		
	}
	
	@Override
	public void spawn(Tile spawnTile)
	{
		super.spawn(spawnTile);
		state = new IdleState();
		
	}
	
	@Override
	public void process()
	{
		if (!isAlive())
			return;
		super.process();
		state.process(this);
	}
	
}
