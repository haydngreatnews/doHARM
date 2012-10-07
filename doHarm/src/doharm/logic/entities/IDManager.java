package doharm.logic.entities;

import java.util.HashSet;
import java.util.Set;

public class IDManager 
{
	private Set<Integer> usedIDs;
	private int currentID;
	
	public IDManager()
	{
		usedIDs = new HashSet<Integer>();
		currentID = 0;
	}
	
	public int takeID()
	{
		while(true)
		{
			if (!usedIDs.contains(currentID))
			{
				usedIDs.add(currentID);
				return currentID++;
			}
			else
				++currentID;
		}
		
	}
	
	public void freeID(int id)
	{
		usedIDs.remove(id);
	}
}
