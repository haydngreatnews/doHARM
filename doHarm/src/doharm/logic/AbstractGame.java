package doharm.logic;

import doharm.logic.camera.Camera;
import doharm.logic.time.Clock;
import doharm.logic.time.Time;
import doharm.logic.weather.Weather;
import doharm.logic.world.World;
import doharm.net.NetworkMode;

public abstract class AbstractGame 
{
	private Camera camera;
	private World world;
	private NetworkMode networkMode;
	
	private Clock clock;
	
	protected AbstractGame(NetworkMode mode)
	{
		this.networkMode = mode;
		
		
		String worldName = "world1";
		
		
		
		world = new World(worldName, networkMode);
		camera = world.getCamera();
		
		
		clock = new Clock(this);
		clock.start();
		
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

	public Clock getClock() {
		return clock;
	}
}
