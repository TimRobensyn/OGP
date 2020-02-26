/**
 * ToDo class commentaar
 * 
 * @version 1.0
 * @author Tim Lauwers, Robbe Van Biervliet, Tim Robensyn
 */

package file;

public class File {
	
	String name;
	int size;
	boolean writable;
	private static int sizeLimit = Integer.MAX_VALUE;
	
	/**
	 * Initialize a File with a name, size and whether or not it is writable.
	 * 
	 * @param name
	 * @param size
	 * @param writable
	 */
	public File(String name, int size, boolean writable) {
		setName(name);
	}
	
	/**
	 * Set the name of the file as the given string name
	 * The string cannot be empty and has to match the regex a-zA-Z0-9.-_
	 * @param name
	 * 			String that will be used as the filename
	 */
	public void setName(String name) {
		if((!name.isEmpty()) && (name.matches("^[a-zA-Z0-9.-_]*$"))) {
			this.name = name;
		}
	}
	
	/**
	 * Returns the size of this file
	 */
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Increase the size of this file with the given size
	 * Does nothing if the resulting file size is larger than the size limit
	 * @param size
	 * 			The size with which the file size will be increased
	 */
	public void enlarge(int size) {
		if(getSize() + size <= File.sizeLimit) {
			this.size = getSize() + size;
		}
	}
	
	/**
	 * Decrease the size of this file with the given size
	 * Does nothing if the resulting file size is negative
	 * @param size
	 * 			The size with which the file size will be decreased
	 */
	public void shorten(int size) {
		if(getSize() - size >= 0) {
			this.size = getSize() - size;
		}
	}
	
	/**
	 * Set the maximum allowed file size
	 * Does nothing if the given limit is negative
	 * @param limit
	 * 			The new maximum file size
	 */
	public static void setSizeLimit(int limit) {
		if(limit >= 0) {
			File.sizeLimit = limit;
		}
	}
	
	
	public static void main(String[] Args) {
		System.out.println("Eeeeeerste functie huyyyyy");
		System.out.println("Git is awesome wooohooow");
		System.out.println("Annabel zegt hallo");
		System.out.println("ok");
		System.out.println("extra test");
	}
}
