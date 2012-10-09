package doharm.gui.decorations;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import doharm.logic.entities.characters.players.Player;

public class ManaBar extends JPanel {
	Player player;
	
	public ManaBar(Player p){
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
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, (int) (getWidth()*player.getMana()), getHeight());
		g.setColor(Color.BLACK);
		g.drawString("MANA", 2, getHeight()/4*3);
	}
}
