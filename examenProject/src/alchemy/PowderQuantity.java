package alchemy;

import be.kuleuven.cs.som.annotate.*;

public enum PowderQuantity implements Quantity{
	
	PINCH(1,1), SPOON(6,2), SACHET(7,3), BOX(6,4), SACK(3,5), CHEST(10,6), STOREROOM(5,7);
	
	/**
	 * Initialize this capacity with the given amount
	 * @param amount
	 * 		  The amount of this capacity
	 */
	@Raw
	private PowderQuantity(int amount, int index) {
		this.quantity = amount;
		this.index = index;
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

	/**
	 * Returns the index of this unit.
	 */
	@Basic @Raw @Immutable @Override
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
	@Raw @Immutable @Override
	public int getNbOfSmallestUnit() {
		int NbOfPinches = 1;
		for (PowderQuantity quantity : PowderQuantity.values()) {
			if (quantity.getIndex()<=this.index)
				NbOfPinches = NbOfPinches*quantity.getQuantity();
		}
		return NbOfPinches;
	}
	
	//TODO doc
	public static Container getContainer(int drops) {
		for (Container container:Container.values()) {			
			for (PowderQuantity quantity:PowderQuantity.values()) {
				if (container.toString()==quantity.toString()) {
					if (drops <= quantity.getNbOfSmallestUnit())
						return container;
				}
			}
		}
		return Container.CHEST;
	}
	
//	//TODO doc
//	private static int[] getNbOfSmallestUnitArray() {
//		int[] result = new int[PowderQuantity.values().length];
//		for (PowderQuantity quantity : PowderQuantity.values()) {
//			result[quantity.getIndex()]	= quantity.getNbOfSmallestUnit();	
//		}
//		return result;
//	}

}
