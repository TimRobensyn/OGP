package alchemy;

import be.kuleuven.cs.som.annotate.*;
import temperature.Temperature;

/**
 * A class of alchemic ingredients involving a name, temperature, type, quantity and a liquid or powder state.
 * 
 * @invar    The quantity of each alchemic ingredient must be a valid quantity for any ingredient.
 * 			 | isValidQuantity(getQuantity())
 * @invar    The temperature of each alchemic ingredient must be a valid temperature.
 * 			 | Temperature.isValidTemperature(getTemperature())
 * @invar    The type of each alchemic ingredient must be a valid type for any ingredient.
 * 			 | isValidType(getType())
 * 
 * @version  1.0
 * @author   Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

public class AlchemicIngredient {
	
	/**************************************************
	 * CONSTRUCTORS
	 **************************************************/
	/**
	 * Initialize a new alchemic ingredient with the given type and quantity.
	 * 
	 * @param type
	 *        The ingredient type of the new alchemic ingredient.
	 * @param quantity
	 *        The quantity (in drops or pinches according to state) of the new alchemic ingredient.
	 * @pre   The quantity must be valid.
	 * 		  | isValidQuantity(quantity)
	 * @pre   The type must be valid.
	 * 		  | isValidType(type)
	 * @post  The type of this alchemic ingredient is equal to the given type.
	 *        | getType() == type
	 * @post  The quantity of this alchemic ingredient is equal to the given quantity.
	 *		  | getQuantity() == quantity
	 * @post  The temperature of this alchemic ingredient is equal to the standard temperature of 
	 * 	      its type.
	 * 		  | getTemperature() == getType().getStandardTemperature()
	 */
	@Raw
	public AlchemicIngredient(IngredientType type, int quantity) {
		assert(isValidQuantity(quantity)):
			"Quantity is not valid";
		assert(isValidType(type)):
			"IngredientType is not valid";
		this.type = type;
		this.quantity = quantity;
		this.temperature = new Temperature(type.getStandardTemperature());
	}

	/**
	 * Initialize a new alchemic ingredient of type 'Water' with given quantity.
	 * 
	 * @param  quantity
	 *		   The quantity of the new alchemic ingredient.
	 * @post   The quantity of this alchemic ingredient is equal to the given quantity.
	 *		   | getQuantity() == quantity
	 * @effect The new alchemic ingredient is initialized with the given quantity, 
	 *         its type is a new type with name "Water", state liquid and temperature {0, 20}.
	 *         | this(new IngredientType("Water",State.LIQUID,new Temperature(0L,20L)),quantity)
	 */
	@Raw
	public AlchemicIngredient(int quantity) {
		this(new IngredientType("Water",new Temperature(0L,20L)),quantity);
	}


	
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
	 * Check whether the given type is valid.
	 * 
	 * @param  type
	 * 		   The type to check.
	 * @return True if and only if the type is effective.
	 * 		   | result == (type!=null)
	 */
	@Raw
	public boolean isValidType(IngredientType type) {
		return (type!=null);
	}
	
	/**
	 * Return the state of the type of this ingredient.
	 */
	@Basic @Immutable
	public State getState() {
		return getType().getState();
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
	 *       has no special name, the full name consists of the simple names with the prefactor.
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
	@Basic
	public String getFullName() {
		String fullName = "";
		
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
		
		return fullName;		
	}
	
	/************************************************************************
	 * Quantity
	 ************************************************************************/
	
	/**
	 * Return the quantity of this ingredient in drops or pinches (depending on the state).
	 */
	@Basic @Immutable
	public int getQuantity() {
		return this.quantity;
	}
	
	/**
	 * Check whether the given quantity is a valid quantity for an alchemic ingredient.
	 * 
	 * @param  quantity
	 * 		   The quantity to check.
	 * @return True if and only if the quantity is not below 0 and not above the maximum value.
	 * 		   | result ==
	 * 		   | 	((quantity >= 0) && (quantity <= Long.MAX_VALUE))
	 */
	@Raw
	public static boolean isValidQuantity(int quantity) {
		return ((quantity >= 0) && (quantity <= Long.MAX_VALUE));
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
	@Basic
	public long[] getTemperature() {
		return temperature.getTemperature();
	}
	
	/**
	 * Return the temperature object of this class.
	 */
	public Temperature getTemperatureObject() {
		return temperature;
	}
	
	/**
	 * Cool this ingredient with the given amount
	 * 
	 * @param amount
	 * 		  The given amount
	 * @effect The temperature of this ingredient gets cooled with the given amount
	 * 		   | temperature.cool(amount)
	 */
	public void cool(long amount) {
		temperature.cool(amount);
	}
	
	/**
	 * Heat this ingredient with the given amount
	 * 
	 * @param amount
	 * 		  The given amount
	 * @effect The temperature of this ingredient gets heated with the given amount
	 * 		   | temperature.heat(amount)
	 */
	public void heat(long amount) {
		temperature.heat(amount);
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
	
	/**
	 * Return the standard temperature object of the type of this alchemic ingredient.
	 */
	public Temperature getStandardTemperatureObject() {
		return type.getStandardTemperatureObject();
	}

	
}
