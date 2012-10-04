package doharm.net.packets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

/** Struct representing a Server Snapshot, which is then converted into a packet to send over the wire. */
public class Snapshot {

	public final int serverTime;
	public final int seqAckd;
	public final PlayerState pState;
	private final HashMap<Integer,EntityUpdate> entityUpdates = new HashMap<Integer,EntityUpdate>();
	private final HashMap<Integer,EntityCreate> entityCreates = new HashMap<Integer,EntityCreate>();
	private final ArrayList<Integer> entityDeletes = new ArrayList<Integer>();
	
	public Snapshot(int serverTime, int seqAckd)
	{
		this.serverTime = serverTime;
		this.seqAckd = seqAckd;
		pState = null;
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
		
		// Read playerstate
		pState = null;
		
		// Read deletes
		int count = (int) buff.get();
		for (int i=0; i<count; ++i)
			entityDeletes.add(buff.getInt());
		
		// Read creates
		count = (int) buff.get();
		for (int i=0; i<count; ++i)
		{
			int id = buff.getInt();
			//entityCreates.put(id, new EntityCreate(id, buff));
		}
		
		// Read updates
		count = (int) buff.get();
		for (int i=0; i<count; ++i)
		{
			int id = buff.getInt();
			//entityUpdates.put(id, new EntityUpdate(id, buff));
		}
		
	}
	
	/** Creates a Snapshot that is a copy of the given snapshot */
	public Snapshot(Snapshot other)
	{
		serverTime = other.serverTime;
		seqAckd = other.seqAckd;
		pState = null;
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
		// Packet type
		buff.write((byte) ServerPacket.SNAPSHOT.ordinal());		// TODO Do we need the byte cast?
		// Servertime
		try { buff.write(ByteBuffer.allocate(4).putInt(serverTime).array()); }
		catch (IOException e) {	e.printStackTrace(); }
		
		if (entityDeletes.size() > 255)
			throw new RuntimeException("Entity deletes was over the 255 limit!");
		if (entityCreates.size() > 255)
			throw new RuntimeException("Entity creates was over the 255 limit!");
		if (entityUpdates.size() > 255)
			throw new RuntimeException("Entity updates was over the 255 limit!");
		
		// Write the entity deletes.
		buff.write((byte) entityDeletes.size());
		for (int eID : entityDeletes)
			buff.write(eID);	// TODO MAKE SURE IT WRITES FOUR BYTES
		
		// Write the entity creates.
		buff.write((byte) entityCreates.size());
		
		// Write the entity updates.
		buff.write((byte) entityUpdates.size());
		
		return buff.toByteArray();
	}
	
	/** Extracts the timestamp from the byte array form of a Snapshot */
	public static int getTimestamp(byte[] data)
	{
		ByteBuffer buff = ByteBuffer.wrap(data);
		buff.position(1);
		return buff.getInt();
	}
	
	public Snapshot clone()
	{
		return this.clone();
	}

	/**
	 * Adds any fields that are in the given snapshot that are not present in this snapshot.
	 * This WILL NOT overwrite any fields that this snapshot already has (even if their values differ). 
	 * @param other Snapshot to copy missing fields from.
	 */
	public void addMissing(Snapshot other)
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
	
	/** Holds changes in player state */
	private class PlayerState
	{
		
	}
}

