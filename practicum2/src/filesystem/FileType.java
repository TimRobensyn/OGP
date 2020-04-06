package filesystem;

import be.kuleuven.cs.som.annotate.*;

/**
 * An enumeration of file types.
 *   In its current form, the class only supports text-, pdf- and java files.   
 * 
 * @invar   The extension of each file type must be a valid extension
 *          for any file type.
 *        | isValidExtension(getExtension())
 * 
 * @author  Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 * @version 1.0
 *
 */

@Value
public enum FileType {

	Text("txt"), Java("java"), Pdf("pdf");
	
	/**
	 * Initialize this file type with the given extension.
	 * 
	 * @param extension
	 *        The extension for this new file type.
	 * @post  The extension for this new file type is equal to the given extension.
	 *      | new.getExtension() == extension
	 */
	@Raw
	private FileType(String extension) {
		this.extension = extension;
	}
	
	/**
	 * Return the extension of this file type.
	 */
	@Basic @Raw @Immutable
	public String getExtension() {
		return this.extension;
	}
	
	/**
	 * Variable storing the extension for this file type.
	 */
	private final String extension;
	
	/**
	 * Check whether the given extension is a valid extension for any file type.
	 * 
	 * @param  extension
	 * 		   The extension to check.
	 * @return True if and only if the given extension is effective,
	 *         not empty and only consists of lower case letters.
	 *       | result ==
	 *       |    ( extension != null
	 *       |   && extension.matches("a-z") )
	 */
	public static boolean isValidExtension(String extension) {
		return extension!=null && extension.matches("[a-z]+");
	}
	
}
