package doharm.gui.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import doharm.gui.view.MainWindow;
import doharm.logic.AbstractGame;
import doharm.logic.camera.Camera;
import doharm.logic.entities.items.usable.UsableItem;

public class KeyboardManager implements KeyListener
{
	private MainWindow window;
	private Camera camera;
	private boolean altDown;
	private AbstractGame game;
	private boolean jDown;
	
	public KeyboardManager(MainWindow m, AbstractGame game){
		this.game = game;
		this.camera = game.getCamera();
		window = m;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
		if (altDown)
		{
			if (game.getWorld().getHumanPlayer() != null)
				game.getWorld().getHumanPlayer().getTaunts().taunt(e.getKeyChar());
		}
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		for (int i = KeyEvent.VK_1; i <= KeyEvent.VK_8; i++)
		{
			if (e.getKeyCode() == i)
			{
				if (!jDown)
				{
					if (game.getWorld().getHumanPlayer() != null)
					{
						UsableItem item = game.getWorld().getHumanPlayer().getInventory().getBelt().getItem(i-KeyEvent.VK_1);
						if (item != null)
							game.getWorld().getHumanPlayer().useItem(item);
					}
				}
				else
				{
					if (game.getWorld().getHumanPlayer() != null)
						game.getWorld().getHumanPlayer().joinAlliance(i-KeyEvent.VK_1);
				}
			}
		}
		
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_ESCAPE:
			System.exit(0); //TODO handle exit confirmation
			break;
			
		case KeyEvent.VK_F9:
			if (altDown && game.getWorld().getHumanPlayer() != null) //test suicide method
				game.getWorld().getHumanPlayer().die(game.getWorld().getHumanPlayer());
			break;
		case KeyEvent.VK_F11:
			window.toggleSize();
			break;
		case KeyEvent.VK_F10:
			window.toggleMenu();
			break;
			
		
			
		
		case KeyEvent.VK_J:
			jDown = true;
			break;
			
		case KeyEvent.VK_ALT:
			altDown = true;
			break;
		
		}
			
		
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_LEFT: case KeyEvent.VK_A:
			camera.turnLeft();
			break;
		case KeyEvent.VK_RIGHT: case KeyEvent.VK_D:
			camera.turnRight();
			break;
		case KeyEvent.VK_ALT:
			altDown = false;
			break;
		case KeyEvent.VK_J:
			jDown = false;
			break;
		}
	}
	
}
