package doharm.logic.testing;

import static org.junit.Assert.*;

import org.junit.Test;

import doharm.logic.camera.Camera;
import doharm.logic.camera.CameraDirection;

public class CameraTests 
{
	
	@Test 
	public void testChangeDirection()
	{
		//create a mock camera
		Camera camera = new Camera(0,0);
		
		//test turning one way and back has no effect, but that turning does have an effect.
		CameraDirection d = camera.getDirection();
		
		camera.turnLeft();
		assertFalse(d == camera.getDirection());
		camera.turnRight();
		assertTrue(d == camera.getDirection());
		
		assertTrue(d == camera.getDirection());
		
		//test full revolution (left)
		for (int i = 0; i < 4; i++)
			camera.turnLeft();
		
		assertTrue(d == camera.getDirection());
		
		
		//test full revolution (right)
		for (int i = 0; i < 4; i++)
			camera.turnRight();
		
		assertTrue(d == camera.getDirection());
	}
	
}
