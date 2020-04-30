package filesystem;

import be.kuleuven.cs.som.annotate.*;
import java.util.ArrayList;

/**
 * A class of directories
 * 
 * @invar   The contents associated with each directory must be proper contents
 *          for that directory.
 *          | hasProperItems()
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
     * File system objects in this directory
     **********************************************************/
	
	/**
	 * Variable referencing the ArrayList with the directories and files contained in this directory.
	 * 
	 * @invar The list of contents is effective.
	 *        | contents != null
	 * @invar Each item in the contents list is effective.
	 *        | for each item in contents:
	 *        |   item != null
	 * @invar Each item in the contents list is not terminated.
	 *        | for each item in contents:
	 *        |   !item.isTerminated()
	 * @invar Each item in the contents list has this directory as its parent directory.
	 *        | for each item in contents:
	 *        |   item.getParentDirectory() == this
	 * @invar The lists of contents is alphabetically ordered.
	 *        | for each I in 2..getNbItems()
	 *        |   getItemAt(I).isLexicographicallyAfter(getItemAt(I-1)) 
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
	 * @param  name
	 *         The name of the file system object to get.
	 * @return The file system object in this directory that owns the given name if it is in
	 *         this directory, returns null otherwise.
	 * @throws IllegalArgumentException
	 * 		   There is no object with the given name in this directory
	 *         | !exists(name)
	 */
	public FileSystemObject getItem(String name) throws IllegalArgumentException {
		if(! exists(name)) {
			throw new IllegalArgumentException("This directory does not contain an object with the given name.");
		} else {
			FileSystemObject item = null;
			int R = getNbItems();
			int L = 1;
			while(L <= R) {
				int mid =  (int) Math.floor((R+L)/2);
				String objName = this.getItemAt(mid).getName();
				if(name.compareToIgnoreCase(objName) > 0) {
					L = mid+1;
				} else if(name.compareToIgnoreCase(objName) < 0) {
					R = mid-1;
				} else {
					item = this.getItemAt(mid);
					break;
				}
			}
			return item;
		}
	}
	
	
	/**
	 * Check whether this directory can have the given file system object as one of its content items.
	 * 
	 * @param  obj
	 *         The file system object to check.
	 * @return If the given file system object is not effective, it is equal to this directory, it is not writable,
	 *         it is terminated or this directory is terminated, then return false.
	 *         | if ( obj == null || obj == this || !this.isWritable()
	 *         |   || obj.isTerminated() || this.isTerminated() )
	 *         |   then result == false 
	 *         Else if the given object is an item of this directory, then true if and only if this objects name
	 *         does not occur at other objects in this directory.
	 *         | else if ( this.hasAsItem(obj))
	 *         |   then result == for one I in 1...getNbItems():
	 *         |                    getItemAt(i).getName().equalsIgnoreCase(obj.getName())
	 *         else the given object is not an item of this directory, then true if and only if the given objects
	 *         name does not yet occur in this directory and the given object is a root item or its parent
	 *         directory allows us to move the object to this directory.
	 *         |   else result == ( !this.exists(obj.getName()) &&
	 *         |                    (obj.isRoot() || obj.getParentDirectory().isWritable()) )
	 */
	@Raw
	public boolean canHaveAsItem(@Raw FileSystemObject obj) {
		if (obj == null || obj==this || !this.isWritable() 
			|| obj.isTerminated() || this.isTerminated() )
		    return false;
		if (hasAsItem(obj)) {
			int indexOfObj = getIndexOf(obj);
			String nameOfObj = obj.getName();
			boolean bool = true;
			for (int i=1; i<=getNbItems();i++) {
				if (getItemAt(i).getName().equalsIgnoreCase(nameOfObj)&& i!=indexOfObj) bool = false;
			}
			return bool;
		}
		else {
			return ( !this.exists(obj.getName()) &&
					  (obj.isRoot() || obj.getParentDirectory().isWritable()) );
		}
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
	 *         Otherwise, if the object is in this directory, then true if and only if its predecessor is 
	 *         lexicographically before this object and its successor after this object.
	 *         If the object is not in this directory, then true if and only if inserting this object
	 *         in the list would not result in an unordered list.
	 *         | else if (hasAsItem(obj))
	 *         |   then result == (index == 1 ||  getItemAt(index-1).isLexicographicallyBefore(obj))
			   |	              && (index == getNbItems() || getItemAt(index+1).isLexicographicallyAfter(obj)) )
	 *		   |   else result == (index == 1 ||  getItemAt(index-1).isLexicographicallyBefore(obj))
			   |	              && (index == getNbItems() || getItemAt(index+1).isLexicographicallyAfter(obj)) )
	 */
	@Raw
	public boolean canHaveAsItemAt(FileSystemObject obj, int index) {
		if (! canHaveAsItem(obj))
		    return false;
		if ( (index<1) || (index>getNbItems()+1) )
			return false;
		
		if (hasAsItem(obj)) {
			return (index == 1 ||  getItemAt(index-1).isLexicographicallyBefore(obj))
					&& (index == getNbItems() || getItemAt(index+1).isLexicographicallyAfter(obj));
		}
		else {
			return (index == 1 ||  getItemAt(index-1).isLexicographicallyBefore(obj))
					&& (index == getNbItems() + 1 || getItemAt(index).isLexicographicallyAfter(obj));
		}
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
	@Basic @Raw
	public int getNbItems() {
		return contents.size();
	}
	
	
	
	/**
	 * Checks whether this directory contains the given file system object.
	 * @param  obj
	 *         The file system object to be checked.
	 * @return True if and only if a file system object equal to the given file system objects
	 *         exists with this directory as its parent directory.
	 *         | result == 
	 *         |    there exists an I such that
	 *         |    (I>0) && (I<=getNbItems()) && (getItemAt(I)==obj)
	 */
	@Raw
	public boolean hasAsItem(@Raw FileSystemObject obj) {
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
	 * Checks whether this directory contains a file system object that owns the given name
	 * 
	 * @param  name
	 * 		   The name to check.
	 * @return True if and only if this directory contains a file system object with the given name.
	 */
	public boolean exists(String name) {
		boolean bool = false;
		int index = 1;
		while((index <= getNbItems())&&(bool == false)) {
			if(getItemAt(index).getName().equalsIgnoreCase(name)) {
				bool = true;
			}
			index++;
		}
		return bool;
	}
	

	/**
	 * Add an file system object to the contents of this directory.
	 * 
	 * @param  obj
	 *         The file system object that will be added to the content items of this directory.
	 * @effect This directory has the given file system object as one of its content items.
	 *         | new.hasAsItem(obj)
	 * @effect The number of content items of this directory is incremented by 1.
	 *         | new.getNbItems() == getNbItems()+1
	 * @effect All content items for this directory at an index exceeding the index of the newly added 
	 *         file system object, are registered as content item at one index higher.
	 *         | for each I in new.getIndexOf(obj)..getNbItems():
	 *         |   (new.getItemAt(I+1) == getItemAt(I))
	 * @effect The modification time of this directory is updated.
	 *         | setModificationTime()
	 * @throws IllegalArgumentException
	 *         This directory already has this file system object as one of its content items or 
	 *         it cannot have the given file system object as content item.
	 *         | (hasAsItem(obj) || !canHaveAsItem(obj))
	 */
	@Raw
	public void addAsItem(@Raw FileSystemObject obj) throws IllegalArgumentException {
		if (hasAsItem(obj) || !canHaveAsItem(obj) )
			throw new IllegalArgumentException("Cannot add the given file system object to this directory");	
				
		int size = getNbItems();
		int pos = 1;
		while (pos <= size) {
			if (obj.isLexicographicallyAfter(getItemAt(pos)))
				pos++;
			else break;
		}
		addItemAt(obj,pos);
		setModificationTime();
	}
	
	/**
	 * Add the given file system object as a content item for this directory at the given index.
	 * 
	 * @param  obj
	 *         The file system object to be added as content item.
	 * @param  index
	 *         The index of the object to be added.
	 * @post   This directory has the given file system object as one of its content items at the given index.
	 *         | new.getItemAt(index) == obj
	 * @post   The number of content items of this directory is incremented by 1.
	 *         | new.getNbItems() == getNbItems()+1
	 * @post   All content items for this directory at an index exceeding the given index, are registered
	 *         as content item at one index higher.
	 *         | for each I in index..getNbItems():
	 *         |   (new.getItemAt(I+1) == getItemAt(I))
	 * @throws IllegalArgumentException
	 *         This directory cannot have the given file system object as a content item
	 *         at the given index.
	 *         | !canHaveAsItemAt(obj,index)
	 * @throws ObjectNotWritableException(this)
	 * 		   This directory is not writable
	 *         | ! isWritable()
	 */
	private void addItemAt(@Raw FileSystemObject obj, int index) throws IllegalArgumentException, ObjectNotWritableException {
		if (!canHaveAsItemAt(obj,index))
			throw new IllegalArgumentException("Invalid file system object for this index.");
		if(! isWritable()) {
			throw new ObjectNotWritableException(this);
		}
		contents.add(index-1, obj);
	}
	
	/**
	 * Remove an file system object as a content item associated with this directory.
	 * 
	 * @param  obj
	 *         The file system object to be removed.
	 * @effect This directory no longer has the file system object as one of its content items.
	 *         | ! new.hasAsItem(obj)
	 * @effect The number of content items associated with this directory is decremented by 1.
	 *         | new.getNbItems() == this.getNbItems()-1
	 * @effect All file system objects associated with this directory at an index exceeding the index
	 *         of the file system object that was removed are registered as file system object at one index lower.
	 *         | for each I in this.getIndexOf(obj)+1..getNbItems()
	 *         |   (new.getItemAt(I-1) == this.getItemAt(I))
	 * @throws IndexOutOfBoundsException
	 *         The given index is not positive or it exceeds the number of content items
	 *         associated with this directory.
	 *         | (index<1 || index> getNbItems())
	 */
	public void removeAsItem(FileSystemObject obj) throws IllegalArgumentException {
		if (!hasAsItem(obj))
			throw new IllegalArgumentException("This file system object is not an item of this directory.");
		removeItemAt(getIndexOf(obj));
		setModificationTime();
	}
	
	/**
	 * Remove the file system object for this directory at the given index.
	 * 
	 * @param  index
	 *         The index of the file system object to be removed.
	 * @post   This directory no longer has the file system object at the given index 
	 *         as one of its content items.
	 *         | ! new.hasAsItem(getItemAt(index))
	 * @post   The number of content items associated with this directory is decremented by 1.
	 *         | new.getNbItems() == this.getNbItems()-1
	 * @post   All file system objects associated with this directory at an index exceeding the given index,
	 *         are registered as file system object at one index lower.
	 *         | for each I in index+1..getNbItems()
	 *         |   (new.getItemAt(I-1) == this.getItemAt(I))
	 * @throws IndexOutOfBoundsException
	 *         The given index is not positive or it exceeds the number of content items
	 *         associated with this directory.
	 *         | (index<1 || index> getNbItems())
	 * @throws ObjectNotWritableException
	 *         This directory is not writable
	 *         | ! isWritable()
	 */
	private void removeItemAt(int index) throws IndexOutOfBoundsException, ObjectNotWritableException {
		if (index<1 || index>getNbItems())
			throw new IndexOutOfBoundsException();
		if(! isWritable()) {
			throw new ObjectNotWritableException(this);
		}
		contents.remove(index-1);
	}
	
	
	/**
	 * Orders a directory such that the names of its file system objects are alphabetically ordered 
	 * after one objects name has been changed. 
	 * 
	 * @param  changedObj
	 *         The object of which the name has changed.
	 * @post   The content list of this directory is properly ordered.
	 *         | hasProperItems()
	 * @effect The changed object is first removed from the content list,
	 *         | removeItemAt(getIndexOf(changedObj))
	 *         then a new proper index is searched at which the object is inserted 
	 *         in the content list.
	 *         | addItemAt(changedObj,newIndex)
	 * @note   Errors are caught by the underlying used methods, removeItemAt and addItemAt. 
	 */
	protected void orderDirectory(FileSystemObject changedObj) {
		int index = getIndexOf(changedObj);
		removeItemAt(index);
		
		int size = this.getNbItems();
		int pos = 1;
		while (pos <= size) {
			if (changedObj.isLexicographicallyAfter(getItemAt(pos)))
				pos++;
			else break;
		}
		
		addItemAt(changedObj,pos);
	}
	
	
	/**
	 * Checks whether this directory is a direct or indirect subdirectory of the given directory.
	 * 
	 * @param dir
	 * 		  The given directory
	 * @return True if and only if this directory is a direct or indirect subdirectory of a directory equal to the given directory
	 * 		   | result == false 
	 * 		   | do
	 *         |  	if(parent==dir) then
	 *         | 	  result == true
	 *         |   	else parent=parent.getParentDirectory()
	 *         | while(parent != null)
	 */
	public boolean isDirectOrIndirectSubdirectoryOf(Directory dir) {
		Directory parent = this.getParentDirectory();
		while (parent != null) {
			if (parent == dir) {
				return true;
			} else {
				parent = parent.getParentDirectory();
			}
		}
		return false;
	}
		
	
	/**********************************************************
     * Termination
     **********************************************************/
	
	/**
	 * Terminate this directory.
	 * 
	 * @effect If this directory is empty, it will be terminated.
	 *         | if getNbItems() == 0
	 *         |   then super.terminate()
	 * @throws IllegalStateException
	 *         This directory is not empty
	 *         | getNbItems() != 0
	 */
	@Override
	public void terminate() throws IllegalStateException {
		if(getNbItems() != 0)
			throw new IllegalStateException("This directory is not empty.");
		
		super.terminate();
	}
	
	
	
	
	public void printList() {
		for (int i=1; i<=getNbItems(); i++) {
			System.out.print(i);
			System.out.print(" ");
			System.out.println(getItemAt(i).getName());
		}
	}
}
