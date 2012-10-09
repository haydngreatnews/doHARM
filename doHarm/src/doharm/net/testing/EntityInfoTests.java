package doharm.net.testing;

import static org.junit.Assert.fail;

import org.junit.Test;

import doharm.logic.entities.characters.players.HumanPlayer;
import doharm.net.packets.entityinfo.CharacterCreate;

public class EntityInfoTests {

	@Test
	public void CharacterCreateTest() {
		//HumanPlayer player = new HumanPlayer();
		CharacterCreate cc = new CharacterCreate(null);
		fail("Not yet implemented");
	}

}
