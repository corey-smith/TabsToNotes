package tabToNotes.core;

import java.util.HashMap;

/**
 * 
 * @author Corey
 * Actual note
 */
public class Note {
	
	NoteUtil noteUtil;
	//literal string name of note
	String noteName;
	//A0 - C8 - starts over on Cs
	//tonal position - C4, etc.
	String pitchPos;
	//numeric value of note starting at 1 and going to 88
	//12 tones from C1 - B1
	Integer noteCount;
	
	/**
	 * Create note based off of string count and fret number
	 * @param stringCount - aka line number
	 * @param fretCount - fret number of note on string
	 */
	public Note(NoteUtil noteUtil, int stringCount, int fretCount) {
		if(this.noteUtil == null) {
			this.noteUtil = noteUtil;
		}
		//get note value from string and fret numbers
	}
	
	/**
	 * Create note from pitch position
	 * @param pitchPo - pitch position of note
	 */
	public Note(NoteUtil noteUtil, String pitchPos) {
		if(this.noteUtil == null) {
			this.noteUtil = noteUtil;
		}
		this.pitchPos = pitchPos;
		this.noteCount = noteUtil.pitchToCount.get(pitchPos);
	}
	
	/**
	 * Creates note from note count
	 * @param noteCount - integer value of note
	 */
	public Note(NoteUtil noteUtil, int noteCount) {
		if(this.noteUtil == null) {
			this.noteUtil = noteUtil;
		}
		this.noteCount = noteCount;
		this.pitchPos = noteUtil.countToPitch.get(noteCount);
	}
	
	public Integer getNoteCount() {
		return noteCount;
	}
	
	public String getPitch() {
		return pitchPos;
	}

}
