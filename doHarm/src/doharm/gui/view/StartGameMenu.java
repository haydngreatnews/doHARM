package doharm.gui.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import doharm.gui.decorations.ColorIcon;
import doharm.logic.ClientGame;
import doharm.logic.AbstractGame;
import doharm.logic.entities.characters.classes.CharacterClassType;
import doharm.logic.time.Clock;
import doharm.net.client.Client;

public class StartGameMenu extends JFrame {
	private Map<CharacterClassType, JRadioButton> classButtons;
	private JTextField nameField, serverField, portField;
	private ButtonGroup classRadios, colorRadios;
	private String lastError = "";
	private MainWindow parent;

	public StartGameMenu(MainWindow parent) {
		super("Get started!");
		this.parent = parent;
		setLayout(new MigLayout("fill, wrap 2"));
		// setBounds(0, 0, 800, 600);
		add(new JLabel("Name:"));
		nameField = new JTextField(20);
		add(nameField, "growx");
		add(new JLabel("Class:"), "aligny top");
		classButtons = new HashMap<CharacterClassType, JRadioButton>();
		classRadios = new ButtonGroup();
		for (CharacterClassType c : CharacterClassType.values()) {
			String name = c.toString().substring(0, 1)
					+ c.toString().substring(1).toLowerCase();
			classButtons.put(c, new JRadioButton(name));
			add(classButtons.get(c), "flowy, cell 1 1, align center, sg but");
			classButtons.get(c).setActionCommand(c.toString());
			classRadios.add(classButtons.get(c));
			// If this is the last player character class, then leave
			if (c == CharacterClassType.getLastPlayerClass())
				break;
		}
		classRadios.setSelected(classButtons
				.get(CharacterClassType.values()[0]).getModel(), true);
		add(new JLabel("Color:"), "");
		colorRadios = new ButtonGroup();
		int[] colors = new int[] { 0x0000DD, 0x00DD00, 0xDD0000, 0xFFFF00,
				0x00DDDD, 0xDD00DD };
		String constraints = "skip 1, span 2, split 6, flowx";
		for (int c : colors) {
			Color color = new Color(c);
			JRadioButton b = new JRadioButton(new ColorIcon(color));
			b.setSelectedIcon(new ColorIcon(color, true));
			b.setActionCommand("" + c);
			colorRadios.add(b);
			add(b, constraints);
			constraints = "";
			colorRadios.setSelected(b.getModel(), true);
		}
		add(new JLabel("Server:"));
		serverField = new JTextField("127.0.0.1", 20);
		add(serverField, "growx");
		add(new JLabel("Port:"));
		add(portField = new JTextField("8080", 5));
		JButton startButton = new JButton("Start");
		JButton cancelButton = new JButton("Cancel");
		add(startButton);
		add(cancelButton);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doStart();
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: Try a little harder
				System.exit(0);
			}
		});

		pack();
		setVisible(true);
	}

	/*
	 * Check the following for validity - Name != null or "" - Class is selected
	 * and valid (should be safe, but just in case) - IP/Hostname in serverField
	 * is valid - Possible reachable? - Port in portField is numeric, <65536
	 */

	private boolean checkValues() {
		String name = nameField.getText();
		if (name == null || name.equals("")) {
			lastError = "Please enter a character name";
			return false;
		}
		System.out.println("Name OK");
		ButtonModel clsModel = classRadios.getSelection();
		if (clsModel == null)
			return false;
		String selClass = clsModel.getActionCommand();
		if (selClass == null || selClass.equals(""))
			return false;
		try {
			CharacterClassType.valueOf(selClass);
		} catch (NullPointerException e) {
			System.out.println(e);
			lastError = "Please select a character class";
			return false;
		} catch (IllegalArgumentException e) {
			System.out.println(e);
			lastError = "Please select a character class";
			return false;
		}
		System.out.println("Class OK");
		String serverName = serverField.getText();
		if (serverName == null)
			return false;
		if (!(serverName.matches("^([0-9]{0,3}\\.){1,3}[0-9]{0,3}$") || serverName
				.matches("^[A-Za-z][\\w-]+(\\.[\\w-]+)*$"))) {
			lastError = (serverName + " is an invalid server name");
			System.out.println("Matches ip form = "
					+ serverName.matches("^([0-9]{0,3}\\.){1,3}[0-9]{0,3}$"));
			System.out.println("Matches hostname form = "
					+ serverName.matches("^[A-Za-z][\\w-]+(\\.[\\w-]+)*$"));
			return false;
		}
		System.out.println("Server Name OK");
		String portVal = portField.getText();
		if (portVal == null)
			return false;
		if (!portVal.matches("[0-9]+")) {
			lastError = ("Port value " + portVal + " is non-numeric");
			return false;
		}
		int port = Integer.valueOf(portVal);
		if (port > 65535 || port == 0) {
			lastError = ("Port number invalid, port does not exist, pick a number between 1-65535");
			return false;
		}
		System.out.println("Port OK");
		return true;
	}

	private void doStart() {
		if (checkValues()) {
			String name = nameField.getText();
			CharacterClassType selClass = CharacterClassType
					.valueOf(classRadios.getSelection().getActionCommand());
			String serverName = serverField.getText();
			int port = Integer.valueOf(portField.getText());
			Color colour = new Color(Integer.parseInt(colorRadios
					.getSelection().getActionCommand()));
		
			Client client = new Client();			
			String result = client.connect(new InetSocketAddress(serverName,port), name, colour, selClass);
			if(result != null){
				JOptionPane.showMessageDialog(this, result, "Connect Error",
				JOptionPane.ERROR_MESSAGE);
				return;
			}

			AbstractGame game = new ClientGame(parent, client);
			game.start();
			this.dispose();
		} else {
			// Display the last error
			JOptionPane.showMessageDialog(this, "An error occurred:\n"
					+ lastError, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
