package filesystem;

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
	
	public FileSystemObject getItemAt(int index) {
		return null;
	}
	
	public FileSystemObject getItem(String name) {
		return null;
	}
	
	public boolean exists(String name) {
		return false;
	}
	
	public int getIndexOf(FileSystemObject object) {
		return 0;
	}
	
	public int getNbItems() {
		return 0;
	}
	
	public boolean hasAsItem(Directory dir) {
		return false;
	}
	
	public boolean isDirectOrIndirectSubdirectoryOf(Directory dir) {
		return false;
	}
}
