package laboratory;

import be.kuleuven.cs.som.annotate.*;
import alchemy.*;

/**
 * A class indicating the item cannot load more ingredients.
 */

public class ItemEmptyException extends RuntimeException{
	
	/**
	 * Required because this class inherits from Exception
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A variable indicating the laboratory.
	 */
	private final Laboratory laboratory;
	
	/**
	 * Initialize a new item empty exception with the given laboratory.
	 * 
	 * @param 	laboratory
	 * 		    The laboratory that caused the exception because it was empty.
	 * @post	The laboratory variable of this item empty exception is set to the given laboratory.
	 * 			| new.getEmptyLaboratory == laboratory
	 */
	@Raw
	public ItemEmptyException(Laboratory laboratory) {
		this.laboratory = laboratory;
	}
	
	/**
	 * Return the laboratory which caused this exception.
	 * @return
	 */
	@Raw @Immutable @Basic
	public Laboratory getEmptyLaboratory() {
		return laboratory;
	}

}
