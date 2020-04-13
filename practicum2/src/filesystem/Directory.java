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
	 *         | super(dir,name,writable) 
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
	 *         | this(dir,name,true)
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
	 *         | this(null,name,true)
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
	 *         | this(name,true)
	 */
	public Directory(String name) {
		this(name, true);
	}
	

	
    /**********************************************************
     * Nieuwe shit
     **********************************************************/
	
	/**
	 * Variable referencing the ArrayList with the directories and files contained in this directory.
	 * 
	 * @invar The list of contents is effective.
	 *        | contents != null
	 * @invar CONSTRAINTS ON THE ITEMS IN THE CONTENTS LIST
	 *        each item is effective
	 *        each item is not terminated
	 *        each item has this directory as its parent directory
	 * 
	 */
	public ArrayList<FileSystemObject> contents = new ArrayList<FileSystemObject>();
	

	/**
	 * Return the file system object in this directory that has the given index
	 * 
	 * @param  index
	 * 		   The index of the wanted file system object
	 * @return The file system object in this directory with the given index
	 * 		   | return contents.get(index)
	 * @throws IndexOutOfBoundsException
	 *         The given index is not positive or is greater than the amount of 
	 *         items in this directory.
	 *         | (index < 1) || (index > getNbItems())
	 */
	@Basic @Raw
	public FileSystemObject getItemAt(int index) throws IndexOutOfBoundsException {
		return contents.get(index-1);
	}
	
	/**
	 * Return the file system object in this directory that owns the given name
	 * @param name
	 *        The given name
	 * @return the file system object in this directory that owns the given name if it is in
	 *         this directory, returns null otherwise
	 */
	public FileSystemObject getItem(String name) {
		FileSystemObject item = null;
		int R = this.getNbItems()-1;
		int L = 0;
		while(L<= R) {
			int mid = (int) Math.floor((R+L)/2);
			String objName = this.getItemAt(mid).getName();
			if(name.compareToIgnoreCase(objName) > 0) {
				L = mid+1;
			} else if(name.compareToIgnoreCase(objName) < 0) {
				R = mid-1;
			} else {
				item = this.getItemAt(mid);
			}
		}
		return item;
	}
	
	/**
	 * Check whether this directory can have the given file system object as one of its content items
	 * at the given index.
	 * 
	 * @param  obj
	 *         The file system object to check.
	 * @param  index
	 *         The index to check.
	 * @return False if this directory cannot have the given file system object 
	 *         as a content at any index.
	 *         | if (! canHaveAsItem(obj))
	 *         |   then result == false
	 *         Otherwise, false if the given index is not positive, or it exceeds the number
	 *         of grantees with more than one.
	 *         | else if ( (index<1)
	 *         |        || (index > getNbItems()+1) )
	 *         |   then result == false
	 *         Otherwise, true if and only if ...
	 */
	@Raw
	public boolean canHaveAsItemAt(FileSystemObject obj, int index) {
		// if (! canHaveAsItem(obj))
		//     return false;
		// Volgens één van de coding rules moet canHaveAsItem() in de klasse zitten, dit moet echter nog gebeuren.
		if ( (index<1) || (index>getNbItems()+1) )
			return false;
		
		// Check of het object op de juiste plaats zit volgens zijn naam (als het er tenminste inzit)
		// Als het er nog niet inzit: als je het in de lijst steekt op de vermelde index, of de lijst nog steeds juist geordend zou zijn
		if (hasAsItem(obj)) {
			
		}
		else {
			
		}
		
		return true;
		
	}
	
	
	
	/**
	 * Check whether this directory has proper contents associated with it.
	 * 
	 * @return True if and only if this directory can have each of its items as a item
	 *         at their index and if each item has this directory as its parent directory.
	 *         | result ==
	 *         |   for each I in 1...getNbItems() :
	 *         |      canHaveAsItemAt(getItemAt(I),I)  
	 *         |      && getItemAt(I).getParentDirectory==this
	 */
	@Raw
	public boolean hasProperItems() {
		for (int i=1; i<=getNbItems(); i++) {
			if ( !((canHaveAsItemAt(getItemAt(i),i) ) &&
				 (getItemAt(i).getParentDirectory() == this)) )
				return false;
		}
		return true;
	}
	
	
	/**
	 * Checks whether this directory contains a file system object that owns the given name
	 * 
	 * @param  name
	 * 		   The name to check.
	 * @return True if and only if this directory contains a file system object with the given name.
	 */
	public boolean exists(String name) {
		return false;
	}
	
	/**
	 * Returns the index of a given file system object.
	 * 
	 * @param  object
	 *         The file system object to get the index of.
	 * @return The index in the list where the given object is registered.
	 *         | object == getItemAt(index)
	 * @throws IllegalArgumentException
	 *         The given object is not in the directory.
	 *         | !hasAsItem(object)
	 */
	public int getIndexOf(FileSystemObject object) throws IllegalArgumentException {
		if (!hasAsItem(object))
			throw new IllegalArgumentException("This directory does not contain the given object.");
		else {
			int index = 1;
			while(getItemAt(index)!=object && index<=getNbItems() ) {
				index++;
			}
			return index;
		}		
	}
	
	/**
	 * Return the number of file system objects in this directory.
	 */
	public int getNbItems() {
		return contents.size();
	}
	
	/**
	 * Checks whether this directory contains the given file system object
	 * @param  obj
	 *         The file system object to be checked.
	 * @return True if and only if a file system object equal to the given file system objects
	 *         exists with this directory as its parent directory.
	 *         | result == 
	 *         |    there exists an I such that
	 *         |    (I>0) && (I<=getNbItems()) && getItemAt(I)==obj
	 */
	public boolean hasAsItem(FileSystemObject obj) {
		boolean bool = false;
		int index = 1;
		while((index <= this.getNbItems()) && (bool == false)) {
			if(this.getItemAt(index) == obj) 
				bool = true;
			else 
				index++;			
		}
		return bool;
	}
	
	
	/**
	 * Add an item to the contents of this directory
	 * 
	 * @param obj
	 *        The object that will be added to the directory
	 * @post  The file system object will be added to the contents of the directory such that the contents are lexicographically ordered
	 * 		  | int size = this.getNbItems();
			  | if(size == 0) then contents.add(obj)
		      | 	else
		      | int pos = 0;
		      | while(obj.getName().compareToIgnoreCase(this.getItemAt(pos).getName()) > 0)
		      |		pos = pos + 1
		      | while(size != pos) 
		      | 	contents.set(size+1, contents.get(size))
		      | 	size = size - 1
		      |	contents.set(pos, obj);
	 */
	public void addItem(FileSystemObject obj) {
		int size = this.getNbItems();
		if(size == 0) {
			contents.add(obj);
		} else {
			int pos = 0;

			while(obj.getName().compareToIgnoreCase(this.getItemAt(pos).getName()) > 0) {
				pos = pos + 1;
			}
			
			while(size != pos) {
				contents.set(size+1, contents.get(size));
				size = size - 1;
			}
			contents.set(pos, obj);
		}
	}
	
	
	public boolean isDirectOrIndirectSubdirectoryOf(Directory dir) {
		return false;
	}
}
