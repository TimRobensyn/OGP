package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * An interface for enum classes containing counting systems
 * 
 * @version	1.0
 * @author	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 *
 */

public interface Quantity {

	/**
	 * Returns the amount of this capacity
	 */
	@Basic @Raw @Immutable
	public int getQuantity();

}
