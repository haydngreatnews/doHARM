package doharm.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Thread for listening on a UDP Socket, and adding the packets to a queue.
 * @author Adam McLaren (300248714)
 */
public class UDPReceiver extends Thread {
	DatagramSocket socket;
	Queue<DatagramPacket> queue = new LinkedList<DatagramPacket>();

	/**
	 * Create a new UDP Receiver.
	 * @param udpSock Socket to listen on.
	 */
	public UDPReceiver(DatagramSocket udpSock)
	{
		socket = udpSock;
	}

	/**
	 * Begin listening for packets.
	 */
	public void run()
	{
		while (true)
		{
			byte[] buffer = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			try {
				socket.receive(packet);
				queue.add(packet);
			} catch (IOException e) { e.printStackTrace(); }
		}
	}
	
	/**
	 * Pulls the packet at the front of the queue off the queue.
	 * @return Packet at the front of the queue.
	 */
	public DatagramPacket poll()
	{
		return queue.poll();
	}
	
	/**
	 * @return Is the queue empty.
	 */
	public boolean isEmpty()
	{
		return queue.isEmpty();
	}

}
