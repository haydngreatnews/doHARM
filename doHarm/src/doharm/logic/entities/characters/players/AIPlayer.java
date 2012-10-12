package doharm.logic.entities.characters.players;

import java.util.HashMap;
import java.util.Map;

import doharm.logic.entities.characters.players.ai.AIAttackState;
import doharm.logic.entities.characters.players.ai.AIIdleState;
import doharm.logic.entities.characters.players.ai.AIMoveState;
import doharm.logic.entities.characters.players.ai.AIPickupState;
import doharm.logic.entities.characters.players.ai.AIState;
import doharm.logic.entities.characters.states.CharacterStateType;
import doharm.logic.world.tiles.Tile;


public class AIPlayer extends Player
{
	//Uses the flyweight pattern - only need a single instance of each state.
	private static Map<CharacterStateType, AIState> states;
	
	
	static
	{
		states = new HashMap<CharacterStateType, AIState>();
		
		for (CharacterStateType type: CharacterStateType.values())
		{
			AIState state = null;
			switch(type)
			{
			case IDLE:
				state = new AIIdleState();
				break;
			case ATTACK:
				state = new AIAttackState();
				break;
			case MOVE:
				state = new AIMoveState();
				break;
			case PICKUP:
				state = new AIPickupState();
				break;
			
				default:
					throw new UnsupportedOperationException("Unknown AI character state");
			}
			
			states.put(type, state);
		}
	}
	
	
	protected AIPlayer() 
	{
		super(PlayerType.AI);
		
		
	}
	
	@Override
	public void spawn(Tile spawnTile)
	{
		super.spawn(spawnTile);
		
		
	}
	
	@Override
	public void process()
	{
		if (!isAlive())
			return;
		super.process();
		
		//Strategy pattern
		states.get(getStateType()).process(this);
	}
	
	
	
}
