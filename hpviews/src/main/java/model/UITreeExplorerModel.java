package model;

import java.util.ArrayList;
import java.util.List;


public class UITreeExplorerModel {
	
	private int id;
	private String name;
	private boolean isDirectory;
	private List<UITreeExplorerModel> children = new ArrayList<>();
	private int level;
	
	
	
	public UITreeExplorerModel(String name, boolean isDirectory, int level) {
		super();
		this.name = name;
		this.isDirectory = isDirectory;
		this.level = level;
	}
	
	
	public UITreeExplorerModel() {
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
	public List<UITreeExplorerModel> getChildren() {
		return children;
	}
	public void setChildren(List<UITreeExplorerModel> children) {
		this.children = children;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	
	
	
}
