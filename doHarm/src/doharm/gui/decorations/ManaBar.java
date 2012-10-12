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
		player = p;
		setOpaque(false);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLUE.darker());
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);
		g.setColor(Color.BLUE.darker());
		g.fillRect(0, 0, (int) (getWidth()*player.getMana()), getHeight());
		g.setColor(Color.WHITE);
		g.drawString("MANA", 2, getHeight()/4*3);
	}
}
