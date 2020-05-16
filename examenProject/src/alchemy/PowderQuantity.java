package alchemy;

import be.kuleuven.cs.som.annotate.*;

public enum PowderQuantity implements Quantity{
	
	PINCH(1), SPOON(6), SACHET(7), BOX(6), SACK(3), CHEST(10), STOREROOM(5);
	
	/**
	 * Initialize this capacity with the given amount
	 * @param amount
	 * 		  The amount of this capacity
	 */
	@Raw
	private PowderQuantity(int amount) {
		this.quantity = amount;
	}
	
	/**
	 * Returns the amount of this capacity
	 */
	@Basic @Raw @Immutable
	public int getQuantity() {
		return this.quantity;
	}
	
	/**
	 * Variable storing the amount of capacity
	 */
	private final int quantity;

}
