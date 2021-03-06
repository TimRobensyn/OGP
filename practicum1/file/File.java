package file;

import be.kuleuven.cs.som.annotate.*;
import java.util.Date;


/**
 * A class of files, involving a file name, size, a size limit and its writability.
 * 
 * @invar   The name of a file must be valid
 *        | isValidName(getName())
 * @invar   The size of a file must be valid
 *        | isValidSize(getSize())
 * @invar   The size limit that applies to all bank accounts must be a valid size limit
 *        | isValidSizeLimit(getSizeLimit())
 * @invar   The creation time of a file must be valid.
 *        | isValidCreationTime(getCreationTime())
 * @invar   The modification time of a file must be valid.
 *        | canHaveAsModificationTime(getModificationTime())
 * 
 * @version 1.0
 * @author  Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
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
	 * @post  If the given name is not empty and consists only out of
	 *  	  lower and upper case letters, numbers and the following symbols:
	 *        points (.), hyphens (-) and underscores (_), the initial name of this file
	 *        is the given name. Otherwise its name is "New_file".
	 * @post  If the given size is greater than or equal to zero and lower than the size
	 *        limit of all files, the initial size of this file is the given number.
	 *        Otherwise its initial size is 0.
	 * @post  The initial writability state of this new file is equal to the given flag.      
	 */
	@Raw
	public File(String name, long size, boolean writable) {
		setName(name);
		setSize(size);
		setWritable(writable);
		this.modificationTime = null;

	}
	
	/**
	 * Initialize this new file with a given name, size 0 and possibility to write.
	 * 
	 * @param  name
	 *         The name for this new file.
	 * @effect This new file is initialized with the given name as its name, 0 as its 
	 *         size and true as is writability state. 
	 */
	@Raw
	public File(String name) {
		this(name,0,true);
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
	 * @param  name
	 * 	       String that will be used as the filename
	 * @post   If the name is valid, the new name of this file is equal to the given name.
	 *       | new.name = name
	 * @throws FileNotWritableException
	 * 		   This file is not writable and cannot be renamed by the user.
	 *      | (! this.isWritable())
	 */
	public void setName(String name) 
	throws FileNotWritableException {
		if(! this.isWritable()) {
			throw new FileNotWritableException(this);
		}
		else {
		if (isValidName(name)) {
			this.name = name;
			setModificationTime();
		}
		}
	}
	
	/**
	 * Check whether the given name is valid for a file.
	 * 
	 * @param  name
	 *         The name to be checked
	 * @return True if and only if the name is not empty and consists only out of
	 *  	   lower and upper case letters, numbers and the following symbols:
	 *         points (.), hyphens (-) and underscores (_)
	 *       | result ==
	 *       |    ( !name.isEmpty()
	 *       |   && name.matches("^[a-zA-Z0-9.-_]*$") )
	 */
	public static boolean isValidName(String name) {
		return ( (!name.isEmpty()) 
			     && (name.matches("^[a-zA-Z0-9.-_]*$")) );
	}
	
	/*
	 * Variable registering the name of this file.	
	 */
	private String name = "New_file";
	
	
	
	
	/**
	 * Check whether the given size is valid for a file. 
	 * 
	 * @param  size
	 * 		   The size to check.
	 * @return True if and only if the size is not below 0 and not 
	 *         above the size limit.
	 *       | result ==
	 *       |    ( (size >= 0)
	 *       |   && (size <= getSizeLimit()) )
	 */
	public static boolean isValidSize(long size) {
		return ((size>=0L) && (size <= getSizeLimit()));
	}
	
	
	/**
	 * Returns the size of this file
	 */
	@Basic
	public long getSize() {
		return this.size;
	}
	
	
	/**
	 * Return a boolean reflecting whether this file can accept the given amount for enlarge. 
	 * 
	 * @param  amount
	 * 		   The amount to be checked
	 * @return True if and only if the given amount is positive, if there's no overflow
	 *         and if the size of this file incremented with the given amount is a 
	 *         valid size for any file.
	 *       | result == 
	 *       |    ( (amount > 0)
	 *       |   && ( getSize() <= Long.MAX_VALUE - amount)
	 *       |   && ((isValidSize(getSize()+amount)) )
	 */
	public boolean canAcceptForEnlarge(long amount) {
		return ( (amount > 0) 
				&& (getSize() <= Long.MAX_VALUE - amount)
				&& (isValidSize(getSize()+amount)) );
	}
	
	/**
	 * Increase the size of this file with the given size
	 *  Does nothing if the resulting file size is larger than the size limit
	 * 
	 * @param   amount
	 * 		    The amount with which the file size will be increased.
	 * @pre     This amount is accepted for enlargement.
	 *        | canAcceptForEnlarge(amount)
	 * @effect  The new size of this file is equal to the old size incremented
	 *          with the give amount of bytes.
	 *        | new.getSize() == this.getSize() + amount
	 */
	public void enlarge(long amount) {
		assert canAcceptForEnlarge(amount): "Precondition: Acceptable amount for enlarge";
		setSize(getSize() + amount);
	}
	
	/**
	 * Return a boolean reflecting whether this file can accept the given amount for shorten.
	 * 
	 * @param  amount
	 * 		   The amount to be checked.
	 * @return True if and only if the given amount is positive and the size of the file
	 * 		   decremented with the given amount is a valid size for all files.
	 *       | result == 
	 *       |    ( (amount > 0)
	 *       |   && ((isValidSize(getSize()-amount)) )
	 */
	public boolean canAcceptForShorten(long amount) {
		return ( (amount>0) && (isValidSize(getSize()-amount)) );
	}
	
	/**
	 * Decrease the size of this file with the given size
	 * Does nothing if the resulting file size is negative
	 * 
	 * @param   amount
	 * 	        The amount with which the file size will be decreased.
	 * @pre     This amount is accepted to shorten the file.
	 *        | canAcceptForShorten(amount)
	 * @effect  The new size of this file is equal to the old size decremented
	 *          with the given amount of bytes.
	 *        | new.getSize() == this.getSize() - amount
	 */
	public void shorten(long amount) {
		assert canAcceptForShorten(amount): "Precondition: Acceptable amount for shorten";
		setSize(getSize() - amount);
	}
	
	/**
	 * Set the size of the file to the given size.
	 * 
	 * @param size
	 *        The size for this file.
	 * @pre   This amount is a valid size
	 *      | isValidSize(size)
	 * @post  The size of the file is set to the given size.
	 * 		| new.size = size
	 * @throws FileNotWritableException
	 *         This file is not writable and its size cannot be set by the user.
	 *      | (! this.isWritable())
	 */
	public void setSize(long size)
	throws FileNotWritableException{
		if(! this.isWritable()) {
			throw new FileNotWritableException(this);
		}
		else {
		assert isValidSize(size): "Precondition: Acceptable number for size";
		this.size = size;
		setModificationTime();
		}
	}
	
	/*
	 * Variable registering the size of this file.
	 */
	private long size = 0;
	
	
	/*
	 * Returns the size limit that applies to all files
	 *   The size limit expresses the highest possible value for the 
	 *   size of a file.
	 */
	@Basic
	@Immutable
	public static long getSizeLimit() {
		return File.sizeLimit;
	}
		
	
	/**
	 * Set the maximum allowed file size
	 * Does nothing if the given limit is negative
	 * @param limit
	 * 		  The new maximum file size for all files.
	 * @pre   The given limit must be positive and less than or equal to the max value
	 *      | limit >= 0
	 *      | limit <= Long.MAX_VALUE
	 * @post  The new size limit that applies to all files is equal to the given
	 *        limit.
	 *      | new.getSizeLimit == limit      
	 */
	public static void setSizeLimit(long limit) {
		assert ((limit >= 0) && (limit <= Long.MAX_VALUE)): "Precondition: Acceptable number for size limit.";
		File.sizeLimit = limit;
	}
	
	/*
	 * Variable registering the size limit of this file.
	 */
	private static long sizeLimit = Long.MAX_VALUE;
	

	
	
	/**
	 * Check whether the given date is a valid creation date for this file.
	 * 
	 * @param   date
	 *          The date to check.
	 * @return  True if and only if the date 
	 *          is before or at the same time as the current time.
	 *        | result =
	 *        |    ((getCreationTime().before(getCurrentTime()))
	 *        |   || (getCreationTime().equals(getCurrentTime())) )
	 */
	public boolean isValidCreationTime(Date date) {
		return ((date.before(getCurrentTime())) ||
			   (date.equals(getCurrentTime())) );
	}
	
	/**
	 * Return the current time
	 */
	private static Date getCurrentTime() {
		Date date = new Date();
		return date;
	}
	
	/**
	 * Return the creation time of the file.
	 */
	@Basic
	@Immutable
	public Date getCreationTime() {
		return this.creationTime;
	}

	/*
	 * Variable registering the creation time of this file.	
	 */
	private final Date creationTime = new Date();
	
	
	/**
	 * Check whether the given date is a valid modification date for this file.
	 * 
	 * @param  date
	 *         The date to check
	 * @return True if and only if date points to the null object or the date 
	 *         is before or at the same time as the current time and after or at 
	 *         the same time as the creation time
	 *       | result =
	 *       |    ( (date == null)
	 *		 | || (	( (date.after(getCreationTime()))
	 *	     |      || (date.equals(getCreationTime())) )
	 *	     |    && ( (date.before(getCurrentTime()))
	 *	     |      || (date.equals(getCurrentTime())) )) )
	 */
	public boolean canHaveAsModificationTime(Date date) {
		return ( (date == null)
			  || (	( (date.after(getCreationTime()))
		           || (date.equals(getCreationTime())) )
		         && ( (date.before(getCurrentTime()))
		           || (date.equals(getCurrentTime())) )	 ) );
	}
	
	/**
	 * Set the time of the last modification of the file.
	 * 
	 * @post  The time when the file was last modified will be set to the current time.
	 */
	private void setModificationTime() {
		this.modificationTime = getCurrentTime();
	}
	
	/**
	 * Returns the modification time of the file.
	 */
	@Basic
	public Date getModificationTime() {
		return this.modificationTime;
	}
	
	/*
	 * Variable registering the modification time of this file.
	 */
	private Date modificationTime = null;
		
	
	/**
	 * Return a boolean reflecting whether a file's use period overlaps with another given file's use period
	 * 
	 * @param  file
	 * 		   The file which use period will be compared with the current file.
	 * @return True if and only if both this file as the file to be compared with has a
	 *         valid modification time and the use period of this file overlaps with the
	 *         use period of the given file
	 *       | result ==
	 *       |  !( this.getModificationTime().before(file.getCreationTime())
			 |	|| this.getCreationTime().after(file.getModificationTime()) )
	 */
	public boolean hasOverlappingUsePeriod(File file) {
		if (this.getModificationTime() != null
		   && file.getModificationTime() != null) {
			return !(this.getModificationTime().before(file.getCreationTime())
					|| this.getCreationTime().after(file.getModificationTime()));
		}
		else
			return false;
	}
	


	
	/*
	 * Check whether this file is writable
	 *   Some methods have no effect when invoked against non writable files.
	 */
	@Basic
	public boolean isWritable() {
		return this.writable;
	}
	
	/**
	 * Set the writable state of this file according to the given flag.
	 * 
	 * @param flag
	 *        The new writable state of this account
	 * @post  The new writable state of this account is equal to the given flag.
	 *      | new.writable = flag
	 */
	public void setWritable(boolean flag) {
		this.writable = flag;
	}
	
	/*
	 * Variable registering the writable state of this file.
	 */
	private boolean writable = true;

}
