package doharm.gui.decorations;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import doharm.logic.entities.characters.players.Player;

public class HealthBar extends JPanel {
	Player player;
	
	public HealthBar(Player p){
		super();
		Dimension d = new Dimension(100,20);
		setPreferredSize(d);
		setOpaque(false);
		player = p;
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED.darker());
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);
		g.setColor(new Color((1-player.getHealthRatio())*.8f, player.getHealthRatio()*.8f, 0));
		g.fillRect(0, 0, (int) (getWidth()*player.getHealthRatio()), getHeight());
		g.setColor(Color.WHITE);
		g.drawString("HEALTH   "+(int)player.getHealth()+"/"+(int)player.getMaxHealth(), 2, getHeight()/4*3);
	}
}
