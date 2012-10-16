package doharm.rendering;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.imageio.ImageIO;

import doharm.logic.AbstractGame;
import doharm.logic.entities.characters.Character;
import doharm.logic.entities.characters.CharacterType;
import doharm.logic.entities.characters.players.HumanPlayer;
import doharm.logic.entities.characters.players.Player;
import doharm.logic.entities.characters.players.PlayerType;
import doharm.logic.entities.characters.states.CharacterStateType;
import doharm.logic.entities.characters.states.MoveState;
import doharm.logic.physics.Vector;
import doharm.logic.world.World;
import doharm.logic.world.tiles.Tile;

public class PlayerRenderer {

	private AbstractGame game;
	private World world;
	private ArrayList<BufferedImage> playerImgs;

	public PlayerRenderer(AbstractGame game) {
		this.game = game;
		this.world = game.getWorld();
		
		loadPlayerImages("res\\tilesets\\player sprites.png");
	}



	/*public void redraw(Graphics2D graphics, int imgIsoW, int imgIsoH) 
	{
		for (Player player: world.getPlayerFactory().getEntities())
		{
			drawPlayer(player,graphics, imgIsoW, imgIsoH);
		}
	}*/
	
	public void drawInfo(int cx, int cy, Character character, Graphics2D graphics, int tileW, int tileH)
	{
		if (!character.isAlive())
			return;
		
		Vector position = character.getPosition();
		float row = position.getY()/tileH;
		float col = position.getX()/tileW;
		
		
		RenderUtil.convertCoordsToIso(col, row, character.getCurrentLayer().getLayerNumber(), game.getCamera(), v);
		
		Dimension size = character.getSize();
		
		int x = cx+(int)v.getX()-size.width/2;// -tileW/2;
		int y = cy+(int)v.getY()-size.height/4;// -tileH/2;
		
		
		graphics.setColor(new Color(1-character.getHealthRatio(),character.getHealthRatio(),0,1));
		graphics.fillRect(x, y-10, (int)(size.width*character.getHealthRatio()), 3);
		
		graphics.setColor(new Color(1f,0,1));
		graphics.fillRect(x, y-5, (int)(size.width*character.getExperienceRatio()), 3);
		
		
//		if (player.getPlayerType() == PlayerType.HUMAN)
//		{
//			HumanPlayer hp = (HumanPlayer)player;
//			graphics.setColor(Color.magenta.darker().darker());
//			graphics.drawString("Icon: " + hp.getMouseIcon().toString(), x, y-35);
//		}
//		
//		graphics.setColor(Color.white);
//		graphics.drawString("State: " + player.getStateType().toString(), x, y-15);
		
		graphics.setColor(Color.white);
		graphics.drawString(character.getName(), x, y-55);
		
		graphics.setColor(Color.white);
		graphics.drawString("Level: "+ character.getLevel(), x, y-35);
		
		graphics.setColor(Color.white);
		String alliance = "no";
		if (character.getAlliance() != null)
			alliance = character.getAlliance().getName().toString();
		graphics.drawString(alliance + " alliance", x, y-15);
		
		
	}
	Vector v = new Vector();
	private void drawPlayer(int cx, int cy, Character character, Graphics2D graphics, int tileW, int tileH) 
	{
		if (!character.isAlive())
			return;
		
		Dimension size = character.getSize();
		//Tile tile = player.getCurrentTile();
		//Layer layer = player.getCurrentLayer();
		//Vector relative = player.getPositionRelativeToTile();
		Vector position = character.getPosition();
		float row = position.getY()/tileH;
		float col = position.getX()/tileW;
		
		
		
		/*if (player.getPlayerType() == PlayerType.HUMAN)
			graphics.setColor(Color.white);
		else if (player.getPlayerType() == PlayerType.AI)
			graphics.setColor(Color.RED);
		else if (player.getPlayerType() == PlayerType.NETWORK)
			graphics.setColor(Color.GRAY);
		else
			throw new UnsupportedOperationException(player.getPlayerType() + " not implemented");
		*/
		
		graphics.setColor(character.getColour());

		RenderUtil.convertCoordsToIso(col, row, character.getCurrentLayer().getLayerNumber(), game.getCamera(), v);
		
	
		
		int x = cx+(int)v.getX()-size.width/2;
		int y = cy+(int)v.getY()-size.height/4;
		
		
		//draw the player
		graphics.fillOval(x, y, size.width, size.height/2);
		
		
		
		
		
		
		/*if (player.getPlayerType() == PlayerType.HUMAN)
		{
			if (player.getStateType() == CharacterStateType.MOVE)
			{
				MoveState state = (MoveState)player.getState();
				
				//Path
				Collection<Tile> path = state.getPath();
				graphics.setColor(Color.white);
				for (Tile tile: path)
				{
					row = tile.getY()/world.getTileHeight();
					col = tile.getX()/world.getTileWidth();
					v = RenderUtil.convertCoordsToIso(col, row, player.getCurrentLayer().getLayerNumber(), game.getCamera());
					graphics.fillOval((int)v.getX()-size.width/8, (int)v.getY()-size.height/16, size.width/4, size.height/8);
				}
				
				
				//Goal
				graphics.setColor(Color.red);
				Vector goal = state.getDestination();
				row = goal.getY()/world.getTileHeight();
				col = goal.getX()/world.getTileWidth();
				v = RenderUtil.convertCoordsToIso(col, row, player.getCurrentLayer().getLayerNumber(), game.getCamera());
				graphics.fillOval((int)v.getX()-size.width/8, (int)v.getY()-size.height/16, size.width/4, size.height/8);
			}
		
		
			HumanPlayer hp = (HumanPlayer)player;
			graphics.setColor(Color.orange);
			Tile t = hp.getHoverTile();
			if (t != null)
			{
				row = t.getY()/world.getTileHeight();
				col = t.getX()/world.getTileWidth();
				v = RenderUtil.convertCoordsToIso(col, row, player.getCurrentLayer().getLayerNumber(), game.getCamera());
				graphics.fillOval((int)v.getX()-size.width/8, (int)v.getY()-size.height/16, size.width/4, size.height/8);
			}
		}*/
		
		
		
		
	}

	public void redrawPlayer(int cx, int cy, Character character, Graphics2D graphics, int fTileW,
			int wTileH) {
		// TODO Auto-generated method stub
		drawPlayer(cx,cy,character,graphics, fTileW, wTileH);
		
	}
	
	private void loadPlayerImages(String string){
		BufferedImage sheet = null;
		try {
			sheet = ImageIO.read(new File(string));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} 
		
		
		int size = 32;
		 playerImgs = new ArrayList<BufferedImage>();
		 for(int i = 0; i < 6; i++){
			 for (int j = 0; j < 4; j++){
				 BufferedImage next = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
				 Graphics2D g = next.createGraphics();
				 g.drawImage(sheet, 0, 0, size, size, i*size, j*size, size, size, null);
				 playerImgs.add(next);
			 }
		 }
		
	}

}
