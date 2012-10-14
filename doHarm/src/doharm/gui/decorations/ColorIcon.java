package doharm.gui.decorations;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class ColorIcon implements Icon {
    private Color color;
    private boolean pressed = false;
    
    public ColorIcon(Color c){
	color = c;
    }
    public ColorIcon(Color c, boolean pressed){
	this.pressed = pressed;
	color = c;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
	g.setColor(color);
	g.fillRect(x, y, 40, 40);
	if (pressed){
	    g.setColor(Color.black);
	    g.drawRect(x, y, 39, 39);
	    g.drawRect(x+1, y+1, 37, 37);
	}
    }

    @Override
    public int getIconWidth() {
	return 40;
    }

    @Override
    public int getIconHeight() {
	return 40;
    }

}
