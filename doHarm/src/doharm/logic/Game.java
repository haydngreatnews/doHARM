package doharm.logic;

import doharm.logic.camera.Camera;
import doharm.logic.world.World;

public class Game 
{
	private Camera camera;
	private World world;
	
	
	public Game()
	{
		camera = new Camera();		
		world = new World(camera);
	}
	
	public Camera getCamera()
	{
		return camera;
	}
	

	public void run() 
	{
		world.moveEntities();
	}

	public World getWorld()
	{
		return world;
	}

	public void mouseEvent(int x, int y) 
	{
		world.moveHumanPlayer(x, y);	
	}
}
