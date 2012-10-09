package doharm.logic.entities.characters.states;

import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

import doharm.logic.physics.Vector;
import doharm.logic.world.tiles.PathFinder;
import doharm.logic.world.tiles.Tile;
import doharm.logic.entities.characters.Character;

public class MoveState extends CharacterState
{
	private Stack<Tile> path;
	private Tile destination;	
	private Vector nextNodeInPath;
	private static final float MIN_NODE_DISTANCE = 10;
	private static final float MIN_DESTINATION_DISTANCE = 2;
	
	public MoveState(Tile destination) 
	{
		super(CharacterStateType.MOVE);
		this.destination = destination;
		path = new Stack<Tile>();
		nextNodeInPath = new Vector();
	}

	@Override
	public void process(Character character) 
	{
		//dynamically calculate path.
		
		if (character.fromNetwork())
			return;
		
		path = PathFinder.calculatePath(character.getWorld(), character.getCurrentTile(), destination);
		
		float distanceToDestination = character.getCurrentTile().distanceToTile(destination);
		
		
		
		if (path == null || path.isEmpty() || distanceToDestination < MIN_DESTINATION_DISTANCE)
		{
			character.setState(new IdleState());
			return;
		}
		
		
		
		
		Tile next = path.pop();
		
		nextNodeInPath.set(next.getX(), next.getY());
		
		
		
		
		Vector direction = nextNodeInPath.subtract(character.getCurrentTile().getX(),character.getCurrentTile().getY());//character.getPosition());
		
		Vector velocity = character.getVelocity();
		
		
		
		
		direction.multiply(1, 2);
		direction.normalize();
		direction.multiply(character.getMovementSpeed());
		velocity.add(direction);
		character.setVelocity(velocity);
		
	}
	
	public Vector getDestination() 
	{
		return new Vector(destination.getX(),destination.getY());
	}
	public Collection<Tile> getPath()
	{
		return Collections.unmodifiableCollection(path);
	}
	
	/*private void nextNodeInPath() 
	{
		if (!path.isEmpty())
		{
			
			//if (path.size()== 1)
			{
				Tile next = path.pop();
				destination.set(next.getX(), next.getY());
			}
//			else// if (path.size()== 2)
//			{
//				//average between next target and the one after
//				Tile next = path.pop();
//				Tile nextNext = path.peek();
//				float x = (next.getX()+nextNext.getX())*0.5f;
//				float y = (next.getY()+nextNext.getY())*0.5f;
//				destination.set(x, y);
//			}
//			else
//			{
//				//average between next target and the one after
//				Tile next = path.pop();
//				Tile nextNext = path.pop();
//				Tile nextNextNext = path.peek();
//				float x = (next.getX()+nextNext.getX()*2 + nextNextNext.getX())/4;
//				float y = (next.getY()+nextNext.getY()*2 + nextNextNext.getY())/4;
//				destination.set(x, y);
//				path.push(nextNext);
//			}
		}
	}*/
}