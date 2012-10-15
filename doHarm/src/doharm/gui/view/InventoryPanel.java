package doharm.gui.view;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import doharm.logic.entities.characters.players.HumanPlayer;
import doharm.logic.entities.items.Item;
import doharm.logic.inventory.Inventory;
import doharm.logic.inventory.ItemContainer;

public class InventoryPanel extends JPanel {
	
	private Inventory inventory;
	private Item[][] stash;
	
	public InventoryPanel(HumanPlayer p){
		inventory = p.getInventory();
		setLayout(new MigLayout("wrap "+inventory.STASH_COLS));
		stash = inventory.getStash().getItems();
		for(Item[] r : stash){
			for(Item i: r){
				if (i != null) //NOTE: because one item can take up more than one square, it is only located at the top left square.
					new JLabel(new ImageIcon(images.get(r.getImageId())));
			}
		}
	}
	public InventoryPanel(ItemContainer i){
		inventory = i;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}
}
