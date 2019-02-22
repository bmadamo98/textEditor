import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class TextAnalysis implements Analysis{
	
	protected int wordCount = 0, totalLines = 0, linesRemoved = 0, spaceAdded = 0;
	protected double aveWPL = 0, aveLineLength = 0;
	private String s;
	private Scanner scan;
	//changes to parameters of TextAnalysis affects AnalysisWindow.java's parameters
	//changes to parameters also affects Analysis.java interface
	//changes to parameters also affects parameters AnalysisWindow is called in GUI Analyze function
	public TextAnalysis(String string, int initLines, int charLength){
		// TODO Auto-generated constructor stub
		s = string;
		spaceAdded = getSpaceAdded(charLength);
		wordCount = wordCount(s);
		aveWPL = aveWordsPerLine();
		aveLineLength = aveLineLength();
		this.totalLines = getTotalLines();
		linesRemoved = initLines - this.totalLines;
	}
	public TextAnalysis() {
		s = "";
	}
	
	public int wordCount(String s){		
		 if (s == null || s.isEmpty()) {
		      return 0;
		    }

		    String[] words = s.split("\\s+");
		    return words.length;
	}
	
	public double aveLineLength(){
		scan = new Scanner(s);
		scan.useDelimiter("\\n");
		double average = 0;
		int m = 0;
		if(scan.hasNext()) {
			do{
				average = average + scan.next().length();
				m++;
			}while(scan.hasNext());
			
			average = average/m;
		}
		return average;
	}
	
	public double aveWordsPerLine(){
		scan = new Scanner(s);
		scan.useDelimiter("\\n");
		int m = 0;
		double average = 0;
		if(scan.hasNext()) {
			do{
				average = average + wordCount(scan.next());
				m++;
			}while(scan.hasNext());
			average = average/m;
		}
		return average;
	}
	
	public int getSpaceAdded(int c) {
		scan = new Scanner(s);
	
		int space = 0;
		int totalChar = (c*getTotalLines())-s.length();

		return totalChar;
	}
	
	public int getTotalLines(){
		String sections[] = s.split("\\n");
		totalLines = sections.length;
		return totalLines;
	}
}
