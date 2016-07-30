package tabToNotes.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

/**
 * TODO: add logic to change base file directory to transport to a server
 */
@Path("core")
public class TabToNotes {
	
	NoteUtil noteUtil;
	
	//String fileDirString = "C:\\Users\\Corey\\programming stuff\\tomcat\\apache-tomcat-7.0.56\\webapps\\tabToNotes\\images\\";
	String fileDirString = "C:\\Temp\\testPDFs\\";
	String fileName = "test1.pdf";
	String filePath = fileDirString + fileName;
	
	static int PAGE_HEIGHT = 792;
	static int PAGE_WIDTH = 612;
	
	int stringCount = 6;
	int firstLineIndex;
	int lastLineIndex;
	//TODO: set image scale dynamically based on tab line length vs page size.
	final double IMAGE_SCALE = 0.5;
	//image variables
	//width and heigh of final notes image
	double finalImgHeight;
	double finalImgWidth;
	//width and height of note positions - individual units in produced sheet music
	//notes are much shorter than staffs. Idk if I actually need note height
	double posWidth = 15 * IMAGE_SCALE;
	double clefWidth = 35 * IMAGE_SCALE;
	double noteHeight = 30 * IMAGE_SCALE;
	double staffHeight = 130 * IMAGE_SCALE;
	
	//used to know the size of the final image
	double biggestX = 0;
	double biggestY = 0;
	
	//adds buffer to account for any text added to document
	double textBuffer = 20 * IMAGE_SCALE;
	
	//border buffer - for borders on page
	//staff buffer, between lines of music
	double borderBuffer = 20 * IMAGE_SCALE;
	double staffBuffer = 20 * IMAGE_SCALE;
	
	//Individual image files
	BufferedImage staffImg = getImageFromFile("new_images/staff.png");
	BufferedImage clefImg = getImageFromFile("new_images/clefs.png");
	BufferedImage barImg = getImageFromFile("new_images/bar.png");
	//normal notes
	BufferedImage noteUpImg = getImageFromFile("new_images/noteUp2.png");
	BufferedImage noteDownImg = getImageFromFile("new_images/noteDown2.png");
	//sharps
	BufferedImage noteUpSharpImg = getImageFromFile("new_images/noteUp2Sharp.png");
	BufferedImage noteDownSharpImg = getImageFromFile("new_images/noteDown2Sharp.png");
	//flats
	BufferedImage noteUFlatImg = getImageFromFile("new_images/noteUp2Flat.png");
	BufferedImage noteDownFlatImg = getImageFromFile("new_images/noteDown2Flat.png");
	//middle C
	BufferedImage noteUpC = getImageFromFile("new_images/MiddleCDown.png");
	BufferedImage noteDownC = getImageFromFile("new_images/MiddleCUp.png");
	
	
	Staff[] staffs;
	
	PDDocument returnFile;
	
	/**
	 * Only accessed from dev mode - only creates image to draw
	 * @param input - tab
	 */
	public BufferedImage[] getImage(String input){
		noteUtil = new NoteUtil(IMAGE_SCALE);
		return createImageArray(input);
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	//@Path("/id/{id}") -- this will point to this function if URL is core/id/[variable value] - you can get the variable value as parameter in the function with @PathParam("id")
	
	/**
	 * Core function to read tab and return filepath of newly created file
	 * @param  - incoming tab from js page
	 * @return - file path to newly created file
	 */
	public String tabToNotes(String tab) {
		//TODO: NEED LOGIC HERE TO CREATE FILE WHEN NOT IN DEV MODE
		noteUtil = new NoteUtil(IMAGE_SCALE);
	
		//Read in input - write to String
		File fileDir = new File(fileDirString);
		
		//if directory doesn't exist, create it
		if(!(fileDir.exists() && fileDir.isDirectory())){
			new File(fileDirString).mkdir();
		}
		return filePath;
	}
	
	/**
	 * @param input - String of incoming tab
	 * @return returnFile - newly created file
	 */
	public PDDocument createFile(String input) {
		noteUtil = new NoteUtil(IMAGE_SCALE);
		PDDocument returnFile = new PDDocument();
		writeFile(returnFile, input);
		try {
			//see if directory exists
			File fileDir = new File(fileDirString);
			if(!fileDir.exists() || !fileDir.isDirectory()) {
				fileDir.mkdirs();
			}
			//overwrite existing file (delete and resave)
			File saveFile = new File(filePath);
			if(saveFile.exists()) {
				saveFile.delete();
			}
			returnFile.save(filePath);
		} catch (IOException | COSVisitorException e) {
			e.printStackTrace();
		}
		return returnFile;
	}
	
	/**
	 * Writes the actual file given the File object and incoming tab
	 * @param returnFile - file to write to
	 * @param input - String for incoming tab
	 */
	public void writeFile(PDDocument returnFile, String input) {
		try {
			BufferedImage[] bImg = createImageArray(input);
			PDPage pages[] = new PDPage[bImg.length];
			for(int i = 0; i < pages.length; i++) {
				pages[i] = new PDPage();
				// Start a new content stream to hold the to be created content
				PDPageContentStream cos = new PDPageContentStream(returnFile, pages[i]);
				PDXObjectImage ximage = new PDPixelMap(returnFile, bImg[i]);
	            cos.drawXObject(ximage, 0, 0, ximage.getWidth(), ximage.getHeight());
	            returnFile.addPage(pages[i]);
				cos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * TODO: might want to make this an array of images for each page
	 * @param input - String for incoming tab
	 * @return return Image - image containing sheet music
	 */
	public BufferedImage[] createImageArray(String input) {
		BufferedImage[] returnImages = null;
		Line[] lines = textToLines(input);
		LineGroup[] lineGroups = groupLines(lines);
		setNotes(lineGroups);
		buildStaffs(lineGroups);
		setImageSize(lines);
		returnImages = buildImages(lines);
		return returnImages;
	}

	/**
	 * 
	 * @param input - String input - only one line of tab text
	 * @return Line object for each line of input
	 */
	public Line[] textToLines(String input) {
		String[] linesText = input.split("\n");
		Line[] lines = new Line[linesText.length];
		for(int i = 0; i < linesText.length; i++) {
			//create line from lineText and lineCount
			Line line = new Line(linesText[i], i);
			lines[i] = line;
		}
		return lines;
	}
	
	/**
	 * 
	 * @param lines - Line object array of lines in tab text
	 * @return LineGroup object array of all relevant line groups in lines
	 */
	public LineGroup[] groupLines(Line[] lines) {
		LineGroup[] lineGroups;
		//loop through lines
		for(int i = 0; i < lines.length; i++) {
			//see if next lines are part of the same group based off of length and count number
			for(int j = 0; j < stringCount; j++) {
				//avoiding out of bounds exception here
				if(i + j < lines.length) {
					//if lengths differ - not group starter - move on to next line
					if(!(lines[i].text.length() == lines[i + j].text.length())) {
						lines[i].lineStart = false;
					//else -line length does equal - verify special characters in line
					} else {
						//line has -, line + - has -
						if(lines[i].text.indexOf("-") >= 0 && lines[i + j].text.indexOf("-") >= 0) {
							boolean inGroup = false;
							boolean specialCharsPresent = true;
							for(int k = 0; k < stringCount; k++){
								//check to see if any previous lines withing string count range are line starts
								if(i - k >= 0) {
									if(lines[i - k].lineStart){
										inGroup = true;
										break;
									}
								}
								//loop through next lines in string count range and verify -'s are present
								if((i + k) < lines.length) {
									if(!(lines[i + k].text.indexOf("-") >= 0)) {
										specialCharsPresent = false;
										break;
									}
								}
							}
							if(!inGroup && specialCharsPresent) {
								lines[i].lineStart = true;
							}
						}
					}
				}
			}
		}
		//loop back through lines - find starting lines to determing line group count
		int groupCount = 0;
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].lineStart) {
				groupCount++;
			}
		}
		//System.out.println("groupCount: "+groupCount);
		lineGroups = new LineGroup[groupCount];
		int groupCounter = 0;
		//loop back through lines, add other lines to line group
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].lineStart) {
				LineGroup lg = new LineGroup(stringCount);
				for(int j = 0; j < stringCount; j++) {
					if(i + j < lines.length) {
						lines[i + j].tabLine = true;
						lg.lines[j] = lines[i + j];
						lg.startingIndex = i;
					}
				}
				//add line group to array, assign string notes in lineGroup
				lineGroups[groupCounter] = lg;
				lineGroups[groupCounter].assignStringNotes(noteUtil);
				lineGroups[groupCounter].setLength();
				groupCounter++;
			}
		}
		return lineGroups;
	}
	
	/**
	 * loops through characters in all lines, setting note values
	 */
	public void setNotes(LineGroup[] lineGroups) {
		//loop through groups
		for(int i = 0; i < lineGroups.length; i++) {
			//loop through lines in groups
			for(int j = 0; j < lineGroups[i].stringCount(); j++) {
				//loop through characters/positions in lines
				for(int k = 0; k < lineGroups[i].lines[j].pos.length; k++) {
					char character = lineGroups[i].lines[j].text.charAt(k);
					String charString = new StringBuilder().append(character).toString();
					//add digits
					if(isDigit(character)) {
						//prevent out of bounds exception
						if((k+1) < lineGroups[i].lines[j].text.length()-1) {
							//if this and next chars are numeric, combine both into 1 number
							char nextChar = lineGroups[i].lines[j].text.charAt(k+1);
							if(isDigit(nextChar)) {
								String charsString = new StringBuilder().append(character).append(nextChar).toString();
								lineGroups[i].lines[j].pos[k] = (Integer.parseInt(charsString) + lineGroups[i].lines[j].note.getNoteCount());
							//next char isn't a digit - only add this one
							} else {
								lineGroups[i].lines[j].pos[k] = (Integer.parseInt(charString) + lineGroups[i].lines[j].note.getNoteCount());
							}
						}
					//check bar lines
					} else if(charString.equals("|")){
						//-1 denotes barline
						lineGroups[i].lines[j].pos[k] = -1;
					//ignore all other characters
					} else {
						//do nothing
					}
				}
			}
		}
	}
	
	public boolean isDigit(char c) {
		boolean isDigit = (c >= '0' && c <= '9');
		return isDigit;
	}
	
	/**
	 * This should take the array of line groups and
	 * -build a corresponding staff for each line group
	 * -Build an ArrayList of NotePositions in each staff (based on length of lines in linegroup)
	 * -Build an ArrayList of Notes at each NotePosition
	 * @param lineGroups
	 */
	public void buildStaffs(LineGroup[] lineGroups) {
		//build array of staffs to correspond with LineGroups
		staffs = new Staff[lineGroups.length];
		//loop through line groups
		//create new Staff for each line group
		for(int i = 0; i < lineGroups.length; i++) {
			Staff staff = new Staff();
			staffs[i] = staff;
			staffs[i].setStartingIndex(lineGroups[i].startingIndex);
			//loop through positions in lines
			for(int j = 0; j < lineGroups[i].length; j++) {
				//build new StaffPosition for each char in lines (LineGroup/Line length)
				//add each StaffPosition to corresponding staff
				StaffPos staffPos = new StaffPos(j);
				staff.positions.add(staffPos);
				//loop through each line in line group finding each position from j
				for(int k = 0; k < lineGroups[i].lines.length; k++) {
					int noteNumber = lineGroups[i].lines[k].pos[j];
					Note note = new Note(noteUtil, noteNumber);
					staffPos.notes.add(note);
				}
			}
		}
	}
	
	/**
	 * This method basically matches build image - used to set image size to draw
	 * on before actually drawing image - it would be better if this could be combined
	 * with the buildImages method so that I don't have to maintain 2 matching methods
	 */
	public void setImageSize(Line[] lines) {
		//loop through staffs
		double xPos = 0;
		double yPos = 0;
		BufferedImage curImg = null;
		
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].tabLine) {
				for(int j = 0; j < staffs.length; j++) {
					if(i == staffs[j].startingIndex) {
						//loop through positions in staff
						System.out.println("");
						for(int k = 0; k < staffs[j].positions.size(); k++) {
							//set x position
							if(k == 0) {
								curImg = clefImg;
								xPos = borderBuffer;
							} else {
								curImg = staffImg;
								//minus 1 pos width because first position is account for with clefWidth
								xPos = (k*posWidth) + borderBuffer + clefWidth - posWidth;
							}
							//set image size based on biggest x/y values
							if(xPos + curImg.getWidth() > biggestX) biggestX = (xPos + curImg.getWidth() + borderBuffer*2);
						}
						yPos += (staffHeight + staffBuffer*2);
						if(yPos > biggestY) biggestY = yPos;
					}
				}
			//draw text
			} else if(lines[i].text.length() > 0) {
				if(yPos == 0) {
					yPos = textBuffer;
				}
				yPos += textBuffer;
				if(yPos > biggestY) biggestY = yPos;
			}
		}
		this.finalImgWidth = biggestX;
		this.finalImgHeight = biggestY;
	}
	
	/**
	 * Creates a new image and then concatenates all of the note images etc on top
	 * @param lineGroups
	 * @return final image
	 */
	public BufferedImage[] buildImages(Line[] lines) {
		int pageCount = (int) Math.ceil(finalImgHeight/PAGE_HEIGHT);
		BufferedImage[] returnImages = new BufferedImage[pageCount + 1];
		for(int i = 0; i < returnImages.length; i++) {
			returnImages[i] = new BufferedImage((int) this.finalImgWidth, PAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		}
		//loop through staffs
		double xPos = 0;
		double yPos = 0;
		BufferedImage curImg = null;
		
		for(int i = 0; i < lines.length; i++) {
			Graphics g = returnImages[getImageIndex(yPos)].createGraphics();
			//draw staffs
			if(lines[i].tabLine) {
				for(int j = 0; j < staffs.length; j++) {
					if(i == staffs[j].startingIndex) {
						//loop through positions in staff
						for(int k = 0; k < staffs[j].positions.size(); k++) {
							//set x position
							if(k == 0) {
								curImg = clefImg;
								xPos = borderBuffer;
							} else {
								curImg = staffImg;
								//minus 1 pos width because first position is account for with clefWidth
								xPos = (k*posWidth) + borderBuffer + clefWidth - posWidth;
							}
							//set image size based on biggest x/y values
							if(xPos + curImg.getWidth() > biggestX) biggestX = (xPos + curImg.getWidth() + borderBuffer*2);
							g.drawImage(curImg, (int) xPos, getPagePosY(yPos), null);
						}
						yPos += (staffHeight + staffBuffer*2);
						if(yPos > biggestY) biggestY = yPos;
					}
				}
			//draw text
			} else if(lines[i].text.length() > 0) {
				if(yPos == 0) {
					yPos = textBuffer;
				}
				g.setColor(Color.BLACK);
				g.drawString(lines[i].text, (int) borderBuffer, getPagePosY(yPos));
				yPos += textBuffer;
				if(yPos > biggestY) biggestY = yPos;
			}
		}
		
		yPos = 0;
		
		//draw notes
		for(int i =0; i < lines.length; i++) {
			if(lines[i].tabLine) {
				for(int j = 0; j < staffs.length; j++) {
					if(i == staffs[j].startingIndex) {
						//loop through positions in staff
						for(int k = 0; k < staffs[j].positions.size(); k++) {
							//default positions
							//set x position
							if(k == 0) {
								curImg = clefImg;
								xPos = borderBuffer;
							} else {
								curImg = staffImg;
								//minus 1 pos width because first position is account for with clefWidth
								xPos = (k*posWidth) + borderBuffer + clefWidth - posWidth;
							}
							//loop through notes in current position
							for(int l = 0; l < staffs[j].positions.get(k).notes.size(); l++) {
								Note curNote = staffs[j].positions.get(k).notes.get(l);
								//-10 is a blank staff pos
								if(curNote.noteCount > -10) {
									//barline
									if(curNote.noteCount == -1) {
										curImg = barImg;
									}
									if(curNote.noteCount > 51) {
										if(curNote.pitchPos.indexOf("#") > 0) {
											curImg = noteDownSharpImg;
										} else {
											curImg = noteDownImg;
										}
									} else {
										if(curNote.pitchPos != null) {
											if(curNote.pitchPos.indexOf("#") > 0) {
												curImg = noteUpSharpImg;
											} else {
												curImg = noteUpImg;
											}
										}
									}
									//YOffset to position individual notes on staff;
									int yOffSet = noteUtil.getNoteOffSet(curNote.pitchPos);
									returnImages[getImageIndex(yPos + yOffSet)].createGraphics().drawImage(curImg, (int) xPos, getPagePosY(yPos + yOffSet), null);
								}
							}
						}
						yPos += (staffHeight + staffBuffer*2);
					}
				}
			//text line
			} else if(lines[i].text.length() > 0) {
				if(yPos == 0) {
					yPos = textBuffer;
				}
				yPos += textBuffer;
			}
		}
		
		return returnImages;
	}
	
	//get page number based on Y index
	public int getImageIndex(double yPos) {
		int imgIndex = (int) Math.floor(yPos/PAGE_HEIGHT);
		return imgIndex;
	}
	
	//get Y position on page
	public int getPagePosY(double yPos) {
		int pageY = (int) yPos % PAGE_HEIGHT;
		return pageY;
	}
	
	public BufferedImage getImageFromFile(String fileName) {
		Image img = null;
		BufferedImage returnImg = null;
		File file = new File(fileName);
		try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			returnImg = getScaledImage((BufferedImage) img, img.getWidth(null)*IMAGE_SCALE, img.getHeight(null)*IMAGE_SCALE);
			//returnImg = (BufferedImage) img;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnImg;
	}
	
	public static BufferedImage getScaledImage(BufferedImage image, double width, double height) throws IOException {
	    int imageWidth  = image.getWidth();
	    int imageHeight = image.getHeight();

	    double scaleX = (double)width/imageWidth;
	    double scaleY = (double)height/imageHeight;
	    AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
	    AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

	    return bilinearScaleOp.filter(
	        image,
	        new BufferedImage((int) width, (int) height, image.getType()));
	}
}


