package doharm.gui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import net.miginfocom.swing.MigLayout;
import doharm.gui.decorations.HealthBar;
import doharm.gui.decorations.ManaBar;
import doharm.gui.decorations.RageBar;
import doharm.gui.decorations.XPBar;
import doharm.gui.extras.CursorFactory;
import doharm.gui.extras.EjectorQueue;
import doharm.gui.input.KeyboardManager;
import doharm.gui.input.MenuButtonListener;
import doharm.gui.input.MouseManager;
import doharm.logic.AbstractGame;
import doharm.logic.chat.Message;
import doharm.logic.chat.MessagePart;
import doharm.logic.entities.characters.players.HumanPlayer;
import doharm.logic.entities.characters.states.CharacterStateType;
import doharm.logic.testing.TestGame;
import doharm.rendering.WorldRenderer;

public class MainWindow {
    private static final long serialVersionUID = 1L;
    private static final String TITLE = "OUR GAME";
    public static final int MAXIMIZED = 1;
    public static final int MINIMIZED = 2;

    private int state;
    private JFrame frame;
    private AbstractGame game;
    private MenuScreen menu;
    private JPanel canvas;
    private MouseManager mouseManager;
    private KeyboardManager keyboardManager;
    private JPanel southPanel;
    private JTextPane textPane;
    private EjectorQueue<String> messages;

    public MainWindow() {
	state = MAXIMIZED;
	canvas = new JPanel(); // Dummy canvas
	canvas.setLayout(new MigLayout("fill"));
	toggleSize();
	frame.setCursor(CursorFactory.getCursors().get(CharacterStateType.IDLE));
	MenuScreen welcomeMenu = new MenuScreen(new File("res/menu/background.png"), new MenuButtonListener(this), false);
	canvas.add(welcomeMenu, "grow");
	canvas.revalidate();
	welcomeMenu.requestFocusInWindow();
	welcomeMenu.addKeyListener(new KeyListener() {
	    @Override
	    public void keyTyped(KeyEvent e) {
	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
	    }

	    @Override
	    public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
		    System.exit(0); // TODO handle exit confirmation
		    break;
		case KeyEvent.VK_F11:
		    toggleSize();
		    break;
		case KeyEvent.VK_F12:
			setGame(new TestGame(MainWindow.this));
			break;
		}
	    }
	});
	
	//TODO REMOVE
	setGame(new TestGame(this));
    }

    public void setGame(AbstractGame game) {
	WorldRenderer renderer = new WorldRenderer(game);
	frame.remove(canvas);
	canvas = new WorldCanvas(game, renderer);
	state = MAXIMIZED;
	toggleSize();
	textPane = new JTextPane();
	textPane.setContentType("text/html");
	textPane.setOpaque(false);
	textPane.setFocusable(false);
	messages = new EjectorQueue<String>(10);
	canvas.setLayout(new BorderLayout());
	southPanel = new JPanel(new MigLayout("wrap 3, align center center", "[grow 33, sg][fill, grow 33, sg][align right, grow 33, sg]"));
	southPanel.setOpaque(false);
	southPanel.add(new HealthBar(game.getWorld().getHumanPlayer()), "cell 0 2, aligny bottom, grow");
	southPanel.add(new ManaBar(game.getWorld().getHumanPlayer()), "aligny bottom, grow");
	southPanel.add(new RageBar(game.getWorld().getHumanPlayer()), "aligny bottom, grow");
	southPanel.add(new XPBar(game.getWorld().getHumanPlayer()), "cell 0 3, span 3, grow, h 5");

	southPanel.add(textPane, "cell 0 0, grow, span 3, gapleft 55%");
	mouseManager = new MouseManager(game, renderer);
	keyboardManager = new KeyboardManager(this, game);
	this.game = game;

	canvas.add(southPanel, BorderLayout.SOUTH);
	addMessage(new Message(-1, new MessagePart("Welcome to the game")));
	new CursorThread().start();
	//frame.revalidate(); //Roland: Not defined in Java 6?
	frame.validate();
	canvas.requestFocusInWindow();
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
	System.out.println("Toggle Menu");
	if (menu == null) {
	    // Make only one menu, so we don't end up with lots
	    menu = new MenuScreen(new MenuButtonListener(this));
	    menu.setAlignmentX(0.5f);
	    menu.setAlignmentY(0.5f);
	}
	if (menu.isShowing()) {
	    canvas.remove(menu);
	    canvas.requestFocusInWindow();
	    canvas.addKeyListener(keyboardManager);
	    canvas.addMouseListener(mouseManager);
	} else {
	    canvas.add(menu);
	    canvas.setComponentZOrder(menu, 0);
	    canvas.setComponentZOrder(southPanel, 1);
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
	if (game != null) {
	    Collection<Message> messages = game.getWorld().getAndClearMessages();
	    for (Message message : messages)
		addMessage(message);
	}
	frame.repaint();
	//System.out.println(frame.getFocusOwner());
    }

    private DateFormat dateFormat = new SimpleDateFormat("[HH:MM]");

    public void addMessage(Message message) {
	// TODO FIX TEMP CRAPPY CODE
	String temp = message.getParts()[0].getText();
	// String hex = message.getParts()[0].getColour().
	String text = dateFormat.format(new Date()) + temp + "<br />";
	messages.offer(text);
	StringBuilder sb = new StringBuilder();
	sb.append("<div style=\"font-family:sans-serif; color:#00CC00\">");
	for (String s : messages) {
	    sb.append(s);
	}
	sb.append("</div>");
	textPane.setText(sb.toString());
    }

    private class CursorThread extends Thread {
	public void run() {
	    HumanPlayer human = game.getWorld().getHumanPlayer();
	    while (true) {
		frame.setCursor(CursorFactory.getCursors().get(human.getMouseIcon()));
		try {
		    Thread.sleep(100);
		} catch (InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	}
    }

}
