package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * An enumeration of powder quantities.
 * 
 * @invar   Each powder quantity must have a valid quantity value.
 * 			| Quantity.isValidQuantity(getQuantity())
 * @invar   Each powder quantity must have a valid index.
 *          | isValidIndex(getIndex())
 * 
 * @author  Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 * @version 1.0
 */

public enum PowderQuantity implements Quantity{
	
	PINCH(1,1), SPOON(6,2), SACHET(7,3), BOX(6,4), SACK(3,5), CHEST(10,6), STOREROOM(5,7);
	
	/**
	 * Initialize this capacity with the given amount.
	 * 
	 * @param amount
	 * 		  The amount of this capacity.
	 */
	@Raw
	private PowderQuantity(int amount, int index) {
		this.quantity = amount;
		this.index = index;
	}
	
	/**
	 * Returns the amount of this capacity.
	 */
	@Basic @Immutable
	public int getQuantity() {
		return this.quantity;
	}
	
	/**
	 * Variable storing the amount of capacity.
	 */
	private final int quantity;

	
	/**
	 * Check whether the given index is valid or not.
	 * 
	 * @param  index
	 * 		   The index to check.
	 * @return True if and only if the greater than zero and not above the number of values
	 *         in this enumeration class.
	 */
	public static boolean isValidIndex(int index) {
		return (index>0 && index<=PowderQuantity.values().length);
	}
	
	/**
	 * Returns the index of this unit.
	 */
	@Basic @Immutable
	public int getIndex() {
		return this.index;
	}
	
	/**
	 * Variable storing the index of the unit
	 */
	private final int index;

	/**
	 * Return the quantity of this unit in drops.
	 */
	@Immutable
	public int getNbOfSmallestUnit() {
		int NbOfPinches = 1;
		for (PowderQuantity quantity: PowderQuantity.values()) {
			if (quantity.getIndex()<=this.index)
				NbOfPinches = NbOfPinches*quantity.getQuantity();
		}
		return NbOfPinches;
	}
	
	//TODO doc
	public static Container getContainer(int drops) {
		for (Container container: Container.values()) {			
			for (PowderQuantity quantity:PowderQuantity.values()) {
				if (container.toString()==quantity.toString()) {
					if (drops <= quantity.getNbOfSmallestUnit())
						return container;
				}
			}
		}
		return Container.CHEST;
	}
	
	/**
	 * Return the ratio between a spoon of solids (in pinches) and spoon of liquids (in drops).
	 */
	public static double getLiquidRatio() {
		return PowderQuantity.SPOON.getQuantity()/LiquidQuantity.SPOON.getQuantity();
	}


	public static void main(String[] args) {
		System.out.println(PowderQuantity.values().length);
	}
}
