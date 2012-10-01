package doharm.net.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.Queue;

import doharm.net.ClientState;
import doharm.net.UDPReceiver;
import doharm.net.packets.Command;
import doharm.net.packets.ServerPacket;
import doharm.net.packets.Snapshot;

public class Client {
	
	private UDPReceiver receiver;
	private DatagramSocket udpSock;
	
	private SocketAddress serverAddress;
	
	private ClientState state;
	
	private Snapshot snapCurrent, snapNext;
	
	private int latestSeqSent;
	
	/** Holds on to all unack'd Commands we've sent the server. */
	private LinkedList<Command> cmdsBuffer;
	
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
			
			// If the packet isn't from the game server we are connected/talking to, discard.
			if (packet.getSocketAddress() != serverAddress)
				continue;
			
			byte[] data = packet.getData();
			
			// Check what type of packet it is.
			switch (ServerPacket.values()[data[0]])
			{
			case SNAPSHOT:
				updateSnapshotPacket(data);
				break;
				
			case GAMESTATE:
				updateGamestatePacket(data);
				break;
				
			case RESPONSE:
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
	
	/**
	 * Builds a new Command and sends it out to the server we're connected to.
	 */
	public void dispatchCommand()
	{
		// Remove all acknowledged commands from the Command buffer.
		while (cmdsBuffer.peek().seqNum <= snapNext.seqAckd)
			cmdsBuffer.poll();
		
		Command cmd = new Command(++latestSeqSent, snapNext.serverTime);
		
		// TODO
		
		transmit(cmd.convertToBytes());
	}
	
	/**
	 * Sends a UDP Packet out to the desired address.
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
	
	/**
	 * Sends a UDP Packet to the server we are connected to.
	 * REQUIRES: serverAddress equals valid address.
	 * @param data Packet contents.
	 * @return
	 */
	public boolean transmit(byte[] data)
	{
		return transmit(data, serverAddress);
	}
	
	// Fake main method, placeholder used so coding on the flow of operations can be done.
	private void main()
	{
		while (true)
		{
			// process incoming packets
			processIncomingPackets();
			
			// perform client-side game logics
			
			// render
			
			// create then send command packet
			dispatchCommand();
			
			// wait for next tick
		}
	}

}
