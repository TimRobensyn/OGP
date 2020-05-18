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
		assert(Quantity.isValidQuantity(quantity)):
			"Quantity is not valid";
		assert(type!=null):
			"IngredientType is not valid";
		assert(Temperature.isValidTemperature(temperature)):
			"Temperature is not valid";
		this.type = type;
		this.quantity = quantity;
		this.temperature = temperature;
	}
	
	public AlchemicIngredient(IngredientType type, int quantity, long[] temperatureArray) {
		this(type,quantity, new Temperature(temperatureArray));
	}
	
	public AlchemicIngredient(String name, State state, Temperature standardTemperature, int quantity, Temperature temperature) {
		this(new IngredientType(name,state,standardTemperature), quantity, temperature);
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
	
	/**
	 * Variable indicating the type of this alchemic ingredient.
	 */
	private final IngredientType type;
	
	/**
	 * Return the full name of this ingredient. This is the special name followed
	 * by the formatted simple names between brackets or, in case the ingredient type has no special
	 * name yet,
	 * @return	String
	 * 			specialName ([Cooled/Heated] simpleNames)
	 */
	public String getFullName() {
		String fullName = "";
		
		//Add cooled or heated to simple name
		switch(Temperature.compareTemperature(getTemperatureObject(),getStandardTemperatureObject())){
		case -1:
			fullName.concat("Cooled ");
			break;
		case 1:
			fullName.concat("Heated ");
			break;
		default:
			fullName.concat(type.getSimpleName());
		}
		
		//Add special name
		if (type.getSpecialName()!=null) {
			fullName = type.getSpecialName() + " (" + fullName + ")";
		}
		return fullName;
	}
	
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
	
	private Temperature getTemperatureObject() {
		return temperature;
	}
	/**
	 * A variable keeping the temperature of this ingredient.
	 */
	private Temperature temperature;
	
	/**
	 * Get the standard temperature of this ingredient.
	 * 
	 * @return The standard temperature of this ingredient.
	 */
	@Basic @Immutable
	public long[] getStandardTemperature() {
		return this.type.getStandardTemperature();
	}
	
	public Temperature getStandardTemperatureObject() {
		return type.getStandardTemperatureObject();
	}
	
}
