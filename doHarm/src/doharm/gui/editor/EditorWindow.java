package doharm.gui.editor;

import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;

public class EditorWindow extends JFrame {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new EditorWindow().setVisible(true);
	}
	
	public EditorWindow(){
		setLayout(new MigLayout());
		EditorCanvas  c = new EditorCanvas();
		add(c);
	}

}
