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
	
	
	/*******************************************************
	 * FULL NAME
	 ******************************************************/
	
	
	/**
	 * Set the full name of this alchemic ingredient.
	 * 
	 * @post If this alchemic ingredient has a special name, the full name is the special name followed by the
	 *       formatted simple names between brackets, possibly with a prefactor indicating whether this alchemic
	 *       ingredient is warmer or cooler than the standard temperature of its ingredient type. If this ingredient
	 *       has no special name, the full consists of the simplenames with the prefactor.
	 *       | if (this.temperature < getStandardTemperature())
	 *       |    prefactor == "Cooled"
	 *       | else if (this.temperature > getStandardTemperature())
	 *       |    prefactor == "Heated"
	 *       | else
	 *       |    prefactor == ""
	 *       | 
	 *       | if (getType().getSpecialName() != null)
	 *       |    this.fullName == this.specialName (prefactor this.simpleNames)
	 *       | else
	 *       |    this.fullname == prefactor this.simpleNames
	 */
	private void setFullName() {
		
		//Add cooled or heated to simple name
		switch(Temperature.compareTemperature(this.temperature,getStandardTemperatureObject())){
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
		if (getType().getSpecialName()!=null) {
			fullName = type.getSpecialName() + " (" + fullName + ")";
		}
		
	}
	
	/**
	 * After setting the full name of this alchemic ingredient, return it.
	 */
	public String getFullName() {
		setFullName();
		return this.fullName;
	}
	
	private String fullName = "";
	
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
