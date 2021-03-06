package filesystem;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class for signalling illegal attempts to change a file.
 * 
 * @author 	Tommy Messelis
 * @version	2.1
 */
public class ObjectNotWritableException extends RuntimeException {

	/**
	 * Required because this class inherits from Exception
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Variable referencing the file to which change was denied.
	 */
	private final FileSystemObject fileSysObj;

	/**
	 * Initialize this new file not writable exception involving the
	 * given file.
	 * 
	 * @param	file
	 * 			The file for the new file not writable exception.
	 * @post	The file involved in the new file not writable exception
	 * 			is set to the given file.
	 * 			| new.getFile() == file
	 */
	@Raw
	public ObjectNotWritableException(FileSystemObject FSObj) {
		this.fileSysObj = FSObj;
	}
	
	/**
	 * Return the file involved in this file not writable exception.
	 */
	@Raw @Basic @Immutable
	public FileSystemObject getFile() {
		return fileSysObj;
	}
	
	
}
