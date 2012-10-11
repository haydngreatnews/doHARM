package doharm.gui.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JPanel;

import doharm.rendering.RenderUtil;
import doharm.storage.TilesetLoader;

public class EditorCanvas extends JPanel {
	ArrayList<char[][]> layers = new ArrayList<char[][]>();
	int currentLayer = 0;

	public EditorCanvas() {
		super();
		setBackground(Color.black);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage below = null;
		if (currentLayer > 0 && layers.get(currentLayer - 1) != null) {
			below = new BufferedImage(getWidth(), getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			drawLayer(layers.get(currentLayer - 1), below.createGraphics());
		}
		// Do scaling
		if (below != null) {
			RenderUtil.scaleImage(below, (int) (getWidth() * .9),
					(int) (getHeight() * .9));
			g.drawImage(below, (int) (getWidth() * .05),
					(int) (getHeight() * .05), null);
		}
		drawLayer(layers.get(currentLayer), (Graphics2D) g);
	}

	private void drawLayer(char[][] layer, Graphics2D g) {
		// TODO Auto-generated method stub

	}

	public void loadTiles(String fname) {
		try {
			TilesetLoader t = new TilesetLoader(fname);
		} catch (FileNotFoundException e) {
			System.out.println("Tileset could not be found at " + fname);
			e.printStackTrace();
		}

	}
}
