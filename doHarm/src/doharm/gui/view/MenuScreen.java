package doharm.gui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.layout.ConstraintParser;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

public class MenuScreen extends JPanel {
	public static void main(String[] args){
		JFrame  frame;
		(frame = new JFrame()).add(new MenuScreen());		
		frame.setUndecorated(false);
		frame.setBounds(0, 0, 800, 600);
		//frame.pack();
		frame.setVisible(true);	
	}
	
	public MenuScreen(){
		//setLayout(new BorderLayout());
		setLayout(new FlowLayout());
		Dimension d;
		
		JPanel menu = new JPanel();
		menu.setBackground(Color.gray);
		add(menu);
		LC constraints = new LC();
		constraints.flowY().fillY().setWidth(ConstraintParser.parseBoundSize("400:70%:600", false, true));
		menu.setLayout(new MigLayout(constraints));
		System.out.printf("menu w=%d, menu h=%d\n", menu.getWidth(), menu.getHeight());
		try {
			JButton b1 = new PictureButton(ImageIO.read(new File("res/menu/startgame.png")));
			JButton b2 = new PictureButton(ImageIO.read(new File("res/menu/quitgame.png")));
			System.out.println("Created buttons");
			menu.add(b1, "w 100%");
			System.out.printf("b1 w=%d, h=%d\n", b1.getWidth(), b1.getHeight());
			menu.add(b2, "w 70%");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//setVisible(true);
	}
}
