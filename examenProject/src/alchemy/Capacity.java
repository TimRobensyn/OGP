package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * A value class for quantities of ingredients.
 * 
 * @version	1.0
 * @author	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 *
 */

public enum Capacity {
	
	SPOON(1), VIAL(5), BOTTLE(15), JUG(105), BARREL(1260), SACHET(7), BOX(42), SACK(126), CHEST(1260);
	
	/**
	 * Initialize this capacity with the given amount
	 * @param amount
	 * 		  The amount of this capacity
	 */
	@Raw
	private Capacity(int amount) {
		this.capacity = amount;
	}
	
	/**
	 * Returns the amount of this capacity
	 */
	@Basic @Raw @Immutable
	public int getCapacity() {
		return this.capacity;
	}
	
	/**
	 * Variable storing the amount of capacity
	 */
	private final int capacity;
}
