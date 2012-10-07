package doharm.logic.time;

import doharm.gui.view.MainWindow;
import doharm.logic.Game;

public class Clock extends Thread
{
	private static final int CLOCK_INTERVAL = 30;
	
	private MainWindow window;

	private Game game;
	
	
	public Clock(Game game)
	{
		this(game,null);
	}
			
	public Clock(Game game, MainWindow window)
	{
		this.game = game;
		this.window = window;
	}
	
	public void run()
	{
		while(true)
		{
			game.run();
			if (window != null)
				window.repaint();
			try 
			{
				Thread.sleep(CLOCK_INTERVAL);
			} 
			catch (InterruptedException e){}
		}
	}
}
