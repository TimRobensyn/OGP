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
	public AlchemicIngredient(IngredientType type, int quantity, long temperature) {
		this.quantity = quantity;
		setType(type);
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
	 * Set the type of this alchemic ingredient to the given type.
	 * 
	 * @param type
	 *        The type to be set.
	 * @post  The type of this alchemic ingredient is equal to the given type.
	 *        | new.getType() = type
	 */
	private void setType(IngredientType type) {
		this.type = type;
	}
	
	
	
	/**
	 * Variable indicating the type of this alchemic ingredient.
	 */
	private IngredientType type = null;
	
	
	/************************************************************************
	 * Quantity
	 ************************************************************************/
	
	/**
	 * A variable referencing the quantity of this alchemic ingredient in number of spoons.
	 */
	private final int quantity;
	
	/**
	 * Check whether the given quantity is a valid quantity for an alchemic ingredient.
	 * 
	 * @param quantity
	 * 		  The quantity to check
	 * @return True if and only if the quantity is not below 0 and not above the maximum value
	 * 		   | result ==
	 * 		   | 	((quantity >= 0) && (quantity <= Long.MAX_VALUE))
	 */
	public static boolean isValidQuantity(int quantity) {
		return((quantity >= 0) && (quantity <= Long.MAX_VALUE));
	}
	
	/**
	 * Returns the amount of drops of this alchemic ingredient.
	 * 
	 * @pre The state of the ingredient type of alchemic ingredient is a liquid.
	 *      | this.getType().getState() = LIQUID
	 */
	public int getNbOfDrops() {
		assert (this.getType().getState() == State.LIQUID) : 
			"Precondition: Ingredient type of alchemic ingredient is a liquid";
		return quantity*dropsInSpoon;
	}
	
	/**
	 * Returns the amount of vials of this alchemic ingredient.
	 * 
	 * @pre The state of the ingredient type of alchemic ingredient is a liquid.
	 *      | this.getType().getState() = LIQUID
	 */
	public float getNbOfVials() {
		assert (this.getType().getState() == State.LIQUID) : 
			"Precondition: Ingredient type of alchemic ingredient is a liquid";
		return quantity/spoonsInVial;
	}
	
	/**
	 * Returns the amount of bottles of this alchemic ingredient.
	 * 
	 * @pre The state of the ingredient type of alchemic ingredient is a liquid.
	 *      | this.getType().getState() = LIQUID
	 */
	public float getNbOfBottles() {
		return getNbOfVials()/vialsInBottle;
	}
	
	/**
	 * Returns the amount of jugs of this alchemic ingredient.
	 * 
	 * @pre The state of the ingredient type of alchemic ingredient is a liquid.
	 *      | this.getType().getState() = LIQUID
	 */
	public float getNbOfJugs() {
		return getNbOfBottles()/bottlesInJug;
	}
	
	/**
	 * Returns the amount of barrels of this alchemic ingredient.
	 * 
	 * @pre The state of the ingredient type of alchemic ingredient is a liquid.
	 *      | this.getType().getState() = LIQUID
	 */
	public float getNbOfBarrels() {
		return getNbOfJugs()/jugsInBarrel;
	}
	

	
	/**
	 * Returns the amount of pinches of this alchemic ingredient.
	 * 
	 * @pre The state of the ingredient type of alchemic ingredient is a powder.
	 *      | this.getType().getState() = POWDER
	 */
	public int getNbOfPinches() {
		assert (this.getType().getState() == State.POWDER) : 
			"Precondition: Ingredient type of alchemic ingredient is a powder";
		return quantity*pinchesInSpoon;
	}
	
	/**
	 * Returns the amount of sachets of this alchemic ingredient.
	 * 
	 * @pre The state of the ingredient type of alchemic ingredient is a powder.
	 *      | this.getType().getState() = POWDER
	 */
	public float getNbOfSachets() {
		assert (this.getType().getState() == State.POWDER) : 
			"Precondition: Ingredient type of alchemic ingredient is a powder";
		return quantity/spoonsInSachet;
	}
	
	/**
	 * Returns the amount of boxes of this alchemic ingredient.
	 * 
	 * @pre The state of the ingredient type of alchemic ingredient is a powder.
	 *      | this.getType().getState() = POWDER
	 */
	public float getNbOfBoxes() {
		return getNbOfSachets()/sachetsInBox;
	}
	
	/**
	 * Returns the amount of sacks of this alchemic ingredient.
	 * 
	 * @pre The state of the ingredient type of alchemic ingredient is a powder.
	 *      | this.getType().getState() = POWDER
	 */
	public float getNbOfSacks() {
		return getNbOfBoxes()/boxesInSack;
	}
	
	/**
	 * Returns the amount of chests of this alchemic ingredient.
	 * 
	 * @pre The state of the ingredient type of alchemic ingredient is a powder.
	 *      | this.getType().getState() = POWDER
	 */
	public float getNbOfChests() {
		return getNbOfSacks()/sacksInChest;
	}
	
	
	/**
	 * Returns the amount of storerooms of this alchemic ingredient.
	 */
	public float getNbOfStorerooms() {
		float number = 0;
		if(this.getType().getState() == State.LIQUID) {
			number = (getNbOfBarrels()/barrelsInStoreroom);
		}
		if(this.getType().getState() == State.POWDER) {
			number = (getNbOfChests()/chestsInStoreroom);
		}
		return number;
	}
	
	/**
	 * Variables referencing the number of a smaller quantity unit included in the quantity unit
	 * one level higher for a liquid.
	 * 
	 * @note The order is: drops, spoons, vials, bottles, jugs, barrels, storerooms
	 */
	private static final int dropsInSpoon = 8;
	private static final int spoonsInVial = 5;
	private static final int vialsInBottle = 3;
	private static final int bottlesInJug = 7;
	private static final int jugsInBarrel = 12;
	private static final int barrelsInStoreroom = 5;
	
	/**
	 * Variables referencing the number of a smaller quantity unit included in the quantity unit
	 * one level higher for a powder.
	 * 
	 * @note The order is: pinches, spoons, sachets, boxes, sacks, chests, storerooms
	 */
	private static final int pinchesInSpoon = 6;
	private static final int spoonsInSachet = 7;
	private static final int sachetsInBox = 6;
	private static final int boxesInSack = 3;
	private static final int sacksInChest = 10;
	private static final int chestsInStoreroom = 5;
	
	
	/************************************************************************
	 * Coldness/Hotness
	 ************************************************************************/
	
	/**
	 * Check whether the given number is a valid value for coldness or hotness for
	 * all alchemic ingredients.
	 * 
	 * @param  temp
	 *         The temperature to check.
	 * @return True if and only if the given temperature is positive and 
	 *         not above the temperature upper limit.
	 *         | result == (temp>=0
	 *         |           && temp<=getTemperatureUpperLimit())
	 *         
	 */
	private static boolean isValidTemperatureValue(long temp) {
		return (temp>=0 && temp<=IngredientType.getTemperatureUpperLimit());
	}
	
	
	/**
	 * Set the coldness of this alchemic ingredient to the given value.
	 * 
	 * @param coldness
	 *        The new coldness of this alchemic ingredient.
	 * @post  If the given value is valid, the coldness 
	 *        of this alchemic ingredient is set to the given value.
	 *        | if (isValidTemperatureValue(coldness))
	 *        |   then new.coldness == coldness
	 */
	private void setColdness(long coldness) {
		if (isValidTemperatureValue(coldness))
			this.coldness = coldness;
	}
	
	/**
	 * Return the coldness of this alchemic ingredient.
	 */
	private long getColdness() {
		return this.coldness;
	}
	
	/**
	 * A variable indicating the coldness of this alchemic ingredient.
	 */
	private long coldness = 0;
	

	
	/**
	 * Set the hotness of this alchemic ingredient to the given value.
	 * 
	 * @param hotness
	 *        The new hotness of this alchemic ingredient.
	 * @post  If the given value is valid, the hotness 
	 *        of this alchemic ingredient is set to the given value.
	 *        | if (isValidTemperatureValue(hotness))
	 *        |   then new.hotness == hotness
	 */
	private void setHotness(long hotness) {
		if (isValidTemperatureValue(hotness))
			this.hotness = hotness;
	}
	
	/**
	 * Return the hotness of this alchemic ingredient.
	 */
	private long getHotness() {
		return this.hotness;
	}
	
	/**
	 * A variable indicating the hotness of this alchemic ingredient.
	 */	
	private long hotness = 0;
	
	
	/**
	 * Decrease the temperature of this alchemic ingredient with the given amount.
	 * 
	 * @param  tempValue
	 *         The value to decrease the temperature of this alchemic ingredient with.
	 * @effect If the given value is not above the current hotness, the hotness is decreased 
	 *         with this value.
	 *         | if (tempValue<=getHotness())
	 *         |     then new.getHotness() = old.getHotness()-tempValue
	 *         If the given value is above the current hotness and it is lesser than or equal to the 
	 *         current hotness summed up with the temperature upper limit value and decreased with the 
	 *         current coldness, the hotness is set to zero and the coldness is increased with the 
	 *         difference between the given value and the previous hotness.
	 *         | if (tempValue>getHotness()
	 *		   |  && tempValue<=getHotness()+IngredientType.getTemperatureUpperLimit()-getColdness())
	 *		   |     then new.getHotness() = 0
	 *		   |          && new.getColdness() = old.getColdness()+tempValue-old.getHotness()
	 */
	public void cool(long tempValue) {
		if (tempValue<=getHotness())
		    setHotness(getHotness()-tempValue);
		
		else if (tempValue>getHotness()
			  && tempValue<=getHotness()+IngredientType.getTemperatureUpperLimit()-getColdness()) {
			long prevHotness = getHotness();
			setHotness(0);
			setColdness(getColdness()+tempValue-prevHotness);
		}
		
	}
	
	/**
	 * Increase the temperature of this alchemic ingredient with the given amount.
	 * 
	 * @param  tempValue
	 *         The value to increase the temperature of this alchemic ingredient with.
	 * @effect If the given value is not above the current coldness, the coldness is decreased 
	 *         with this value.
	 *         | if (tempValue<=getColdness())
	 *         |     then new.getColdness() = old.getColdness()-tempValue
	 *         If the given value is above the current coldness and it is lesser than or equal to the 
	 *         current coldness summed up with the temperature upper limit value and decreased with the 
	 *         current hotness, the coldness is set to zero and the hotness is increased with the 
	 *         difference between the given value and the previous coldness.
	 *         | if (tempValue>getColdness()
	 *		   |  && tempValue<=getColdness()+IngredientType.getTemperatureUpperLimit()-getHotness())
	 *		   |     then new.getColdness() = 0
	 *		   |          && new.getHotness() = old.getHotness()+tempValue-old.getColdness()
	 */
	public void heat(long tempValue) {
		if (tempValue<=getColdness())
			setColdness(getColdness()-tempValue);
		
		else if (tempValue>getColdness()
			  && tempValue<=getColdness()+IngredientType.getTemperatureUpperLimit()-getHotness() ) {
			long prevColdness = getColdness();
			setColdness(0);
			setHotness(getHotness()+tempValue-prevColdness);
		}
		
	}
	
	
	/************************************************************************
	 * TEMPERATURE
	 ************************************************************************/
	
	/**
	 * Check whether the given temperature is a valid temperature for all
	 * alchemic ingredients.
	 * 
	 * @param  temperature
	 *         The temperature to check.
	 * @return True if and only if the temperature array containts exactly two elements,
	 *         both elements are valid temperature values and those elements aren't both
	 *         not zero.
	 *         | result == ( temperature.length==2
	 *         |          && isValidTemperatureValue(temperature[0])
	 *         |          && isValidTemperatureValue(temperature[1])
	 *         |          && !(temperature[0]!=0 && temperature[1]!=0) ) 
	 */
	public static boolean isValidTemperature(long[] temperature) {
		if (temperature.length==2) {
			long coldValue = temperature[0];
			long heatValue = temperature[1];
			return (isValidTemperatureValue(coldValue) 
					&& isValidTemperatureValue(heatValue)
					&& !(coldValue!=0 && heatValue!=0) );
		}
		else return false;
		
	}
	
	
	/**
	 * Get the temperature of this alchemic ingredient.
	 * 
	 * @return The temperature of this alchemic ingredient.
	 */
	@Basic @Immutable
	public long[] getTemperature() {
		return new long[] {this.getColdness(), this.getHotness()};
	}
	
}
