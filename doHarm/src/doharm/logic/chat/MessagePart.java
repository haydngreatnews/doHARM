package doharm.logic.chat;

import java.awt.Color;

/**
 * A part of a message. Contains a string and a colour.
 * Message parts are concatenated together to make the final message.
 * @author Roland
 */

public class MessagePart 
{
	private final Color colour; //the colour to display this message part on the screen
	private final String text; //the text to display
	
	public MessagePart(String text)
	{
		this(text, Color.white);
	}

	public MessagePart(String text, Color colour) 
	{
		if (text == null)
			throw new UnsupportedOperationException("Empty message part");
		this.text = text;
		this.colour = colour;
	}

	public Color getColour() 
	{
		return colour;
	}

	public String getText() 
	{
		return text;
	}
}
