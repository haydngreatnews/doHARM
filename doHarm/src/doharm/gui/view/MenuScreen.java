package doharm.gui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.layout.ConstraintParser;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

public class MenuScreen extends JPanel {
	Image background = null;

	//Testing method for menuscreen
	public static void main(String[] args) {
		JFrame frame;
		(frame = new JFrame()).add(new MenuScreen(new File(
				"res/menu/background.png")));
		frame.setUndecorated(false);
		frame.setBounds(0, 0, 800, 600);
		// frame.pack();
		frame.setVisible(true);
		int i = 1;
	}

	public MenuScreen() {
		super();
		// setLayout(new BorderLayout());
		setLayout(new FlowLayout());
		Dimension d;

		JPanel menu = new JPanel();
		//menu.setBackground(Color.gray);
		add(menu);
		LC constraints = new LC();
		constraints.flowY();
		menu.setLayout(new MigLayout(constraints));
		System.out.printf("menu w=%d, menu h=%d\n", menu.getWidth(),
				menu.getHeight());

		JButton b1 = new PictureButton("res/menu/joingame.png");
		JButton b2 = new PictureButton("res/menu/runserver.png");
		JButton b3 = new PictureButton("res/menu/quitgame.png");
		System.out.println("Created buttons");
		menu.add(new JLabel(new ImageIcon("res/menu/header.png")));
		menu.add(new JLabel(), "h 120");
		menu.add(b1, "align center");
		menu.add(b2, "align center");
		menu.add(b3, "align center");

		repaint();
	}

	public MenuScreen(File img) {
		this();
		try {
			background = ImageIO.read(img);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		//TODO:Make background work properly?
		if (background != null)
			g.drawImage(background, 0, 0, 600, 600, null);
		super.paintComponent(g);
	}
}
