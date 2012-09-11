package doharm.gui.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import doharm.gui.input.KeyboardManager;
import doharm.gui.input.MouseManager;
import doharm.logic.Game;
import doharm.rendering.WorldRenderer;

public class MainWindow extends JFrame
{
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "OUR GAME";

	public MainWindow(Game game)
	{
		super(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //TODO window close listener and handle exit confirmation
		
		WorldRenderer renderer = new WorldRenderer(game);
		WorldCanvas canvas = new WorldCanvas(renderer);
		add(canvas);

		addKeyListener(new KeyboardManager());
		MouseManager mouseManager = new MouseManager(game);
		addMouseListener(mouseManager);
		addMouseMotionListener(mouseManager);
		
		
		//TODO add option for switching between fullscreen
		//FULLSCREEN CODE
		////////////////////////////////////////////////////////////////////
		setUndecorated(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0,0,screenSize.width, screenSize.height);
		////////////////////////////////////////////////////////////////////
		
		//WINDOWED CODE
		////////////////////////////////////////////////////////////////////
		//...
		
		//pack();
		////////////////////////////////////////////////////////////////////
		
		
		
		setVisible(true);
		
		
		
		
	}
}
