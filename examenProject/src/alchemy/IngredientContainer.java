package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * An class of ingredient containers involving a capacity unit and alchemic ingredient as contents of it.
 * 
 * @invar   The capacity of a container must be valid for any container.
 * 			| isValidCapacity(getCapacity())
 * @invar   Each container can have its contents as its contents.
 * 			| canHaveAsContents(getContents())
 * 
 * @version	1.0
 * @author	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 *
 */

public class IngredientContainer {
	
	/**
	 * Initialize this container with the given capacity and contents.
	 * 
	 * @param  capacity
	 * 		   The capacity of this container.
	 * @param  contents
	 * 		   The contents in this container.
	 * @pre	   The given capacity unit can be used as a container.
	 *         | isValidCapacity(capacity)
	 * @post   The capacity of this container is equal to the given capacity.
	 * 		   | new.getCapacity() == capacity
	 * @effect The given contents are set as the contents of this container.
	 * 		   | setContents(contents)
	 */
	@Raw
	public IngredientContainer(AlchemicIngredient contents, Unit capacity) {
		assert (isValidCapacity(capacity)):
			"This unit cannot be used as a container.";
		this.capacity = capacity;
		
		setContents(contents);
	}
	
	/**
	 * Initialize this empty container with the given capacity.
	 * 
	 * @param  capacity
	 * 		   The capacity of this container.
     * @effect This container is initialized with the given capacity. It has non-effective contents.
     * 		   | this(null, capacity)
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
	@Basic
	public AlchemicIngredient getContents() {
		return this.contents;
	}

	/**
	 * Returns the quantity of the content in this container.
	 */
	public int getContentQuantity() {
		return getContents().getQuantity();
	}
	
	/**
	 * Set the contents off this container to the given contents.
	 * 
	 * @param contents
	 * 		  The given contents
	 * @pre   This ingredient container can have the given alchemic ingredient as contents.
	 * 		  | canHaveAsContents(contents)
	 */
	@Raw
	public void setContents(AlchemicIngredient contents) {
		assert(canHaveAsContents(contents)) :
			"Precondition: The contents are valid for this container.";
		this.contents = contents;
	}
	
	/**
	 * Check whether the given alchemic ingredients as contents is valid.
	 * 
	 * @param  contents
	 * 		   The contents to check.
	 * @return True if and only if the contents are not effective or the given contents has a quantity not 
	 * 		   below zero, above the absolute capacity of this container and the state of the given contents
	 * 		   is equal to that of the capacity of this container.
	 *         | result == ( contents==null
	 *         |           || (contents.getQuantity()>=0
	 *         |     		  && contents.getQuantity()<=getAbsoluteCapacity()
	 *		   |              && contents.getState()==getCapacity().getState()) )
	 */
	@Raw
	public boolean canHaveAsContents(AlchemicIngredient contents) {
		if (contents == null)
			return true;
		return ( contents.getQuantity()>=0
			   && contents.getQuantity()<=getAbsoluteCapacity()
			   && contents.getState()==getCapacity().getState());
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
	@Basic @Raw @Immutable
	public Unit getCapacity() {
		return this.capacity;
	}
	
	/**
	 * Check whether the given capacity is valid for any ingredient container.
	 * 
	 * @param  capacity
	 * 		   The capacity to check.
	 * @return True if and only if the given capacity can be used as an container.
	 * 		   | result == capacity.isContainer()
	 */
	@Raw
	public static boolean isValidCapacity(Unit capacity) {
		return capacity.isContainer();
	}
	
	/**
	 * Return the capacity of this container measured in the smallest unit of the same state.
	 */
	@Raw @Immutable
	public int getAbsoluteCapacity() {
		return getCapacity().getAbsoluteCapacity();
	}

	/**
	 * Variable storing the capacity of this container.
	 */
	private final Unit capacity;
}
