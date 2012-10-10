package doharm.gui.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JTextPane;

import net.miginfocom.swing.MigLayout;
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
		canvas.setLayout(new MigLayout("fill, nogrid"));
		canvas.add(textPane, "y container.h-200, x 70%, w 30%");
		canvas.add(new HealthBar(), "y 5, x container.w/2-50");
		mouseManager = new MouseManager(game, renderer);
		keyboardManager = new KeyboardManager(this,game.getCamera());
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
			canvas.add(menu, "align 50% 50%");
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

	public void repaint(Collection<String> messages) {
		for (String message: messages)
			addMessage(message);
		frame.repaint();
	}

	public void addMessage(String text) {
		messages.offer(text);
		StringBuilder sb = new StringBuilder();
		sb.append("<div style=\"font-family:sans-serif; font-weight:bold\">");
		for (String s : messages) {
			sb.append(s);
		}
		sb.append("</div>");
		textPane.setText(sb.toString());
	}

}
