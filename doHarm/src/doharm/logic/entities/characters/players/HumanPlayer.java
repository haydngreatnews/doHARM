package doharm.logic.entities.characters.players;

import doharm.logic.entities.AbstractEntity;
import doharm.logic.entities.EntityType;
import doharm.logic.entities.characters.Character;
import doharm.logic.entities.characters.states.AttackState;
import doharm.logic.entities.characters.states.CharacterStateType;
import doharm.logic.entities.characters.states.MoveState;
import doharm.logic.entities.characters.states.PickupState;
import doharm.logic.entities.inventory.DragonRadar;
import doharm.logic.entities.items.Item;
import doharm.logic.world.tiles.Tile;


public class HumanPlayer extends Player
{
	private static final int NUM_MOUSE_BUTTONS = 3;
	private static final float MAX_DISTANCE = 999;
	
	private DragonRadar dragonRadar;
	private Tile hoveringTile;
	
	private boolean[] mouseDown;
	private CharacterStateType mouseIcon;
	private AbstractEntity hoverEntity; //the entity we are hovering over with the mouse


	
	
	
	protected HumanPlayer() 
	{
		super(PlayerType.HUMAN);
		mouseDown = new boolean[NUM_MOUSE_BUTTONS];
	}
	
	@Override
	public void process()
	{
		if (!isAlive())
			return;
		
		super.process();
		
		
		
		mouseIcon = CharacterStateType.IDLE;
		//if (hoveringTile.getEnt)
		
		if (hoveringTile != null)
		{
			mouseIcon = CharacterStateType.MOVE;
			
			hoverEntity = null;
			float closestDistance = MAX_DISTANCE;
			
			for (AbstractEntity entity: getWorld().getEntityFactory().getEntities())
			{
				if (!entity.isAlive())
					continue;
				
				float distance = (float) Math.hypot(hoveringTile.getX()-entity.getX(), hoveringTile.getY()-entity.getY());
				
				float entitySize = (float)Math.hypot(entity.getSize().width, entity.getSize().height);
				
				if (distance < closestDistance && distance < entitySize)
				{
					hoverEntity = entity;
					closestDistance = distance;
					
				}
			}
			
			if (hoverEntity != null)
			{
				if (hoverEntity.getEntityType() == EntityType.CHARACTER)
				{
					Character character = (Character)hoverEntity;
					if (character != this)
					{
						if (character.getAlliance() == null || character.getAlliance() != getAlliance())
						{
							//show attack icon!
							mouseIcon = CharacterStateType.ATTACK;
						}
					}
					else
					{
						//show info about yourself TODO
					}
				}
			}
		}
		
		System.out.println("MouseIcon = " + mouseIcon.toString());
		if (mouseDown[0])
			leftClick();
		if (mouseDown[1])
			rightClick();
		if (mouseDown[2])
			middleClick();
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
		switch (mouseIcon)
		{
		case MOVE:
			setState(new MoveState(getHoverTile(),true));
			break;
		case ATTACK:
			setState(new AttackState((Character)hoverEntity));
		case PICKUP:
			setState(new PickupState((Item)hoverEntity));
			
		}
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
