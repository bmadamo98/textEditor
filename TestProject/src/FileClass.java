import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;



public class FileClass implements Format{
	private ArrayList<String> text = new ArrayList <String>(), lines = new ArrayList <String> (), paragraphs = new ArrayList <String> ();
	private String entire = "", name = "", path = null;
	private Scanner scan;
	private int initLines = 0, totalLines = 0,/*count = 0, */charLength = 80, spaces = 0;
	private boolean fileSelected = false;
	//changes to this parameters affects GUI during open function
	//changes also affect Format.java interface
	public FileClass(String filepath){
		this.path = filepath;
		clearLists();
		//STEP 1
		load();
		operations();
		return;
	}
	
	public FileClass() {
		clearLists();
		//STEP 1
		load();
		operations();
		return;
	
	}//end of constructor
	

	public void operations(){
		/*
		 * OPERATIONS
		 *	1) Check if there is a file selected via JFileChooser - call load function
		 *	2) Check if said file is null or wasn't selected
		 *	3) If there is a file, then open it
		 *	4) Read file
		 *		a)check if the first line has anything
		 *			CONCLUSION
		 *			i)if nothing, then ArrayList object "text" will be null
		 *			ii)otherwise, we have proof that there is something in the file
		 *		b)copy rest of file if ArrayList object "text" is not empty
		 *	5) Implement "Buffer" method to reorganize the strings within ArrayList object "text" to be within 80 char limit specifications
		 *	6) Final version of formatted file
		 *	7) Close file and return <- if no "return" then Program will be unresponsive
		 */
//			totalLines = 0;
			entire = "";
			String fileName = getFilePath();
			//String total = "";
			if(fileName != null) {
				scan = new Scanner(fileName);
			}
			if(fileName == null){		//STEP 2
				System.out.println("No file selected");
				entire = "";
				return;
			}
			else if(scan.hasNextLine()) {
			//else if(initLines(fileName) != 0){	//guarantees that fileName is returned - load file name to read
												//file is not empty
				getNewLines(fileName);
				fileSelected = true;
				entire = reformat();
			}
			else{
				entire = "";
			}//end of if - else-if - else statement
			int m = 0;
			int totalLength = 0;
			// interpreting blank lines removed as LINES FOUND minus FINAL LINES in the new file (paragraphs.size() - totalLines). 
			
			setText(entire);	
			scan.close();
			return;
	}
	public void clearLists() {
		paragraphs.clear();
		lines.clear();
		text.clear();
	}
	
	/*LOAD FUNCTION - TEAM EFFORT
	 * 1) Create File window to prompt user for selection
	 * 2) Make it so that only .txt files are read
	 * 3) Return the file's path (getAbsolutePath()) or file's name (getFileName()) - (returning file name didn't work)
	 * The whole idea was to save space, know which file to load, and to see if there were any exceptions with the window
	 */
	public void load(){	//JFileChooser appears to prompt .txt file selection
		JFileChooser choose = new JFileChooser(System.getProperty("user.home") + "Documents");	//sets current path at directory	
		//STEP 2
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT files", "txt");	//includes only .txt files
		choose.setFileFilter(filter);	//sets filter
		if(choose.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){	//condition to say that JFileChooser can show
			//STEP 3
			setFilePath(choose.getSelectedFile().getAbsolutePath());	//fileName is returned
			setFileName(choose.getSelectedFile().getName());
		}
		setFilePath(path);
	}
	
	public String reformat(){	//has valid file path and file has at least one line
		lines.clear();
		int n = 0;
		String temp = "";
		//Set up the code at this point that when called several factors are considered
		/*
		 * 1) File exists
		 * 2) File is not empty
		 * 3) Able the read the next (first) word till end (last word, which may not be read based on do-while condition)
		 * 4) Paragraphs ArrayList consists of a string until \n from original text
		 * 		a) when Paragraphs was created, "\t" was replaced with " " spaces
		 * 		b) using  scanner to read Paragraphs <- delimiter is " "
		 * 			i) possible complications with " \t " situation from original text being reformatted
		 * 5) Trim the entire string in the paragraph
		 * 6) Have a string constantly updated <- monitor that it is <80 char
		 * 		a) if it is 80 or more, then store into lines ArrayList and re-updates string to be ""
		 * 		b) if there is word >=80 char, check if there is a word before it
		 * 			i) if there is a word followed up by 80+ char word, then store word and update the string to be ""
		 * 			ii) else, continuously partition the long word into lines of 80 char until last partition is reached
		 * 				1) implies that last partition is <80 char <- set string to be this last segment of the long word
		 * 		c)else, several considerations 
		 * 			i) section a) only updates strings, does not store the string
		 * 				section b.i) stores and partitions strings
		 * 				section b.ii) does not store last partitioned string
		 * 				
		 * 				Taking those into consideration:
		 * 					This one needs to store regularly monitored string
		 * 						1) string is >80 and next word is not >80 char
		 * 						2) so when string is stored and re-updated to be "", next word is hanging
		 * 						3) re-update string to be next word instead
		 * 7) Lines ArrayList now contains all the lines
		 * 		a) no "\n" or "\t"
		 * 		b) update entire string for display using while loop
		 * 8) return entire string
		 * 9) Easy-peasy, lemon-squeezy ;)
		 */
		while (n < paragraphs.size()){
			scan = new Scanner(paragraphs.get(n));	//each item in text goes up to "\n" <- keep in mind that "\t" are replaced with " "
			scan.useDelimiter(" ");
			do{
				String next = scan.next().trim();	//trims excess " " possibly from "\t" converted into " " resulting in a series of "    " between words
				if(next.isEmpty()){
					//case where string is empty due to delimiter <- "\t" was replaced with " "
				}
				else if((temp.length()+ next.length())<charLength){	//if the string plus the next word plus a space is less than 80 char, then update string
					temp = temp + next + " ";
				}
				else if(next.length() >= charLength){	//a word >=80 characters
					if(temp.length() > 0){
						lines.add(temp);
						temp = "";
					}
					int m = 0;
					int length = next.length(); 
					do{
						if(next.length()<charLength){
							if(!scan.hasNext()){	//last word is a big word
								lines.add(next);
								break;
							}
							temp = next + " ";
						}
						else{
							lines.add(next.substring(0,charLength));
							next = next.substring(charLength, next.length());
							m++;
						}
					}while(m < length/charLength);
				}
				else{	//else, string is larger than 80 characters
					lines.add(temp);	
					temp = next + " ";
				}
			}while(scan.hasNext());//end of paragraph parsing
			n++;
		}
		/*the do while's condition involved scan having next item to read
		 *however, I believe it exits when dealing with the last word
		 *somewhere, temp was given the string of the next (or last word in this case)
		 *if it is not empty, then add it
		 */
		if(!temp.isEmpty()){	 
			lines.add(temp);
			temp = "";
		}
		//the lines array consists of the final strings to be displayed <- cleared at end of FileClass constructor
		totalLines = lines.size();
		//		setTotalLines(lines.size());
		int m = 0;
		entire = "";
		while (m < lines.size()){
			entire = entire + lines.get(m) + "\n";
			m++;
		}
		m = 0;
		//delete comment below to view what was stored in lines ArrayList
		return entire;
	}
	
	
	public void save(char align, char space, String test){
		if(fileSelected)
		{
			//JFileChooser appears to prompt .txt file selection
			//STEP 1
			JFileChooser choose = new JFileChooser(System.getProperty("user.home") + "Documents");	//sets current path at directory	
			//STEP 2
			FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT files", "txt");	//includes only .txt files
			choose.setFileFilter(filter);	//sets filter
			if(choose.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){	//condition to say that JFileChooser can show
				//STEP 3
				setFilePath(choose.getSelectedFile().getAbsolutePath());	//fileName is returned
				setFileName(choose.getSelectedFile().getName());
			}
			else{
				setFileName(null);	//null only happens when user "exits" JFileChooser window
								//without this, program crashes when user closes without canceling
				//ONLY POSSIBLE ISSUE
				//You have a file formatted and accidentally open a file.
				//Closing/canceling causes the format to be updated to say "null"
			}
			System.out.println("Saving file");
		
			// Printwriter
			//Initial statements
			int i = 0;

			String outFile = getFilePath();
			scan = new Scanner(outFile);
			scan.useDelimiter("formatted.txt");	//used in case there is a fileNameformatted.txt already created <- overwrites previously formatted file
			String formOutFile = scan.next();
			if(formOutFile.substring(formOutFile.length()-4,formOutFile.length()).equals(".txt")){	//delimiter didn't work <-file = fileName.txt
				formOutFile = formOutFile.substring(0, formOutFile.length()-4) + "formatted.txt";
			}
			else{
				formOutFile = formOutFile + "formatted.txt";	//delimiter was used <- file = fileNameformatted.txt <- file = fileName
			}
			
			try {
				PrintWriter writer = new PrintWriter(formOutFile);//Opening writer method
				while(i < lines.size())
				{
					String newLine = "\r\n";
					String get = lines.get(i);
					//Right Experiment
					if(align == 'r')
					{
						while(get.length() < charLength)
						{	spaces = spaces + 1;
							get = " "+ get ;
						}
						System.out.println(spaces);
						if(space == '1')
						{get = get + "\r\n";}
					}else if(align == 'b')
					{
						Scanner s = new Scanner(test);
						get = test;
						if(space == '1') {
						s.useDelimiter("\n");
						String first = s.next();
						get = first;
						while(s.hasNext()) {
						String second = s.next();
						get = get +"\n\n" + second;
						}
						}
						i = lines.size();
					}
					if(align == 'l' && space == '1')
					{get = get + "\r\n";}
					writer.println(get);
					i++;
				}
			System.out.println("Saving Done");
			writer.close();
			}//end of try
			catch(IOException t)
			{
				System.err.format("Exception: %s%n", t);
			}
		}
		else
		{
			System.out.println("No file to save");
		}
		 
	}
	
	public void getNewLines(String fileName){
		try{
			scan = new Scanner(new File(fileName));
			scan.useDelimiter("\\n");
			while(scan.hasNext()){
				paragraphs.add(scan.next().replaceAll("\t", " "));
			}
			int size = paragraphs.size();
			if(size != 0) {
				setInitLines(paragraphs.size()+1);
			}
			else {
				setInitLines(paragraphs.size());
			}
		}
		catch(NullPointerException d){
			System.err.format("Exception: %s%n", d);
		}
		catch(FileNotFoundException f){
			System.err.format("Exception: %s%n", f);
		}
		return;
	}
	
	public int blanksRemoved(){
		int linesRemoved = 0;
		if(totalLines > 1){
			linesRemoved = paragraphs.size() - totalLines;
		}
		return linesRemoved;
	}
	
	public String blockJustify(){
		String display = "";
		//Adds spaces to the string until it reaches 80 characters
		//goes through each line and breaks each line into words
		//due to the difference in non-space characters to 80 will involve that many iterations
		for(int i = 0; i < lines.size()-1;i++){
			String line = lines.get(i);				//get the line to manipulate
			String parts[] = line.split(" ");		//how many words are in the line
			parts[parts.length-1].trim();			//get rid of \n at the end
			//calculating how many spaces are needed
			//(total needed) - (string length) + (spaces between each word)
			if(parts.length > 1) {
				int remaining = charLength-(line.length()-1)+(parts.length-1);			
				int j = 0;								//index at j
			    while(remaining > 0){
		        	int index = j%(parts.length-1);			//counter mod (words-1)
		        	parts[index] = parts[index] + " ";	//adds spaces to the end of each word but the last
		        	j++;
		        	remaining--;
		        }
			}
		        for(int k = 0; k < parts.length; k++){
		        	display = display + parts[k];		//stores words with appropriate spaces into display
		        }
		        display = display + "\n";			//appends \n to the end of the line lines.size()-1 times
		        //System.out.println(display.length()%(charLength+1));
		}
		display = display + lines.get(lines.size()-1);	//appends last line
        //System.out.println(display.length()%charLength);
		
		return display;
	}
	
	public void setCharLim(int limit) {
		charLength = limit;
		entire = reformat();
		setText(entire);
		return;
	}
	
	public boolean checkFileSelected()
	{
		return fileSelected;
	}
	
	//basic getter functions
	public String getText(){	//retrieve final display string that is within specifications
		return entire;
	}
		
	public String getFileName(){
		return name;
	}
	
	public String getFilePath(){
		return this.path;
	}
		
	public int getInitLines(){
		return initLines;
	}
	//basic setter functions
	public void setText(String input){	//Final String to be displayed on textArea JComponent of JTextField
		entire = input;
	}
	
	public void setFileName(String file){
		this.name = file;
	}
	
	public void setFilePath(String path){
		this.path = path;
	}
	
	public void setInitLines(int n){
		initLines = n;
	}

	@Override
	public void addSpace() {
		
		
	}
	
	public int getCharLength()
	{
		return charLength;
	}

	
	
	
/*	public void setTotalLines(int n){
		totalLines = n;
	}
	
	public void setCount(int n){
		count = n;
	}
*/	
}
