package model;

import java.util.ArrayList;
import java.util.List;


public class ExplorerSchema {
	
	private int id;
	private String name;
	private boolean isDirectory;
	private List<ExplorerSchema> children = new ArrayList<>();
	private int level;
	
	
	
	public ExplorerSchema(String name, boolean isDirectory, int level) {
		super();
		this.name = name;
		this.isDirectory = isDirectory;
		this.level = level;
	}
	
	
	public ExplorerSchema() {
		super();
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isDirectory() {
		return isDirectory;
	}
	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}
	public List<ExplorerSchema> getChildren() {
		return children;
	}
	public void setChildren(List<ExplorerSchema> children) {
		this.children = children;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	
	
	
}
