package doharm.net.packets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Code that is common to both Snapshot and Action packets.
 */
public abstract class Update
{
	private HashMap<Integer,ArrayList<String>> commands = null;
	
	protected void readCommands(ByteBuffer buff)
	{
		byte commandBatches = buff.get();
		if (commandBatches != 0)
		{
			commands = new HashMap<Integer,ArrayList<String>>();
			for (int i=0; i<commandBatches; ++i)
			{
				int batchNumber = buff.getInt();
				int numCommands = buff.getInt();
				ArrayList<String> cmdList = new ArrayList<String>();
				for (int j=0; j<numCommands; ++j)
					cmdList.add(Bytes.getString(buff));
				commands.put(batchNumber,cmdList);
			}
		}
	}
	
	protected void writeCommands(ByteArrayOutputStream buff) throws IOException
	{
		if (commands == null)
			buff.write((byte) 0);
		else
		{
			buff.write((byte) commands.size());	// write number of command batches
			for ( int i : commands.keySet() )
			{
				buff.write(Bytes.setInt(i));	// write seqnumber for this command batch
				ArrayList<String> list = commands.get(i); 	
				buff.write(Bytes.setInt(list.size()));	// write number of commands in this command batch
				for ( String s : list )
					buff.write(Bytes.setString(s));	// write each command
			}
		}
	}
	
	public void addCommands(HashMap<Integer,ArrayList<String>> cmds)
	{
		if (commands == null)
		{
			commands = new HashMap<Integer,ArrayList<String>>();
			commands.putAll(cmds);
		}
	}
	
	public Map<Integer, ArrayList<String>> getCommands()
	{
		if (commands != null)
			return Collections.unmodifiableMap(commands);
		else
			return null;
	}
	
	
	/** Extracts the timestamp from the byte array form of the Update
	 * (serverTime in case of Snapshot, seqNum in case of Action) */
	public static int getTimestamp(byte[] data)
	{
		ByteBuffer buff = ByteBuffer.wrap(data);
		buff.position(1);
		return buff.getInt();
	}
}
