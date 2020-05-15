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
	public IngredientContainer(Capacity capacity, AlchemicIngredient contents) {
		setCapacity(capacity);
		setContents(contents);
	}
	
	/************************************************************************
	 * Capacity
	 ************************************************************************/
	
	/**
	 * Return the capacity of this container
	 */
	@Raw @Basic @Immutable
	public int getCapacity() {
		return this.capacity.getCapacity();
	}
	
	/**
	 * Set the capacity of this container to the given capacity
	 * @param capacity
	 * 		  The capacity of this container
	 */
	private void setCapacity(Capacity capacity) {
		this.capacity = capacity;
	}
	
	/**
	 * Variable storing the capacity of this container
	 */
	private Capacity capacity;
	
	
	/************************************************************************
	 * Contents
	 ************************************************************************/

	/**
	 * Returns the contents in this container
	 */
	@Raw @Basic
	public int getContentQuantity() {
		if (contents.getType().getState()==State.LIQUID) {
			return contents.
		}
		return this.contents.;
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
			assert((contents.getNbOfVials() >= 0) && (contents <= this.getCapacity())) :
				"Precondition: Contents is not less than 0 or more than the container's capacity";
		}
		
		this.contents = contents;
	}
	
	/**
	 * Variable storing the contents in this container
	 */
	private AlchemicIngredient contents = null;

}
