package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * An interface for quantity unit enum classes containing counting systems
 * 
 * @version	1.0
 * @author	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 *
 */

public interface Quantity {

	/**
	 * Return the amount of this quantity unit.
	 */
	@Basic @Immutable
	public int getQuantity();
	
	/**
	 * Return the index of this quantity unit, relative to the other
	 * units (smaller unit, smaller index, starting at 1).
	 */
	@Basic @Immutable
	public int getIndex();
	
	/**
	 * Return the quantity of this unit measured in the smallest units
	 * of this quantity unit class.
	 */
	@Raw @Immutable
	public int getNbOfSmallestUnit();
	

	/**
	 * Check whether the given quantity is a valid quantity for an alchemic ingredient.
	 * 
	 * @param  quantity
	 * 		   The quantity to check.
	 * @return True if and only if the quantity is not below 0 and not above the maximum value.
	 * 		   | result ==
	 * 		   | 	((quantity >= 0) && (quantity <= Long.MAX_VALUE))
	 */
	public static boolean isValidQuantity(int quantity) {
		return((quantity >= 0) && (quantity <= Long.MAX_VALUE));
	}

}
