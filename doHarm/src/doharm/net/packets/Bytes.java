package doharm.net.packets;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

/** Class containing static methods to assist in the reading of bytes. */
abstract public class Bytes {

	/** Translates a series of bytes that represent a String (that was 
	 * produced by the setString method in this class) back into a String*/
	public static String getString(ByteBuffer buff)
	{
		int count = (int) buff.get();
		if (count == 0)
			return null;
		byte[] array = new byte[count];
		buff.get(array);
		return new String(array);	
	}
	
	/** Translates a string into an array of bytes (first byte determining the length of the string) */
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
	
	/** Translates an int into the 4-byte representation. */
	public static byte[] setInt(int i)
	{
		byte[] temp = new byte[4];
		ByteBuffer buff = ByteBuffer.wrap(temp);
		buff.putInt(i);
		return temp;
	}
	
	/** Translates a float into the 4-byte representation. */
	public static byte[] setFloat(float f)
	{
		byte[] temp = new byte[4];
		ByteBuffer buff = ByteBuffer.wrap(temp);
		buff.putFloat(f);
		return temp;
	}
	
	/** Returns a  to */
	public static byte[] compress(ByteBuffer buff)
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		output.write(buff.array(), 0, buff.position());
		return output.toByteArray();
	}
}
