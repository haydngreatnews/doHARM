package doharm.gui.editor;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
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

	public static Image makeSelected(Image i) {
		Image ret = new BufferedImage(i.getWidth(null), i.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) (ret.getGraphics());
		g.drawImage(i, 0, 0, null);
		g.setStroke(new BasicStroke(4));
		g.setColor(Color.black);
		g.drawRect(0, 0, i.getWidth(null), i.getHeight(null));
		return ret;
	}

	public static Image makeTransparent(Image i, float alpha) {
		// // Create the image using the
		// BufferedImage aimg = new BufferedImage(i.getWidth(null),
		// i.getHeight(null), BufferedImage.TRANSLUCENT);
		// // Get the images graphics
		// Graphics2D g = aimg.createGraphics();
		// // Set the Graphics composite to Alpha
		// g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
		// alpha));
		// // Draw the LOADED img into the prepared reciver image
		// g.drawImage(i, 0, 0, null);
		// // let go of all system resources in this Graphics
		// g.dispose();
		// // Return the image
		// return aimg;
		return i;
	}

	public static String toTitleCase(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}

	public static class EditorTileSetLoader {
		private int tileWidth;
		private int tileHeight;
		private String tileSetImage;
		private List<EditorTileData> tileData;
		private List<Image> tileImages;

		public EditorTileSetLoader(String filename)
				throws FileNotFoundException {
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
				Image tiles = ImageIO.read(new File("res/tilesets/"
						+ tileSetImage));
				for (int i = 0; i < tileData.size(); ++i) {
					Image temp = new BufferedImage(tileWidth, tileHeight,
							BufferedImage.TYPE_INT_ARGB);
					temp.getGraphics()
							.drawImage(tiles, -i * tileWidth, 0, null);
					tileImages.add(temp);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public Image getTileImage(int i) {
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
