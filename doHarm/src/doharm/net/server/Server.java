package doharm.net.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Queue;

import doharm.net.ClientState;
import doharm.net.UDPReceiver;
import doharm.net.packets.Snapshot;

public class Server {

	private final int maxPlayers = 64;
	private ArrayList<ConnectedClient> clients;
	private UDPReceiver receiver;
	private DatagramSocket udpSock;
	
	public Server(int port) throws IOException
	{
		// Setup the UDP socket.
		udpSock = new DatagramSocket();
		
		InetSocketAddress address = new InetSocketAddress(port);
		
		udpSock.bind(address);
		
		receiver = new UDPReceiver(udpSock);
		receiver.start();
	}
	
	public void processIncomingPackets()
	{		
		while (!receiver.isEmpty())
		{
			DatagramPacket packet = receiver.poll();
			byte[] data = packet.getData();
			
			// Check what type of packet it is.
			switch (data[0])
			{
			case 1:		// case CPK_COMMAND:
				for (ConnectedClient c : clients)
					if ( c.getAddress().equals(packet.getSocketAddress()) )
					{
						c.updateClientCommandPacket(data);
						break;
					}
				break;
				
			case 2:		// case CPK_JOIN:
				if (clients.size() < maxPlayers)
				{
					// TODO check if player is already connected with that address.
					// If so, they must've dropped, so kill them and start them along the joining process again. 
					createClient(packet);
				}
				else
				{
					// Reject, inform them so.
					byte[] response = new byte[1];
					response[0] = 3;	// 3 is a Server Response packet.
					response[1] = 1;	// 1 = NO at this point in time.
					transmit(response, packet.getSocketAddress());
				}
				break;
				
			case 3:		// case CPK_OKACK:
				for (ConnectedClient c : clients)
					if ( c.getAddress().equals(packet.getSocketAddress()) )
					{
						// blah
					}
				break;
				
			case 4:		// case CPK_READY:
				for (ConnectedClient c : clients)
					if ( c.getAddress().equals(packet.getSocketAddress()) )
					{
						c.setState(ClientState.READY);
						// send gamestate
					}
			}
		}
	}
	
	/**
	 * Sends a UDP Packet out.
	 * @param data Packet contents.
	 * @param address IP and Port to send to.
	 * @return
	 */
	public boolean transmit(byte[] data, SocketAddress address)
	{
		try {
			udpSock.send(new DatagramPacket(data, data.length, address));
			return true;
		}
		catch (SocketException e) { e.printStackTrace(); }
		catch (IOException e) {	e.printStackTrace(); }
		return false;
	}
	
	private void createClient(DatagramPacket packet)
	{
		clients.add(new ConnectedClient(packet.getSocketAddress()));
	}
	
	/**
	 * Builds new snapshots from the game state then sends them out.
	 */
	private void dispatchSnapshots()
	{	
		// get game changes.
		Snapshot snap = null;
		
		for (ConnectedClient c : clients)
		{
			if (c.getState() == ClientState.INGAME)
			{
				// add snapshot to client
				c.addSnapshot(snap);
				// build n send
				transmit( c.buildTransmissionSnapshot().convertToBytes() , c.getAddress() );
			}
		}
	}
	
	
	// Fake main method, placeholder used so coding on the flow of operations can be done.
	private void main()
	{
		while (true)
		{
			// process incoming packets
			processIncomingPackets();
			
			// perform game logics
			
			
			// create then send snapshots
			dispatchSnapshots();
			
			// wait for next tick
		}
	}
}
