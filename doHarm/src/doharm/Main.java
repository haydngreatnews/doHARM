package doharm;

import doharm.gui.view.MainWindow;
import doharm.logic.Game;
import doharm.logic.time.Clock;

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
		
		Game game = new Game();
		MainWindow window = new MainWindow(game);
		Clock clock = new Clock(window);
		clock.start();
		
	}

	private static void runServer() 
	{
		//TODO load world if not creating a new one.
		
	}
}
