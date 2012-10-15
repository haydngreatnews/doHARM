package doharm.gui.editor;

import doharm.storage.LayerData;

public class EditorLayerData {
	private byte[][] tileIDs;
	private int width, height;

	public EditorLayerData(LayerData l, int xNum, int yNum) {
		tileIDs = new byte[yNum][xNum];
		for (int x = 0; x < xNum; ++x) {
			for (int y = 0; y < yNum; ++y) {
				setTileID(y, x, l.getTileID(y, x));
			}
		}
	}
	
	public EditorLayerData(EditorLayerData l, int oldX, int oldY, int newX, int newY) {
		tileIDs = new byte[newY][newX];
		for (int x = 0; x < Math.min(oldX, newX); ++x) {
			for (int y = 0; y < Math.min(oldY, newY); ++y) {
				setTileID(y, x, l.getTileID(y, x));
			}
		}
	}

	public EditorLayerData(int xNum, int yNum) {
		tileIDs = new byte[yNum][xNum];
		for (int x = 0; x < xNum; ++x) {
			for (int y = 0; y < yNum; ++y) {
				setTileID(y, x, 0);
			}
		}
	}

	public int getTileID(int y, int x) {
		return tileIDs[y][x];
	}

	public void setTileID(int y, int x, int tileType) {
		tileIDs[y][x] = (byte) tileType;
	}
}
