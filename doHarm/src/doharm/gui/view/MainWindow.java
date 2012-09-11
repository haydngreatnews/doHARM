package doharm.gui.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import doharm.gui.input.WindowKeyListener;
import doharm.logic.Game;
import doharm.rendering.WorldRenderer;

public class MainWindow extends JFrame
{
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "OUR GAME";

	public MainWindow(Game game)
	{
		super(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //TODO window close listener
		
		WorldRenderer renderer = new WorldRenderer(game);
		Canvas canvas = new Canvas(renderer);
		add(canvas);


		setUndecorated(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0,0,screenSize.width, screenSize.height);
		
		addKeyListener(new WindowKeyListener());
		
		//pack();
		setVisible(true);
		
		
		
		
	}
}
