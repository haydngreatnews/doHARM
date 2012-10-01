package doharm.logic.gameobjects.entities.characters.players;

import doharm.logic.gameobjects.entities.characters.classes.CharacterClassType;
import doharm.logic.gameobjects.entities.characters.players.ai.AIState;
import doharm.logic.gameobjects.entities.characters.players.ai.WanderState;
import doharm.logic.world.tiles.Tile;


public class AIPlayer extends Player
{
	private AIState state;
	
	protected AIPlayer(Tile spawnTile, String name, CharacterClassType classType, int id) 
	{
		super(spawnTile, name, id,classType, PlayerType.AI);
		
		state = new WanderState();
	}
	
	@Override
	public void move()
	{
		super.move();
	}
	
}
