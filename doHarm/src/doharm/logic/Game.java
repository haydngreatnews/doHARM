package doharm.logic;

import doharm.logic.camera.Camera;
import doharm.logic.physics.Vector;
import doharm.logic.time.Time;
import doharm.logic.weather.Weather;
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
		
		//TODO Network-Sync
		Time time = new Time();
		Weather weather = new Weather();
		
		world = new World(worldName, networkMode,time,weather);
		camera = world.getCamera();
	}
	
	public Camera getCamera()
	{
		return camera;
	}
	

	public void run() 
	{
		world.process();
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
