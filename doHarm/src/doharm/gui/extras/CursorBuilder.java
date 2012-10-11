package doharm.gui.extras;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.util.HashMap;

import doharm.logic.entities.characters.states.CharacterStateType;

public class CursorBuilder {
	public static HashMap<CharacterStateType, Cursor> map = new HashMap<CharacterStateType, Cursor>();
	static {
		for (CharacterStateType c : CharacterStateType.values()){
			Toolkit tk = Toolkit.getDefaultToolkit();
			
			map.put()
		}
	}
}
