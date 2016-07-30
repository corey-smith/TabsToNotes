package tabToNotes.core;

import java.util.HashMap;


/**
 * 
 * @author Corey
 * Text Line - should contain actual text as well as note of line/string
 */
public class Line {
	
	//text in line - from tab
	String text;
	//literal line count from tab
	int lineCount;
	//note value of line/string
	Note note;
	//if this is the beginning line (from the top) of a group or not
	boolean lineStart;
	//text length
	int length;
	//individual possible note positions - should equal text length
	int[] pos;
	//boolean to determine if this line is part of the tab
	boolean tabLine = false;
	
	public Line(String lineText, int lineCount) {
		this.text = lineText;
		this.lineCount = lineCount;
	}
	
	public String getLineText() {
		return text;
	}
	
	public void setLineText(String lineText) {
		this.text = lineText;
	}
	
	public void setNote(NoteUtil noteUtil, String pitchPos) {
		note = new Note(noteUtil, pitchPos);
	}
	
	public Integer getNoteCount() {
		return this.note.getNoteCount();
	}
	
	public String getPitch() {
		return this.note.pitchPos;
	}
	
	public void setLength(int length) {
		this.length = length;
		this.pos = new int[length];
		//set all pos indexes to -10 - which I'm going to say means empty...
		for(int i = 0; i < pos.length; i++) {
			pos[i] = -10;
		}
	}
}
