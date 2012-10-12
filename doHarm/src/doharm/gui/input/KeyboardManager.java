package doharm.gui.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import doharm.gui.view.MainWindow;
import doharm.logic.Game;
import doharm.logic.camera.Camera;

public class KeyboardManager implements KeyListener
{
	private MainWindow main;
	private Camera camera;
	private boolean altDown;
	private Game game;
	
	public KeyboardManager(MainWindow m, Game game){
		this.game = game;
		this.camera = game.getCamera();
		main = m;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
		if (altDown)
		{
			game.getWorld().getHumanPlayer().getTaunts().taunt(e.getKeyChar());
		}
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_ESCAPE:
			System.exit(0); //TODO handle exit confirmation
			break;
		case KeyEvent.VK_F11:
			main.toggleSize();
			break;
		case KeyEvent.VK_F10:
			main.toggleMenu();
			break;
			
		case KeyEvent.VK_ALT:
			altDown = true;
		
		
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
		}
	}
	
}
