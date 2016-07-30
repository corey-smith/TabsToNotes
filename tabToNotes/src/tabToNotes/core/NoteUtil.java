package tabToNotes.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NoteUtil {
	
	HashMap<String , Integer> pitchToCount = new HashMap<String, Integer>();
	HashMap<Integer, String> countToPitch = new HashMap<Integer, String>();
	HashMap<String, Integer> noteToOffSet = new HashMap<String, Integer>();
	
	double IMAGE_SCALE;
	
	public NoteUtil(double IMAGE_SCALE) {
		this.IMAGE_SCALE = IMAGE_SCALE;
		buildPitchToCount();
		buildCountToPitch();
		buildNoteToOffSet();
	}
	
	public void buildPitchToCount() {
		pitchToCount.put("A0", 1);
		pitchToCount.put("A0#", 2);
		pitchToCount.put("B0", 3);
		pitchToCount.put("C1", 4);
		pitchToCount.put("C#1", 5);
		pitchToCount.put("D1", 6);
		pitchToCount.put("D#1", 7);
		pitchToCount.put("E1", 8);
		pitchToCount.put("F1", 9);
		pitchToCount.put("F#1", 10);
		pitchToCount.put("G1", 11);
		pitchToCount.put("G#1", 12);
		pitchToCount.put("A1", 13);
		pitchToCount.put("A#1", 14);
		pitchToCount.put("B1", 15);
		pitchToCount.put("C2", 16);
		pitchToCount.put("C#2", 17);
		pitchToCount.put("D2", 18);
		pitchToCount.put("D#2", 19);
		pitchToCount.put("E2", 20);
		pitchToCount.put("F2", 21);
		pitchToCount.put("F#2", 22);
		pitchToCount.put("G2", 23);
		pitchToCount.put("G#2", 24);
		pitchToCount.put("A2", 25);
		pitchToCount.put("A#2", 26);
		pitchToCount.put("B2", 27);
		pitchToCount.put("C3", 28);
		pitchToCount.put("C#3", 29);
		pitchToCount.put("D3", 30);
		pitchToCount.put("D#3", 31);
		pitchToCount.put("E3", 32);
		pitchToCount.put("F3", 33);
		pitchToCount.put("F#3", 34);
		pitchToCount.put("G3", 35);
		pitchToCount.put("G#3", 36);
		pitchToCount.put("A3", 37);
		pitchToCount.put("A#3", 38);
		pitchToCount.put("B3", 39);
		pitchToCount.put("C4", 40);
		pitchToCount.put("C#4", 41);
		pitchToCount.put("D4", 42);
		pitchToCount.put("D#4", 43);
		pitchToCount.put("E4", 44);
		pitchToCount.put("F4", 45);
		pitchToCount.put("F#4", 46);
		pitchToCount.put("G4", 47);
		pitchToCount.put("G#4", 48);
		pitchToCount.put("A4", 49);
		pitchToCount.put("A#4", 50);
		pitchToCount.put("B4", 51);
		pitchToCount.put("C5", 52);
		pitchToCount.put("C#5", 53);
		pitchToCount.put("D5", 54);
		pitchToCount.put("D#5", 55);
		pitchToCount.put("E5", 56);
		pitchToCount.put("F5", 57);
		pitchToCount.put("F#5", 58);
		pitchToCount.put("G5", 59);
		pitchToCount.put("G#5", 60);
		pitchToCount.put("A5", 61);
		pitchToCount.put("A#5", 62);
		pitchToCount.put("B5", 63);
		pitchToCount.put("C6", 64);
		pitchToCount.put("C#6", 65);
		pitchToCount.put("D6", 66);
		pitchToCount.put("D#6", 67);
		pitchToCount.put("E6", 68);
		pitchToCount.put("F6", 69);
		pitchToCount.put("F#6", 70);
		pitchToCount.put("G6", 71);
		pitchToCount.put("G#6", 72);
		pitchToCount.put("A6", 73);
		pitchToCount.put("A#6", 74);
		pitchToCount.put("B6", 75);
		pitchToCount.put("C7", 76);
		pitchToCount.put("C#7", 77);
		pitchToCount.put("D7", 78);
		pitchToCount.put("D#7", 79);
		pitchToCount.put("E7", 80);
		pitchToCount.put("F7", 81);
		pitchToCount.put("F#7", 82);
		pitchToCount.put("G7", 83);
		pitchToCount.put("G#7", 84);
		pitchToCount.put("A7", 85);
		pitchToCount.put("A#7", 86);
		pitchToCount.put("B7", 87);
		pitchToCount.put("C8", 88);
	}
	
	public void buildCountToPitch() {
		Set p2cSet = (Set) pitchToCount.entrySet();
        Iterator p2cIter = p2cSet.iterator();
        while (p2cIter.hasNext())  {
        	Map.Entry p2cEntry = (Map.Entry) p2cIter.next();
        	String p2cKey = (String) p2cEntry.getKey();
        	Integer p2cVal = (Integer) p2cEntry.getValue();
        	countToPitch.put(p2cVal, p2cKey);
        }
	}
	
	/**
	 * To find YOffSet from note value
	 * There's about 10 pixels in 3 steps on the staff
	 */
	public void buildNoteToOffSet() {
		noteToOffSet.put("A0", (int) (130 * IMAGE_SCALE));
		noteToOffSet.put("A0#", (int) (126 * IMAGE_SCALE));
		noteToOffSet.put("B0", (int) (123 * IMAGE_SCALE));
		noteToOffSet.put("C1", (int) (120 * IMAGE_SCALE));
		noteToOffSet.put("C#1", (int) (120 * IMAGE_SCALE));
		noteToOffSet.put("D1", (int) (116 * IMAGE_SCALE));
		noteToOffSet.put("D#1", (int) (116 * IMAGE_SCALE));
		noteToOffSet.put("E1", (int) (113 * IMAGE_SCALE));
		noteToOffSet.put("F1", (int) (110 * IMAGE_SCALE));
		noteToOffSet.put("F#1", (int) (110 * IMAGE_SCALE));
		noteToOffSet.put("G1", (int) (106 * IMAGE_SCALE));
		noteToOffSet.put("G#1", (int) (106 * IMAGE_SCALE));
		noteToOffSet.put("A1", (int) (103 * IMAGE_SCALE));
		noteToOffSet.put("A#1", (int) (103 * IMAGE_SCALE));
		noteToOffSet.put("B1", (int) (100 * IMAGE_SCALE));
		noteToOffSet.put("C2", (int) (96 * IMAGE_SCALE));
		noteToOffSet.put("C#2", (int) (96 * IMAGE_SCALE));
		noteToOffSet.put("D2", (int) (93 * IMAGE_SCALE));
		noteToOffSet.put("D#2", (int) (93 * IMAGE_SCALE));
		noteToOffSet.put("E2", (int) (90 * IMAGE_SCALE));
		noteToOffSet.put("F2", (int) (86 * IMAGE_SCALE));
		noteToOffSet.put("F#2", (int) (86 * IMAGE_SCALE));
		noteToOffSet.put("G2", (int) (83 * IMAGE_SCALE));
		noteToOffSet.put("G#2", (int) (83 * IMAGE_SCALE));
		noteToOffSet.put("A2", (int) (80 * IMAGE_SCALE));
		noteToOffSet.put("A#2", (int) (80 * IMAGE_SCALE));
		noteToOffSet.put("B2", (int) (76 * IMAGE_SCALE));
		noteToOffSet.put("C3", (int) (73 * IMAGE_SCALE));
		noteToOffSet.put("C#3", (int) (73 * IMAGE_SCALE));
		noteToOffSet.put("D3", (int) (70 * IMAGE_SCALE));
		noteToOffSet.put("D#3", (int) (70 * IMAGE_SCALE));
		noteToOffSet.put("E3", (int) (66 * IMAGE_SCALE));
		noteToOffSet.put("F3", (int) (63 * IMAGE_SCALE));
		noteToOffSet.put("F#3", (int) (63 * IMAGE_SCALE));
		noteToOffSet.put("G3", (int) (60 * IMAGE_SCALE));
		noteToOffSet.put("G#3", (int) (60 * IMAGE_SCALE));
		noteToOffSet.put("A3", (int) (56 * IMAGE_SCALE));
		noteToOffSet.put("A#3", (int) (56 * IMAGE_SCALE));
		noteToOffSet.put("B3", (int) (53 * IMAGE_SCALE));
		noteToOffSet.put("C4", (int) (25 * IMAGE_SCALE));////////////////////////////MIDDLE C/////^BASS^
		noteToOffSet.put("C#4", (int) (25 * IMAGE_SCALE));
		noteToOffSet.put("D4", (int) (21 * IMAGE_SCALE));
		noteToOffSet.put("D#4", (int) (21 * IMAGE_SCALE));
		noteToOffSet.put("E4", (int) (18 * IMAGE_SCALE));
		noteToOffSet.put("F4", (int) (14 * IMAGE_SCALE));
		noteToOffSet.put("F#4", (int) (14 * IMAGE_SCALE));
		noteToOffSet.put("G4", (int) (11 * IMAGE_SCALE));
		noteToOffSet.put("G#4", (int) (11 * IMAGE_SCALE));
		noteToOffSet.put("A4", (int) (7 * IMAGE_SCALE));
		noteToOffSet.put("A#4", (int) (7 * IMAGE_SCALE));
		noteToOffSet.put("B4", (int) (4 * IMAGE_SCALE));
		noteToOffSet.put("C5", (int) (0 * IMAGE_SCALE));
		noteToOffSet.put("C#5", (int) (0 * IMAGE_SCALE));
		noteToOffSet.put("D5", (int) (-3 * IMAGE_SCALE));
		noteToOffSet.put("D#5", (int) (-3 * IMAGE_SCALE));
		noteToOffSet.put("E5", (int) (-7 * IMAGE_SCALE));
		noteToOffSet.put("F5", (int) (-10 * IMAGE_SCALE));
		noteToOffSet.put("F#5", (int) (-10 * IMAGE_SCALE));
		noteToOffSet.put("G5", (int) (-14 * IMAGE_SCALE));
		noteToOffSet.put("G#5", (int) (-14 * IMAGE_SCALE));
		noteToOffSet.put("A5", (int) (-17 * IMAGE_SCALE));
		noteToOffSet.put("A#5", (int) (-17 * IMAGE_SCALE));
		noteToOffSet.put("B5", (int) (-21 * IMAGE_SCALE));
		noteToOffSet.put("C6", (int) (-24 * IMAGE_SCALE));
		noteToOffSet.put("C#6", (int) (-24 * IMAGE_SCALE));
		noteToOffSet.put("D6", (int) (-28 * IMAGE_SCALE));
		noteToOffSet.put("D#6", (int) (-28 * IMAGE_SCALE));
		noteToOffSet.put("E6", (int) (-31 * IMAGE_SCALE));
		noteToOffSet.put("F6", (int) (-35 * IMAGE_SCALE));
		noteToOffSet.put("F#6", (int) (-35 * IMAGE_SCALE));
		noteToOffSet.put("G6", (int) (-38 * IMAGE_SCALE));
		noteToOffSet.put("G#6", (int) (-38 * IMAGE_SCALE));
		noteToOffSet.put("A6", (int) (-42 * IMAGE_SCALE));
		noteToOffSet.put("A#6", (int) (-42 * IMAGE_SCALE));
		noteToOffSet.put("B6", (int) (-47 * IMAGE_SCALE));
		noteToOffSet.put("C7", (int) (-51 * IMAGE_SCALE));
		noteToOffSet.put("C#7", (int) (-51 * IMAGE_SCALE));
		noteToOffSet.put("D7", (int) (-54 * IMAGE_SCALE));
		noteToOffSet.put("D#7", (int) (-54 * IMAGE_SCALE));
		noteToOffSet.put("E7", (int) (-60 * IMAGE_SCALE));
		noteToOffSet.put("F7", (int) (-63 * IMAGE_SCALE));
		noteToOffSet.put("F#7", (int) (-63 * IMAGE_SCALE));
		noteToOffSet.put("G7", (int) (-67 * IMAGE_SCALE));
		noteToOffSet.put("G#7", (int) (-67 * IMAGE_SCALE));
		noteToOffSet.put("A7", (int) (-70 * IMAGE_SCALE));
		noteToOffSet.put("A#7", (int) (-70 * IMAGE_SCALE));
		noteToOffSet.put("B7", (int) (-74 * IMAGE_SCALE));
		noteToOffSet.put("C8", (int) (-77 * IMAGE_SCALE));
	}
	
	/**
	 * This function is to determine offset of Y from top of the staff image to the note's Y position
	 * @param noteCount - number of note from 1 - 88
	 * @return
	 */
	public int getNoteOffSet(String pitchPos) {
		int noteOffSet = 0;
		if(pitchPos != null) {
			//int noteOffSet = noteCount - 20;
			noteOffSet = noteToOffSet.get(pitchPos);
		}
		return noteOffSet;
	}
}
