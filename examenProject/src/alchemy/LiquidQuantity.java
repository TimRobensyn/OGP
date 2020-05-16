package alchemy;

import be.kuleuven.cs.som.annotate.*;


public enum LiquidQuantity implements Quantity {
	
	DROP(1), SPOON(8), VIAL(5), BOTTLE(3), JUG(7), BARREL(12), STOREROOM(5);
	
	/**
	 * Initialize this capacity with the given amount
	 * @param amount
	 * 		  The amount of this capacity
	 */
	@Raw
	private LiquidQuantity(int amount) {
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
