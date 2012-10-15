package doharm.net.packets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import doharm.logic.entities.characters.players.HumanPlayer;
import doharm.logic.entities.characters.players.Player;
import doharm.logic.time.Time;
import doharm.logic.world.World;
import doharm.net.packets.entityinfo.CharacterCreate;
import doharm.net.packets.entityinfo.CharacterUpdate;
import doharm.net.packets.entityinfo.EntityCreate;
import doharm.net.packets.entityinfo.EntityUpdate;

/**
 * Struct representing a Server Snapshot, which is then converted into a packet to send over the wire.
 * @author Adam McLaren (300248714)
 */
public class Snapshot extends Update {

	public final int serverTime;
	public final int seqAckd;
	public final float weather;
	public final float timeOfDay;
	private PlayerState pState = null;
	private final HashMap<Integer,EntityUpdate> entityUpdates = new HashMap<Integer,EntityUpdate>();
	private final HashMap<Integer,EntityCreate> entityCreates = new HashMap<Integer,EntityCreate>();
	private final ArrayList<Integer> entityDeletes = new ArrayList<Integer>();
	
	public Snapshot(int serverTime, int seqAckd, World world)
	{
		this.serverTime = serverTime;
		this.seqAckd = seqAckd;
		this.weather = world.getWeather().getConditions();
		this.timeOfDay = world.getTime().getTimeOfDay();
	}
	
	/**
	 * Constructs a Snapshot object out of a Snapshot packet byte array.
	 * @param packet
	 * @return
	 */
	public Snapshot(byte[] packet)
	{	
		ByteBuffer buff = ByteBuffer.wrap(packet);
		
		buff.position(1);	// Skip packet type, as we obviously already know what it is.
		
		serverTime = buff.getInt();
		
		seqAckd = buff.getInt();
		
		weather = buff.getFloat();
		
		timeOfDay = buff.getFloat();
		
		// Read playerstate
		pState = PlayerState.getPlayerState(buff);
		
		// Read deletes
		int count = (int) buff.get();
		for (int i=0; i<count; ++i)
			entityDeletes.add(buff.getInt());
		
		// Read creates
		count = (int) buff.get();
		for (int i=0; i<count; ++i)
		{
			int id = buff.getInt();
			entityCreates.put(id, EntityCreate.newEntityCreate(id, buff));
		}
		
		// Read updates
		count = (int) buff.get();
		for (int i=0; i<count; ++i)
		{
			int id = buff.getInt();
			entityUpdates.put(id, EntityUpdate.newEntityUpdate(id, buff));
		}
		
		readCommands(buff);
	}
	
	/** Creates a Snapshot that is a copy of the given snapshot (minus Commands) */
	public Snapshot(Snapshot other)
	{
		serverTime = other.serverTime;
		seqAckd = other.seqAckd;
		weather = other.weather;
		timeOfDay = other.timeOfDay;
		pState = other.pState;
		entityDeletes.addAll(other.entityDeletes);
		entityCreates.putAll(other.entityCreates);
		entityUpdates.putAll(other.entityUpdates);
	}
	
	/**
	 * Translates the Snapshot object into a byte-array for transmission.
	 * @param snap Snapshot object to convert
	 * @return 
	 */
	public byte[] convertToBytes()
	{	
		ByteArrayOutputStream buff = new ByteArrayOutputStream();
		
		try { 
			buff.write((byte) ServerPacket.SNAPSHOT.ordinal());	// Packet type
			
			buff.write(Bytes.setInt(serverTime));	// Servertime
			
			buff.write(Bytes.setInt(seqAckd));	// SeqAckd 
			
			buff.write(Bytes.setFloat(weather));
			
			buff.write(Bytes.setFloat(timeOfDay));

			if (entityDeletes.size() > 255)
				throw new RuntimeException("Entity deletes was over the 255 limit!");
			if (entityCreates.size() > 255)
				throw new RuntimeException("Entity creates was over the 255 limit!");
			if (entityUpdates.size() > 255)
				throw new RuntimeException("Entity updates was over the 255 limit!");

			// Write the entity deletes.
			buff.write((byte) entityDeletes.size());
			for (int eID : entityDeletes)
				buff.write(Bytes.setInt(eID));

			// Write the entity creates.
			buff.write((byte) entityCreates.size());
			for ( EntityCreate c : entityCreates.values() )
				buff.write(c.toBytes());

			// Write the entity updates.
			buff.write((byte) entityUpdates.size());
			for ( EntityUpdate u : entityUpdates.values() )
				buff.write(u.toBytes());
			
			// Write the commands
			writeCommands(buff);

		} catch (IOException e) {	e.printStackTrace(); }
		
		return buff.toByteArray();
	}
	
	public void addECreate(EntityCreate ent) { entityCreates.put(ent.id, ent); }
	
	public void addEDelete(int entID) { entityDeletes.add(entID); }
	
	public void addEUpdate(EntityUpdate ent) { entityUpdates.put(ent.id, ent); }
	
	public ArrayList<Integer> getEDeletes() { return (ArrayList<Integer>) Collections.unmodifiableList(entityDeletes); }
	
	public HashMap<Integer,EntityCreate> getECreates() { return (HashMap<Integer,EntityCreate>) Collections.unmodifiableMap(entityCreates); }
	
	public HashMap<Integer,EntityUpdate> getEUpdates() { return (HashMap<Integer,EntityUpdate>) Collections.unmodifiableMap(entityUpdates); }
	
	public PlayerState getPlayerState() { return pState; }
	
	public void setPlayerState(PlayerState state)
	{
		if (pState == null)
			pState = state;
	}

	/**
	 * Adds any fields that are in the given snapshot that are not present in this snapshot.
	 * This WILL NOT overwrite any fields that this snapshot already has (even if their values differ). 
	 * @param other Snapshot to copy missing fields from.
	 */
	public void addMissingEntities(Snapshot other)
	{
		// add deletes first, as we'll want to check when adding creates/updates
		// if it's already been deleted, in which case shouldn't bother adding it. 
		for (int eID : other.entityDeletes)
			if (!entityDeletes.contains(eID))
				entityDeletes.add(eID);
		
		for (int eID : other.entityCreates.keySet())
			if (!entityCreates.containsKey(eID) && !entityDeletes.contains(eID))
				entityCreates.put(eID, other.entityCreates.get(eID));
		
		for (int eID : other.entityUpdates.keySet())
			if (!entityUpdates.containsKey(eID) && !entityDeletes.contains(eID))
				entityUpdates.put(eID, other.entityUpdates.get(eID));
	}
}

