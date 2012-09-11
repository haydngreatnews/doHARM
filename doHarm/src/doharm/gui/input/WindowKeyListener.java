package doharm.gui.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WindowKeyListener implements KeyListener
{

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
			System.exit(0);
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		
	}
	
}
