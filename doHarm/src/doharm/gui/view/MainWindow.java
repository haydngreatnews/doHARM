package doharm.gui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import net.miginfocom.swing.MigLayout;
import doharm.gui.decorations.HealthBar;
import doharm.gui.decorations.ManaBar;
import doharm.gui.decorations.RageBar;
import doharm.gui.extras.EjectorQueue;
import doharm.gui.input.KeyboardManager;
import doharm.gui.input.MenuButtonListener;
import doharm.gui.input.MouseManager;
import doharm.logic.Game;
import doharm.rendering.WorldRenderer;

public class MainWindow {
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
	private JTextPane textPane;
	private EjectorQueue<String> messages;

	public MainWindow(Game game) {
		WorldRenderer renderer = new WorldRenderer(game);
		canvas = new WorldCanvas(game, renderer);
		textPane = new JTextPane();
		textPane.setContentType("text/html");
		textPane.setOpaque(false);
		textPane.setFocusable(false);
		messages = new EjectorQueue<String>(10);
		canvas.setLayout(new BorderLayout());
		JPanel southPanel = new JPanel(new MigLayout("wrap 3, align center center","[][][align right]"));
		southPanel.setOpaque(false);
		southPanel.add(new HealthBar(game.getWorld().getHumanPlayer()), "cell 2 1,split 3");
		southPanel.add(new ManaBar(game.getWorld().getHumanPlayer()));
		southPanel.add(new RageBar(game.getWorld().getHumanPlayer()));

		southPanel.add(textPane, "cell 3 1");
		mouseManager = new MouseManager(game, renderer);
		keyboardManager = new KeyboardManager(this,game.getCamera());
		this.game = game;
		state = MAXIMIZED;
		toggleSize();
		canvas.add(southPanel, BorderLayout.SOUTH);
		addMessage("Welcome to the game");
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
			state = MINIMIZED;
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addListeners();
		frame.addKeyListener(keyboardManager);
		frame.setVisible(true);
	}

	public void toggleMenu() {
		if (menu == null) {
			// Make only one menu, so we don't end up with lots
			menu = new MenuScreen(new MenuButtonListener(this));
		}
		if (menu.isShowing()) {
			canvas.remove(menu);
			canvas.requestFocusInWindow();
			canvas.addKeyListener(keyboardManager);
			canvas.addMouseListener(mouseManager);
		} else {
			canvas.add(menu, BorderLayout.CENTER);
			menu.requestFocusInWindow();
			menu.addKeyListener(keyboardManager);
			canvas.removeMouseListener(mouseManager);
		}
		canvas.revalidate();
	}

	private void addListeners() {
		canvas.addMouseListener(mouseManager);
		canvas.addMouseMotionListener(mouseManager);
		canvas.addKeyListener(keyboardManager);
		canvas.requestFocusInWindow();
	}

	public JFrame getFrame() {
		return frame;
	}

	public void repaint() {
		frame.repaint();
	}
	
	private DateFormat dateFormat = new SimpleDateFormat("[HH:MM]");

	public void addMessage(String text) {
		text = dateFormat.format(new Date()) + text;
		messages.offer(text);
		StringBuilder sb = new StringBuilder();
		sb.append("<div style=\"font-family:sans-serif; color:#00CC00\">");
		for (String s : messages) {
			sb.append(s);
		}
		sb.append("</div>");
		textPane.setText(sb.toString());
	}

}
