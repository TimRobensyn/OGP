package alchemy;

import be.kuleuven.cs.som.annotate.*;
import java.util.regex.*;

/**
 * A class defining the type of an alchemic ingredient with a name, a standard temperature and a state (liquid or powder).
 * 
 * @version 1.0
 * @author  Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 *
 */
public class IngredientType {
	
	/********************************************************
	 * CONSTRUCTOR(EN)
	 ********************************************************/
	
	public IngredientType(String name, State state, long[] standardTemperature) {
		setSimpleName(name);
		setState(state);
		setStandardTemperature(standardTemperature);
	}
	
	/********************************************************
	 * NAME (defensief)
	 ********************************************************/
	
	/**
	 * Return the name of this ingredient's type in the form of a String.
	 */
	@Basic @Raw
	public String getSimpleName() {
		return this.simpleName;
	}
	
	/**
	 * Check whether a string is a valid name.
	 */
	public static boolean isValidSimpleName(String name){
		
		boolean valid = true;
		String[] choppedUpName = name.split(" ");
		
		// Naam van 1 woord is minstens 3 tekens lang.
		if ( choppedUpName.length<2
		   && choppedUpName[0].length()<3 ) // Naam van 1 woord is minstens 3 tekens lang.
			valid = false;
		
		for (String word : choppedUpName) {
			
			// Geen with of mixed in de naam
			if (word.matches("^[" + specialCharacters + "]?[Ww]ith$")||(word.matches("^[" + specialCharacters + "]?[Mm]ixed$"))) {
				valid = false;
				break;
			}
			
			//Elk woord is minstens 2 letters lang
			if (word.length()<2) {
				valid = false;
				break;
			}
			
			//Elk woord begint met een hoofdletter (hier kan een speciaal teken voor staan), de rest van de letters zijn klein of speciaal
			if (!word.matches("^[" + specialCharacters + "]?[A-Z][a-z" + specialCharacters + "]*$")){
				valid = false;
				break;
			}
		}
		return valid;
	}
	
	/**
	 * Set the simple name of this ingredient type to the given name.
	 * 
	 * @param  newName
	 *         The new simple name of this ingredient type.
	 * @post   If the given simple name is not empty and valid, the simple name 
	 *         of this ingredient type is equal to the given name.
	 *         | if ( !newName==null
	 *         |    || isValidSimpleName(newName) )
	 *         |  then new.getName().equals(newName)
	 * @throws NullPointerException
	 *         The given name is empty.
	 *         | newName==null
	 * @throws IllegalNameException
	 *         The given name is not valid.
	 *         | !isValidSimpleName(newName)
	 */
	private void setSimpleName(String newName) 
			throws NullPointerException, IllegalNameException {
		if (newName == null) throw new NullPointerException();
		
		if (isValidSimpleName(newName))
			this.simpleName = newName;
		else {
			throw new IllegalNameException(newName);
		}
	}
	
	/**
	 * A string containing the name of this type of ingredient
	 */
	public String simpleName;
	
	/**
	 * A string containing the special characters that can be used in an ingredient's name.
	 */
	private final static String specialCharacters = "'()";
	
	/************************************************************************
	 * STATE
	 ************************************************************************/
	
	// OPMERKING, moet kunnen veranderd worden in het labo
	
	/**
	 * A basic method returning the state of this ingredient type.
	 * 
	 * @returns	The state of this ingredient type.
	 */
	@Basic @Raw
	public State getState() {
		return this.state;
	}
	
	/**
	 * Set the state of this ingredient type to the given state.
	 * 
	 * @param newState
	 *        The new state for this ingredient type.
	 * @post  The state of this ingredient type is equal to the given state.
	 */
	@Basic
	private void setState(State newState) {
		if (State.isValidState(newState))
			this.state = newState;
	}
	
	/**
	 * A variable referencing the state of this ingredient type.
	 */
	private State state;
	
	
	
	
	/************************************************************************
	 * Coldness/Hotness (Rim has both)
	 ************************************************************************/
	
	/**
	 * Check whether the given number is a valid value for coldness or hotness for
	 * all ingredient types.
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
	 * Set the coldness of this ingredient type to the given value.
	 * 
	 * @param coldness
	 *        The new coldness of this ingredient type.
	 * @post  If the given value valid, the coldness 
	 *        of this ingredient type is set to the given value.
	 *        | if (isValidTemperatureValue(coldness))
	 *        |   then new.coldness == coldness
	 */
	private void setColdness(long coldness) {
		if (isValidTemperatureValue(coldness))
			this.coldness = coldness;
	}
	
	
	private long getColdness() {
		return this.coldness;
	}
	
	/**
	 * A variable indicating the coldness of this ingredient type.
	 */
	private long coldness = 0;
	

	
	/**
	 * Set the hotness of this ingredient type to the given value.
	 * 
	 * @param hotness
	 *        The new hotness of this ingredient type.
	 * @post  If the given value is valid, the hotness 
	 *        of this ingredient type is set to the given value.
	 *        | if (isValidTemperatureValue(hotness))
	 *        |   then new.hotness == hotness
	 */
	private void setHotness(long hotness) {
		if (isValidTemperatureValue(hotness))
			this.hotness = hotness;
	}
	
	private long getHotness() {
		return this.hotness;
	}
	
	/**
	 * A variable indicating the hotness of this ingredient type.
	 */	
	private long hotness = 0;
	
	
	/**
	 * Lower the temperature of this ingredient type with the given amount.
	 * 
	 * @param tempValue
	 */
	public void cool(long tempValue) {
		//TODO
	}
	
	/**
	 * Increase the temperature of this ingredient type with the given amount.
	 * 
	 * @param tempValue
	 */
	public void heat(long tempValue) {
		//TODO
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
		return IngredientType.tempUpperLimit;
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
			IngredientType.tempUpperLimit = temperature;
	}
	
	/**
	 * A variable containing the temperature upper limit of all ingredient types.
	 */
	private static long tempUpperLimit = 10000;
	
	
	/************************************************************************
	 * TEMPERATURE
	 ************************************************************************/
	
	/**
	 * Check whether the given temperature is valid temperature for all
	 * ingredient types.
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
	 * Get the temperature of this ingredient type.
	 * 
	 * @return The temperature of this ingredient type.
	 */
	@Basic @Immutable
	public long[] getTemperature() {
		long[] temp = {this.getColdness(), this.getHotness()};
		return temp;
	}

	
	
	
	/************************************************************************
	 * STANDARD TEMPERATURE
	 ************************************************************************/
	
	/**
	 * Get the standard temperature of this ingredient type.
	 * 
	 * @return The standrad temperature of this ingredient type.
	 */
	@Basic @Immutable
	public long[] getStandardTemperature() {
		return this.standardTemperature;
	}
	
	/**
	 * Set the standard temperature of this ingredient type to the given temperature.
	 * 
	 * @param temperature
	 *        The new standard temperature
	 * @post  If the given temperature is valid and both the standard coldness and
	 *        hotness are not zero at the same time, the standard temperature
	 *        of this ingredient type is set to the given temperature.
	 *        | if (isValidTemperature(temperature)
	 *        |    && !(temperature[0]==0 && temperature[0]==0))
	 *        |   then new.getStandardTemperature() == temperature
	 */
	private void setStandardTemperature(long[] temperature) {
		if (isValidTemperature(temperature)
		   && !(temperature[0]==0 && temperature[0]==0))
			this.standardTemperature = temperature;
	}
	
	/**
	 * An array containing coldness and hotness, thus the standardtemperature of this ingredient type.
	 */
	private long[] standardTemperature = {0, 0};

}
