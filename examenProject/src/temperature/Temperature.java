package temperature;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of temperatures as used for alchemic purposes.
 * 
 * @invar   Each temperature must be valid as to be able to be used correctly.
 *          | isValidTemperature(this)
 * 
 * @author  Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 * @version 1.0
 *
 */

public class Temperature {
	
	// Opmerking: temperature moet totaal geïmplementeerd zijn
	
	/************************************************************************
	 * CONSTRUCTOR
	 ************************************************************************/

	
	/**
	 * Initialize a new temperature, as used in alchemy, with a given array of two values, the first 
	 * the coldness and the second the hotness.
	 * 
	 * @param temperature
	 *        The array that contains the temperature values of this new temperature.
	 * @post  If the given temperature array contains exactly two elements and the first element of 
	 *        the array is a valid temperature value, the coldness of this new
	 *        temperature is equal to that element. Otherwise, it is equal to zero.
	 *        | if ( temperature.length==2
	 *        |   && isValidTemperature(temperature[0]) )
	 *        |   then new.getColdness() == temperature[0]
	 *        | else
	 *        |   then new.getColdness() == 0
	 * @post  If the given temperature array contains exactly two elements and the second element of 
	 *        the array is a valid temperature value, the hotness of this new
	 *        temperature is equal to that element. Otherwise, it is equal to zero.
	 *        | if ( temperature.length==2
	 *        |   && isValidTemperature(temperature[1]) )
	 *        |   then new.getHotness() == temperature[1]
	 *        | else
	 *        |   then new.getHotness() == 0
	 */
	@Raw
	public Temperature(long[] temperature) {
		if (temperature.length == 2)
			if (isValidTemperatureValue(temperature[0]))
				this.coldness = temperature[0];
			if (isValidTemperatureValue(temperature[1]))
				this.hotness = temperature[1];
	}
	
	/**
	 * Initialize a new temperature, as used in alchemy, with given coldness and hotness.
	 * 
	 * @param  coldness
	 *         The coldness of the new temperature.
	 * @param  hotness
	 *         The hotness of the new temperature.
	 * @effect This new temperature is initialized with {coldness, hotness} of its 
	 *         temperature array.
	 *         | this(new long[] {coldness, hotness})
	 */
	@Raw
	public Temperature(long coldness, long hotness) {
		this(new long[] {coldness, hotness});
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
	@Raw
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
	 * @effect If the given value is negative or zero, do nothing.
	 * 		   If the given value is not above the current hotness, the hotness is decreased 
	 *         with this value.
	 *         | if (tempValue<=getHotness()
	 *         |    && tempValue>0)
	 *         |     then new.getHotness() == old.getHotness()-tempValue
	 *         If the given value is above the current hotness and it is lesser than or equal to the 
	 *         current hotness summed up with the temperature upper limit value and decreased with the 
	 *         current coldness, the hotness is set to zero and the coldness is increased with the 
	 *         difference between the given value and the previous hotness.
	 *         | if (tempValue>getHotness()
	 *         |    && tempValue>0
	 *		   |    && tempValue<=getHotness()+IngredientType.getTemperatureUpperLimit()-getColdness())
	 *		   |     then new.getHotness() == 0
	 *		   |          && new.getColdness() == old.getColdness()+tempValue-old.getHotness()
	 */
	public void cool(long tempValue) {
		if (tempValue>0) {
			if (tempValue<=getHotness())
				setHotness(getHotness()-tempValue);		
			else if (tempValue>getHotness()
					&& tempValue<=getHotness()+getTemperatureUpperLimit()-getColdness()) {
				long prevHotness = getHotness();
				setHotness(0);
				setColdness(getColdness()+tempValue-prevHotness);
			}
		}
		
	}
	
	/**
	 * Increase the temperature of this Temperature object with the given amount.
	 * 
	 * @param  tempValue
	 *         The value to increase the temperature of this Temperature object with.
	 * @effect If the given value is negative or zero, do nothing.
	 * 		   If the given value is not above the current coldness, the coldness is decreased 
	 *         with this value.
	 *         | if (tempValue<=getColdness()
	 *         |    &&(tempValue>0))
	 *         |     then new.getColdness() = old.getColdness()-tempValue
	 *         If the given value is above the current coldness and it is lesser than or equal to the 
	 *         current coldness summed up with the temperature upper limit value and decreased with the 
	 *         current hotness, the coldness is set to zero and the hotness is increased with the 
	 *         difference between the given value and the previous coldness.
	 *         | if (tempValue>getColdness()
	 *         |    && tempValue>0
	 *		   |    && tempValue<=getColdness()+IngredientType.getTemperatureUpperLimit()-getHotness())
	 *		   |     then new.getColdness() = 0
	 *		   |          && new.getHotness() = old.getHotness()+tempValue-old.getColdness()
	 */
	public void heat(long tempValue) {
		if (tempValue>0) {
			if (tempValue<=getColdness())
				setColdness(getColdness()-tempValue);
			else if (tempValue>getColdness()
				  && tempValue<=getColdness()+getTemperatureUpperLimit()-getHotness() ) {
				long prevColdness = getColdness();
				setColdness(0);
				setHotness(getHotness()+tempValue-prevColdness);
			}
		}
		
		
	}
	
	
	/************************************************************************
	 * TEMPERATURE
	 ************************************************************************/
	
	/**
	 * Check whether the given temperature array is valid for all
	 * Temperature objects.
	 * 
	 * @param  temperature
	 *         The temperature to check.
	 * @return True if and only if the temperature array contains exactly two elements,
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
	 * Check whether the given temperature object is valid.
	 * 
	 * @param  temperature
	 * 		   The temperature object to check.
	 * @return True if the temperature array consisting of coldness and hotness is valid.
	 *         | result == isValidTemperature(temperature.getTemperature())
	 */
	public static boolean isValidTemperature(Temperature temperature) {
		return isValidTemperature(temperature.getTemperature());
	}
	
	/**
	 * Compares two temperatures and checks whether the first is cooler or warmer than the second.
	 * 
	 * @param  temp1
	 *         The temperature to check.
	 * @param  temp2
	 *         The temperature to compare with.     
	 * @return Returns one if the first temperature is warmer than the second, minus one if it's cooler
	 *         and 0 if both temperatures are equal.
	 *         | if (temp1 > temp2)
	 *         |    return 1
	 *         | else if (temp1 < temp2)
	 *         |    return -1
	 *         | else
	 *         |    return 0
	 */
	public static int compareTemperature(Temperature temp1, Temperature temp2) {
		long firstComparable = -temp1.getColdness()+temp1.getHotness();
		long secondComparable = -temp2.getColdness()+temp2.getHotness();
		if (firstComparable<secondComparable) return -1;
		else if (firstComparable>secondComparable) return 1;
		else return 0;
	}
	
	/**
	 * Return the difference between two temperatures, positive if first temperature is bigger than the second.
	 */
	public static long temperatureDifference(Temperature temp1, Temperature temp2) {
		return -temp1.getColdness()+temp1.getHotness()-(-temp2.getColdness()+temp2.getHotness());
	}

	
	/**
	 * Return the temperature of this Temperature object.
	 */
	@Basic 
	public long[] getTemperature() {
		return new long[] {this.getColdness(), this.getHotness()};
	}
	
	/************************************************************************
	 * TEMPERATURE UPPER LIMIT
	 ************************************************************************/
	
	/**
	 * Get the temperature upper limit value of all temperatures.
	 */
	@Basic @Immutable
	public static long getTemperatureUpperLimit() {
		return tempUpperLimit;
	}
	
	/**
	 * Set the temperature upper limit value of all temperatures to the given value.
	 * 
	 * @param upperLimit
	 *        The new temperature upper limit of all ingredient types.
	 * @post  If the given temperature does not exceed the maximal possible 
	 *        value for a long type of number, the temperature upper limit value for all 
	 *        temperatures is set to the given temperature.
	 *        | if (temperature <= Long.MAX_VALUE)
	 *        |   then IngredientType.getTemperatureUpperLimit() == temperature
	 */
	public static void setTemperatureUpperLimit(long upperLimit) {
		if (upperLimit <= Long.MAX_VALUE)
			tempUpperLimit = upperLimit;
	}
	
	/**
	 * A variable containing the temperature upper limit value of all temperatures.
	 */
	private static long tempUpperLimit = 10000;

}
