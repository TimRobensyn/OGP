package filesystem;

import java.util.*;

public class File extends FileSystemObject {

	
	public File(Directory dir, String name, int size, boolean writable, String type) {
		super(dir, name, writable);
	}
	
	public File(String name, int size, boolean writable, String type) {
		this(null, name, size, writable, type);
	}
	
	public File(String name, String type) {
		this(null, name, 0, true, type);
	}
	
	public File(Directory dir, String name, String type) {
		this(dir, name, 0, true, type);
	}
	


	/**
	 * Variable referencing the type of this file
	 */
	private String type = "txt";
	
	public String getType() {
		return this.type;
	}
	
	
	/**
	 * Variable referencing the list of possible types
	 */
	// Moet dit ding final zijn of static ofzo
	Set<String> typeSet = new HashSet<>(Arrays.asList("txt","pdf","java"));
	
	public Set<String> getTypeSet() {
		return typeSet;
	}
	
	//Is dit static dan?
	public void addTypeSet(String type) {
		typeSet.add(type);
	}
	

}
