package doharm.gui.extras;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import doharm.logic.entities.characters.states.CharacterStateType;

public class CursorFactory {
	private static HashMap<CharacterStateType, Cursor> map = new HashMap<CharacterStateType, Cursor>();
	public static final int IMAGE_SIZE = 32;
	static {
		CharacterStateType[] values = CharacterStateType.values();
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image[] imgs = new Image[values.length];
		try {
			Image src = ImageIO.read(new File("res/cursors/basic.png"));
			for (int i = 0; i < values.length; i++) {
				imgs[i] = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE,
						BufferedImage.TYPE_INT_ARGB);
				Graphics g = imgs[i].getGraphics();
				g.drawImage(src, -IMAGE_SIZE * i, 0, null);
				g.dispose();
				map.put(values[i], tk.createCustomCursor(imgs[i], new Point(0,
						0), values[i].toString()));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static HashMap<CharacterStateType, Cursor> getCursors() {
		if (map != null)
			return map;
		else {
			System.out.println("Cursor map is null");
			return null;
		}
	}
}
