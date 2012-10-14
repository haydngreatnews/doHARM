package doharm.gui.view;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import doharm.logic.gameobjects.entities.characters.classes.CharacterClassType;

public class CharacterSelect extends JFrame {
	Map<CharacterClassType, JRadioButton> classButtons;
	JTextField nameField;
	ButtonGroup radios;
	
	public CharacterSelect(){
		setLayout(new MigLayout("fill, wrap 2"));
		//setBounds(0, 0, 800, 600);
		add(new JLabel("Name:"));
		nameField = new JTextField(20);
		add(nameField, "growx");
		add(new JLabel("Class:"),"aligny top");
		classButtons = new HashMap<CharacterClassType, JRadioButton>();
		radios = new ButtonGroup();
		for (CharacterClassType c: CharacterClassType.values()){
			String name = c.toString().substring(0, 1)+c.toString().substring(1).toLowerCase();
			classButtons.put(c, new JRadioButton(name));
			add(classButtons.get(c),"flowy, cell 1 1, align center, sg but");
			classButtons.get(c).setActionCommand(c.toString());
			radios.add(classButtons.get(c));
		}
		radios.setSelected(classButtons.get(CharacterClassType.WARRIOR).getModel(), true);
		pack();
		setVisible(true);
		System.out.println(radios.getSelection().getActionCommand());
	}
	
	/*Check the following for validity
	 * - Name != null or ""
	 * - Class is selected and valid (should be safe, but just in case)
	 * - 
	 */
	
	private boolean checkValues(){
		
		return true;
	}
	
	public static void main(String[] args){
		new CharacterSelect();
	}
}
