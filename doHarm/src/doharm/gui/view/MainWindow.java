package doharm.gui.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;
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
		// TODO window close

		WorldRenderer renderer = new WorldRenderer(game);
		canvas = new WorldCanvas(game,renderer);
		canvas.setLayout(new MigLayout());
		mouseManager = new MouseManager(game,renderer);
		keyboardManager = new KeyboardManager(this);
		
		
		this.game = game;

		state = MAXIMIZED;
		
		toggleSize();
	}

	public void toggleSize() {
		if (frame != null)
			frame.dispose();
		frame = new JFrame(TITLE);
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //TODO change to window listener and handle exit confirmation

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
