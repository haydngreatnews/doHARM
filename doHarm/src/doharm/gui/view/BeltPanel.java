package doharm.gui.view;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

import net.miginfocom.swing.MigLayout;
import doharm.gui.decorations.ColorIcon;
import doharm.logic.entities.characters.players.HumanPlayer;
import doharm.logic.entities.items.Item;
import doharm.logic.inventory.Belt;
import doharm.logic.inventory.Inventory;
import doharm.logic.inventory.ItemContainer;

public class BeltPanel extends JPanel {

    private Belt inventory;
    private Item[][] stash;

    public BeltPanel(HumanPlayer p) {
	inventory = p.getInventory().getBelt();
	setLayout(new MigLayout("wrap " + inventory.getItems()[0].length));
	ToolTipManager.sharedInstance().setInitialDelay(100);
	stash = inventory.getItems();
	for (Item[] r : stash) {
	    for (Item i : r) {
		if (i != null) {
		    // NOTE: because one item can take up more than one square,
		    // it is only located at the top left square.
		    JLabel icon;
		    icon = new JLabel(new ImageIcon(i.getImage()));
		    icon.setToolTipText(i.toString());
		    add(icon);
		}
	    }
	}
    }

    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
    }
}
