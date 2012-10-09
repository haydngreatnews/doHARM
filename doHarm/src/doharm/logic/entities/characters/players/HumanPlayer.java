package doharm.logic.entities.characters.players;

import doharm.logic.entities.AbstractEntity;
import doharm.logic.entities.EntityType;
import doharm.logic.entities.characters.Character;
import doharm.logic.entities.characters.states.CharacterStateType;
import doharm.logic.entities.characters.states.MoveState;
import doharm.logic.entities.inventory.DragonRadar;
import doharm.logic.world.tiles.Tile;


public class HumanPlayer extends Player
{
	private static final int NUM_MOUSE_BUTTONS = 3;
	private static final float MAX_DISTANCE = 999;
	
	private DragonRadar dragonRadar;
	private Tile hoveringTile;
	
	private boolean[] mouseDown;
	private CharacterStateType mouseIcon;


	
	
	
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
		
		mouseIcon = CharacterStateType.IDLE;
		//if (hoveringTile.getEnt)
		
		if (hoveringTile != null)
		{
			mouseIcon = CharacterStateType.MOVE;
			
			AbstractEntity closestEntity = null;
			float closestDistance = MAX_DISTANCE;
			
			for (AbstractEntity entity: getWorld().getEntityFactory().getEntities())
			{
				float distance = (float) Math.hypot(hoveringTile.getX()-entity.getX(), hoveringTile.getY()-entity.getY());
				
				float entitySize = (float)Math.hypot(entity.getSize().width, entity.getSize().height);
				
				if (distance < closestDistance && distance < entitySize)
				{
					closestEntity = entity;
					closestDistance = distance;
					System.out.println("Found entity");
				}
			}
			
			if (closestEntity != null)
			{
				if (closestEntity.getEntityType() == EntityType.CHARACTER)
				{
					Character character = (Character)closestEntity;
					if (character.getAlliance() == null || character.getAlliance() != getAlliance())
					{
						//show attack icon!
						mouseIcon = CharacterStateType.ATTACK;
					}
				}
			}
		}
	}
	
	public CharacterStateType getMouseIcon()
	{
		return mouseIcon;
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
		
		setState(new MoveState(getHoverTile()));
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
