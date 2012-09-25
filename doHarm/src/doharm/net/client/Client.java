package doharm.net.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Queue;

import doharm.net.ClientState;
import doharm.net.UDPReceiver;
import doharm.net.packets.Snapshot;

public class Client {
	
	private UDPReceiver receiver;
	private DatagramSocket udpSock;
	
	private ClientState state;
	
	private Snapshot snapCurrent, snapNext;
	
	public Client(int port) throws IOException
	{
		// Setup the UDP socket.
		udpSock = new DatagramSocket();
		
		InetSocketAddress address = new InetSocketAddress(port);
		 
		udpSock.bind(address);
		
		receiver = new UDPReceiver(udpSock);
		receiver.start();
		
		state = ClientState.NONE;
	}
	
	public void processIncomingPackets()
	{		
		// TODO Might want to eventually add to the condition "|| !runningLate" for if we're taking too long. Probably won't care about this for quite a while.
		while (!receiver.isEmpty())
		{
			DatagramPacket packet = receiver.poll();
			byte[] data = packet.getData();
			
			// Check what type of packet it is.
			switch (data[0])
			{
			case 1:		// case SPK_SNAPSHOT:
				updateSnapshotPacket(data);
				break;
				
			case 2:		// case SPK_GAMESTATE:
				updateGamestatePacket(data);
				
				break;
				
			case 3:		// case SPK_RESPONSE:
				// THIS CASE DOESN'T CHECK WHAT STATE WE ARE IN (ie we could be INGAME then get forced back into LOADING cause of receiving a packet of this type. This should be changed by only listening for certain packets at certain points of time, not sure how exactly want to do that at the mo.
				// if OK, [create TCP connection] and then load the static game into memory.
				if (data[1] == 0)	// response code 0 = OK
				{
					System.out.println("Server responded with OK, moving to LOADING state...");
					state = ClientState.LOADING;
					/* TODO Possibly run the loading in a separate thread so the queue continue to be looped thru.
					Then again, there is only one thing the client needs to be worrying about, 
					so he doesn't need to be listening for anything else at this point? 
					unless we want the server to ask us if were still here every once in a while.. */
				}
				else	// it's an error, print the message and return to menu or w/e
				{
					System.out.println("Server responded with NO.");
				}
				break;
			}
		}
	}
	
	/**
	 * Update what the latest snapshot packet from the server is.
	 * @param data
	 */
	public void updateSnapshotPacket(byte[] data)
	{
		// Extract the timestamp from the packet.
		int timestamp = Snapshot.getTimestamp(data);
		
		// If this packet isn't more recent than the latest snapshot we've received, discard.
		if ( timestamp <= snapNext.serverTime )
			return;
		
		snapNext = new Snapshot(data);
	}
	
	public void updateGamestatePacket(byte[] data)
	{
		// TODO
		;
	}
	
	public void buildCommandPacket()
	{
		byte[] temp = new byte[1024];
		temp[0] = 1;	// This is a Command Packet.
		// temp[1-4] server time of last snapshot/gamestate we received.
		// my desired viewing direction
		// my desired movement
		// my selected weapon
		// my commands
	}

}
