package doharm.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.LinkedList;
import java.util.Queue;

import doharm.net.client.Client;
import doharm.net.server.Server;

/**
 * Thread for listening on a UDP Socket, adding packets to a queue.
 */
public class UDPReceiver extends Thread {
	DatagramSocket socket;
	Queue<DatagramPacket> queue = new LinkedList<DatagramPacket>();

	public UDPReceiver(DatagramSocket udpSock)
	{
		socket = udpSock;
	}

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
	
	public DatagramPacket poll()
	{
		return queue.poll();
	}
	
	public boolean isEmpty()
	{
		return queue.isEmpty();
	}

}
