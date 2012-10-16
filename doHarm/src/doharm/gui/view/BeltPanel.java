package doharm.gui.view;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

import net.miginfocom.swing.MigLayout;
import doharm.logic.entities.characters.players.HumanPlayer;
import doharm.logic.entities.items.Item;
import doharm.logic.inventory.Belt;

public class BeltPanel extends JPanel {

    private Belt belt;

    public BeltPanel(HumanPlayer p) {
	belt = p.getInventory().getBelt();
	setLayout(new MigLayout("wrap " + Belt.NUM_COLS));
	ToolTipManager.sharedInstance().setInitialDelay(100);
	setOpaque(false);
	for (int i = 0; i < Belt.NUM_COLS; ++i) {
	    Item item = belt.getItem(i);
	    if (item != null) {
		// NOTE: because one item can take up more than one square,
		// it is only located at the top left square.
		System.out.println(item.toString());
		JLabel icon;
		icon = new JLabel(new ImageIcon(item.getImage()));
		icon.setToolTipText(item.toString());
		add(icon);
	    }
	}
    }

    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	for (int i = 0; i < Belt.NUM_COLS; ++i) {
	    Item item = belt.getItem(i);
	    if (item != null) {
		// NOTE: because one item can take up more than one square,
		// it is only located at the top left square.
		System.out.println(item.toString());
		JLabel icon;
		icon = new JLabel(new ImageIcon(item.getImage()));
		icon.setToolTipText(item.toString());
		add(icon);
	    }
	}
	g.drawLine(0, 0, 0, getHeight());
	g.drawLine(0, 0, getWidth(), 0);
	g.drawLine(getWidth()-1, 0, getWidth()-1, getHeight()-1);
	g.drawLine(0, getHeight()-1, getWidth()-1, getHeight()-1);
	for (int i = 0; i<Belt.NUM_COLS; ++i){
	    g.drawLine(getWidth()/Belt.NUM_COLS*i, 0, getWidth()/Belt.NUM_COLS*i, getHeight());
	}
    }
}
