package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class indicating illegal names.
 */

public class IllegalNameException extends RuntimeException{
	
	/**
	 * Required because this class inherits from Exception
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * A variable indicating the illegal string.
	 */
	private final String name;
	
	/**
	 * Initialize a new illegal name exception with the given name.
	 * 
	 * @param	name
	 * 			The name that caused the exception.
	 * @post	The name of this illegal name exception is set to the given name.
	 * 			| new.getName() == name
	 */
	@Raw
	public IllegalNameException(String name) {
		this.name = name;
	}
	
	/**
	 * Return the illegal name which caused this exception.
	 */
	@Raw @Immutable @Basic
	public String getName() {
		return name;
	}

}
