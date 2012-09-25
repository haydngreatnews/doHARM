package doharm.gui.view;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		setOpaque(false);
		JPanel menu = new JPanel();
		LC constraints = new LC();
		constraints.flowY();//.alignY("40");
		menu.setLayout(new MigLayout(constraints));
		menu.setOpaque(false);
		System.out.printf("menu w=%d, menu h=%d\n", menu.getWidth(),menu.getHeight());
		JButton b1 = new PictureButton("res/menu/joingame.png");
		JButton b2 = new PictureButton("res/menu/runserver.png");
		JButton b3 = new PictureButton("res/menu/quitgame.png");
		System.out.println("Created buttons");
		menu.add(new JLabel(new ImageIcon("res/menu/header.png")),"align center, gaptop 100");
		//menu.add(new JLabel(), "h 60");
		menu.add(b1, "align center, gaptop 40");
		menu.add(b2, "align center");
		menu.add(b3, "align center, gaptop 40");
		add(menu);
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
		super.paintComponent(g);
		if (background != null)
			g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		}
}
