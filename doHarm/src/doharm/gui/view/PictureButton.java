package doharm.gui.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JButton;

public class PictureButton extends JButton {
	Image image;
	public PictureButton(Image img){
		super();
		this.image = img;
	}
	@Override
	public void paint(Graphics g){
		System.out.println("drawing image: "+image);
		g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
	}
	public Dimension getPreferredSize(){
		return new Dimension(image.getWidth(null), image.getHeight(null));
	}
}
