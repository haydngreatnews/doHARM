package doharm.gui.editor;

import doharm.storage.LayerData;


public class EditorLayerData 
{
	private byte[][] tileIDs;
	private int width, height;

	public EditorLayerData(LayerData l, int xNum, int yNum){
	    tileIDs = new byte[yNum][xNum];
	    for (int x = 0; x<xNum; ++x){
		for(int y = 0; y<yNum; ++y){
		    setTileID(y, x, l.getTileID(y, x));
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
