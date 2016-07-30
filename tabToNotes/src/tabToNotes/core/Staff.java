package tabToNotes.core;

import java.util.ArrayList;

public class Staff {
	
	//holds noteCounts for each individual position on staff
	ArrayList<StaffPos> positions = new ArrayList<StaffPos>();
	int startingIndex;
	
	//each note on staff
	Note[] notes;
	
	public Staff() {
		
	}
	
	public void setStartingIndex(int startingIndex) {
		this.startingIndex = startingIndex;
	}
}
