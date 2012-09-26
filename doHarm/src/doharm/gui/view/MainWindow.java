package doharm.gui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

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
	private MenuScreen menu;
	private WorldCanvas canvas;
	private MouseManager mouseManager;
	private KeyboardManager keyboardManager;

	public MainWindow(Game game) {
		// TODO window close

		WorldRenderer renderer = new WorldRenderer(game);
		canvas = new WorldCanvas(game,renderer);

		canvas.setLayout(new BorderLayout());
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
	
	public void toggleMenu(){
		if (menu == null){
			//Make only one menu, so we don't end up with lots
			menu = new MenuScreen();
		}
		if(menu.isShowing()){
			canvas.remove(menu);
			canvas.requestFocusInWindow();
			canvas.addKeyListener(keyboardManager);
			canvas.addMouseListener(mouseManager);
		} else {
			canvas.add(menu, BorderLayout.CENTER);
			menu.requestFocusInWindow();
			menu.addKeyListener(keyboardManager);
			frame.removeMouseListener(mouseManager);
		}
		canvas.revalidate();
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
