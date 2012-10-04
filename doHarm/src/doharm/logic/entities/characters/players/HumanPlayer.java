package doharm.logic.entities.characters.players;

import doharm.logic.entities.AbstractEntity;
import doharm.logic.entities.EntityType;
import doharm.logic.entities.characters.Character;
import doharm.logic.entities.inventory.DragonRadar;
import doharm.logic.world.tiles.Tile;


public class HumanPlayer extends Player
{
	private static final int NUM_MOUSE_BUTTONS = 3;
	
	
	private DragonRadar dragonRadar;
	private Tile hoveringTile;
	
	private boolean[] mouseDown;
	
	protected HumanPlayer() 
	{
		super(PlayerType.HUMAN);
		mouseDown = new boolean[NUM_MOUSE_BUTTONS];
	}
	
	@Override
	public void move()
	{
		super.move();
		
		if (mouseDown[0])
			leftClick();
		if (mouseDown[1])
			rightClick();
		if (mouseDown[2])
			middleClick();
		
		
		//if (hoveringTile.getEnt)
		
		if (hoveringTile != null)
		{
			for (AbstractEntity entity: hoveringTile.getEntities())
			{
				if (entity.getEntityType() == EntityType.CHARACTER)
				{
					Character character = (Character)entity;
					if (character.getAlliance() == null || character.getAlliance() != getAlliance())
					{
						//show attack!
					}
				}
				
				
			}
		}
		
		
		
	}

	

	public void hover(Tile tile) 
	{
		hoveringTile = tile;
	}

	public Tile getHoverTile()
	{
		return hoveringTile;
	}

	public void click(int button, boolean down) 
	{
		button--;
		if (button < 0 || button >= NUM_MOUSE_BUTTONS)
			return;
		
		mouseDown[button] = down;
	}

	private void leftClick() 
	{
		//either move, pick up, attack etc..
		
		moveTo(getHoverTile());
	}
	
	private void rightClick() 
	{
		//ranged attack / cast spell, etc...
	}
	
	private void middleClick() 
	{
		// ??
		
	}
}
