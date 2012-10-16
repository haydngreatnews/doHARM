package doharm.logic.entities.characters.alliances;

import java.util.ArrayList;
import java.util.List;

import doharm.logic.world.World;

public class AllianceManager 
{
	private List<Alliance> alliances;
	
	public AllianceManager(World world)
	{
		alliances = new ArrayList<Alliance>();
		for (AllianceName name: AllianceName.values())
		{
			alliances.add(new Alliance(world, name));
		}
	}
	
	public void process()
	{
		for (Alliance alliance: alliances)
		{
			alliance.process();
		}
	}

	public Alliance getAlliance(int allianceNumber) 
	{
		return alliances.get(allianceNumber);
	}
}
