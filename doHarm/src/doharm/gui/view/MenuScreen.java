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

import doharm.gui.input.MenuButtonListener;

import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

public class MenuScreen extends JPanel {
	Image background = null;

	public MenuScreen(MenuButtonListener mb) {
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
		JButton bResume = new PictureButton("res/menu/resume.png", "resume");
		JButton bJoin = new PictureButton("res/menu/joingame.png", "join");
		JButton bRun = new PictureButton("res/menu/runserver.png", "runserver");
		JButton bQuit = new PictureButton("res/menu/quitgame.png", "quit");
		bResume.addActionListener(mb);
		bJoin.addActionListener(mb);
		bRun.addActionListener(mb);
		bQuit.addActionListener(mb);
		System.out.println("Created buttons");
		menu.add(new JLabel(new ImageIcon("res/menu/header.png")),"align center, gaptop 100");
		//menu.add(new JLabel(), "h 60");
		menu.add(bResume, "align center, gaptop 40");
		menu.add(bJoin, "align center");
		menu.add(bRun, "align center");
		menu.add(bQuit, "align center, gaptop 40");
		add(menu);
	}
	
	public MenuScreen(File img, MenuButtonListener mb) {
		this(mb);
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
