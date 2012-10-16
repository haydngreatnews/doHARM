package doharm.gui.editor;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import doharm.gui.decorations.ColorIcon;

public class EditorWindow extends JFrame {
	private EditorCanvas canvas;
	private EditorMouseListener listen;
	private int currentTileType = 0;
	private JLabel currentLayer;
	ButtonGroup tilesGroup;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new EditorWindow().setVisible(true);
	}

	public EditorWindow() {
		setBounds(0, 0, 800, 600);
		setLayout(new BorderLayout());
		canvas = new EditorCanvas();
		add(canvas, BorderLayout.CENTER);
		canvas.setWorld(EditorLogic.loadWorld("world1"));
		JPanel editor = new JPanel(new MigLayout("wrap 2"));
		editor.add(new JLabel("Layer:"));
		JButton upButton, downButton;
		editor.add(downButton = new JButton("-"), "skip 1, span 2, split 3");
		editor.add(currentLayer = new JLabel("" + canvas.getCurrentLayer()));
		editor.add(upButton = new JButton("+"));
		JButton addLayer;
		editor.add(addLayer = new JButton("Add Layer"), "span 2, align center");
		addLayer.setToolTipText("Add a new layer between the current layer, and that above");
		ActionListener layerChange = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Add Layer")) {
					canvas.addLayer();
				} else {
					String delta;
					if (e.getActionCommand().equals("+"))
						delta = "1";
					else
						delta = e.getActionCommand() + "1";
					canvas.changeLayer(Integer.valueOf(delta));
					currentLayer.setText("" + canvas.getCurrentLayer());
				}
				canvas.requestFocusInWindow();
			}
		};
		upButton.addActionListener(layerChange);
		downButton.addActionListener(layerChange);
		addLayer.addActionListener(layerChange);
		tilesGroup = new ButtonGroup();
		for (int i = 0; i < canvas.getTiles().getNumTiles(); ++i) {
			EditorTileData d = canvas.getTiles().getTileData(i);
			Image current = canvas.getTiles().getTileImage(i);
			JRadioButton b = new JRadioButton(new ImageIcon(current));
			b.setSelectedIcon(new ImageIcon(EditorLogic.makeSelected(current)));
			b.setActionCommand("" + i);
			tilesGroup.add(b);
			editor.add(new JLabel(EditorLogic.toTitleCase(d.getName())));
			editor.add(b);
			tilesGroup.setSelected(b.getModel(), true);
		}
		final JTextField xDim, yDim;
		editor.add(new JLabel("Map Size:"), "");
		editor.add(new JLabel("X:"), "skip 1, split 2");
		editor.add(xDim = new JTextField(canvas.getXDim() + "", 4));
		editor.add(new JLabel("Y:"), "skip 1, split 2");
		editor.add(yDim = new JTextField(canvas.getYDim() + "", 4));
		JButton commit;
		editor.add(commit = new JButton("Commit"), "span 2");
		commit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.changeMapSize(Integer.valueOf(xDim.getText()),
						Integer.valueOf(yDim.getText()));
				canvas.requestFocusInWindow();
			}
		});
		JButton writeOut;
		editor.add(writeOut = new JButton("Writeout"), "span 2, grow");
		writeOut.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.writeout();
				canvas.requestFocusInWindow();
			}
		});
		add(editor, BorderLayout.EAST);
		
		canvas.addMouseMotionListener(listen = new EditorMouseListener(this));
		EditorKeyListener edk;
		canvas.addKeyListener(edk = new EditorKeyListener(canvas));
		addKeyListener(edk);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas.requestFocusInWindow();
		new repaintThread().start();
	}

	EditorCanvas getCanvas() {
		return canvas;
	}

	public int getCurrentTileType() {
		return currentTileType;
	}

	private class repaintThread extends Thread {
		public void run() {
			while (true) {
				canvas.repaint();
				currentTileType = Integer.valueOf(tilesGroup.getSelection()
						.getActionCommand());
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
