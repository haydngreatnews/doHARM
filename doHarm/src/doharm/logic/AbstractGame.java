package doharm.logic;

import doharm.logic.camera.Camera;
import doharm.logic.entities.characters.Character;
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
	private boolean ended;
	
	private Clock clock;
	
	protected AbstractGame(NetworkMode mode)
	{
		this.networkMode = mode;
		
		
		String worldName = "world2";
		
		
		
		world = new World(this, worldName, networkMode);
		camera = world.getCamera();
		
		
		clock = new Clock(this);
		clock.start();
		ended = false;
	}
	
	public Camera getCamera()
	{
		return camera;
	}
	

	public void run() 
	{
		if (ended)
			return;
		
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

	public void end(Character winner) 
	{
		ended = true;
	}
	
	public boolean hasEnded()
	{
		return ended;
	}
}
