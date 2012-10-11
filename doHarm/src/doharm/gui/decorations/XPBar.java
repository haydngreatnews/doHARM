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
	private Player player;
	private JLabel label;
	private int lastLevel = 1;

	public XPBar(Player p) {
		super();
		setLayout(new FlowLayout(FlowLayout.CENTER, 2, 0));
		Dimension d = new Dimension(100, 10);
		setPreferredSize(d);
		label = new JLabel("", JLabel.CENTER);
		label.setForeground(Color.yellow);
		add(label);
		player = p;
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GRAY);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		for (int i = (int) Math.round(getWidth() / 8.0); i < getWidth()-20; i += (int) Math.round(getWidth() / 8.0)) {
			g.drawLine(i, 0, i, getHeight());
		}
		g.setColor(new Color(player.getExperienceRatio()*.6f+.2f, 0, player.getExperienceRatio()*.6f+.2f));

		g.fillRect(0, 0, (int) (getWidth() * player.getExperienceRatio()),
				getHeight());
		if (lastLevel < (lastLevel = player.getLevel())){
			g.setColor(Color.yellow);
			g.fillRect(0, 0, (int) (getWidth()),getHeight());
			//lastLevel = player.getLevel();
		}

		label.setText("XP:"+(int)player.getCharacterClass().getExperience()+"/"+(int)player.getCharacterClass().getNextLevelExperience()+"            Level:" + player.getLevel());
	}
}
