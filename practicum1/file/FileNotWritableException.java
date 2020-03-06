package file;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of exceptions signaling that a file is not writable when changing the file.
 *  Each file not writable exception involves the file.
 *  
 * @version 1.0
 * @author  Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

@SuppressWarnings("serial")
public class FileNotWritableException extends RuntimeException{
	
	/**
	 * Initialize this new file not writable exception with given file.
	 * 
	 * @param  file
	 *         The file for this file not writable exception
	 * @post   The file of this new file not writable exception is the same as the given file.
	 *       | new.getFile() == file
	 * @effect This new file not writable exception is further initialized as a new runtime
	 *         exception involving no diagnostic message and no cause.
	 *       | super() 
	 */
	public FileNotWritableException(File file) {
		this.file = file;
	}
	
	/**
	 * Return the file of this file not writable exception.
	 */
	@Basic
	@Raw
	public File getFile() {
		return file;
	}
	
	/*
	 * Variable referencing the file of this file not writable exception.
	 */
	private final File file;
}
