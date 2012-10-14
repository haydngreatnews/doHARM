package doharm.logic.testing;

import doharm.gui.view.MainWindow;
import doharm.logic.AbstractGame;
import doharm.net.NetworkMode;

public class TestGame extends AbstractGame
{
	/**
	 * Create game for testing purposes only!
	 */
	public TestGame(MainWindow window)
	{
		super(NetworkMode.OFFLINE);
		getClock().setWindow(window);
	}
}
