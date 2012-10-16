package doharm.net.packets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import doharm.logic.time.Time;
import doharm.logic.world.World;
import doharm.net.server.ConnectedClient;

/**
 * Representation of a Full Gamestate, which is a Snapshot with additional world information that only needs to be sent once.
 * @author Adam McLaren (300248714)
 */
public class Gamestate extends Snapshot {

	/** Name of the world the game is running. */
	public final String worldName;
	/** Entity Network ID that corresponds to the Player this Client will be controlling. */
	public final int playerEntityID;
	/** Current Date in the game world. */
	public final int year, month, day;
	
	/**
	 * Construct the bare-bones Gamestate from the Server.
	 * @param serverTime Server time of when the snap was created.
	 * @param seqAckd Last Client Action packet acknowledged. 
	 * @param world World to get world properties from.
	 * @param client Client this Gamestate is for.
	 */
	public Gamestate(int serverTime, int seqAckd, World world, ConnectedClient client)
	{
		super(serverTime, seqAckd, world);
		worldName = world.toString();
		playerEntityID = client.getPlayerEntity().getID();
		Time t = world.getTime();
		this.year = t.getYear();
		this.month = t.getMonth();
		this.day = t.getYear();
	}
	
	/**
	 * Constructs a Gamestate object out of a Gamestate packet byte array.
	 * @param packet Raw byte array form of the Gamestate to convert from.
	 * @return Gamestate generated from the packet.
	 */
	public Gamestate(byte[] packet)
	{
		super(packet);
		
		ByteBuffer buff = ByteBuffer.wrap(packet);
		buff.position(snapshotLength);	// place the position at where the snapshot finished reading.
		worldName = Bytes.getString(buff);
		playerEntityID = buff.getInt();
		// Convert the one integer back into the year, month and day.
		int date = buff.getInt();
		year = date / 10000;
		month = (date-year*10000) / 100;
		day = date - year*10000 - month*100;
	}
	
	/**
	 * Translates the Gamestate object into a byte-array for transmission.
	 * @return Byte array form of the Gamestate.
	 */
	public byte[] convertToBytes()
	{
		byte[] snapshot = super.convertToBytes();
		snapshot[0] = (byte) ServerPacket.GAMESTATE.ordinal();	// replace the Snapshot type with Gamestate type.
		ByteArrayOutputStream buff = new ByteArrayOutputStream();
		try
		{
			buff.write(snapshot);	// Copy the snapshot over into our new gamestate array.
			buff.write(Bytes.setString(worldName));
			buff.write(Bytes.setInt(playerEntityID));
			buff.write(Bytes.setInt(day + month*100 + year*10000));	// Put date into one integer.
		} catch (IOException e) { e.printStackTrace(); }
		
		return buff.toByteArray();
	}

}
