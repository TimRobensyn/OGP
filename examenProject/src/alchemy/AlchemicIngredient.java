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
	 * Coldness/Hotness (Rim has both)
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
