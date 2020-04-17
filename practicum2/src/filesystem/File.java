package filesystem;

import be.kuleuven.cs.som.annotate.*;


/**
 * A class of files.
 * 
 * @invar	Each file must have a valid size.
 * 		    | isValidSize(getSize())
 * @invar   The file type of each file must be a valid file type for any file.
 *          | isValidType(getType())
 * @author  Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 * @version 1.0
 *
 */

public class File extends FileSystemObject {

    /**********************************************************
     * Constructors
     **********************************************************/
	
	/**
	 * Initialize a new file with given parent directory, name, size, writability and type.
	 * 
	 * @param  dir
	 *         The parent directory of the new file.
	 * @param  name
	 *         The name of the new file.
	 * @param  size
	 *         The size of the new file.
	 * @param  writable
	 *         The writability of the new file.
	 * @param  type
	 *         The file type of the new file.
	 * @pre    The type is effective
	 *         | type != null
	 * @effect The new file is a file system object with given parent directory, name and writability.
	 *         | super(dir,name,writable)
	 * @effect The new file has the given size as its size.
	 *         | setSize(size)
	 * @post   The new file has the given type as its file type.
	 *         | new.getType() == type
	 * 
	 */
	public File(Directory dir, String name, int size, boolean writable, FileType type) {
		super(dir, name, writable);
		setSize(size);
		this.type = type;
	}
	
	/**
	 * Initialize a new file with given parent directory, name and type that is empty and writable.
	 * 
	 * @param  dir
	 *         The parent directory of the new file.
	 * @param  name
	 *         The name of the new file.
	 * @param  type
	 *         The file type of the new file.
	 * @effect The new file is initialized with the given parent directory, name and type,
	 *         a zero size and true writability.
	 *         | this(dir,name,0,true,type)
	 */
	public File(Directory dir, String name, FileType type) {
		this(dir, name, 0, true, type);
	}
	
	
	/**
	 * Initialize a new root file with given name, size, writability and type.
	 * 
	 * @param  name
	 *         The name of the new file.
	 * @param  size
	 *         The size of the new file.
	 * @param  writable
	 *         The writability of the new file.
	 * @param  type
	 *         The file type of the new file.
	 * @effect The new root file is initialized with the given name, size, writability, type 
	 *         and no parent directory.
	 *         | this(null,name,size,writable,type)
	 */
	public File(String name, int size, boolean writable, FileType type) {
		this(null, name, size, writable, type);
	}
	
	/**
	 * Initialize a new root file with given name and type that is empty and writable.
	 * 
	 * @param  name
	 *         The name of the new file.
	 * @param  type
	 *         The file type of the new file.
	 * @effect The new root file is initialized with the given name and type,
	 *         a zero size and true writability.
	 *         | this(null,name,0,true,type)
	 */
	public File(String name, FileType type) {
		this(name, 0, true, type);
	}


	
	
    /**********************************************************
     * File type
     **********************************************************/

	/**
	 * Return the type of this file.
	 */
	@Basic @Immutable
	public FileType getType() {
		return type;
	}
	
	/**
	 * Check whether this type is a valid file type for any file.
	 * 
	 * @param  type
	 *         The file type to check.
	 * @return True if and only if the given type is effective
	 *       | result ==
	 *       |   (type != null)
	 */
	@Raw
	public boolean isValidType(FileType type) {
		return type != null;
	}
	
	/**
	 * Variable registering the type of this file.
	 */
	private final FileType type;
	
	
	
    
    /**********************************************************
     * size - nominal programming
     **********************************************************/
    
    /**
     * Variable registering the size of this file (in bytes).
     */
    private int size = 0;
    
    /**
     * Variable registering the maximum size of any file (in bytes).
     */
    private static final int maximumSize = Integer.MAX_VALUE;


    /**
     * Return the size of this file (in bytes).
     */
    @Raw @Basic 
    public int getSize() {
        return size;
    }
    
    /**
     * Set the size of this file to the given size.
     *
     * @param  size
     *         The new size for this file.
     * @pre    The given size must be legal.
     *         | isValidSize(size)
     * @post   The given size is registered as the size of this file.
     *         | new.getSize() == size
     */
    @Raw @Model 
    private void setSize(int size) {
        this.size = size;
    }
   
    /**
     * Return the maximum file size.
     */
    @Basic @Immutable
    public static int getMaximumSize() {
        return maximumSize;
    }

    /**
     * Check whether the given size is a valid size for a file.
     *
     * @param  size
     *         The size to check.
     * @return True if and only if the given size is positive and does not
     *         exceed the maximum size.
     *         | result == ((size >= 0) && (size <= getMaximumSize()))
     */
    public static boolean isValidSize(int size) {
        return ((size >= 0) && (size <= getMaximumSize()));
    }

    /**
     * Increases the size of this file with the given delta.
     *
     * @param   delta
     *          The amount of bytes by which the size of this file
     *          must be increased.
     * @pre     The given delta must be strictly positive.
     *          | delta > 0
     * @effect  The size of this file is increased with the given delta.
     *          | changeSize(delta)
     */
    public void enlarge(int delta) throws ObjectNotWritableException {
        changeSize(delta);
    }

    /**
     * Decreases the size of this file with the given delta.
     *
     * @param   delta
     *          The amount of bytes by which the size of this file
     *          must be decreased.
     * @pre     The given delta must be strictly positive.
     *          | delta > 0
     * @effect  The size of this file is decreased with the given delta.
     *          | changeSize(-delta)
     */
    public void shorten(int delta) throws ObjectNotWritableException {
        changeSize(-delta);
    }

    /**
     * Change the size of this file with the given delta.
     *
     * @param  delta
     *         The amount of bytes by which the size of this file
     *         must be increased or decreased.
     * @pre    The given delta must not be 0
     *         | delta != 0
     * @effect The size of this file is adapted with the given delta.
     *         | setSize(getSize()+delta)
     * @effect The modification time is updated.
     *         | setModificationTime()
     * @throws IllegalStateException
     *         This file is terminated.
     *         | isTerminated()
     * @throws ObjectNotWritableException(this)
     *         This file is not writable.
     *         | ! isWritable()
     */
    @Model 
    private void changeSize(int delta) throws IllegalStateException, ObjectNotWritableException{
        if (isTerminated())
        	throw new IllegalStateException("This file is terminated.");    	
    	if (isWritable()) {
            setSize(getSize()+delta);
            setModificationTime();            
        }else{
        	throw new ObjectNotWritableException(this);
        }
    }

}
