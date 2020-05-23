package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * An class of ingredient containers involving a capacity type and contents in spoons.
 * 
 * @version	1.0
 * @author	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 *
 */

public class IngredientContainer {
	
	/**
	 * Initialize this container with the given capacity and contents.
	 * 
	 * @param 	capacity
	 * 		 	The capacity of this container.
	 * @param	contents
	 * 			The contents in this container.
	 * @pre		//TODO
	 */
	@Raw
	public IngredientContainer(AlchemicIngredient contents, Unit capacity) {
		setContents(contents);
		assert (capacity.isContainer()):
			"This unit cannot be used as a container.";
		assert (contents.getQuantity()<=capacity.getAbsoluteCapacity()):
			"This container is not big enough for this ingredient.";
		assert (contents.getState()==capacity.getState()):
			"This container can't contain ingredients with this state.";
		this.capacity = capacity;
	}
	
	/**
	 * Initialize this empty container with the given capacity.
	 * 
	 * @param 	capacity
	 * 		 	The capacity of this container.
	 * @pre		//TODO
	 */
	@Raw
	public IngredientContainer(Unit capacity) {
		this(null, capacity);
	}
	
	/************************************************************************
	 * Contents
	 ************************************************************************/
	
	/**
	 * Returns the alchemic ingredient of this container.
	 */
	@Raw @Basic
	public AlchemicIngredient getIngredient() {
		return this.contents;
	}

	/**
	 * Returns the quantity of the content in this container.
	 */
	@Raw
	public int getContentQuantity() {
		return getIngredient().getQuantity();
	}
	
	/**
	 * Set the contents off this container to the given contents
	 * @param contents
	 * 		  The given contents
	 * @pre  The given contents is not less than 0 or greater than the capacity of this container
	 *		 | (contents >= 0) && (contents <= this.getCapacity()) //TODO
	 */
	public void setContents(AlchemicIngredient contents) {
		assert((contents.getQuantity() >= 0) && (contents.getQuantity() <= getAbsoluteCapacity())) :
			"Precondition: Contents is not less than 0 or more than the container's capacity";
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
	public Unit getCapacity() {
		return this.capacity;
	}
	
	/**
	 * Return the capacity of this container measured in the smallest unit of the same state.
	 */
	@Immutable
	public int getAbsoluteCapacity() {
		return getCapacity().getAbsoluteCapacity();
	}

	/**
	 * Variable storing the capacity of this container.
	 */
	private final Unit capacity;
}
