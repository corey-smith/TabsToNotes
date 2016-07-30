/***************************************************************************************************/
/***************************************************************************************************/
/////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////MAIN FUNCTIONALITY FOR TAB CONVERSION////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
/***************************************************************************************************/
/***************************************************************************************************/
/*
var noteList = 	   {0: "A", 		//A 	= 0
					1: "A#",  		//A#/Bb = 1;
					2: "B",  		//B 	= 2;
					3: "C",  		//C 	= 3;
					4: "C#",  		//C#/Db = 4;
					5: "D",  		//D		= 5;
					6: "D#",  		//D#/Eb = 6;
					7: "E",  		//E		= 7;
					8: "F",  		//F		= 8;
					9: "F#",  		//F#/Gb = 9;
				   10: "G",	 		//G		= 10;
				   11: "G#" }		//G#/Ab	= 11;
*/
var noteList = ["A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"];
var lines = [];
   
//onclick event - base function
function transpose(){
	var tabText = fromText.value;
	lines = [];
	
	var linesStringArray = tabText.split(/\r\n|\r|\n/g);
	//build object array consisting of lines and properties
	for(i = 0; i < linesStringArray.length; i++){
		var line = new Object();
		line.text = linesStringArray[i];
		line.isHighE = false;
		line.tabLine = false;
		line.curLine = -1;
		lines.push(line);
	}
	
	//line 0 = highE, 1 = B, 2 = G, 3 = D, 4 = A, 5 = lowE;
	var curLine = 0;
	
	for(i = 0; i < lines.length; i++){
		//check for dashes and numbers and see if you can assign string to line
		if(lines[i].text != null && lines[i].text != undefined && lines[i].text.length > 0){
			//set tab line indicator - if'-' is present, int is present, and can assign string index to line
			if(lines[i].text.indexOf("-") >= 0 && findStrings(lines, i)){
				lines[i].tabLine = true;
			}
			else{
				lines[i].tabLine = false;
			}
		}
	}
	for(i = 0; i < lines.length; i++){
		//only process lines that are actually tab lines
		if(lines[i].tabLine){
			curLine = getCurLine(i);
			lines[i].curLine = curLine;
			lines[i] = processLine(i, lines[i], curLine);
		}
	}
	tabText = rebuildText();
	toText.value = tabText;
	toText.scrollTop = fromText.scrollTop;
	var highECount = 0;
	for(i = 0; i < lines.length; i++){
	if(lines[i].isHighE)
		highECount++;
	}
}

function getCurLine(i){
		 if(lines[i] 	 != undefined && lines[i].isHighE) 	   {return 0;}	//E - high
	else if(lines[i - 1] != undefined && lines[i - 1].isHighE) {return 1;}	//B
	else if(lines[i - 2] != undefined && lines[i - 2].isHighE) {return 2;}	//G
	else if(lines[i - 3] != undefined && lines[i - 3].isHighE) {return 3;}	//D
	else if(lines[i - 4] != undefined && lines[i - 4].isHighE) {return 4;}	//A
	else if(lines[i - 5] != undefined && lines[i - 5].isHighE) {return 5;}	//E - low
}

//loop through lines - find ints
function processLine(lineIndex, line, curLine){
	if(line != undefined && line != null && line.text.length > 0){
		for(j = 0; j < line.text.length; j++){
		//TODO: account for note indexes above 9
			if((!isNaN(line.text.charAt(j))) && (!isNaN(line.text.charAt(j + 1)))){
				var replaceChar = line.text.charAt(j) + line.text.charAt(j+1);
				line.text = replaceAt(lineIndex, line.text, j, getNote(curLine, replaceChar));
			}
			else if(!isNaN(line.text.charAt(j))){
				var replaceChar = line.text.charAt(j);
				line.text = replaceAt(lineIndex, line.text, j, getNote(curLine, replaceChar));
			}
		}
	}
	return line;
}

//loop through characters in line - point to corresponding note functions
function getNote(curLine, index){
	var note = "";
	if(curLine == 0 || curLine == 5){	//E
		note = getNoteFromIndex(7, index);
	} else if(curLine == 1){			//B
		note = getNoteFromIndex(2, index);
	} else if(curLine == 2){			//G
		note = getNoteFromIndex(10, index);
	} else if(curLine == 3){			//D
		note = getNoteFromIndex(5, index);
	} else if(curLine == 4){			//A
		note = getNoteFromIndex(0, index);
	}
	if(note != "" &&  note != " " && note != undefined){
		return note;
	}
	else{
		return index;
	}
}

function getNoteFromIndex(stringNo, index){
	var noteNo = parseInt(stringNo) + parseInt(index);
	if(noteNo > 11) {
		noteNo = noteNo%12;
	}
	var note = noteList[noteNo];
	return note;
}

//used to verify if a line is part of a tab and needs to be processed
//check length of this line against surrounding lines - not a great method
function findStrings(lines, lineNum){
	var bool = false;
	if(lines[lineNum] != null && lines[lineNum] != undefined){
		if(lines[lineNum - 1] != null && lines[lineNum - 1] != undefined){
			if(lines[lineNum].text.length == lines[lineNum - 1].text.length && lines[lineNum - 1].text.indexOf("--") >= 0) bool = true;
		}
		if(lines[lineNum + 1] != null && lines[lineNum + 1] != undefined){
			if(lines[lineNum].text.length == lines[lineNum + 1].text.length && lines[lineNum + 1].text.indexOf("--") >= 0) bool = true;
		}
		//find if string is high e - next 5 lines exist, are equal in length, and have at least 2 dashes - set indicator
		var highE = true;
		for(l = 0; l < 6; l++){
			if(lines[lineNum + l] != null && lines[lineNum + l] != undefined){
				if(!(lines[lineNum].text.length == lines[lineNum + l].text.length && lines[lineNum + l].text.indexOf("--") >= 0)){
					highE = false;
				}
			} else{
				highE = false;
			}
		}
		if(highE == true){
			lines[lineNum].isHighE = true;
		} else {
			lines[lineNum].isHighE = false;
		}
	}
	return bool;
}

/*	not currently used
function getELineIndex(lineIndex){
	if(lines[lineIndex] !- undefined && lines[lineIndex].isHighE) return (lineIndex);
	if(lines[lineIndex - 1] !- undefined && lines[lineIndex - 1].isHighE) return (lineIndex-1);
	if(lines[lineIndex - 2] !- undefined && lines[lineIndex - 2].isHighE) return (lineIndex-2);
	if(lines[lineIndex - 3] !- undefined && lines[lineIndex - 3].isHighE) return (lineIndex-3);
	if(lines[lineIndex - 4] !- undefined && lines[lineIndex - 4].isHighE) return (lineIndex-4);
	if(lines[lineIndex - 5] !- undefined && lines[lineIndex - 5].isHighE) return (lineIndex-5);
}
*/

//insert character at index for all lines in string set
/*	not currently used
function addChar(lineNum, index, character){
	lines[lineNum].text = lines[lineNum].text.substr(0, index) + character + lines[lineNum].text.substr(index);
	for(m = lines[lineNum].curLine; m < 6; m++){
		lines[lineNum+m].text = lines[lineNum+m].text.substr(0, index) + character + lines[lineNum+m].text.substr(index);
	}
	for(m = lines[lineNum].curLine; m > -1; m--){
		lines[lineNum-m].text = lines[lineNum-m].text.substr(0, index) + character + lines[lineNum-m].text.substr(index);
	}
}
*/

//utility to replace chars at index of string
function replaceAt(lineIndex, line, index, character){
	if(character != undefined){
		var charNum = 1;
		//when the immediate next char is a number (as in the note index is above 9) - remove char in all 
		if(!(isNaN(line.substr(index+1, 1)))){
			line = replaceAt(lineIndex, line, index+1, '-');
		}
		//check for special chars - chars that aren't '-' or a number
		if((isNaN(line.substr(index+1, 1))) && line.substr(index+1, 1) != '-'){
			character += line.substr(index+1, 1);
		}
		//while the next char isn't '-' and isn't "", keep adding whatever char it is to the replace char
		while(line.substr(index+character.length, charNum).indexOf("-") == -1 && line.substr(index+1, charNum) != "" && (index+1+charNum) <= line.length){
			character += line.substr(index+character.length, charNum);
			charNum++;
		}
		return line.substr(0, index) + character + line.substr(index+character.length);
	}
	else{
		return null
	}
}

function rebuildText(){
	var linesStringArray = [];
	for(i = 0; i < lines.length; i++){
		linesStringArray[i] = lines[i].text;
	}
	var rebuiltText = linesStringArray.join('\n');
	return rebuiltText;
}