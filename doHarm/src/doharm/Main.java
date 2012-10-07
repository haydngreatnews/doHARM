package doharm;

import doharm.gui.view.MainWindow;
import doharm.logic.Game;
import doharm.logic.time.Clock;
import doharm.net.NetworkMode;

/**
 * TODO
 * @author 
 */
public class Main 
{
	public static void main(String[] args)
	{
		if (args.length > 1 && args[0].equals("-server"))
			runServer();
		else
			runClient();
	}

	private static void runClient() 
	{
		
		Game game = new Game(NetworkMode.CLIENT);
		MainWindow window = new MainWindow(game);
		Clock clock = new Clock(game,window);
		clock.start();
		
	}

	private static void runServer() 
	{
		Game game = new Game(NetworkMode.SERVER);
		Clock clock = new Clock(game);
		clock.start();
	}
}
