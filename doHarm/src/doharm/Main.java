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
		if (args.length > 0 && args[0].equals("-server"))
		{
			int port = 0;
			try
			{
				port = Integer.parseInt(args[1]);
			}
			catch(Exception e)
			{
				System.err.println("Error: Please specify port number.");
				System.err.println("Usage: java -jar doHarm.jar -server <portnumber>");
				System.exit(1);
			}
			
			runServer(port);
		}
		else
			runClient();
	}

	private static void runClient() 
	{
		
		//Game game = new Game(NetworkMode.CLIENT);
		new MainWindow();//game);
		//Clock clock = new Clock(game,window);
		//clock.start();
		
	}

	private static void runServer(int port) 
	{
		new Game(port);
	}
}
