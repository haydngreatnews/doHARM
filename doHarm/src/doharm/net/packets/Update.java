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
 * @author Adam McLaren (300248714)
 */
public abstract class Update
{
	// List of commands this update contains.
	private HashMap<Integer,ArrayList<String>> commands = null;

	/**
	 * Read in the Commands contained in the byte array form of this update.
	 * The ByteBuffer passed must have it's position at the start of the command section of the byte array.
	 * @param buff ByteBuffer to read the bytes with.
	 */
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

	/**
	 * Write the commands section of the Update out into Bytes.
	 * @param buff OutputStream to write the bytes on.
	 * @throws IOException
	 */
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

	/**
	 * Add a completed list of Commands to this Update. Added when creating a transmission Snap/Gamestate.
	 * @param cmds Map of commands to add.
	 */
	public void addCommands(HashMap<Integer,ArrayList<String>> cmds)
	{
		if (commands == null)
		{
			commands = new HashMap<Integer,ArrayList<String>>();
			commands.putAll(cmds);
		}
	}

	/**
	 * @return View of Commands contained in this update. null if none exist.
	 */
	public Map<Integer, ArrayList<String>> getCommands()
	{
		if (commands != null)
			return Collections.unmodifiableMap(commands);
		else
			return null;
	}


	/**
	 * Extracts the timestamp from the byte array form of an Update
	 * (serverTime in case of Snapshot, seqNum in case of Action)
	 * @param data Byte-array form of the packet.
	 * @return
	 */
	public static int getTimestamp(byte[] data)
	{
		ByteBuffer buff = ByteBuffer.wrap(data);
		buff.position(1);
		return buff.getInt();
	}
}
