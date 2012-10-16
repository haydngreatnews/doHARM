package doharm.gui.editor;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class EditorMouseListener implements MouseListener, MouseMotionListener {
    EditorWindow window;

    public EditorMouseListener(EditorWindow editorWindow) {
	window = editorWindow;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	window.getCanvas().setTileUnder(e.getX(), e.getY(), window.getCurrentTileType());
    }

    @Override
    public void mousePressed(MouseEvent e) {
    	window.getCanvas().setTileUnder(e.getX(), e.getY(), window.getCurrentTileType());	

    }

    @Override
    public void mouseReleased(MouseEvent e) {
	
	
    }

    @Override
    public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub

    }

	@Override
	public void mouseDragged(MouseEvent e) {
		window.getCanvas().setTileUnder(e.getX(), e.getY(), window.getCurrentTileType());		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
