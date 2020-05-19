package alchemy;

import be.kuleuven.cs.som.annotate.*;


public enum LiquidQuantity implements Quantity {
	
	DROP(1,1), SPOON(8,2), VIAL(5,3), BOTTLE(3,4), JUG(7,5), BARREL(12,6), STOREROOM(5,7);
	
	/**
	 * Initialize this LiquidQuantity with the given amount and an index
	 * @param	amount
	 * 			The amount of this quantity
	 * @param	index
	 * 			The index of this quantity
	 */
	@Raw
	private LiquidQuantity(int amount, int index) {
		this.quantity = amount;
		this.index = index;
	}
	
	/**
	 * Returns the amount of this quantity relative to the unit smaller than it.
	 */
	@Basic @Raw @Immutable @Override
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
		int NbOfDrops = 1;
		for (LiquidQuantity quantity : LiquidQuantity.values()) {
			if (quantity.getIndex()<=this.index)
				NbOfDrops = NbOfDrops*quantity.getQuantity();
		}
		return NbOfDrops;
	}
	
//	/**
//	 * Return the quantity of this unit in drops.
//	 */
//	@Raw @Immutable @Override
//	public int getNbOfSmallestUnit() {
//		int NbOfDrops = 1;
//		LiquidQuantity[] units = LiquidQuantity.values();
//		for (int index = 1; index < units.; index++)){
//			//TODO
//	}
	
	//TODO doc
	public static Container getContainer(int drops) {
		for (Container container:Container.values()) {			
			for (LiquidQuantity quantity:LiquidQuantity.values()) {
				if (container.toString()==quantity.toString()) {
					if (drops <= quantity.getNbOfSmallestUnit())
						return container;
					
				}
			}
		}
		return Container.BARREL;
//		int index = 1;
//		while ((getNbOfSmallestUnitArray()[index]<drops)
//				&&(index<getNbOfSmallestUnitArray().length)){
//			index += 1;
//		}
//		LiquidQuantity.values().
//		return Container.valueOf(LiquidQuantity.values()[index].toString());
	}
	
	//TODO
	public static double getPowderRatio() {
		return LiquidQuantity.SPOON.getQuantity()/PowderQuantity.SPOON.getQuantity();
	}

}
