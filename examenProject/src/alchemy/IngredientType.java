package alchemy;

import be.kuleuven.cs.som.annotate.*;

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
	/**
	 * 
	 * @param name
	 * @param state
	 * @param standardTemperature
	 */
	public IngredientType(Name name, State state, long[] standardTemperature) {
		
		if (Name.isValidName(name))
			name = new Name("Water");
		this.name = name;
		
		if (State.isValidState(state))
			state = State.LIQUID;
		this.state = state;
		
		Temperature newStandardTemperature;
		if (Temperature.isValidTemperature(standardTemperature)) {
			newStandardTemperature = new Temperature(standardTemperature);
		} else {
			newStandardTemperature = new Temperature(0L,20L);
		}
		this.standardTemperature = newStandardTemperature;
	}
	/**
	 * 
	 * @param name
	 * @param standardTemperature
	 */
	public IngredientType(Name name, Temperature standardTemperature) {
		this(name, State.LIQUID, standardTemperature.getTemperature());
	}
	
	/********************************************************
	 * NAME (defensief)
	 ********************************************************/
	
	/**
	 * Return the simple name of this ingredient type.
	 */
	public String getSimpleName() {
		return name.getSimpleName();
	}
	
	/**
	 * Return the special name of this mixed ingredient type.
	 */
	public String getSpecialName() {
		return name.getSpecialName();
	}

	/**
	 * Return the full name of this ingredient type.
	 */
	public String getFullName() {
		return ""; //TODO
	}
	
	/**
	 * A variable for the name of this ingredient type.
	 */
	private final Name name;

	
	/************************************************************************
	 * STATE
	 ************************************************************************/
	
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
	 * A variable referencing the state of this ingredient type.
	 */
	private final State state;
	
	/************************************************************************
	 * STANDARD TEMPERATURE
	 ************************************************************************/
	
	/**
	 * Get the standard temperature of this ingredient type.
	 * 
	 * @return The standard temperature of this ingredient type.
	 */
	@Basic @Immutable
	public long[] getStandardTemperature() {
		return this.standardTemperature.getTemperature();
	}
	
//	/**
//	 * Set the standard temperature of this ingredient type to the given temperature.
//	 * 
//	 * @param temperature
//	 *        The new standard temperature
//	 * @post  If the given temperature is valid and both the standard coldness and
//	 *        hotness are not zero at the same time, the standard temperature
//	 *        of this ingredient type is set to the given temperature.
//	 *        | if (isValidTemperature(temperature)
//	 *        |    && !(temperature[0]==0 && temperature[0]==0))
//	 *        |   then new.getStandardTemperature() == temperature
//	 */
//	private void setStandardTemperature(long[] temperature) {
//		if (Temperature.isValidTemperature(temperature)) {
//			Temperature newTemperature = new Temperature(temperature);
//			this.standardTemperature = newTemperature;
//		}
//			
//	} ==> Niet nodig omdat standardTemperature final is. (Hergebruik evt. documentatie)
	
	/**
	 * A variable containing the Temperature object that is the standard temperature of this type.
	 */
	private final Temperature standardTemperature;

}
