package doharm.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TilesetLoader 
{
	private int tileWidth;
	private int tileHeight;
	private String tileSetImage;
	private List<String> tileNames;

	public TilesetLoader(String filename) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(new File("res/tilesets/"+filename));
		tileWidth = scanner.nextInt();
		tileHeight = scanner.nextInt();
		tileSetImage = scanner.next();
		
		tileNames = new ArrayList<String>();
		while (scanner.hasNext())
		{
			tileNames.add(scanner.next());
		}
	}
	
	
	public List<String> getTileNames()
	{
		return Collections.unmodifiableList(tileNames);
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
	
	
}
