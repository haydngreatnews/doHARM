package doharm.gui.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import doharm.gui.view.MainWindow;

public class KeyboardManager implements KeyListener
{
	MainWindow main;
	public KeyboardManager(MainWindow m){
		main = m;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
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
			main.showMenu();
			break;
		}
			
		
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		
	}
	
}
