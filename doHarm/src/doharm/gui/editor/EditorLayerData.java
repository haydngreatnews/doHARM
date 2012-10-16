package doharm.gui.editor;

import java.util.Arrays;

import doharm.storage.LayerData;

public class EditorLayerData {
	private byte[][] tileIDs;
	private int width, height;

	public EditorLayerData(LayerData l, int xNum, int yNum) {
		tileIDs = new byte[yNum][xNum];
		width = xNum;
		height = yNum;
		for (int x = 0; x < xNum; ++x) {
			for (int y = 0; y < yNum; ++y) {
				setTileID(y, x, l.getTileID(y, x));
			}
		}
	}

	public EditorLayerData(EditorLayerData l, int oldX, int oldY, int newX,	int newY) {
		tileIDs = new byte[newY][newX];
		for(byte[] now : tileIDs){
			Arrays.fill(now, (byte) 2);
		}
		width = newX;
		height = newY;
		for (int x = 0; x < Math.min(oldX, newX); ++x) {
			for (int y = 0; y < Math.min(oldY, newY); ++y) {
				setTileID(y, x, l.getTileID(y, x));
			}
		}
	}

	public EditorLayerData(int xNum, int yNum) {
		tileIDs = new byte[yNum][xNum];
		width = xNum;
		height = yNum;
		for (int x = 0; x < xNum; ++x) {
			for (int y = 0; y < yNum; ++y) {
				setTileID(y, x, 2);
			}
		}
	}

	public int getTileID(int y, int x) {
		return tileIDs[y][x];
	}

	public void setTileID(int y, int x, int tileType) {
		tileIDs[y][x] = (byte) tileType;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(width + (height + 1));
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				sb.append(getTileID(y, x));
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
