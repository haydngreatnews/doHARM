package doharm.gui.view;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PictureButton extends JButton {
	/**
	 * This class builds a JButton with a set of icons for up, roll-over and
	 * down, and none of the normal background features or colouring of the
	 * current look & feel
	 * 
	 * @param fn
	 *            The filename of the primary (up) icon. This will have _ro and
	 *            _pr appended before the . to find the location of the
	 *            roll-over and pressed images, respectively </br> ie new.png
	 *            would give: </br> new.png as the up image, new_ro.png as the
	 *            roll-over image and new_pr.png as the pressed image
	 */
	public PictureButton(String fn, String n) {
		super(n, new ImageIcon(fn));
		String[] name = fn.split("\\.");
		setRolloverIcon(new ImageIcon(name[0] + "_ro." + name[1]));
		setPressedIcon(new ImageIcon(name[0] + "_pr." + name[1]));
		setBorderPainted(false);
		setFocusable(false);
		setContentAreaFilled(false);
	}

}
