package doharm.logic;


import doharm.gui.view.MainWindow;
import doharm.logic.world.World;
import doharm.net.NetworkMode;
import doharm.net.client.Client;

/**
 * @author Roland Bewick and Adam McLaren (300248714)
 */
public class ClientGame extends AbstractGame
{
	private Client client;
	private MainWindow window;	
	
	/**
	 * Create game for CLIENT
	 * @param window
	 * @param client
	 */
	public ClientGame(MainWindow window, Client client)
	{
		super(NetworkMode.CLIENT);
		getClock().setWindow(window);
		this.client = client;
		this.window = window;
	}
	
	@Override
	public void run()
	{
		client.processIncomingPackets();
		World newWorld = client.updateWorld(getWorld(), this);
		if (newWorld != null)
		{
			if (getWorld() == null)
			{
				setWorld(newWorld);
				setCamera(newWorld.getCamera());
				System.out.println("GAME GO");
				window.setGame(this);
			}
			setWorld(newWorld);
		}
		
		if (getWorld() != null)
		{
			super.run();
			client.dispatchAction(getWorld());
		}
	}
	
}
