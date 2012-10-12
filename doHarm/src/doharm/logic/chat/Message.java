package doharm.logic.chat;

public class Message 
{
	private int senderID;
	private MessagePart[] parts;

	
	/**
	 * 
	 * @param senderID -1 = server!
	 * @param parts
	 */
	public Message(int senderID, MessagePart... parts)
	{
		if (parts == null || parts.length == 0)
			throw new UnsupportedOperationException("Empty message");
		
		
		this.senderID = senderID;
		this.parts = parts;
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
