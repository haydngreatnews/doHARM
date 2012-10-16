package doharm.logic;

import doharm.logic.camera.Camera;
import doharm.logic.entities.characters.Character;
import doharm.logic.time.Clock;
import doharm.logic.world.World;
import doharm.net.NetworkMode;

/**
 * 
 * @author Roland Bewick and Adam McLaren (300248714)
 *
 */
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
		
		

		String worldName = "world5";

		
		
		if (networkMode != NetworkMode.CLIENT)
			world = new World(this, worldName, networkMode);
		else
			world = null;
		
		if (networkMode != NetworkMode.OFFLINE)
			camera = null;
		else
			camera = world.getCamera();
		
		clock = new Clock(this);
		ended = false;
	}
	
	public Camera getCamera()
	{
		return camera;
	}
	
	public void setCamera(Camera camera)
	{
		this.camera = camera;
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
	
	public void setWorld(World newWorld)
	{
		world = newWorld;
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
	
	public void start()
	{
		clock.start();
	}
}
