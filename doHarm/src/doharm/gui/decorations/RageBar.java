package doharm.gui.decorations;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import doharm.logic.entities.characters.players.Player;

public class RageBar extends JPanel {
	Player player;
	
	public RageBar(Player p){
		super();
		Dimension d = new Dimension(100,20);
		setPreferredSize(d);
		setMaximumSize(d);
		setMinimumSize(d);
		player = p;
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.drawRect(0, 0, getWidth(), getHeight());
		g.fillRect(0, 0, (int) (getWidth()*player.getRage()), getHeight());
		g.setColor(Color.BLACK);
		g.drawString("RAGE", 2, getHeight()/4*3);
	}
}
