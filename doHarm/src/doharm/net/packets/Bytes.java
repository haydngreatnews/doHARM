package doharm.net.packets;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

/** Class containing static methods to assist in the reading of bytes. */
public class Bytes {

	public static String getString(ByteBuffer buff)
	{
		int count = (int) buff.get();
		if (count == 0)
			return null;
		byte[] array = new byte[count];
		buff.get(array);
		return new String(array);	
	}
	
	public static byte[] setString(String string)
	{
		byte[] temp = string.getBytes();
		if (temp.length > 255)
			return null;
		
		byte[] real = new byte[temp.length+1];
		ByteBuffer buff = ByteBuffer.wrap(real);
		buff.put((byte) temp.length);
		buff.put(temp);
		return real;
	}
	
	public static byte[] compress(ByteBuffer buff)
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		output.write(buff.array(), 0, buff.position());
		return output.toByteArray();
		
		/*
		byte[] array = new byte[buff.position()];
		ByteBuffer copy = ByteBuffer.wrap(array);
		copy.put(buff.array(), 0, buff.position());
		return array;
		*/
	}
}
