package file;

import be.kuleuven.cs.som.annotate.*;

/**
 * ToDo class commentaar: @invar, @pre enz.
 * 
 * @version 1.0
 * @author Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */


public class File {
	
	
	/**
	 * Initialize a File with a name, size and whether or not it is writable.
	 * 
	 * @param name
	 * 		  The name for this new file.
	 * @param size
	 * 		  The size of this file.
	 * @param writable
	 * 		  The initial value for the writability of this new file.
	 * @pre   The given size must be valid.
	 *      | isValidSize(size) 
	 */
	public File(String name, int size, boolean writable) {
		setName(name);
		setSize(size);
		this.writable = writable;
	}
	
	/*
	 * Returns the name of this file.
	 */
	@Basic
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name of the file as the given string name.
	 * 
	 * @param name
	 * 	      String that will be used as the filename
	 * @post  If the name is not empty and if the name consists only out of
	 *  	  lower and upper case letters, numbers and the following symbols:
	 *        points (.), hyphens (-) and underscores (_), the new name of the
	 *        of this file is equal to the given name.
	 *      | new.name = name  
	 */
	public void setName(String name) {
		if( (!name.isEmpty()) 
		     && (name.matches("^[a-zA-Z0-9.-_]*$")) ) {
			this.name = name;
		}
	}
	
	/*
	 * Variable registering the name of this file.	
	 */
	private String name;
	
	
	
	// ALLES IVM MET SIZE --> NOMINAAL PROGRAMMEREN: PRE, CHECKER
	
	/**
	 * 
	 * @param  size
	 * 		   The size to check.
	 * @return True if and only if the size is not below 0 and not 
	 *         above the size limit.
	 *       | result ==
	 *       |    ( (size >= 0)
	 *       |   && (size <= getSizeLimit()) )
	 */
	public static boolean isValidSize(int size) {
		return ((size>=0) && (size <= getSizeLimit()));
	}
	
	
	/**
	 * Returns the size of this file
	 */
	@Basic
	public int getSize() {
		return this.size;
	}
	
	
	/**
	 * Increase the size of this file with the given size
	 *  Does nothing if the resulting file size is larger than the size limit
	 * 
	 * @param  amount
	 * 		   The amount with which the file size will be increased.
	 * @pre    This amount is accepted for enlargement.
	 *       | canAcceptForEnlarge(amount)
	 * @post   The new size of this file is equal to the old size incremented
	 *         with the give amount of bytes.
	 *       | new.getSize() == this.getSize() + amount
	 */
	public void enlarge(int amount) {
		setSize(getSize() + amount);
	}
	
	/**
	 * Decrease the size of this file with the given size
	 * Does nothing if the resulting file size is negative
	 * 
	 * @param amount
	 * 	      The amount with which the file size will be decreased.
	 * @pre   This amount is accepted to shorten the file.
	 *      | canAcceptForShorten(amount)
	 * @post  The new size of this file is equal to the old size decremented
	 *        with the given amount of bytes.
	 *      | new.getSize() == this.getSize() - amount
	 */
	public void shorten(int amount) {
		setSize(getSize() - amount);
	}
	
	/**
	 * Set the size of the file to the given size.
	 * 
	 * @param size
	 *        The size for this file.
	 * @post  If the size is valid, the size of the file will be set
	 * 		  to the given size.
	 */
	public void setSize(int size) {
		if (isValidSize(size)) {
			this.size = size;
		}
	}
	
	/*
	 * Variable registering the size of this file.
	 */
	private int size = 0;
	
	
	
	
	/*
	 * Returns the size limit that applies to all files
	 *   The size limit expresses the highest possible value for the 
	 *   size of a file.
	 */
	@Basic @Immutable
	public static int getSizeLimit() {
		return File.sizeLimit;
	}
	
	/**
	 * Set the maximum allowed file size
	 * Does nothing if the given limit is negative
	 * @param limit
	 * 		  The new maximum file size
	 */
	public static void setSizeLimit(int limit) {
		if (limit >= 0) {
			File.sizeLimit = limit;
		}
	}
	
	/*
	 * Variable registering the size limit of this file.
	 */
	private static int sizeLimit = Integer.MAX_VALUE;
	
	

	/*
	 * Check whether this file is writable
	 *   Some methods have no effect when invoked against non writable files.
	 */
	public boolean isWritable() {
		return this.writable;
	}
	
	/**
	 * Set the writable state of this file according to the given flag.
	 * 
	 * @param flag
	 *        The new writable state of this account
	 * @post  The new writable state of this account is equal to the given flag.
	 */
	public void setWritable(boolean flag) {
		this.writable = flag;
	}
	
	/*
	 * Variable registering the writable state of this file.
	 */
	private boolean writable = true;

}
