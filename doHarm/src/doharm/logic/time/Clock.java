package doharm.logic.time;

import doharm.gui.view.MainWindow;
import doharm.logic.Game;

public class Clock extends Thread
{
	private static final int CLOCK_INTERVAL = 30;
	
	private Game game;
	private MainWindow window;
	
	public Clock(Game game, MainWindow window)
	{
		this.game = game;
		this.window = window;
	}
	
	public void run()
	{
		while(true)
		{
			if (!game.isRunning())
				break;
			game.run();
			window.repaint();
			try 
			{
				Thread.sleep(CLOCK_INTERVAL);
			} 
			catch (InterruptedException e){}
		}
	}
}
