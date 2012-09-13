package doharm.logic.time;

import doharm.gui.view.MainWindow;
import doharm.logic.Game;

public class Clock extends Thread
{
	private static final int CLOCK_INTERVAL = 30;
	
	private MainWindow window;
	
	public Clock(MainWindow window)
	{
		this.window = window;
	}
	
	public void run()
	{
		while(true)
		{
			window.repaint();
			try 
			{
				Thread.sleep(CLOCK_INTERVAL);
			} 
			catch (InterruptedException e){}
		}
	}
}
