import javax.swing.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.Window.Type;
import java.beans.*;

public class GUI extends JFrame{
	//Functional items		
	private JFrame frame;
	private JTextPane textArea;
	private JMenuItem mntmOpen, mntmSave, mntmExit, mnAnalyze, mnLeft, mnRight, mnBlock, mnSingle, mnDouble;
	private FileClass fc = null;
	private JTextField input;
	private JButton enter;
	private String display = "";	//final display of strings
	private char alignm = 'l';
	public char spacing = '0';
	private String fontStyle = "Consolas";
	private int fontSize = 11, limit = 80;
	private Font font = new Font(fontStyle, Font.PLAIN, fontSize);
	private char block = 'n';
	
	
	
	private JMenuItem mntmSingleSpace;
	private JMenu mnSpacing;
	private JMenuItem mntmDoubleSpace;

	
	
	public GUI() {
		frame = new JFrame("Text Formatter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//getContentPane().setLayout(new FlowLayout());			//not sure if this is needed
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{357, 0};
		gridBagLayout.rowHeights = new int[]{291, 16, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		
		textArea = new JTextPane();		//JTextPane object
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);				///////////////disabled input
		textArea.setFont(font);
		
		// Main TextPane Default Styling
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet align = new SimpleAttributeSet();
		StyleConstants.setAlignment(align, StyleConstants.ALIGN_LEFT);
		doc.setParagraphAttributes(0, doc.getLength(), align, false);		
		
		
		//Random Items 
		JToolBar bottomBar = new JToolBar();
		bottomBar.setFloatable(false);
		GridBagConstraints gbc_bottomBar = new GridBagConstraints();
		gbc_bottomBar.anchor = GridBagConstraints.NORTHWEST;
		gbc_bottomBar.gridx = 0;
		gbc_bottomBar.gridy = 1;
		frame.getContentPane().add(bottomBar, gbc_bottomBar);
		
		input = new JTextField("80");
		input.setMinimumSize(new Dimension(59, 19));
		input.setPreferredSize(new Dimension(60,20));
		bottomBar.add(input);
		
		enter = new JButton("Enter");
		enter.addActionListener(new actionListener());
//		JLabel wordCountText = new JLabel("Word Count : ");
//		bottomBar.add(wordCountText);
		bottomBar.add(enter);
//		wordCountNum = new JLabel();
//		bottomBar.add(wordCountNum);
		
		frame.setSize(586, 400);
		
		//Identify functions that need EventHandler actionListeners
		JMenuBar menuBar = new JMenuBar();	//menu bar
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");	//file menu
		menuBar.add(mnFile);
		
		mntmOpen = new JMenuItem("Open");	//OPEN FUNCTION
		mnFile.add(mntmOpen);
		
		mntmSave = new JMenuItem("Save");	//SAVE FUNCTION
		mnFile.add(mntmSave);
		
		mntmExit = new JMenuItem("Exit");	//EXIT FUNCTION
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");	//edit menu
		menuBar.add(mnEdit);
		
		mnAnalyze = new JMenuItem("Analyze");	//ANALYZE FUNCTION
		mnEdit.add(mnAnalyze);
		
		mnSpacing = new JMenu("Space Line");
		mnEdit.add(mnSpacing);
		
		mnDouble = new  JMenuItem("Double-Spaced");
		mnSpacing.add(mnDouble);
		
		mnSingle = new JMenuItem("Single-Spaced");
		mnSpacing.add(mnSingle);
		
		JMenu mnNewMenu = new JMenu("Alignment");	//alignment options
		mnEdit.add(mnNewMenu);
		
		mnLeft = new JMenuItem("Left");		//LEFT FUNCTION
		mnNewMenu.add(mnLeft);
		
		mnRight = new JMenuItem("Right");	//RIGHT FUNCTION
		mnNewMenu.add(mnRight);
		
		mnBlock = new JMenuItem("Block");	//BLOCK FUNCTION
		mnNewMenu.add(mnBlock);
		
	
		
		mntmOpen.addActionListener(new actionListener());	//open
		mntmSave.addActionListener(new actionListener());	//save
		mntmExit.addActionListener(new actionListener());	//exit
		mnAnalyze.addActionListener(new actionListener());	//analyze
		mnLeft.addActionListener(new actionListener());		//left justify
		mnRight.addActionListener(new actionListener());	//right justify
		mnBlock.addActionListener(new actionListener());    //block justify
		mnSingle.addActionListener(new actionListener());	//Single Space
		mnDouble.addActionListener(new actionListener());	//Double Space
		
		frame.setLocationRelativeTo(null); //Centers the Window
		frame.setVisible(true);		
		
	}
	
	public void check_block() {
		if(block == 'y') {
			textArea.setText(fc.blockJustify());
			return;
		}
		textArea.setText(display);
		return;
	}
	
	public boolean file_status() {
		if(fc != null && !fc.getText().isEmpty()) {
			return true;
		}
		return false;
	}
	
	private class actionListener implements ActionListener{	//edit textArea
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == mntmOpen){			//OPEN FUNCTION
				if(fc != null){
					//changes to FileClass parameters -> changes to Format.java interface
					fc = new FileClass(fc.getFilePath());
				}
				else{
					fc = new FileClass();		//calls FileClass that loads, reads, and displays text into textArea
				}
				//at this point, fc has been created for the rest of the functions to use with no repercussions
				display = fc.getText();
				//test = new TextAnalysis(display);
				textArea.setText(display);
				//wordCountNum.setText(Integer.toString(test.wordCount(display)));
			}//end of OPEN FUNCTION
			
			else if(e.getSource() == mntmSave){		//SAVE FUNCTION
				if(fc != null){	//means that open function was called and retrieved a file that was later reformatted
					if(alignm != 'b') {
					String holder = " ";
					fc.save(alignm, spacing,holder );//Defaults to left
				}else if(alignm == 'b')
				{
					String temp = fc.blockJustify();
					fc.save(alignm, spacing,temp);
					
				}
				}
			}
			else if(e.getSource() == mntmExit){		//EXIT FUNCTION
				frame.dispose();
			}
			else if(e.getSource() == mnAnalyze){	//ANALYZE FUNCTION
				if(fc != null && fc.getInitLines() != 0){
					//parameters of AnalysisWindow -> parameters of TextAnalysis -> changes to Analysis.java interface
					AnalysisWindow aw = new AnalysisWindow(display, fc.getInitLines(), fc.getCharLength(), alignm);	
				}
				else{
					AnalysisWindow aw = new AnalysisWindow();

				}
			}
			if(e.getSource() == mnLeft){			//LEFT FUNCTION
				block = 'n';
				textArea.setText(display);
				StyledDocument doc = textArea.getStyledDocument();
				SimpleAttributeSet align = new SimpleAttributeSet();
				StyleConstants.setAlignment(align, StyleConstants.ALIGN_LEFT);
				doc.setParagraphAttributes(0, doc.getLength(), align, false);
				alignm = 'l';
				
			}
			else if(e.getSource() == mnRight){		//RIGHT FUNCTION
				block = 'n';
				textArea.setText(display);
				StyledDocument doc = textArea.getStyledDocument();
				SimpleAttributeSet align = new SimpleAttributeSet();
				StyleConstants.setAlignment(align, StyleConstants.ALIGN_RIGHT);
				doc.setParagraphAttributes(0, doc.getLength(), align, false);
				alignm = 'r';
				
			}
			else if(e.getSource() == mnBlock){		//BLOCK FUNCTION
				if(file_status()) {
					//default auto-left
					StyledDocument doc = textArea.getStyledDocument();
					SimpleAttributeSet align = new SimpleAttributeSet();
					StyleConstants.setAlignment(align, StyleConstants.ALIGN_LEFT);
					doc.setParagraphAttributes(0, doc.getLength(), align, false);
					alignm = 'l';
					
					block = 'y';
					String temp = fc.blockJustify();
					//System.out.println(temp);
					//this next two lines of code changes how the text is presented in textArea - Consolas can display the spaces
					//former problem was that added spaces were not displaying and still looked like left justification
					//Font font = new Font(fontStyle, Font.PLAIN, fontSize);
					textArea.setText(temp);
					alignm = 'b';
				}
			}
			else if(e.getSource() == mnSingle){		//Single-Space FUNCTION
				if(file_status()) {
					check_block();
					StyledDocument doc = textArea.getStyledDocument();
					SimpleAttributeSet space = new SimpleAttributeSet();
					StyleConstants.setLineSpacing(space, 0);
					doc.setParagraphAttributes(0, doc.getLength(), space, false);
					spacing = '0';
				}
			}
			else if(e.getSource() == mnDouble){		//Double-Space FUNCTION
				if(file_status()) {
					check_block();
					StyledDocument doc = textArea.getStyledDocument();
					SimpleAttributeSet space = new SimpleAttributeSet();
					StyleConstants.setLineSpacing(space, 1);
					doc.setParagraphAttributes(0, doc.getLength(), space, false);
					spacing = '1';
				}
			}
			else if(e.getSource() == enter)
			{
				if(file_status()) {
					textArea.setText("Test");
					try
					{
						limit = Integer.parseInt(input.getText());
					}
					catch(NumberFormatException f)
					{
						check_block();
					}
					catch(NullPointerException d)
					{
						check_block();
					}
					if(limit >= 50 && limit <= 120)
					{
						fc.setCharLim(limit);
						display = fc.getText();
						//System.out.println(fc.getText());
					}
					check_block();
					
				}
			}
		}
	}
	
	
}