package doharm.gui.editor;

import java.io.IOException;
import java.util.List;

import doharm.storage.WorldLoader;

public class EditorLogic {
	
	public static List<char[][]> loadWorld(String worldName){
		try {
			WorldLoader world = new WorldLoader(worldName);
			world.getTilesetLoader();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}

