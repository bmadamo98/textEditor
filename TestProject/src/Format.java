
public interface Format {
	public void operations();
	public void load();
	public void save(char align, char space, String test);
	public String reformat();
	public void clearLists();
	
	public boolean checkFileSelected();
	public String blockJustify();
		
	public String getText();
	public String getFileName();
	public String getFilePath();
	public int getInitLines();
	public void getNewLines(String fileName);
	
	public void setText(String input);
	public void setFileName(String file);
	public void setFilePath(String path);
//	public void setInitLines(int lines);
//	public void setTotalLines(int total);
//	public void setCount(int count);
	public void setCharLim(int limit);
	
	public void addSpace();
}
