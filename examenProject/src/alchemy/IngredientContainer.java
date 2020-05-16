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
	public IngredientContainer(AlchemicIngredient contents, ContainerType capacity) {
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
	 * Returns the quantity of the content in this container.
	 */
	@Raw @Basic
	public int getContentQuantity() {
		if ((contents.getType().getState()==State.LIQUID) || (contents.getType().getState()==State.POWDER)) {
			return contents.getQuantity();
		} else {
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
			assert((contents.getQuantity() >= 0) && (contents.getQuantity() <= this.getCapacity())) :
				"Precondition: Contents is not less than 0 or more than the container's capacity";
		}
		if (contents.getType().getState()==State.POWDER) {
			assert((contents.getQuantity() >= 0) && (contents.getQuantity() <= this.getCapacity())):
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
	
	/**
	 * Return the capacity of this container.
	 */
	@Raw @Basic @Immutable
	public int getCapacity() {
		if(contents.getType().getState()==State.LIQUID) {
			return LiquidQuantity.valueOf(capacity.toString()).getQuantity();
		}
		else if(contents.getType().getState()==State.POWDER) {
			return PowderQuantity.valueOf(capacity.toString()).getQuantity();
		} else {
			return 0;
		}
	}
	
	/**
	 * Set the capacity of this container to the given capacity.
	 * @param capacity
	 * 		  The capacity that this
	 */
	private final void setCapacity(ContainerType capacity) {
		if (contents.getType().getState()==State.LIQUID) {
			assert (contents.getQuantity()<=LiquidQuantity.valueOf(capacity.toString()).getQuantity()):
				"Container is not big enough";
			this.capacity = capacity;
		}
		if (contents.getType().getState()==State.POWDER) {
			assert (contents.getQuantity()<=PowderQuantity.valueOf(capacity.toString()).getQuantity()):
				"Container is not big enough";
			this.capacity = capacity;
		} //else {
			//IK WEET NI WA RIM HIER WOU ZETTEN?
		//}
	}
	
	/**
	 * Variable storing the capacity of this container
	 */
	private ContainerType capacity;
	
	
	

}
