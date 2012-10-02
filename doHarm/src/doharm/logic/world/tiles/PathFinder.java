package doharm.logic.world.tiles;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class PathFinder 
{
	/**
	 * move to a tile on the world
	 * World.resetTiles(); must be called before this method so that the tiles haven't been visited!
	 * @param start the current position
	 * @param goal the tile to move to
	 * @return a path to move along, or null if the goal was not found. The goal will be added to the first
	 * node in the path, as the goal can change.
	 */
	public static Stack<Tile> calculatePath(Tile start, Tile goal) 
	{
		if (!goal.isWalkable())
		{
			int tries = 0;
			//find closest walkable tile
			Queue<Tile> possibilities = new LinkedList<Tile>();
			
			possibilities.offer(goal);
			
			int maxTries = 100;
			while(tries < maxTries)
			{
				Tile possibility = possibilities.poll();
				
				if (possibility.isWalkable())
				{
					goal = possibility;
					break;
				}
				
				for (Tile neighbour: possibility.getNeighbours())	
					possibilities.offer(neighbour);
				
				
				tries++;
			}
			
		
			
			if (!goal.isWalkable())
				return null;
		}
		
		
		//calculate path...
		Stack<Tile> path = new Stack<Tile>();
		
		PriorityQueue<Tile> queue = new PriorityQueue<Tile>();
		
		queue.add(start);

		//boolean foundGoal = false;
		//System.out.println("Goal: " + goal.getRow() +","+goal.getCol());
		while (!queue.isEmpty())
		{
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
				
				//foundGoal = true;
				while (node != start)
				{
					path.push(node);
					node = node.getParent();
				}
				path.push(goal);
				
				return path;
			}
			
			


			for (Tile neighbour: node.getNeighbours())
			{
				
				if (!neighbour.isVisited() && neighbour.isWalkable() && !neighbour.isNextToWall())
				{
					
					
					neighbour.setVisited(true);
					neighbour.setParent(node);
					
					neighbour.setPathLength(node.getPathLength() + node.distanceToTile(neighbour));
					queue.offer(neighbour);
				}
			}
			
		}
		//System.out.println("Found goal: "+foundGoal);
		return null;
	}
	
}
