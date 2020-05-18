package alchemy;

import be.kuleuven.cs.som.annotate.*;

public class Temperature {
	
	/************************************************************************
	 * CONSTRUCTOR
	 ************************************************************************/
	
	/**
	 * //TODO
	 */
	public Temperature(long coldness, long hotness) {
		if ((isValidTemperatureValue(coldness))&&(isValidTemperatureValue(hotness))) {
			this.coldness = coldness;
			this.hotness = hotness;
		}
	}
	
	/**
	 * //TODO
	 */
	public Temperature(long[] temperature) {
		this(temperature[0],temperature[1]);
	}
		
	/************************************************************************
	 * Coldness/Hotness
	 ************************************************************************/
	
	/**
	 * Check whether the given number is a valid value for coldness or hotness for
	 * all Temperature objects.
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
		return (temp>=0 && temp<=getTemperatureUpperLimit());
	}
	
	
	/**
	 * Set the coldness of this Temperature object to the given value.
	 * 
	 * @param coldness
	 *        The new coldness of this Temperature object.
	 * @post  If the given value is valid, the coldness 
	 *        of this Temperature object is set to the given value.
	 *        | if (isValidTemperatureValue(coldness))
	 *        |   then new.coldness == coldness
	 */
	private void setColdness(long coldness) {
		if (isValidTemperatureValue(coldness))
			this.coldness = coldness;
	}
	
	/**
	 * Return the coldness of this Temperature object.
	 */
	public long getColdness() {
		return this.coldness;
	}
	
	/**
	 * A variable indicating the coldness of this Temperature object.
	 */
	private long coldness = 0;
	

	
	/**
	 * Set the hotness of this Temperature object to the given value.
	 * 
	 * @param hotness
	 *        The new hotness of this Temperature object.
	 * @post  If the given value is valid, the hotness 
	 *        of this Temperature object is set to the given value.
	 *        | if (isValidTemperatureValue(hotness))
	 *        |   then new.hotness == hotness
	 */
	private void setHotness(long hotness) {
		if (isValidTemperatureValue(hotness))
			this.hotness = hotness;
	}
	
	/**
	 * Return the hotness of this Temperature object.
	 */
	public long getHotness() {
		return this.hotness;
	}
	
	/**
	 * A variable indicating the hotness of this Temperature object.
	 */	
	private long hotness = 0;
	
	
	/**
	 * Decrease the temperature of this Temperature object with the given amount.
	 * 
	 * @param  tempValue
	 *         The value to decrease the temperature of this Temperature object with.
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
			  && tempValue<=getHotness()+getTemperatureUpperLimit()-getColdness()) {
			long prevHotness = getHotness();
			setHotness(0);
			setColdness(getColdness()+tempValue-prevHotness);
		}
		
	}
	
	/**
	 * Increase the temperature of this Temperature object with the given amount.
	 * 
	 * @param  tempValue
	 *         The value to increase the temperature of this Temperature object with.
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
			  && tempValue<=getColdness()+getTemperatureUpperLimit()-getHotness() ) {
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
	 * Temperature objects.
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
	
	public static boolean isValidTemperature(Temperature temperature) {
		return isValidTemperature(temperature.getTemperature());
	}
	
	/**
	 * Compares two temperatures and checks whether the first is cooler or warmer than the second.
	 * 
	 * @param  first
	 *         The temperature to check.
	 * @param  second
	 *         The temperature to compare with.     
	 * @return Returns one if the first temperature is warmer than the second, minus one if it's cooler
	 *         and 0 if both temperatures are equal.
	 *         | if (first > second)
	 *         |    return 1
	 *         | else if (first < second)
	 *         |    return -1
	 *         | else
	 *         |    return 0
	 */
	public static int compareTemperature(Temperature first, Temperature second) {
		long firstComparable = -first.getColdness()+first.getHotness();
		long secondComparable = -second.getColdness()+second.getHotness();
		if (firstComparable<secondComparable) return -1;
		else if (firstComparable>secondComparable) return 1;
		else return 0;
	}
	
//	public static int compareTemperature(long[] first, long[] second) {
//		compareTemperature(new Temperature(1L,0L), new Temperature(0L,1L));
//	}
	
	/**
	 * Get the temperature of this Temperature object.
	 * 
	 * @return The temperature of this Temperature object.
	 */
	@Basic @Immutable
	public long[] getTemperature() {
		return new long[] {this.getColdness(), this.getHotness()};
	}
	
	/************************************************************************
	 * TEMPERATURE UPPER LIMIT
	 ************************************************************************/
	
	/**
	 * Get the temperature upper limit of all ingredient types.
	 * 
	 * @return The temperature upper limit of all ingredient types.
	 */
	@Basic @Immutable
	public static long getTemperatureUpperLimit() {
		return tempUpperLimit;
	}
	
	/**
	 * Set the temperature upper limit of all ingredient types to the given temperature.
	 * 
	 * @param temperature
	 *        The new temperature upper limit of all ingredient types.
	 * @post  If the given temperature does not exceed the maximal possible 
	 *        value for a long type of number, the temperature upper limit for all 
	 *        ingredient types is set to the given temperature.
	 *        | if (temperature <= Long.MAX_VALUE)
	 *        |   then IngredientType.getTemperatureUpperLimit() == temperature
	 */
	public static void setTemperatureUpperLimit(long temperature) {
		if (temperature <= Long.MAX_VALUE)
			tempUpperLimit = temperature;
	}
	
	/**
	 * A variable containing the temperature upper limit of all ingredient types.
	 */
	private static long tempUpperLimit = 10000;

}
