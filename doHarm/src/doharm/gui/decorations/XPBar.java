package doharm.gui.decorations;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import doharm.logic.entities.characters.players.Player;

public class XPBar extends JPanel {
	Player player;
	JLabel label;
	
	public XPBar(Player p){
		super();
		setLayout(new FlowLayout(FlowLayout.CENTER,	2, 0));
		Dimension d = new Dimension(100,10);
		setPreferredSize(d);
		label = new JLabel("XP Level:"+p.getLevel(), JLabel.CENTER);
		label.setPreferredSize(d);
		label.setForeground(Color.yellow);
		add(label);
		player = p;
		setOpaque(false);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GRAY);
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);
		for (int i = (int)Math.round(getWidth()/8.0); i < getWidth(); i+=(int)Math.round(getWidth()/8.0)){
			g.drawLine(i, 0, i, getHeight());
		}
		g.setColor(new Color(0xAA00AA));
		g.fillRect(0, 0, (int) (getWidth()*player.getExperienceRatio()), getHeight());
		if(player.getExperienceRatio() < .1)
		label.setText("XP Level:"+player.getLevel());
	}
}
