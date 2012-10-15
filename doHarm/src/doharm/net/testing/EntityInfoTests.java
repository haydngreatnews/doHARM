package doharm.net.testing;

import static org.junit.Assert.*;

import java.awt.Color;
import java.nio.ByteBuffer;

import org.junit.Test;

import doharm.logic.entities.characters.classes.CharacterClassType;
import doharm.logic.entities.characters.players.Player;
import doharm.logic.entities.characters.players.PlayerType;
import doharm.logic.world.World;
import doharm.net.NetworkMode;
import doharm.net.packets.entityinfo.CharacterCreate;
import doharm.net.packets.entityinfo.CharacterUpdate;
import doharm.net.packets.entityinfo.EntType;
import doharm.net.packets.entityinfo.EntityCreate;
import doharm.net.packets.entityinfo.EntityUpdate;

public class EntityInfoTests {
	
	@Test
	public void CharacterCreateTest()
	{
		World wrld = new World("world1", NetworkMode.OFFLINE);
		Player player = wrld.getPlayerFactory().createPlayer(wrld.getRandomEmptyTile(), "Blah", CharacterClassType.WARRIOR, 2, PlayerType.HUMAN, new Color(255,0,0), false);
		CharacterCreate cc = new CharacterCreate(player);
		assertEquals(cc.type, (byte)EntType.PLAYER_WARRIOR.ordinal());
		assertTrue(cc.colour.equals(new Color(255,0,0)));
		assertEquals(cc.id,2);
		assertEquals(cc.name,"Blah");
		
		byte[] bytes = cc.toBytes();
		
		ByteBuffer buff = ByteBuffer.wrap(bytes);
		
		cc = (CharacterCreate) EntityCreate.newEntityCreate(buff.getInt(), buff);
		assertEquals(cc.type, (byte)EntType.PLAYER_WARRIOR.ordinal());
		assertTrue(cc.colour.equals(new Color(255,0,0)));
		assertEquals(cc.id,2);
		assertEquals(cc.name,"Blah");
	}
	
	@Test
	public void CharacterUpdateTest()
	{
		World wrld = new World("world1", NetworkMode.OFFLINE);
		Player player = wrld.getPlayerFactory().createPlayer(wrld.getRandomEmptyTile(), "Blah", CharacterClassType.WARRIOR, 2, PlayerType.HUMAN, new Color(255,0,0), false);
		CharacterUpdate cu = new CharacterUpdate(player);
		assertTrue(cu.angle == player.getAngle());
		assertTrue(cu.health == player.getHealth());
		assertEquals(cu.id,player.getID());
		assertEquals(cu.layer,player.getCurrentLayer().getLayerNumber());
		assertEquals(cu.lvl,player.getLevel());
		assertTrue(cu.posX == player.getPosition().getX());
		assertTrue(cu.posY == player.getPosition().getY());
		
		byte[] bytes = cu.toBytes();
		
		ByteBuffer buff = ByteBuffer.wrap(bytes);
		
		CharacterUpdate cu2 = (CharacterUpdate) EntityUpdate.newEntityUpdate(buff.getInt(), buff);
		assertTrue(cu.angle == cu2.angle);
		assertTrue(cu.health == cu2.health);
		assertTrue(cu.id == cu2.id);
		assertTrue(cu.layer == cu2.layer);
		assertTrue(cu.lvl == cu2.lvl);
		assertTrue(cu.posX == cu2.posX);
		assertTrue(cu.posY == cu2.posY);
	}
	
	@Test
	public void FurnitureCreateTest()
	{
		
	}
	
	@Test
	public void FurnitureUpdateTest()
	{
		
	}
	
	@Test
	public void ItemCreateTest()
	{
		
	}
	
	@Test
	public void ProjectileCreateTest()
	{
		
	}
	
	@Test
	public void ProjectileUpdateTest()
	{
		
	}

}
