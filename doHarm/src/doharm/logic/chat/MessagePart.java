package doharm.logic.chat;

import java.awt.Color;

public class MessagePart 
{
	private final Color colour;
	private final String text;
	
	public MessagePart(String text)
	{
		this(text, Color.white);
	}

	public MessagePart(String text, Color colour) 
	{
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
