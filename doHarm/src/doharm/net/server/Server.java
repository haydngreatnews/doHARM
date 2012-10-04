package doharm.net.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

import doharm.net.ClientState;
import doharm.net.UDPReceiver;
import doharm.net.packets.ClientPacket;
import doharm.net.packets.EntityInfo;
import doharm.net.packets.ServerPacket;
import doharm.net.packets.Snapshot;

public class Server {

	private final int maxPlayers = 64;
	private ArrayList<ConnectedClient> clients;
	private UDPReceiver receiver;
	private DatagramSocket udpSock;
	private int serverTime;
	
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
			switch (ClientPacket.values()[data[0]])
			{
			case COMMAND:
				for (ConnectedClient c : clients)
					if ( c.getAddress().equals(packet.getSocketAddress()) )
					{
						c.updateClientCommandPacket(data);
						break;
					}
				break;
				
			case JOIN:
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
					response[0] = (byte) ServerPacket.RESPONSE.ordinal();
					response[1] = 1;	// 1 = NO at this point in time.
					transmit(response, packet.getSocketAddress());
				}
				break;
				
			case OKACK:
				for (ConnectedClient c : clients)
					if ( c.getAddress().equals(packet.getSocketAddress()) )
					{
						// blah
					}
				break;
				
			case READY:
				for (ConnectedClient c : clients)
					if ( c.getAddress().equals(packet.getSocketAddress()) )
					{
						// ready state transition?
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
		
//		HashMap<Integer,EntityUpdate> entityUpdates = new HashMap<Integer,EntityUpdate>();
//		HashMap<Integer,EntityCreate> entityCreates = new HashMap<Integer,EntityCreate>();
		ArrayList<Integer> entityDeletes = new ArrayList<Integer>();
		
		
		for (ConnectedClient c : clients)
		{
			if (c.getState() == ClientState.INGAME)
			{
				Snapshot snap = new Snapshot(serverTime, c.latestCommandPacket.seqNum);
				
				// TODO temp setup is that the entire game change snap shot is sent to all clients, 
				// eventually when we add local area only snapshots, will need to build independently.
				
				for (int eID : entityDeletes)
				{
					// if (relavent)
					snap.addEDelete(eID);
				}
				
//				for (int eID : entityCreates.keySet())
//				{
//					// if (relavent)
//					snap.addECreate(entityCreates.get(eID));
//				}
//				
//				for (int eID : entityUpdates.keySet())
//				{
//					// if (relavent)
//					snap.addEUpdate(entityUpdates.get(eID));
//				}
				
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
