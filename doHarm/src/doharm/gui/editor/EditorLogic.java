package doharm.gui.editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EditorLogic {
	
	public static List<char[][]> loadFile(File fin){
		return null;
		//Load machine output files
	}
	public static List<char[][]> loadHumanFile(File fin){
		List<char[][]> ret = new ArrayList<char[][]>();
		try {
			int sizex, sizey;
			String tileset;
			Scanner sc = new Scanner(fin);
			sizex = sc.nextInt();
			//Only maybe necessary?
			sc.nextLine();
			sizey = sc.nextInt();
			tileset = sc.next();
			List<String> layers = new ArrayList<String>();
			while (sc.hasNext()){
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}
}
