package tabToNotes.core;

public class LineGroup {

	//all lines in this line group
	Line[] lines;
	//line index of starting line (from the top) of this line group
	int startingIndex;
	//length in characters of linegroup lines
	int length;
	//number of lines in group
	int stringCount;
	
	/**
	 * 
	 * @param stringCount - number of strings in tab - defined in TabToNotes;
	 */
	public LineGroup(int stringCount) {
		lines = new Line[stringCount];
		this.stringCount = stringCount;
	}
	
	/**
	 * 
	 * @param noteUtil - note utility to get different note values
	 * @param stringCount - number of strings in tab - defined in TabToNotes;
	 */
	public void assignStringNotes(NoteUtil noteUtil) {
		if(this.stringCount == 6) {
			lines[0].note = new Note(noteUtil, "E4");
			lines[1].note = new Note(noteUtil, "B3");
			lines[2].note = new Note(noteUtil, "G3");
			lines[3].note = new Note(noteUtil, "D3");
			lines[4].note = new Note(noteUtil, "A2");
			lines[5].note = new Note(noteUtil, "E2");
		}
	}
	
	/**
	 * sets group length
	 * sets length of all lines in group
	 * builds pos[] array of all lines in group
	 */
	public void setLength() {
		this.length = lines[0].text.length();
		//this probably isn't necessary really
		for(int i = 0; i < stringCount; i++) {
			this.lines[i].setLength(this.length);
		}
	}
	
	public int getLength() {
		return length;
	}
	
	public int stringCount() {
		return stringCount;
	}
}
