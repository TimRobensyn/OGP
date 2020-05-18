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
	 * @param 	capacity
	 * 		 	The capacity of this container
	 * @param	contents
	 * 			The contents in this container
	 * @pre		//TODO
	 */
	@Raw
	public IngredientContainer(AlchemicIngredient contents, Container newCapacity) {
		setContents(contents);
		if (contents.getType().getState()==State.LIQUID) {
			assert (contents.getQuantity()<=LiquidQuantity.valueOf(newCapacity.toString()).getNbOfSmallestUnit()):
				"Container is not big enough";
			capacity = newCapacity;
		}
		else if (contents.getType().getState()==State.POWDER) {
			assert (contents.getQuantity()<=PowderQuantity.valueOf(newCapacity.toString()).getNbOfSmallestUnit()):
				"Container is not big enough";
			capacity = newCapacity;
		} else {
			capacity = null;
		}
	}
	
	/**
	 * Initialize this empty container with the given capacity
	 * @param 	capacity
	 * 		 	The capacity of this container
	 * @pre		//TODO
	 */
	@Raw
	public IngredientContainer(Container newCapacity) {
		this(null, newCapacity);
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
		if (State.isValidState(contents.getType().getState()))
			return contents.getQuantity();
		else
			return 0;
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
	 * Variable storing the capacity of this container.
	 */
	private final Container capacity; //Geïnitialiseerd in de constructor om te waarborgen dat de variabele idd final is)
}
