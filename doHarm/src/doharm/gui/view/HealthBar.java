package doharm.gui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class HealthBar extends JPanel {
	public HealthBar(){
		super();
		Dimension d = new Dimension(100,20);
		setPreferredSize(d);
		setMaximumSize(d);
		setMinimumSize(d);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int health = 50;
		int maxHealth  = 100;
		g.setColor(Color.RED);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, getWidth()*health/maxHealth, getHeight());
	}
}
