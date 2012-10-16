package doharm.gui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import doharm.gui.input.PassThroughListener;
import doharm.logic.AbstractGame;
import doharm.logic.chat.Message;
import doharm.logic.chat.MessagePart;
import doharm.logic.entities.characters.players.HumanPlayer;
import doharm.logic.entities.characters.states.CharacterStateType;
import doharm.logic.inventory.Inventory;
import doharm.logic.testing.TestGame;
import doharm.rendering.WorldRenderer;

/**
 * The Main window of our game, it supports toggling of menus, window sizes and
 * inventories, and handles most of the human computer interaction
 * 
 * @author Haydn Newport
 * 
 */
public class MainWindow {
    private static final long serialVersionUID = 1L;
    private static final String TITLE = "OUR GAME";
    public static final int MAXIMIZED = 1;
    public static final int MINIMIZED = 2;

    private int state;
    private JFrame frame;
    private AbstractGame game;
    private MenuScreen menu;
    private InventoryPanel inventory;
    private JPanel canvas;
    private HumanPlayer player;
    private JButton levelUpButton;
    private MouseManager mouseManager;
    private PassThroughListener passThrough;
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

	// TODO REMOVE

    }

    /**
     * Set game, used to start playing once a game has been created and joined
     * by the player
     * 
     * @param game
     *            The game to draw and start playing
     */
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
	player = game.getWorld().getHumanPlayer();
	southPanel.add(new HealthBar(player), "cell 0 2, aligny bottom, grow");
	southPanel.add(new ManaBar(player), "aligny bottom, grow");
	southPanel.add(new RageBar(player), "aligny bottom, grow");
	southPanel.add(new XPBar(player), "cell 0 3, span 3, grow, h 5");
	southPanel.add(levelUpButton = new JButton(), "cell 0 0, shrink,span 3, split 2, aligny bottom, alignx left");
	setupLevelButton(levelUpButton);
	southPanel.add(textPane, "grow, gapleft 50%");
	southPanel.add(new BeltPanel(player), "cell 0 4, span 3, w 55%, alignx center");
	mouseManager = new MouseManager(game, renderer);
	keyboardManager = new KeyboardManager(this, game);
	this.game = game;
	canvas.add(southPanel, BorderLayout.SOUTH);
	addMessage(new Message(-1, false, new MessagePart("Welcome to the game")));
	new CursorThread().start();
	new GuiTasksThread().start();
	frame.validate();
	passThrough = new PassThroughListener(canvas);
	canvas.requestFocusInWindow();

	addListeners(); // NEEDED!
    }

    // Helper method for tidiness - this button is nasty
    private void setupLevelButton(JButton button) {
	String path = "res/ui/levelUpButton";
	try {
	    Image up = ImageIO.read(new File(path + ".png"));
	    Image down = ImageIO.read(new File(path + "-down.png"));
	    Image disabled = ImageIO.read(new File(path + "-disabled.png"));
	    button.setIcon(new ImageIcon(up));
	    button.setPressedIcon(new ImageIcon(down));
	    button.setDisabledIcon(new ImageIcon(disabled));
	    button.setBorderPainted(false);
	    button.setFocusable(false);
	    button.setContentAreaFilled(false);
	    button.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    new LevelUpMenu(player).setVisible(true);
		}
	    });
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Switch this window between windowed and fullscreen
     */
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

	frame.setVisible(true);
    }

    /**
     * Toggle whether the menu is showing on this window or not
     */
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
    
    /**
     * Toggle whether the menu is showing on this window or not
     */
    public void toggleInventory() {
	System.out.println("Toggle Menu");
	if (inventory == null) {
	    // Make only one menu, so we don't end up with lots
	    inventory = new InventoryPanel(player);
	    inventory.setAlignmentX(0.5f);
	    inventory.setAlignmentY(0.5f);
	}
	if (inventory.isShowing()) {
	    canvas.remove(inventory);
	    canvas.requestFocusInWindow();
	    canvas.addKeyListener(keyboardManager);
	    canvas.addMouseListener(mouseManager);
	} else {
	    canvas.add(inventory);
	    canvas.setComponentZOrder(inventory, 0);
	    canvas.setComponentZOrder(southPanel, 1);
	    inventory.requestFocusInWindow();
	    inventory.addKeyListener(keyboardManager);
	    canvas.removeMouseListener(mouseManager);
	}
	canvas.revalidate();
    }

    // Helper to add the various listeners around the place after a toggleSize
    private void addListeners() {
	canvas.addMouseListener(mouseManager);
	canvas.addMouseMotionListener(mouseManager);
	canvas.addKeyListener(keyboardManager);
	canvas.requestFocusInWindow();
	if (textPane != null) {
	    textPane.addMouseListener(passThrough);
	    textPane.addMouseMotionListener(passThrough);
	}
	frame.addKeyListener(keyboardManager);
    }

    public void repaint() {
	frame.repaint();
    }

    private DateFormat dateFormat = new SimpleDateFormat("[HH:MM");

    /**
     * Add a message to the message panel of the window, allows coloring of
     * messages, and generally swanks them up a bit. NOTE:Only a set number of
     * messages may exist in the pane at one time (by design) due to the
     * EjectorQueue it uses
     * 
     * @param message
     *            The Message to be added to the panel
     */
    public void addMessage(Message message) {
	// TODO FIX TEMP CRAPPY CODE
	StringBuilder msg = new StringBuilder();
	for (MessagePart s : message.getParts()) {
	    msg.append("<span style=\"color:#");
	    msg.append(Integer.toHexString(s.getColour().getRGB()).substring(0, 6));
	    msg.append("\">");
	    msg.append(s.getText());
	    msg.append("</span>");
	}
	String sender;
	if (message.getSenderID() == -1) {
	    sender = "System";
	} else {
	    sender = game.getWorld().getPlayerFactory().getEntity(message.getSenderID()).getName();
	}
	String text = dateFormat.format(new Date()) + " " + sender + "]:" + msg.toString() + "<br />";
	messages.offer(text);
	StringBuilder sb = new StringBuilder();
	sb.append("<div style=\"font-family:sans-serif; color:#FFFFFF\">");
	for (String s : messages) {
	    sb.append(s);
	}
	sb.append("</div>");
	try {
	    textPane.setText(sb.toString());
	} catch (Exception e) {
	    textPane.setText(""); // FIX THE MESSAGES^
	}
    }

    private class GuiTasksThread extends Thread {
	public void run() {
	    while (true) {
		try {
		    Thread.sleep(200);
		} catch (InterruptedException e) {
		}
		if (game != null) {
		    Collection<Message> messages = game.getWorld().getAndClearMessages();
		    for (Message message : messages)
			addMessage(message);
		}
		if (player.isAlive() && player.getCharacterClass().getAttributePoints() != 0) {
		    levelUpButton.setEnabled(true);
		} else {
		    levelUpButton.setEnabled(false);
		}
	    }
	}
    }

    private class CursorThread extends Thread {
	public void run() {
	    HumanPlayer human = game.getWorld().getHumanPlayer();
	    while (true) {
		if (!human.isAlive()) {
		    frame.setCursor(CursorFactory.getCursors().get(CharacterStateType.IDLE));
		} else {
		    frame.setCursor(CursorFactory.getCursors().get(human.getMouseIcon()));
		    textPane.setCursor(CursorFactory.getCursors().get(human.getMouseIcon()));
		}
		try {
		    Thread.sleep(100);
		} catch (InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	}
    }

    public boolean hasGame() {
	return (game != null);
    }

}
