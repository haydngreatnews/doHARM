package doharm.net.packets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import doharm.logic.time.Time;
import doharm.logic.world.World;
import doharm.net.server.ConnectedClient;

public class Gamestate extends Snapshot {

	public final String worldName;
	public final int playerEntityID, year, month, day;
	
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
