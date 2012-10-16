package doharm.logic.entities.objects;

import doharm.logic.entities.AbstractEntity;
import doharm.logic.entities.EntityType;

public class GameObject extends AbstractEntity
{
	public GameObject(ObjectType type, String imageName) 
	{
		super(EntityType.OBJECT);
		loadImage(imageName);
	}
	
	
	@Override
	public void loadImage(String imageName)
	{
		super.loadImage("res/objects/"+imageName);
	}
	
}
