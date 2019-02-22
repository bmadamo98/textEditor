import java.awt.*;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.text.DecimalFormat;

import javax.swing.SwingConstants;
import javax.swing.JDialog;

public class AnalysisWindow extends TextAnalysis{

	//components to manipulate
	private JDialog Analysis;
	private JLabel wordprocessedNum, lineNum, blanklineNum, alNum, wplNum, spaceAdd;
	private JLabel lblBlankLine;
	
	//changes to parameters affects Analysis.java interface
	//changes also affects TextAnalysis' parameters
	//changes also affects parameters when called in GUI Analyze function
	public AnalysisWindow(String string, int initLines, int charLength,char align)/*int wordC, double aLL, double aWPL, int blanks, int lines)*/{
		super(string, initLines, charLength);
		initialize();
		DecimalFormat df = new DecimalFormat("#.###");
		wordprocessedNum.setText(Integer.toString(super.wordCount));
		lineNum.setText(Integer.toString(super.totalLines));
		if(super.linesRemoved >= 0) {
			lblBlankLine.setText("Number of Lines Removed :");
			blanklineNum.setText(Integer.toString(super.linesRemoved));
		}
		else {
			lblBlankLine.setText("Number of Lines Added :");
			blanklineNum.setText(Integer.toString(super.linesRemoved*(-1)));
		}
		
		alNum.setText(df.format(super.aveLineLength));
		wplNum.setText(df.format(super.aveWPL));
		spaceAdd.setText("0");
		if(align != 'l')
		{
			spaceAdd.setText(Integer.toString(super.spaceAdded));
		}
		
	}
	
	public AnalysisWindow() {	
		super();
		initialize();
	}

	private void initialize() {
		Analysis = new JDialog();	//decided to implement JDialog, another JFrame seemed too bothersome
		Analysis.setTitle("Analysis");

		Analysis.setSize(new Dimension(337, 230));	//Analysis.setMinimumSize(Dimension minimumSize()) may be needed
		Analysis.setModal(false);			//if this is set to true, then you have to exit window in order to interact with anything else
		Analysis.setLocationRelativeTo(null);
		
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{14, 219, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{54, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		Analysis.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblWordsProcessed = new JLabel("Words Processed :");			//Normal JLabel
		lblWordsProcessed.setVerticalAlignment(SwingConstants.BOTTOM);
		lblWordsProcessed.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblWordsProcessed = new GridBagConstraints();
		gbc_lblWordsProcessed.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblWordsProcessed.insets = new Insets(0, 0, 5, 5);
		gbc_lblWordsProcessed.gridx = 1;
		gbc_lblWordsProcessed.gridy = 0;
		Analysis.getContentPane().add(lblWordsProcessed, gbc_lblWordsProcessed);
		
		wordprocessedNum = new JLabel();						//JLabel that updates words processed
		wordprocessedNum.setVerticalAlignment(SwingConstants.BOTTOM);
		GridBagConstraints gbc_wordprocessedNum = new GridBagConstraints();
		gbc_wordprocessedNum.anchor = GridBagConstraints.SOUTHWEST;
		gbc_wordprocessedNum.insets = new Insets(0, 0, 5, 5);
		gbc_wordprocessedNum.gridx = 3;
		gbc_wordprocessedNum.gridy = 0;
		Analysis.getContentPane().add(wordprocessedNum, gbc_wordprocessedNum);
		
		JLabel lblNumberOfLines = new JLabel("Number of Lines :");	//Normal JLabel
		lblNumberOfLines.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblNumberOfLines = new GridBagConstraints();
		gbc_lblNumberOfLines.anchor = GridBagConstraints.EAST;
		gbc_lblNumberOfLines.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfLines.gridx = 1;
		gbc_lblNumberOfLines.gridy = 1;
		Analysis.getContentPane().add(lblNumberOfLines, gbc_lblNumberOfLines);
		
		lineNum = new JLabel();		//JLabel that updates number of lines
		GridBagConstraints gbc_lineNum = new GridBagConstraints();
		gbc_lineNum.anchor = GridBagConstraints.WEST;
		gbc_lineNum.insets = new Insets(0, 0, 5, 5);
		gbc_lineNum.gridx = 3;
		gbc_lineNum.gridy = 1;
		Analysis.getContentPane().add(lineNum, gbc_lineNum);
		
		
		//Space Added Stuff
		
		JLabel lblspaceAdded = new JLabel("Number of Space Added :");	//Normal JLabel
		lblspaceAdded.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblspaceAdded = new GridBagConstraints();
		gbc_lblspaceAdded.anchor = GridBagConstraints.EAST;
		gbc_lblspaceAdded.insets = new Insets(0, 0, 5, 5);
		gbc_lblspaceAdded.gridx = 1;
		gbc_lblspaceAdded.gridy = 6;
		Analysis.getContentPane().add(lblspaceAdded, gbc_lblspaceAdded);
		
		spaceAdd = new JLabel();
		GridBagConstraints gbc_spaceAdd = new GridBagConstraints();
		gbc_spaceAdd.anchor = GridBagConstraints.WEST;
		gbc_spaceAdd.insets = new Insets(0, 0, 5, 5);
		gbc_spaceAdd.gridx = 3;
		gbc_spaceAdd.gridy = 6;
		Analysis.getContentPane().add(spaceAdd, gbc_spaceAdd);
		
		lblBlankLine = new JLabel("Number of Blank Lines (Removed) :");	//Normal JLabel
		GridBagConstraints gbc_lblBlankLine = new GridBagConstraints();
		gbc_lblBlankLine.anchor = GridBagConstraints.EAST;
		gbc_lblBlankLine.insets = new Insets(0, 0, 5, 5);
		gbc_lblBlankLine.gridx = 1;
		gbc_lblBlankLine.gridy = 2;
		Analysis.getContentPane().add(lblBlankLine, gbc_lblBlankLine);
		
		blanklineNum = new JLabel();		//JLabel that updates number of blank lines (removed)
		GridBagConstraints gbc_blanklineNum = new GridBagConstraints();
		gbc_blanklineNum.anchor = GridBagConstraints.WEST;
		gbc_blanklineNum.insets = new Insets(0, 0, 5, 5);
		gbc_blanklineNum.gridx = 3;
		gbc_blanklineNum.gridy = 2;
		Analysis.getContentPane().add(blanklineNum, gbc_blanklineNum);
		
		JLabel lblAverageLineLength = new JLabel("Average Line Length :");	//Normal JLabel
		GridBagConstraints gbc_lblAverageLineLength = new GridBagConstraints();
		gbc_lblAverageLineLength.anchor = GridBagConstraints.EAST;
		gbc_lblAverageLineLength.insets = new Insets(0, 0, 5, 5);
		gbc_lblAverageLineLength.gridx = 1;
		gbc_lblAverageLineLength.gridy = 4;
		Analysis.getContentPane().add(lblAverageLineLength, gbc_lblAverageLineLength);
		
		alNum = new JLabel();	//JLabel that updates average line length
		GridBagConstraints gbc_alNum = new GridBagConstraints();
		gbc_alNum.anchor = GridBagConstraints.WEST;
		gbc_alNum.insets = new Insets(0, 0, 5, 5);
		gbc_alNum.gridx = 3;
		gbc_alNum.gridy = 4;
		Analysis.getContentPane().add(alNum, gbc_alNum);
		
		JLabel lblwpl = new JLabel("WPL(Words/Line) :");	//Normal JLabel
		GridBagConstraints gbc_lblwpl = new GridBagConstraints();
		gbc_lblwpl.insets = new Insets(0, 0, 5, 5);
		gbc_lblwpl.anchor = GridBagConstraints.EAST;
		gbc_lblwpl.gridx = 1;
		gbc_lblwpl.gridy = 5;
		Analysis.getContentPane().add(lblwpl, gbc_lblwpl);
		
		wplNum = new JLabel();		//JLabel that updates words per line
		GridBagConstraints gbc_wplNum = new GridBagConstraints();
		gbc_wplNum.insets = new Insets(0, 0, 5, 5);
		gbc_wplNum.anchor = GridBagConstraints.WEST;
		gbc_wplNum.gridx = 3;
		gbc_wplNum.gridy = 5;
		Analysis.getContentPane().add(wplNum, gbc_wplNum);
		
		//Analysis.pack();
		Analysis.setVisible(true);
	}

}
