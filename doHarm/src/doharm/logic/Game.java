package doharm.logic;

import doharm.logic.camera.Camera;
import doharm.logic.physics.Vector;
import doharm.logic.world.World;
import doharm.net.NetworkMode;

public class Game 
{
	private Camera camera;
	private World world;
	private NetworkMode networkMode;
	
	
	public Game(NetworkMode mode)
	{
		this.networkMode = mode;
		String worldName = "world1";
		world = new World(worldName, networkMode);
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

	public NetworkMode getNetworkMode()
	{
		return networkMode;
	}
}
