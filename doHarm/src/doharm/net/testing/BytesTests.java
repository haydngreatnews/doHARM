package doharm.net.testing;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;

import org.junit.Test;

import doharm.net.packets.Bytes;

public class BytesTests {

	@Test
	public void ints()
	{
		for (int i=0; i<90000; i = i*2+1)
		{
			byte[] bytes = Bytes.setInt(i);
			ByteBuffer buff = ByteBuffer.wrap(bytes);
			assertEquals(i, buff.getInt());
		}
	}
	
	@Test
	public void floats()
	{
		for (float i=0; i<90000; i = i*2+1.02f)
		{
			byte[] bytes = Bytes.setFloat(i);
			ByteBuffer buff = ByteBuffer.wrap(bytes);
			float f = buff.getFloat();
			assertTrue(i == f);
		}
	}
	
	@Test
	public void strings()
	{
		String in = "blah";
		byte[] bytes = Bytes.setString(in);
		ByteBuffer buff = ByteBuffer.wrap(bytes);
		assertEquals(in, Bytes.getString(buff));
		
		// string surrounded with other stuff.
		String in2 = "SDMNfdkfrkivfjslSDg";
		byte[] bytes2 = new byte[32];
		buff = ByteBuffer.wrap(bytes2);
		buff.putInt(5);
		buff.putFloat(20.5f);
		buff.put(Bytes.setString(in2));
		int edge = 20;
		buff.putInt(edge);
		buff = ByteBuffer.wrap(bytes2);
		buff.getInt(); buff.getFloat();
		assertEquals(in2, Bytes.getString(buff));
		assertEquals(edge, buff.getInt());
		
		// Size 0 string.
		bytes = Bytes.setString("");
		buff = ByteBuffer.wrap(bytes);
		assertTrue(Bytes.getString(buff) == null);
		
		// Size 255+ string.
		String big = "ab";
		for (int i=0; i<9; ++i)
			big += big;
		assertTrue(Bytes.setString(big) == null);
	}
	
	@Test
	public void compress() {
		byte[] big = new byte[1024];
		ByteBuffer buff = ByteBuffer.wrap(big);
		int a = 5, b = 256, c = 9433;
		buff.putInt(a);
		buff.putInt(b);
		buff.putInt(c);
		
		byte[] small = Bytes.compress(buff);
		assertEquals(small.length, 12);
		
		buff = ByteBuffer.wrap(small);
		assertEquals(a, buff.getInt());
		assertEquals(b, buff.getInt());
		assertEquals(c, buff.getInt());
	}

}
