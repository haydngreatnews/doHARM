package doharm.logic.entities.characters.players;

import doharm.logic.entities.AbstractEntity;
import doharm.logic.entities.EntityType;
import doharm.logic.entities.characters.Character;
import doharm.logic.entities.characters.alliances.AllianceName;
import doharm.logic.entities.characters.states.AttackState;
import doharm.logic.entities.characters.states.CharacterStateType;
import doharm.logic.entities.characters.states.MoveState;
import doharm.logic.entities.characters.states.PickupState;
import doharm.logic.entities.items.Item;
import doharm.logic.entities.items.misc.dragonballs.DragonRadar;
import doharm.logic.entities.items.usable.UsableItem;
import doharm.logic.world.tiles.Tile;

/**
 * There is one human player per client, and no human players on the server.
 * Human players differ because they have input. Depending on mouse hovers, do 
 * certain actions when the mouse is clicked.
 * 
 * @author Roland
 */
public class HumanPlayer extends Player
{
	private static final int NUM_MOUSE_BUTTONS = 3;
	private static final float MAX_DISTANCE = 999;
	
	private Tile hoveringTile; //the tile we are hovering on. See MouseManager.
	
	private boolean[] mouseDown; //whether or not the mouse buttons are down.
	private CharacterStateType mouseIcon; //the current icon of what we are hovering over.
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
		
		
		//reset the mouse icon
		mouseIcon = CharacterStateType.IDLE;
		
		//check what we are hovering over.
		if (hoveringTile != null)
		{
			mouseIcon = CharacterStateType.MOVE;
			
			hoverEntity = null;
			float closestDistance = MAX_DISTANCE;
			
			for (AbstractEntity entity: getWorld().getEntityFactory().getEntities())
			{
				if (!entity.isAlive())
					continue;
				
				if (entity.getEntityType() == EntityType.ITEM && !((Item)entity).isOnGround())
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
				else if (hoverEntity.getEntityType() == EntityType.ITEM)
				{
					mouseIcon = CharacterStateType.PICKUP;
				}
			}
		}
		
		//process mouse input.
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
			break;
		case PICKUP:
			setState(new PickupState((Item)hoverEntity));
			break;
		}
	}
	
	protected void rightClick() 
	{
		//ranged attack / cast spell, etc...
		
		
		//TODO!?
		//joinAlliance();
	}
	
	private void middleClick() 
	{
		// ??
	}

	public void joinAlliance(int allianceNumber) 
	{
		AllianceName name = AllianceName.values()[allianceNumber];
		
		if (getAlliance() != null)
		{
			boolean leaving = false;
			if (getAlliance().getName() == name)
			{
				leaving = true;
			}
			getAlliance().removeCharacter(this);
			
			if (leaving)
				return;
		}

		getWorld().getAllianceManager().getAlliance(allianceNumber).addCharacter(this);
	}
}
