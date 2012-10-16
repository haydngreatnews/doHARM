package doharm.gui.editor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EditorKeyListener implements KeyListener {
	EditorCanvas canvas;
	boolean down;
	boolean shiftDown = false;
	final int SHIFT_MULT = 5;

	public EditorKeyListener(EditorCanvas c) {
		canvas = c;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			if(shiftDown){
				canvas.changeOffset(0, SHIFT_MULT);
			} else {
				canvas.changeOffset(0, 1);
			}
			break;
		case KeyEvent.VK_UP:
			if(shiftDown){
				canvas.changeOffset(0, -SHIFT_MULT);
			} else {
				canvas.changeOffset(0, -1);
			}
			break;
		case KeyEvent.VK_LEFT:
			if(shiftDown){
				canvas.changeOffset(SHIFT_MULT, 0);
			} else {
				canvas.changeOffset(-1, 0);
			}
			break;
		case KeyEvent.VK_RIGHT:
			if(shiftDown){
				canvas.changeOffset(-SHIFT_MULT, 0);
			} else {
				canvas.changeOffset(-1, 0);
			}
			break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SHIFT:
			shiftDown = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SHIFT:
			shiftDown = false;
		}
	}

	public void moveMap() {

	}

}
