package filesystem;

import be.kuleuven.cs.som.annotate.*;
import java.util.Date;

/**
 * An abstract class of file system objects.
 * 
 * @invar    Each disk item must have a properly spelled name.
 * 		   | isValidName(getName())
 * @invar    Each disk item must have a valid creation time.
 *         | isValidCreationTime(getCreationTime())
 * @invar    Each disk item must have a valid modification time.
 *         | canHaveAsModificationTime(getModificationTime())
 * 
 * @author  Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 * @version 1.0
 *
 */

public abstract class FileSystemObject {
	
    /**********************************************************
     * Constructors
     **********************************************************/
		
	
	/**
	 * Initialize a new file system object with given directory, name and writability.
	 * 
	 * @param  dir
	 *         The parent directory of the new file system object.
	 * @param  name
	 *         The name of the new file system object.
	 * @param  writable
	 * 		   The writability of the new file system object.
	 * @effect The name of the file system object is set to the given name.
	 *         If the given name is not valid, a default name is set.
	 *         | setName(name)
	 * @effect The writability of the new file system object is set to the given flag.
	 *         | setWritable(writable)
     * @post   The new creation time of this file is initialized to some time during
     *         constructor execution.
     *         | (new.getCreationTime().getTime() >= System.currentTimeMillis()) &&
     *         | (new.getCreationTime().getTime() <= (new System).currentTimeMillis())
     * @post   The new file has no time of last modification.
     *         | new.getModificationTime() == null
	 */
	protected FileSystemObject(Directory dir, String name, boolean writable) {
		setName(name);
		setWritable(writable);
		setParentDirectory(dir);
	}
	
	
	/**
	 * Initialize a new root file system object with given name and writability.
	 * 
	 * @param  name
	 *         The name of the new file system object.
	 * @param  writable
	 * 		   The writability of the new file system object.
	 * @effect The new file system object is initialized with the given name and writability
	 *         and no parent directory.
	 *         | this(null,name,writable)
	 */
	protected FileSystemObject(String name, boolean writable) {
		this(null,name,writable);
	}
	
	
	
	
    /**********************************************************
     * name - total programming
     **********************************************************/

    /**
     * Variable referencing the name of this file system object.
     */
    private String name = null;

    /**
     * Return the name of this file system object.
     */
    @Raw @Basic 
    public String getName() {
        return name;
    }
    
    /**
     * Check whether the given name is a legal name for a file system object.
     * 
     * @param  	name
     *			The name to be checked
     * @return	True if the given string is effective, not
     * 			empty and consisting only of letters, digits, dots,
     * 			hyphens and underscores; false otherwise.
     * 			| result ==
     * 			|	(name != null) && name.matches("[a-zA-Z_0-9.-]+")
     */
    public static boolean isValidName(String name) {
        return (name != null && name.matches("[a-zA-Z_0-9.-]+"));
    }
    
    /**
     * Set the name of this filesystem object to the given name.
     *
     * @param   name
     * 			The new name for this file system object.
     * @post    If the given name is valid, the name of
     *          this file system object is set to the given name,
     *          otherwise the name of the file system object is set to a valid name (the default).
     *          | if (isValidName(name))
     *          |      then new.getName().equals(name)
     *          |      else new.getName().equals(getDefaultName())
     */
    @Raw @Model 
    private void setName(String name) {
        if (isValidName(name)) {
        		this.name = name;
        } else {
        		this.name = getDefaultName();
        }
    }
    
    /**
     * Return the name for a new file system object which is to be used when the
     * given name is not valid.
     *
     * @return   A valid file system object name.
     *         | isValidName(result)
     */
    @Model
    private static String getDefaultName() {
        return "new_object";
    }

    /**
     * Change the name of this filesystem object to the given name.
     *
     * @param	name
     * 			The new name for this file system object.
     * @effect  The name of this file system object is set to the given name, 
     * 			if this is a valid name, the file system object is writable and the file system object
     *          is a root or its parent directory is writable, 
     * 			otherwise there is no change.
     * 			| if ( isValidName(name) && isWritable()
     *          |   && (isRoot() || getParentDirectory().isWritable()) )
     *          | then setName(name)
     * @effect  If the name is valid, the file system object is writable, its parent directory has no other
     *          file system object with the same name (ignoring case) and the file system object is a root or 
     *          its parent directory is writable, the modification time of this file system object is updated.
     *          | if ( isValidName(name) && isWritable() && !getParentDirectory().exists(name)
     *          |   && (isRoot() || getParentDirectory().isWritable()) )
     *          | then setModificationTime()
     * @throws  IllegalStateException
     *          This file systemobject is terminated.
     *          | isTerminated()
     * @throws  ObjectNotWritableException(this)
     *          This file system object is not writable.
     *          | ! isWritable() 
     */
    public void changeName(String name) throws IllegalStateException, ObjectNotWritableException {
    	if (isTerminated()) throw new IllegalStateException("This file system object is terminated.");
        if (isWritable()) {
            if (isValidName(name) && !getParentDirectory().exists(name) && name!=getName()
            	&& (isRoot() || getParentDirectory().isWritable()) ){
            	
            	setName(name);
            	getParentDirectory().orderDirectory(this);
                setModificationTime();

            }
        } else {
            throw new ObjectNotWritableException(this);
        }
    }
    
    /**
     * Check whether this file system object is ordered before this file system object,
     * lexographically by name, ignoring case.
     * 
     * @param  obj
     *         The file system object to compare with.
     * @return True if and only if the other object is effective, has a valid name and the
     *         name of this item is lexicographically ordered before the name of the other object.
     *         False otherwise.
     *         | result == (obj != null)
     *         |          && isValidName(obj.getName())
     *         |          && (getName().compareToIgnoreCase(obj.getName()) < 0)         
     */
    public boolean isLexicographicallyBefore(FileSystemObject obj) {
    	return obj!=null && isValidName(obj.getName())
    		   && getName().compareToIgnoreCase(obj.getName()) < 0;
    }
    
    /**
     * Check whether this file system object is ordered after this file system object,
     * lexographically by name, ignoring case.
     * 
     * @param  obj
     *         The file system object to compare with.
     * @return True if and only if the other object is effective, has a valid name and the
     *         name of this item is lexicographically ordered after the name of the other object.
     *         False otherwise.
     *         | result == (obj != null)
     *         |          && isValidName(obj.getName())
     *         |          && (getName().compareToIgnoreCase(obj.getName()) > 0)         
     */
    public boolean isLexicographicallyAfter(FileSystemObject obj) {
    	return obj!=null && isValidName(obj.getName())
     		   && getName().compareToIgnoreCase(obj.getName()) > 0;
    }
    
    
    /**********************************************************
     * creationTime
     **********************************************************/

    /**
     * Variable referencing the time of creation.
     */
    private final Date creationTime = new Date();
   
    /**
     * Return the time at which this file system object was created.
     */
    @Raw @Basic @Immutable
    public Date getCreationTime() {
        return creationTime;
    }

    /**
     * Check whether the given date is a valid creation time.
     *
     * @param  	date
     *         	The date to check.
     * @return 	True if and only if the given date is effective and not
     * 			in the future.
     *         	| result == 
     *         	| 	(date != null) &&
     *         	| 	(date.getTime() <= System.currentTimeMillis())
     */
    public static boolean isValidCreationTime(Date date) {
    	return 	(date!=null) &&
    			(date.getTime()<=System.currentTimeMillis());
    }

    

    /**********************************************************
     * modificationTime
     **********************************************************/

    /**
     * Variable referencing the time of the last modification,
     * possibly null.
     */
    private Date modificationTime = null;
   
    /**
     * Return the time at which this file system object was last modified, that is
     * at which the name or size was last changed. If this file system object has
     * not yet been modified after construction, null is returned.
     */
    @Raw @Basic
    public Date getModificationTime() {
        return modificationTime;
    }

    /**
     * Check whether this file system object can have the given date as modification time.
     *
     * @param	date
     * 			The date to check.
     * @return 	True if and only if the given date is either not effective
     * 			or if the given date lies between the creation time and the
     * 			current time.
     *         | result == (date == null) ||
     *         | ( (date.getTime() >= getCreationTime().getTime()) &&
     *         |   (date.getTime() <= System.currentTimeMillis())     )
     */
    public boolean canHaveAsModificationTime(Date date) {
        return (date == null) ||
               ( (date.getTime() >= getCreationTime().getTime()) &&
                 (date.getTime() <= System.currentTimeMillis()) );
    }

    /**
     * Set the modification time of this file to the current time.
     *
     * @post   The new modification time is effective.
     *         | new.getModificationTime() != null
     * @post   The new modification time lies between the system
     *         time at the beginning of this method execution and
     *         the system time at the end of method execution.
     *         | (new.getModificationTime().getTime() >=
     *         |                    System.currentTimeMillis()) &&
     *         | (new.getModificationTime().getTime() <=
     *         |                    (new System).currentTimeMillis())
     */
    @Model 
    protected void setModificationTime() {
        modificationTime = new Date();
    }

    /**
     * Return whether this filesystem object and the given other filesystem object have an
     * overlapping use period.
     *
     * @param 	other
     *        	The other filesystem object to compare with.
     * @return 	False if the other file is not effective
     * 			False if the prime object does not have a modification time
     * 			False if the other file is effective, but does not have a modification time
     * 			otherwise, true if and only if the open time intervals of this file and
     * 			the other file overlap
     *        	| if (other == null) then result == false else
     *        	| if ((getModificationTime() == null)||
     *        	|       other.getModificationTime() == null)
     *        	|    then result == false
     *        	|    else 
     *        	| result ==
     *        	| ! (getCreationTime().before(other.getCreationTime()) && 
     *        	|	 getModificationTime().before(other.getCreationTime()) ) &&
     *        	| ! (other.getCreationTime().before(getCreationTime()) && 
     *        	|	 other.getModificationTime().before(getCreationTime()) )
     */
    public boolean hasOverlappingUsePeriod(FileSystemObject other) {
        if (other == null) return false;
        if(getModificationTime() == null || other.getModificationTime() == null) return false;
        return ! (getCreationTime().before(other.getCreationTime()) && 
        	      getModificationTime().before(other.getCreationTime()) ) &&
        	   ! (other.getCreationTime().before(getCreationTime()) && 
        	      other.getModificationTime().before(getCreationTime()) );
    }

    
    
    /**********************************************************
     * writable
     **********************************************************/
   
    /**
     * Variable registering whether or not this filesystem object is writable.
     */
    private boolean isWritable = true;
    
    /**
     * Check whether this filesystem object is writable.
     */
    @Raw @Basic
    public boolean isWritable() {
        return isWritable;
    }

    /**
     * Set the writability of this filesystem object to the given writability.
     *
     * @param isWritable
     *        The new writability
     * @post  The given writability is registered as the new writability
     *        for this file.
     *        | new.isWritable() == isWritable
     */
    @Raw 
    public void setWritable(boolean isWritable) {
        this.isWritable = isWritable;
    }
    
    
    /**********************************************************
     * Parent directory
     **********************************************************/
   
    /**
     * Variable referencing the directory of this filesystem object
     */
    private Directory dir = null;
    
    /**
     * Return the directory this filesystem object belongs to
     */
    @Basic @Raw
    public Directory getParentDirectory() {
    	return this.dir;
    }
    
    /**
     * Set the directory of this filesystem object to the given directory.
     * 
     * @param  dir
     * 		   The new directory
     * @effect This file system object gets added to the given directory
     * 		   | dir.addAsItem(this)
     */
    @Raw
    private void setParentDirectory(Directory dir) {
    	dir.addAsItem(this);
    	if (dir != null)
    		this.dir = dir;
    }
    
    /**
     * Makes this file system object a root object.
     * 
     * @effect If this file system object is not a root, it is removed from it's old parent directory.
     *         | if (!isRoot())
     *         |   then getParentDirectory().removeAsItem(this)
     * @effect If this file system object is not a root, its parent directory is set to null.
     *         | if (!isRoot())
     *         |   then setParentDirectory(null)
     * @effect If this file system object is not a root, its modification time is updated.
     *         | if (!isRoot())
	 *         |   then setModificationTime() 
	 * @note   Possible errors are caught by underlying methods.
     */
    public void makeRoot() {
    	if (!isRoot()) {
	    	getParentDirectory().removeAsItem(this);
	    	setParentDirectory(null);
	    	
	    	setModificationTime();
    	}
    }
    
    /**
     * Move a file system object to a given destination directory.
     * 
     * @param  destination
     *         The given directory
     * @effect If this file system object is not a root, it is removed from the old parent directory.
     *         | if (!isRoot())
     *         |   then getParentDirectory().removeAsItem(this)
     * @effect The parent directory of this file system object is set to the given destination directory
     *         | setParentDirectory(destination)
     * @effect The modification time of this file system object is updated.
	 *         | setModificationTime()         
     * @throws IllegalArgumentException
     * 		   The given destination cannot contain this file system object, the destination is null
     *         or the given destination is equal to the current parent directory.
     * 		   | ( !destination.canHaveAsItem(this) 
     *         |   || destination==null
     *         |   || destination==getParentDirectory() )
     * @note   Other errors, such as those occurring because of termination/writability,
     *         are caught by the used underlying methods.        
     */
    public void move(Directory destination) throws IllegalArgumentException {
    	if (destination==null) 
    		throw new IllegalArgumentException("The destination directory cannot be null.");
    	if (destination==getParentDirectory()) 
    		throw new IllegalArgumentException("The destination directory cannot be the same as the current parent directory.");
    	if (!destination.canHaveAsItem(this))
    		throw new IllegalArgumentException("The destination directory cannot contain this file system object");
    	
    	if (!isRoot()) 
    		getParentDirectory().removeAsItem(this);
    	setParentDirectory(destination);
    	
    	setModificationTime();
    }
    
    /**
     * Return the root of this file system object
     * 
     * @return The file system object that is the root of this object
     */
    public FileSystemObject getRoot() {
    	FileSystemObject root = this.getParentDirectory();
    	if(root == null) {
    		root = this;
    	} else {
    		while(root.getParentDirectory() != null) {
    			root = root.getParentDirectory();
    		}
    	}
    	return root;
    }
    
    /**
     * Check whether this file system object is a root object.
     * 
     * @return A boolean indicating whether this file system object is a root object or not.
     */
    public boolean isRoot() {
    	return getRoot() == null;
    }
    
    
    /**********************************************************
    * Termination
    **********************************************************/
    
    
    /**
     * Variable registering whether this file system object is terminated
     */
    private boolean isTerminated = false;
    
    /**
     * Check whether this file system object is terminated
     */
    @Basic @Raw
    public boolean isTerminated() {
    	return(isTerminated);
    }
    
    /**
     * Terminate this file system object.
     * 
     * @post   This file system object will be terminated.
     *         | this.isTerminated = true
     * @effect If this file system object is not yet terminated and it is not a root,
     *         it becomes a root object.
     *         | if !isTerminated() && !isRoot()
     * 		   |   then makeRoot()
     * @throws IllegalStateException
     *         The file system object is not yet terminated and it is not writable or is no root 
     *         or its parent directory is not writable.
     *         | !isTerminated() &&
     *         | ( !isWritable() ||
     *         |   ! (isRoot() || getParentDirectory().isWritable()) )
     */
    public void terminate() throws IllegalStateException {
    	if (!isTerminated()) {
    		
    		if (!isWritable() || !(isRoot() || getParentDirectory().isWritable()) )
    			throw new IllegalStateException("This file system object cannot be terminated.");
    		
    		if (!isRoot()) {
    			makeRoot();
    			this.isTerminated = true;
    		}   		
    	}
    }
    
    
    
}
