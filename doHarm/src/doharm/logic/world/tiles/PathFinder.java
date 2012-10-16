package doharm.logic.world.tiles;

import java.util.PriorityQueue;
import java.util.Stack;

import doharm.logic.world.World;

public class PathFinder 
{
	/**
	 * move to a tile on the world
	 * World.resetTiles(); must be called before this method so that the tiles haven't been visited!
	 * @param world 
	 * @param start the current position
	 * @param goal the tile to move to
	 * @return a path to move along, or null if the goal was not found. The goal will be added to the first
	 * node in the path, as the goal can change.
	 */
	public static Stack<Tile> calculatePath(World world, Tile start, Tile goal) 
	{
		if (start == null || goal == null || goal == start)
		{
			return null;
		}
		
		world.resetTiles(goal);
		
		
		
		//calculate path...
		Stack<Tile> path = new Stack<Tile>();
		
		PriorityQueue<Tile> queue = new PriorityQueue<Tile>();
		
		queue.add(start);

		//boolean foundGoal = false;
		//System.out.println("Goal: " + goal.getRow() +","+goal.getCol());
		int numTries = 0;
		while (!queue.isEmpty() && numTries < 10000)
		{
			numTries++;
			Tile node = queue.poll();
			//System.out.println("Current: " + node.getRow()+","+node.getCol());
			
			/*System.out.println("Neighbours of " + node.getRow() +","+node.getCol()+":");
			
			for (Tile n: node.getNeighbours())
			{
				System.out.println(n.getRow() +","+n.getCol());
			}*/
			
			
			if (node == goal)
			{
				path.clear();
				
				
				if (!goal.isEmpty())
				{
					node = node.getParent();
				}
				
				//foundGoal = true;
				while (node != start)
				{
					path.push(node);
					node = node.getParent();
				}
				
				
				return path;
			}
			
			node.setVisited(true);


			for (Tile neighbour: node.getNeighbours())
			{
				
				if (!neighbour.isVisited() && neighbour.isWalkable() && (neighbour.getRoof() == null || !neighbour.getRoof().isVisible() ||  neighbour.getRoof().isWalkable())/*neighbour.isWalkable()*/ && (neighbour.isEmpty()||neighbour == goal))// && !neighbour.isNextToWall())
				{
					
					float pathLength = node.getPathLength() + node.distanceToTile(neighbour);
					
					if (neighbour.getPathLength() == 0 || pathLength < neighbour.getPathLength())
					{
						if (neighbour.getRow() == node.getRow() || neighbour.getCol() == node.getCol() || !neighbour.isNextToWall())
						{
							neighbour.setParent(node);
						
							neighbour.setPathLength(node.getPathLength() + node.distanceToTile(neighbour));
							queue.offer(neighbour);
						}
					}
				}
			}
			
		}
		//System.out.println("Found goal: "+foundGoal);
		return null;
	}
	
}
