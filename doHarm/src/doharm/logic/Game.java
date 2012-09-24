package doharm.logic;

import doharm.logic.camera.Camera;
import doharm.logic.physics.Vector;
import doharm.logic.world.World;

public class Game 
{
	private Camera camera;
	private World world;
	
	
	public Game()
	{
				
		String worldName = "world1";
		world = new World(worldName);
		camera = world.getCamera();
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

	
}
