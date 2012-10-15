package doharm.gui.input;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import doharm.gui.view.WorldCanvas;

public class PassThroughListener implements MouseListener, MouseMotionListener {
	MouseManager receiver;
	WorldCanvas canvas;
	public PassThroughListener(WorldCanvas c){
		canvas = c;
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		passOn(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		passOn(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		passOn(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		passOn(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		passOn(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		passOn(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		passOn(e);
	}
	
	public void passOn(MouseEvent e){
		Point realPoint = canvas.getMousePosition(true);
		new MouseEvent((Component)(e.getSource()), e.getID(), e.getWhen(), e.getModifiers(), (int)(realPoint.getX()), (int)(realPoint.getY()), e.getClickCount(), false);
		canvas.dispatchEvent(e);
	}

}
