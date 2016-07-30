package tabToNotes.core;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DevMode {
	

	static BufferedImage devImg;
	static BufferedImage[] devImgArray;
	static JFrame devFrame;
	static JPanel devPanel;
	static JScrollPane scrollPane;
	static String testFilePath = "C:\\Users\\Corey\\programming stuff\\tomcat\\apache-tomcat-7.0.56\\webapps\\tabToNotes\\testTabs\\testTab1.txt";
	static TabToNotes t2n;
	
	static int width = 1000;
	static int height = 750;
	
	public static void main(String[] args) {
		
		String input = "";
		try {
			input = getTestTab();
		} catch (IOException e) {
			e.printStackTrace();
		}
		t2n = new TabToNotes();
		if(input.length() > 0) {
			t2n.createFile(input);
			//This doesn't work anymore since we're breaking up the image into a PDF
			devImgArray = t2n.getImage(input);
			devImg = devImgArray[0];
			devDrawImage(devImg);
		}
	}

	/**
	 * TODO: Actually pull in test tab as a string
	 * Method to pull in a test tab as a String
	 * @return returnString - test tab string value
	 */
	public static String getTestTab() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(testFilePath));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}

	/**
	 * Access to dev tools. Based off of devMode value
	 * @param img - what should be drawn onto the pdf.
	 * - drawn onto JPanel in devMode
	 */
	public static void devDrawImage(final Image img) {
		devFrame = new JFrame();
		//not sure about setting size here
		//devFrame.setSize(devImg.getWidth(devFrame), devImg.getHeight(devFrame));
		devFrame.setSize(img.getWidth(null)+20, height);
		devPanel = new JPanel(){
			public void paint(Graphics g){
				//g.drawString("testtt", 20, 20);
				g.drawImage(img, 0, 0, this);
		    }
		};
		devPanel.setPreferredSize(new Dimension(img.getWidth(null), img.getHeight(null)));
		scrollPane = new JScrollPane(devPanel);
		scrollPane.setSize(devFrame.getSize());
		scrollPane.getVerticalScrollBar().setUnitIncrement(100);
		devFrame.add(scrollPane);
		//setting visible at the end makes sure everything renders.
		devFrame.setVisible(true);
	}
}
