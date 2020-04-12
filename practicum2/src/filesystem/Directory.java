package filesystem;

import be.kuleuven.cs.som.annotate.*;
import java.util.ArrayList;

/**
 * A class of directories
 * 
 * INVARS
 * 
 * @author  Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 * @version 1.0
 *
 */


public class Directory extends FileSystemObject {
	
    /**********************************************************
     * Constructors
     **********************************************************/
	
	/**
	 * Initialize a new directory with a given parent directory, name and writability
	 * 
	 * @param  dir
	 *         The parent directory of the new directory.
	 * @param  name
	 *         The name of the new directory.
	 * @param  writable
	 *         The writability of the new directory.
	 * @effect The new directory is a file system object with the given parent directory,
	 *         name and writability.
	 *       | super(dir,name,writable) 
	 */
	public Directory(Directory dir, String name, boolean writable) {
		super(dir, name, writable);
	}
	
	/**
	 * Initialize a new directory with a given parent directory and name.
	 * 
	 * @param  dir
	 *         The parent directory of the new directory.
	 * @param  name
	 *         The name of the new directory.
	 * @effect The new directory is initialized with the given parent directory and name,
	 *         and writability true.
	 *       | this(dir,name,true)
	 */
	public Directory(Directory dir, String name) {
		this(dir, name, true);
	}
	
	/**
	 * Initialize a new root directory with a given name and writability.
	 * 
	 * @param  name
	 *         The name of the new directory.
	 * @param  writable
	 *         The writability of the new directory.
	 * @effect The new root directory is initialized with the given name and writability.
	 *       | this(null,name,true)
	 */
	public Directory(String name, boolean writable) {
		this(null, name, writable);
	}
	
	/**
	 * Initialize a new root directory with a given name.
	 * 
	 * @param  name
	 *         The name of the new directory.
	 * @param  writable
	 *         The writability of the new directory.
	 * @effect The new root directory is initialized with the given name
	 *         and true as its writability.
	 *       | this(name,true)
	 */
	public Directory(String name) {
		this(name, true);
	}
	

	
    /**********************************************************
     * Nieuwe shit
     **********************************************************/
	
	/**
	 * Variable referencing the ArrayList with the directories and files contained in this directory
	 */
	public ArrayList<FileSystemObject> contents = new ArrayList<FileSystemObject>();
	
	/**
	 * Add an item to the contents of this directory
	 * 
	 * @param obj
	 *        The object that will be added to the directory
	 * @post  The file system object will be added to the contents of the directory such that the contents are lexicographically ordered
	 * 		  | int length = this.getNbItems();
			  | if(length == 0) then contents.add(obj)
		      | 	else
		      | int pos = 0;
		      | while(obj.getName().compareToIgnoreCase(this.getItemAt(pos).getName()) > 0)
		      |		pos = pos + 1
		      | while(length != pos) 
		      | 	contents.set(length+1, contents.get(length))
		      | 	length = length - 1
		      |	contents.set(pos, obj);
	 */
	public void addItem(FileSystemObject obj) {
		int length = this.getNbItems();
		if(length == 0) {
			contents.add(obj);
		} else {
			int pos = 0;

			while(obj.getName().compareToIgnoreCase(this.getItemAt(pos).getName()) > 0) {
				pos = pos + 1;
			}
			
			while(length != pos) {
				contents.set(length+1, contents.get(length));
				length = length - 1;
			}
			contents.set(pos, obj);
		}
	}
	

	/**
	 * Return the file system object in this directory that has the given index
	 * 
	 * @param index
	 * 		  The index of the wanted file system object
	 * @return The file system object in this directory with the given index
	 * 		   |return contents.get(index)
	 */
	public FileSystemObject getItemAt(int index) {
		return contents.get(index);
	}
	
	/**
	 * Return the file system object in this directory that owns the given name
	 * @param name
	 * @return
	 */
	public FileSystemObject getItem(String name) {
		
		return null;
	}
	
	/**
	 * Checks whether this directory contains a file system object that owns the given name
	 * 
	 * @param name
	 * 		  The given name
	 * @return true if and only if this directory contains a file system object with the given name
	 */
	public boolean exists(String name) {
		return false;
	}
	
	/**
	 * Returns the index of a given file system object
	 * 
	 * @param object
	 *        The file system object
	 * @return the index of the given object
	 */
	//ER MOET HIER OP EEN OF ANDERE MANIER EEN ERROR OFZO KOMEN ALS ET OBJECT NIET BESTAAT
	public int getIndexOf(FileSystemObject object) {
		int index = 0;
		while(this.getItemAt(index) != object) {
			index = index + 1;
		}
		return index;
	}
	
	/**
	 * return the number of object in this directory
	 */
	public int getNbItems() {
		return contents.size();
	}
	
	/**
	 * Checks whether this directory contains the given file system object
	 * @param dir
	 *        The given file system object
	 * @return
	 */
	public boolean hasAsItem(FileSystemObject obj) {
		boolean bool = false;
		int index = 0;
		while((index <= this.getNbItems()) && (bool == false)) {
			if(this.getItemAt(index) == obj) {
				bool = true;
			} else {
				index = index + 1;
			}
		}
		return bool;
	}
	
	public boolean isDirectOrIndirectSubdirectoryOf(Directory dir) {
		return false;
	}
}
