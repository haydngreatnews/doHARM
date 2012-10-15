package doharm.gui.editor;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import doharm.storage.WorldLoader;

public class EditorLogic {

    public static WorldLoader loadWorld(String worldName) {
	try {
	    return new WorldLoader(worldName);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return null;
    }

    public static class EditorTileSetLoader {
	private int tileWidth;
	private int tileHeight;
	private String tileSetImage;
	private List<EditorTileData> tileData;
	private List<Image> tileImages;

	public EditorTileSetLoader(String filename) throws FileNotFoundException {
	    Scanner scanner = new Scanner(new File("res/tilesets/" + filename));
	    tileWidth = Integer.parseInt(scanner.nextLine());
	    tileHeight = Integer.parseInt(scanner.nextLine());
	    tileSetImage = scanner.nextLine().trim();
	    tileData = new ArrayList<EditorTileData>();
	    tileImages = new ArrayList<Image>();

	    while (scanner.hasNextLine()) {
		String line = scanner.nextLine();
		if (line.trim().length() > 0)
		    tileData.add(new EditorTileData(line));
	    }
	    try {
		Image tiles = ImageIO.read(new File("res/tilesets/" + tileSetImage));
		for (int i = 0; i < tileData.size(); ++i) {
		    Image temp = new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_ARGB);
		    temp.getGraphics().drawImage(tiles, i*tileWidth, 0, null);
		    tileImages.add(temp);
		}
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	}
	
	public Image getTileImage(int i){
	    return tileImages.get(i);
	}

	public int getTileWidth() {
	    return tileWidth;
	}

	public int getTileHeight() {
	    return tileHeight;
	}

	public String getTileSetImage() {
	    return tileSetImage;
	}

	public EditorTileData getTileData(int tileID) {
	    return tileData.get(tileID);
	}

	public int getNumTiles() {
	    return tileData.size();
	}

    }
}
