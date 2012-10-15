package doharm.gui.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import doharm.logic.entities.characters.classes.attributes.AttributeType;
import doharm.logic.entities.characters.players.Player;
import doharm.logic.world.World;
import doharm.net.NetworkMode;

public class LevelUpMenu extends JFrame {

	private int[] changes = new int[AttributeType.values().length];
	private JLabel[] values = new JLabel[AttributeType.values().length + 1];
	private JButton[] minusButtons = new JButton[AttributeType.values().length];
	private JButton[] plusButtons = new JButton[AttributeType.values().length];
	private int pointsToSpend;
	private Player player;

	/**
	 * A menu allowing the player to spend points earnt when they level up
	 * 
	 * @param p
	 *            The player in question
	 */
	public LevelUpMenu(Player p) {
		player = p;
		setLayout(new MigLayout("fill, wrap 2"));
		pointsToSpend = p.getCharacterClass().getAttributePoints();
		add(new JLabel("Attributes:"));
		add(new JLabel("Points left:"));
		add(values[values.length - 1] = new JLabel("" + pointsToSpend));
		ActionListener listen = new LevelUpListener();
		for (AttributeType type : AttributeType.values()) {
			add(new JLabel(type.toString().substring(0,1).toUpperCase()+type.toString().substring(1).toLowerCase() + ":"), "align right");
			JButton minus, plus;
			add(minus = new JButton("-"), "split 3");
			minusButtons[type.ordinal()] = minus;
			minus.setActionCommand("-" + type.toString());
			minus.addActionListener(listen);
			add(values[type.ordinal()] = new JLabel(""
					+ p.getCharacterClass().getAttributes().getAttr(type)), "");
			add(plus = new JButton("+"), "");
			plusButtons[type.ordinal()] = plus;
			plus.setActionCommand("+" + type.toString());
			plus.addActionListener(listen);
		}
		JButton commit = new JButton("Commit Changes");
		commit.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i< changes.length; ++i){
					while (changes[i]>0){
						player.getCharacterClass().addPoint(AttributeType.values()[i]);
						--changes[i];
					}
				}
			}
		});
		add(commit, "skip 1");
		
	}

	private class LevelUpListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			char sign = e.getActionCommand().charAt(0);
			AttributeType type = AttributeType.valueOf(e
					.getActionCommand().substring(1));
			if (sign == '+') {
				changes[type.ordinal()]++;
				pointsToSpend--;
			} else {
				if (changes[type.ordinal()] > 0) {
					changes[type.ordinal()]--;
					pointsToSpend++;
				}
			}
			for (int i = 0; i < values.length - 1; i++) {
				JLabel label = values[i];
				label.setText(""
						+ player.getCharacterClass().getAttributes()
								.getAttr(AttributeType.values()[i]));
			}
			values[values.length - 1].setText("" + pointsToSpend);
			for (int i = 0; i < minusButtons.length; ++i){
				plusButtons[i].setEnabled(true);
				minusButtons[i].setEnabled(true);
			}
			if (pointsToSpend == 0) {
				for (JButton b : plusButtons) {
					b.setEnabled(false);
				}
			}
			for (int i = 0; i < minusButtons.length; ++i) {
				JButton b = minusButtons[i];
				if (changes[i] < 1) {
					b.setEnabled(false);
				}
			}

		}

	}

}
