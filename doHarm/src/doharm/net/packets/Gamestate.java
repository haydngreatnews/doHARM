package doharm.net.packets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import doharm.logic.time.Time;
import doharm.logic.world.World;

public class Gamestate extends Snapshot {

	public final int year, month, day;
	
	public Gamestate(int serverTime, int seqAckd, World world)
	{
		super(serverTime, seqAckd, world);
		
		Time t = world.getTime();
		this.year = t.getYear();
		this.month = t.getMonth();
		this.day = t.getYear();
	}
	
	public Gamestate(byte[] packet)
	{
		super(packet);
		
		ByteBuffer buff = ByteBuffer.wrap(packet);
		buff.position(packet.length - 4);	// place the position at where the snapshot finished reading.
											// in this case length - 4 as there are 4 bytes of extra stuff in GameState.
		// Convert the one integer back into the year, month and day.
		int date = buff.getInt();
		year = date / 10000;
		month = (date-year*10000) / 100;
		day = date - year*10000 - month*100;
	}
	
	public byte[] converToBytes()
	{
		byte[] snapshot = super.convertToBytes();
		snapshot[0] = (byte) ServerPacket.GAMESTATE.ordinal();	// replace the Snapshot type with Gamestate type.
		ByteArrayOutputStream buff = new ByteArrayOutputStream();
		try
		{
			buff.write(snapshot);	// Copy the snapshot over into our new gamestate array.
			buff.write(Bytes.setInt(day + month*100 + year*10000));	// Put date into one integer.
		} catch (IOException e) { e.printStackTrace(); }
		
		return buff.toByteArray();
	}

}
