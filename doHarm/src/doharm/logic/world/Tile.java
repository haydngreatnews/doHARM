package doharm.logic.world;

import java.awt.Dimension;

public class Tile 
{
	public int imageID;
	Dimension position;
	
	public Tile(Dimension position, int imageID) 
	{
		this.position = position;
		this.imageID = imageID;
	}

	public int getImageID() {
		return imageID;
	}

	public Dimension getPosition() {
		return position;
	}
}
