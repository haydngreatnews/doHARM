package doharm.logic.time;

import doharm.gui.view.MainWindow;
import doharm.logic.AbstractGame;

public class Clock extends Thread
{
	public static final int CLOCK_INTERVAL = 30;
	
	private MainWindow window;
	private AbstractGame game;
			
	public Clock(AbstractGame game)
	{
		this.game = game;
	}
	
	public void setWindow(MainWindow window) 
	{
		this.window = window;
	}
	
	public void run()
	{
		while(true)
		{
			
			if (window != null)
				window.repaint(); //calls game.run(); immediately before painting, as we can't guarantee when the game will be repainted.
			else
				game.run();
			try 
			{
				Thread.sleep(CLOCK_INTERVAL);
			} 
			catch (InterruptedException e){}
		}
	}

	
}
