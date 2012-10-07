package doharm.gui.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import doharm.gui.view.MainWindow;
import doharm.logic.camera.Camera;

public class KeyboardManager implements KeyListener
{
	private MainWindow main;
	private Camera camera;
	
	public KeyboardManager(MainWindow m, Camera camera){
		this.camera = camera;
		main = m;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
		
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
		}
	}
	
}
