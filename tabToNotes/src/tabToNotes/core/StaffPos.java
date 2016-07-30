package tabToNotes.core;

import java.util.ArrayList;

/**
 * 
 * Class to hold all notes at a single position on staff
 *
 */
public class StaffPos {
	
	//all notes at an individual staff position
	ArrayList<Note> notes = new ArrayList<Note>();
	
	//position relative to start of staff - beginning with 0
	int index;
	
	public StaffPos(int index) {
		this.index = index;
	}

}
