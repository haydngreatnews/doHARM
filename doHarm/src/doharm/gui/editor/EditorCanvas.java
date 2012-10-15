package doharm.gui.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JPanel;

import doharm.gui.editor.EditorLogic.EditorTileSetLoader;
import doharm.rendering.RenderUtil;
import doharm.storage.WorldLoader;

public class EditorCanvas extends JPanel {
    private ArrayList<EditorLayerData> layers = new ArrayList<EditorLayerData>();
    private int xDim, yDim;
    private int currentLayer = 0;
    private EditorTileSetLoader tiles;
    private final int TILE_SIZE = 25;

    public EditorCanvas() {
	super();
	setBackground(Color.black);
	tiles = loadTiles("tileset1.txt");
	setBounds(0, 0, 700, 600);
    }

    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	BufferedImage below = null;
	if (currentLayer > 0 && layers.get(currentLayer - 1) != null) {
	    below = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
	    drawLayer(layers.get(currentLayer - 1), below.createGraphics());
	}
	// Do scaling
	if (below != null) {
	    RenderUtil.scaleImage(below, (int) (getWidth() * .9), (int) (getHeight() * .9));
	    g.drawImage(below, (int) (getWidth() * .05), (int) (getHeight() * .05), null);
	}
	drawLayer(layers.get(currentLayer), (Graphics2D) g);
    }

    private void drawLayer(EditorLayerData layer, Graphics2D g) {
	g.setStroke(new BasicStroke(2));
	g.setColor(new Color(0xAAAAAAAA, true));
	//Draw a grid
	for (int line = 0; line < (Math.max(getWidth(), getHeight()) / TILE_SIZE)+1; line++) {
	    g.drawLine(TILE_SIZE*line, 0, TILE_SIZE*line, getHeight());
	    g.drawLine(0, TILE_SIZE*line, getWidth(),TILE_SIZE*line);
	}
	for (int x = 0; x < xDim; ++x) {
	    for (int y = 0; y < yDim; ++y) {
		g.drawImage(tiles.getTileImage(layer.getTileID(y, x)), TILE_SIZE * x, TILE_SIZE * y, TILE_SIZE, TILE_SIZE, null);
	    }
	}


    }

    public EditorTileSetLoader loadTiles(String fname) {
	EditorTileSetLoader t = null;
	try {
	    t = new EditorTileSetLoader(fname);
	} catch (FileNotFoundException e) {
	    System.out.println("Tileset could not be found at " + fname);
	    e.printStackTrace();
	}
	return t;
    }

    public void setWorld(WorldLoader w) {
	xDim = w.getNumTilesX();
	yDim = w.getNumTilesY();
	layers.clear();
	for (int i = 0; i < w.getNumLayers(); i++) {
	    layers.add(i, new EditorLayerData(w.getLayerData(i), xDim, yDim));
	}
    }
    
    public Dimension getTileUnder(int x, int y){
	return new Dimension(x/TILE_SIZE, y/TILE_SIZE);
    }
    
    public boolean setTileUnder(int x, int y, int tileType){
	layers.get(currentLayer).setTileID(y/TILE_SIZE, x/TILE_SIZE, tileType);
	return true;
    }
}
