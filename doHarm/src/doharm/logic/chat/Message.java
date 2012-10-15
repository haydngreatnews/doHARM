package doharm.logic.chat;

/**
 * messages are shown in the text area on the right hand side of the canvas.
 * These can be player chat messages or useful messages / notifications from the game server.
 * 
 * @author Roland
 */

public class Message 
{
	private int senderID; //The person who sent the message. -1 for server
	private MessagePart[] parts; //the parts that make up this message
	private boolean sendOverNetwork; //whether to send this message to the server / each client.

	
	/**
	 * 
	 * @param senderID the id of the player who sent the message or -1 for server.
	 * @param parts the parts of the message. There must be atleast one part.
	 */
	public Message(int senderID, boolean sendOverNetwork, MessagePart... parts)
	{
		if (parts == null || parts.length == 0)
			throw new UnsupportedOperationException("Empty message");
		
		
		this.senderID = senderID;
		this.sendOverNetwork = sendOverNetwork;
		this.parts = parts;
	}
	
	
	
	public boolean sendOverNetwork()
	{
		return sendOverNetwork;
	}

	public int getSenderID() 
	{
		return senderID;
	}

	public MessagePart[] getParts() 
	{
		return parts;
	}
}
