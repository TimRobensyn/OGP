package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * An enumeration class of ingredient containers involving a capacity type and contents in spoons.
 * 
 * @version	1.0
 * @author	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 *
 */

public class IngredientContainer {
	
	/**
	 * Initialize this container with the given capacity and contents
	 * @param capacity
	 * 		  The capacity of this container
	 * @param contents
	 * 		  The contents in this container
	 */
	@Raw
	public IngredientContainer(AlchemicIngredient contents, Quantity capacity) {
		setContents(contents);
		setCapacity(capacity);
	}
	
	/************************************************************************
	 * Contents
	 ************************************************************************/
	
	/**
	 * Returns the alchemic ingredient of this container.
	 */
	@Raw @Basic
	public AlchemicIngredient getIngredient() {
		return contents;
	}

	/**
	 * Returns the quantitiy of the content in this container.
	 */
	@Raw @Basic
	public int getContentQuantity() {
		if (contents.getType().getState()==State.LIQUID) {
			return contents.getNbOfDrops();
		}
		if (contents.getType().getState()==State.POWDER) {
			return contents.getNbOfPinches();
		}
		else {
			return 0;
		}
	}
	
	/**
	 * Set the contents off this container to the given contents
	 * @param contents
	 * 		  The given contents
	 * @pre The given contents is not less than 0 or greater than the capacity of this container
	 *		| (contents >= 0) && (contents <= this.getCapacity())
	 */
	public void setContents(AlchemicIngredient contents) {
		if (contents.getType().getState()==State.LIQUID) {
			assert((contents.getNbOfDrops() >= 0) && (contents.getNbOfDrops() <= this.getCapacity())) :
				"Precondition: Contents is not less than 0 or more than the container's capacity";
		}
		if (contents.getType().getState()==State.POWDER) {
			assert((contents.getNbOfPinches() >= 0) && (contents.getNbOfPinches() <= this.getCapacity())):
				"Precondition: Contents is not less than 0 or more than the container's capacity";
		}
		
		this.contents = contents;
	}
	
	/**
	 * Variable storing the contents in this container
	 */
	private AlchemicIngredient contents = null;
	
	/************************************************************************
	 * Capacity
	 ************************************************************************/
	
	public enum container{SPOON,VIAL,BOTTLE,JUG,BARREL,SACHET,BOX,SACK,CHEST,STOREROOM}
	
	/**
	 * Return the capacity of this container.
	 */
	@Raw @Basic @Immutable
	public int getCapacity() {
		return this.capacity.getCapacity();
	}
	
	/**
	 * Set the capacity of this container to the given capacity.
	 * @param capacity
	 * 		  The capacity that this
	 */
	private final void setCapacity(container capacity) {
		assert (((capacity.equals(LiquidQuantity.DROP))
				&&(capacity.equals(PowderQuantity.PINCH)))
				&&((capacity.equals(LiquidQuantity.STOREROOM))
				&&(capacity.equals(PowderQuantity.STOREROOM)))):
					"Container cannot be drop, pinch or storeroom";
		if (contents.getType().getState()==State.LIQUID) {
			assert (contents.getQuantity()<=LiquidQuantity.valueOf(capacity.toString()).getCapacity()):
				"Container is not big enough";
		}
		if (contents.getType().getState()==State.POWDER) {
			this.capacity = capacity;
		}
		else {
			
		}
	}
	/**
	 * Variable storing the capacity of this container
	 */
	private final container capacity;
	
	
	

}
