package filesystem;

import be.kuleuven.cs.som.annotate.*;

import java.util.Date;
import java.util.ArrayList;

public abstract class FileSystemObject {

	
	protected FileSystemObject(Directory dir, String name, boolean writable) {
		setName(name);
		setWritable(writable);
	}
	
	
    /**********************************************************
     * name - total programming
     **********************************************************/

    /**
     * Variable referencing the name of this filesystem object.
     * @note		See Coding Rule 32, for information on the initialization of fields.
     */
    private String name = null;

    /**
     * Return the name of this filesystem object.
     * @note		See Coding Rule 19 for the Basic annotation.
     */
    @Raw @Basic 
    public String getName() {
        return name;
    }
    
    /**
     * Check whether the given name is a legal name for a filesystem object.
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
     * 			The new name for this filesystem object.
     * @post    If the given name is valid, the name of
     *          this filesystem object is set to the given name,
     *          otherwise the name of the filesystem object is set to a valid name (the default).
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
     * Return the name for a new filesystem object which is to be used when the
     * given name is not valid.
     *
     * @return   A valid filesystem object name.
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
     * 			The new name for this filesystem object.
     * @effect  The name of this filesystem object is set to the given name, 
     * 			if this is a valid name and the filesystem object is writable, 
     * 			otherwise there is no change.
     * 			| if (isValidName(name) && isWritable())
     *          | then setName(name)
     * @effect  If the name is valid and the filesystem object is writable, the modification time 
     * 			of this filesystem object is updated.
     *          | if (isValidName(name) && isWritable())
     *          | then setModificationTime()
     * @throws  ObjectNotWritableException(this)
     *          This file is not writable
     *          | ! isWritable() 
     */
    public void changeName(String name) throws ObjectNotWritableException {
        if (isWritable()) {
            if (isValidName(name)){
            	setName(name);
                setModificationTime();
            }
        } else {
            throw new ObjectNotWritableException(this);
        }
    }
    
    
    
    /**********************************************************
     * creationTime
     **********************************************************/

    /**
     * Variable referencing the time of creation.
     */
    private final Date creationTime = new Date();
   
    /**
     * Return the time at which this filesystem object was created.
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
     * Return the time at which this filesystem object was last modified, that is
     * at which the name or size was last changed. If this filesystem object has
     * not yet been modified after construction, null is returned.
     */
    @Raw @Basic
    public Date getModificationTime() {
        return modificationTime;
    }

    /**
     * Check whether this filesystem object can have the given date as modification time.
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
    private void setModificationTime() {
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
     * Nieuwe shit
     **********************************************************/
   
    public void makeRoot() {
    	
    }
    
    public void move(Directory destination) {
    	
    }
    
    public FileSystemObject getRoot() {
    	return null;
    }
    
}
