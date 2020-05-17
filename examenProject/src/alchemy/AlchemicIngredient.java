package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of alchemic ingredients involving a name, temperature, type, quantity and a liquid or powder state.
 * 
 * @version  1.0
 * @author   Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

public class AlchemicIngredient {
	
	/**************************************************
	 * CONSTRUCTORS
	 **************************************************/
	public AlchemicIngredient(IngredientType type, int quantity, Temperature temperature) {
		this.type = type;
		if (Quantity.isValidQuantity(quantity))
			this.quantity = quantity;
		this.temperature = temperature;
	}
	
	public AlchemicIngredient(IngredientType type, int quantity, long[] temperatureArray) {
		Temperature temperature = Temperature(temperatureArray);
		this(type,quantity,temperature);
	}
	
	/*public AlchemicIngredient(String name, State state, long standardTemperature, int quantity, long temperature) {
		this(new IngredientType(name, state, standardTemperature), quantity, temperature);
		//TO DO
	}*/

	
	/************************************************************************
	 * TYPE
	 ************************************************************************/
	
	/**
	 * Return the type of this alchemic ingredient.
	 */
	@Basic @Immutable
	public IngredientType getType() {
		return this.type;
	}
	
//	/**
//	 * Set the type of this alchemic ingredient to the given type.
//	 * 
//	 * @param type
//	 *        The type to be set.
//	 * @post  The type of this alchemic ingredient is equal to the given type.
//	 *        | new.getType() = type
//	 */
//	private void setType(IngredientType type) {
//		this.type = type;
//	}
	
	
	
	/**
	 * Variable indicating the type of this alchemic ingredient.
	 */
	private final IngredientType type;
	
	
	/************************************************************************
	 * Quantity
	 ************************************************************************/
	
	/**
	 * Return the quantity of this ingredient in drops or pinches (depending on the state)
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * A variable referencing the quantity of this alchemic ingredient in number of drops or 
	 * pinches (depending on the state).
	 */
	private final int quantity;
	
	/************************************************************************
	 * Temperature
	 ************************************************************************/
	
	/**
	 * Return the coldness of the temperature of this ingredient.
	 */
	public long getColdness() {
		return temperature.getColdness();
	}
	
	/**
	 * Return the hotness of the temperature of this ingredient.
	 */
	public long getHotness() {
		return temperature.getHotness();
	}
	
	/**
	 * Return an array of long values with the coldness and hotness of the ingredient.
	 */
	public long[] getTemperature() {
		return temperature.getTemperature();
	}
	
	/**
	 * A variable keeping the temperature of this ingredient.
	 */
	private Temperature temperature;
	
}
