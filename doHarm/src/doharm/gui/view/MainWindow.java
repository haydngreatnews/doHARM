package doharm.gui.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import doharm.gui.input.KeyboardManager;
import doharm.gui.input.MouseManager;
import doharm.logic.Game;
import doharm.rendering.WorldRenderer;

public class MainWindow{
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "OUR GAME";
	public static final int MAXIMIZED = 1;
	public static final int MINIMIZED = 2;

	private int state;
	private JFrame frame;
	private Game game;
	private WorldCanvas canvas;
	private MouseManager mouseManager;
	private KeyboardManager keyboardManager;

	public MainWindow(Game game) {
		frame = new JFrame(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		// TODO window close

		WorldRenderer renderer = new WorldRenderer(game);
		canvas = new WorldCanvas(game,renderer);
		
		mouseManager = new MouseManager(game);
		keyboardManager = new KeyboardManager(this);
		
		// listener and handle
		// exit confirmation
		this.game = game;
		// TODO add option for switching between fullscreen
		state = MAXIMIZED;
		// FULLSCREEN CODE
		// //////////////////////////////////////////////////////////////////
		frame.add(canvas);
		frame.setUndecorated(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(0, 0, screenSize.width, screenSize.height);
		// //////////////////////////////////////////////////////////////////

		frame.setVisible(true);

		addListeners();
	}

	// public MainWindow(Game g) {

	//new MainWindow(g, MAXIMIZED);
	// }

	public void toggleSize() {
		frame.dispose();
		frame = new JFrame();
		frame.add(canvas);
		if (state == MINIMIZED) {
			frame.setUndecorated(true);
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			frame.setBounds(0, 0, screenSize.width, screenSize.height);
			state = MAXIMIZED;
		} else if (state == MAXIMIZED) {
			frame.setUndecorated(false);
			frame.setBounds(0, 0, 800, 600);
			//frame.pack();
			state = MINIMIZED;
		}
		addListeners();
		frame.setVisible(true);
	}

	private void addListeners() {
		frame.addMouseListener(mouseManager);
		frame.addMouseMotionListener(mouseManager);
		frame.addKeyListener(keyboardManager);
		
	}

	public JFrame getFrame(){
		return frame;
	}
	public void repaint(){
		frame.repaint();
	}

}
