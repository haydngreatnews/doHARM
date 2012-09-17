package doharm.gui.input;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The transient button listener is a listener which always returns focus to the
 * component given in it's constructor upon completion of the actionPerformed
 * method
 * 
 * @author Haydn Newport
 * 
 */
public class TransientButtonListener implements ActionListener {
	private Component parent = null;
	/**
	 * This button listener will return focus in the window to the element supplied to this constructor
	 * @param parent The Component to return focus to at the end of an actionPerformed
	 */
	public TransientButtonListener(Component parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {

		} finally {
			if (parent != null)
				parent.requestFocusInWindow();
		}

	}
	
	//I suspect this may be necessary later
//	public void setParent(Component parent){
//		this.parent = parent;
//	}

}
