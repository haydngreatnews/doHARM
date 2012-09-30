package doharm.storage;

import java.util.Scanner;

public class WallTileData extends TileData{
	
	public final String tileName;
	public final int type;
	public final int leftWall;
	public final int rightWall;
	
	
	public WallTileData(String line){
		Scanner sc = new Scanner(line);
		tileName = sc.next();
		type = sc.nextInt();
		leftWall = sc.nextInt();
		rightWall = sc.nextInt();
		
	}
}
