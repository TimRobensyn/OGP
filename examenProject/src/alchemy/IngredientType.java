package alchemy;

import be.kuleuven.cs.som.annotate.*;
import temperature.Temperature;

import java.util.Arrays;

/**
 * A class defining the type of an alchemic ingredient with a name, a standard temperature and a state (liquid or powder).
 * 
 * @invar   The simple names of each ingredient type must be valid.
 *          | areValidSimpleNames(getSimpleNames())
 * @invar   The special name of each ingredient type must be valid.
 *          | isValidSpecialName(getSpecialName())
 * @invar   The state of each ingredient type must be valid.
 *          | State.isValidState(getState())
 * @invar   The standard temperature of each ingredient type must be valid.
 *          | Temperature.isValidStandardTemperature(getStandardTemperature())
 * 
 * @version 1.0
 * @author  Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 *
 */
public class IngredientType {
	
	/********************************************************
	 * CONSTRUCTOR(EN) (totaal)
	 ********************************************************/
	
	/**
	 * Initialize an ingredient type object with an array of simple names, a special name,
	 * a state and a standard temperature.
	 * 
	 * @param simpleNames
	 * 		  The array of simple names of the new ingredient type.
	 * @param specialName
	 *        The special name of the new ingredient type.
	 * @param state
	 *        The state of the new ingredient type.
	 * @param standardTemperature
	 *        The standard temperature object of the new ingredient type.
	 * @post  If the given simple names, special name, state and temperature are all valid, 
	 *        the simple names of this ingredient type are equal to the given array. Else, it is 
	 *        equal to the array consisting of the single element 'Water'.
	 *        | if ( !areValidSimpleNames(simpleNames)
	 *	      |   || !isValidSpecialName(specialName)
	 *	      |   || !State.isValidState(state)
	 *	      |   || !Temperature.isValidTemperature(standardTemperature) )
	 *        |    then new.getSimpleNames() == simpleNames
	 *        | else
	 *        |    then new.getSimpleNames() == {"Water"}
	 * @post  If the given simple names, special name, state and temperature are all valid, 
	 *        the special name of this ingredient type is equal to the given name. Else it is set not effective.
	 *        | if ( !areValidSimpleNames(simpleNames)
	 *	      |   || !isValidSpecialName(specialName)
	 *	      |   || !State.isValidState(state)
	 *	      |   || !Temperature.isValidTemperature(standardTemperature) )
	 *        |    then new.getSpecialName() == specialName
	 *        | else
	 *        |    then new.getSpecialName() == null
	 * @post  If the given simple names, special name, state and temperature are all valid,
	 *        the state of this ingredient type is equal to the given state. Else, the state is Liquid.
	 *        | if ( !areValidSimpleNames(simpleNames)
	 *	      |   || !isValidSpecialName(specialName)
	 *	      |   || !State.isValidState(state)
	 *	      |   || !Temperature.isValidTemperature(standardTemperature) )
	 *        |    then new.getState() == state
	 *        | else
	 *        |    then new.getState() == State.LIQUID
	 * @post  If the given simple names, special name, state and temperature are all valid,
	 *        the standard temperature of this ingredient type is equal to the given
	 *        values. Else the standard temperature is set to {0,20}.
	 *        | if ( !areValidSimpleNames(simpleNames)
	 *	      |   || !isValidSpecialName(specialName)
	 *	      |   || !State.isValidState(state)
	 *	      |   || !Temperature.isValidTemperature(standardTemperature) )
	 *        |    then new.getStandardTemperature() == standardTemperature
	 *        | else
	 *        |    then new.getStandardTemperature() == {0,20}
	 */
	@Raw
	public IngredientType(String[] simpleNames, String specialName, State state, Temperature standardTemperature) {
		
		if ( !areValidSimpleNames(simpleNames)
		   ||!isValidSpecialName(specialName)
		   ||!State.isValidState(state)
		   ||!Temperature.isValidTemperature(standardTemperature) ) {
			this.simpleNames = new String[] {"Water"};
			this.specialName = null;
			this.state = State.LIQUID;
			this.standardTemperature = new Temperature(0L,20L);
		} 
		else {
			this.simpleNames = simpleNames;
			this.specialName = specialName;
			this.state = state;
			this.standardTemperature = standardTemperature;
		}
	}
	
	/**
	 * Initialize an ingredient type without a special name with given array of simple names, 
	 * state and temperature object.
	 * 
	 * @param  simpleNames
	 *         The array of simple names of the new ingredient type.
	 * @param  state
	 *         The state of the new ingredient type.
	 * @param  standardTemperature
	 *         The standard temperature object of the new ingredient type.
	 * @effect This new ingredient type is initialized with the given simple names, state and
	 *         temperature object, the new ingredient type has no special name.
	 *         | this(simpleNames,null,state,standardTemperature.getTemperature())
	 */
	@Raw
	public IngredientType(String[] simpleNames, State state, Temperature standardTemperature) {
		this(simpleNames, null, state, standardTemperature);
	}
	
	/**
	 * Initialize an ingredient type without a special name with a given single simple name, 
	 * state and temperature object.
	 * 
	 * @param  name
	 *         The (simple) name of the new ingredient type.
	 * @param  state
	 *         The state of the new ingredient type.
	 * @param  standardTemperature
	 *         The standard temperature object of the new ingredient type.
	 * @effect This new ingredient type is initialized with an array consisting a single element,
	 *         namely the given name, state and temperature object, the new ingredient type has no special name.
	 *         | this(new String[] {name},state,standardTemperature)
	 */
	@Raw
	public IngredientType(String name, State state, Temperature standardTemperature) {
		this(new String[] {name}, state, standardTemperature);
	}
	
	/**
	 * Initialize an ingredient type with no special name, state liquid and a given single name 
	 * and temperature object.
	 * 
	 * @param  name
	 *         The (simple) name of the new ingredient type.
	 * @param  standardTemperature
	 *         The standard temperature object of the new ingredient type.
	 * @effect This new ingredient type is initialized with the given name and standard temperature.
	 *         It has liquid as its state.
	 *         | this(name, State.LIQUID, standardTemperature)       
	 */
	@Raw
	public IngredientType(String name, Temperature standardTemperature) {
		this(name, State.LIQUID, standardTemperature);
	}
	
	/********************************************************
	 * NAME (defensief)
	 ********************************************************/
	
	/**
	 * Return the array with all the simple names of this Name.
	 */
	@Basic @Raw @Immutable
	public String[] getSimpleNames() {
		return simpleNames;
	}
	
	/**
	 * Check whether a given name is valid.
	 * 
	 * @param  name
	 *         The name to check.
	 * @return True if and only if the following conditions don't determine it to be otherwise.
	 *         If the name is not effective or empty, return false.
	 *         | if (name==null || name=="")
	 *         |   then result == false
	 *         If the name consists only of one word and that word has less than three characters, return false.
	 *         A new word starts when there is a space.
	 *         | words = name.split(" ")
	 *         | if (words.length<1 && name.length()<3)
	 *         |   then result == false
	 *         Else if the name consists of more than one word, each word must have more than two characters.
	 *         | for word in words
	 *         |   if (word.length()<2)
	 *         |      then result == false
	 *         Still, if the name consists of more than one word, each word must have a special character
	 *         (to date: (, ) or ') or an uppercase letter as its first letter. The other character must be 
	 *         special or lowercase.
	 *         | for word in words
	 *         |   if (!word.matches("^[" + specialCharacters + "A-Z][a-z]*$"))
	 *         |      then result == false
	 *         Again, if the name consists of more than one word, that word cannot contain "mixed", "with", "cooled"
	 *         or "heated" or these words with the first letter uppercase.
	 *         | for word in words
	 *         |   if (word contains "with" or "With"
	 *         |      || word contains "mixed" or "Mixed"
	 *         |      || word contains "cooled" or "Cooled"
	 *         |      || word contains "heated" or "Heated")
	 *         |     then result == false
	 * 
	 * @note   This checker is equivalent with canHaveAsSimpleName for the array of simple names.         
	 */
	@Raw
	public static boolean isValidSimpleName(String name){
		
		//Naam is niet null of leeg.
		if (name==null||name=="")
			return false;
		
		String[] choppedUpName = name.split(" ");
		
		//Naam van 1 woord is minstens 3 tekens lang.
		if ( choppedUpName.length<2
		   && name.length()<3 )
			return false;
		
		for (String word: choppedUpName) {
			
			//Geen with, mixed, cooled of heated in de naam (ook niet met speciale characters rond)
			if ((word.matches("^[" + specialCharacters + "]*[Ww]ith[" + specialCharacters + "]*$")
			  ||(word.matches("^[" + specialCharacters + "]*[Mm]ixed[" + specialCharacters + "]*$")))
			  ||(word.matches("^[" + specialCharacters + "]*[Cc]ooled[" + specialCharacters + "]*$")
			  ||(word.matches("^[" + specialCharacters + "]*[Hh]eated[" + specialCharacters + "]*$")))){
				return false;
			}
			
			//Elk woord is minstens 2 letters lang
			if (word.length()<2) {
				return false;
			}
			
			// Elk woord begint met een hoofdletter (hier kan een speciaal teken voor staan), 
			// de rest van de letters zijn klein of speciaal
			if (!word.matches("^[" + specialCharacters + "A-Z][a-z]*$")){
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Check whether a given array with names is a valid simpleNames array.
	 * 
	 * @param 	simpleNames
	 * 			The given array of names.
	 * @return	True if and only if the following conditions do not determine it to be otherwise.
	 *          If the given array is empty, return false.
	 *          | if (simpleNames.length<1) 
	 *          |   then result == false
	 *          Each name in simpleNames must be valid and not be equal to or alphabetically after the name
	 *          following this name in the array, thus ensuring the simpleNames array is alphabetically sorted
	 *          with no duplicates.
	 *          | for each I in 0..simpleNames.length-1:
	 *          |   if (! isValidSimpleName(simpleNames[I]))
	 *          |      then result == false
	 *          |   if (I != simpleNames.length-1
	 *          |      && simpleNames[I].compareTo(simpleNames[I+1]) < 1)
	 *          |      then result == false	
	 */
	@Raw
	public static boolean areValidSimpleNames(String[] simpleNames) {
		//At least one name is needed
		if (simpleNames.length<1) return false;
		
		for (int i = 0; i<simpleNames.length; i++) {
			//Each name is valid
			
			if (!isValidSimpleName(simpleNames[i])) 
				return false;
			//The array is alphabetically ordered with no duplicates.
			if (i!=simpleNames.length-1
			 && simpleNames[i].compareTo(simpleNames[i+1]) > 1)
				return false;
		}
		
		return true;	
	}	
	
	/**
	 * Return the simple name of this ingredient type.
	 * 
	 * @return	A string containing the simple name of this ingredient type, formatted according to the 
	 *          size of the simple names array.
	 * 		    If there's only one simple name, return that name.
	 * 			| if (getSimpleNames().length==1)
	 * 			|   then return getSimpleNames()[0]
	 *			If there are 2 simple names, return them in the format 'name1 mixed with name2'.
	 * 			| if (getSimpleNames().length==2)
	 * 			|   then return getSimpleNames()[0] + " mixed with " + getSimpleNames()[1]
	 *	        If there are more than 2 simple names, return them in the format 
	 * 			'firstName mixed with secondName, thirdName... and lastName'
	 * 			| if (getSimpleNames().length>=2)
	 * 			|   then return getSimpleNames()[0] + " mixed with " + 
	 * 			|          for each I in 1..(size-3)
	 * 			|              getSimpleNames()[I] + ", " +
	 * 			|          getSimpleNames()[size-2] + " and " + getSimpleNames()[size-1]
	 */
	@Basic @Raw
	public String getSimpleName() {
		
		int size = getSimpleNames().length;
		if (size==1)
			return getSimpleNames()[0];
		if (size==2) {
			return getSimpleNames()[0] + " mixed with " + getSimpleNames()[1];
		}
		else {
			String result = getSimpleNames()[0] + " mixed with " + getSimpleNames()[1];
			
			for (int i = 2; i <= (size-2); i++) {
				result.concat(", "+getSimpleNames()[i]);
			}
			result.concat(" and "+getSimpleNames()[size-1]);
			return result;
		}
	}
	
	/**
	 * An array of strings containing all the names of this type of ingredient.
	 */
	public final String[] simpleNames;

	
	/**
	 * A string containing the special characters that can be used in an ingredient's name.
	 */
	private final static String specialCharacters = "'()";
	
	/**
	 * Return the special name of this name object.
	 */
	@Basic
	public String getSpecialName() {
		return this.specialName;
		}
	
	/**
	 * Set the special name of this Name object to the given String.
	 * @param	specialName
	 * 			The string to change the specialName of this object to.
	 * @post	If the given specialName is a valid simple name, the specialName
	 * 			of this object is set to the given specialName.
	 * 			| if isValidSimpleName(specialName)
	 * 			|   then new.getName().equals(specialName)
	 * @throws	IllegalNameException
	 * 			If the given String is not a valid name, an IllegalNameException
	 * 			is thrown.
	 * 			| !isValidSimpleName(specialName)
	 */
	public void changeSpecialName(String specialName) throws IllegalNameException {
		if ((!isValidSimpleName(specialName))&&(specialName!=null))
			throw new IllegalNameException(specialName);
		this.specialName = specialName;
	}
	
	/**
	 * Check whether a given special name is valid.
	 * 
	 * @param  specialName
	 *         The name to check.
	 * @return True if and only if the name is a valid simple name or is not effective.
	 *         | result ==
	 *         |   (isValidSimpleName(specialName) || specialName==null)
	 */
	@Raw
	public static boolean isValidSpecialName(String specialName) {
		return (isValidSimpleName(specialName) || specialName==null);
	}
	
	
	/**
	 * A variable for a special name for an ingredient.
	 */
	private String specialName = null;	


	
	/************************************************************************
	 * STATE
	 ************************************************************************/
	
	/**
	 * A basic method returning the state of this ingredient type.
	 * 
	 * @returns	The state of this ingredient type.
	 */
	@Basic
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
	
	/**
	 * Returns the temperature object that is associated with the standard temperature of this ingredient type.
	 */
	public Temperature getStandardTemperatureObject() {
		return this.standardTemperature;
	}
	
	/**
	 * Check whether the given temperature array is valid for all ingredient types.
	 * 
	 * @param  temp
	 * 		   The temperature array to check.
	 * @return True if and only if the array is valid for all temperatures and if it is valid, the second element
	 *         is the hotness and this value must be higher than zero.
	 *         | result == (Temperature.isValidTemperature(temp)
	 *		   |           && temp[1]>0)
	 */
	public static boolean isValidStandardTemperature(long[] temp) {
		return (Temperature.isValidTemperature(temp)
			 && temp[1]>0);
	}
	
	
	/**
	 * A variable containing the Temperature object that is the standard temperature of this ingredient type.
	 */
	private final Temperature standardTemperature;
	
	/**
	 * Check whether the given ingredient type is equal to this ingredient type.
	 * 
	 * @param  type
	 * 		   The given ingredient type to check.
	 * @return True if and only if this ingredient type and the given ingredient type have the same simple names, special name,
	 * 		   state and standard temperature.
	 * 		   | result == (Arrays.equals(this.getSimpleNames(),type.getSimpleNames())  
	 *         |           && this.getSpecialName() == type.getSpecialName()
	 *         |           && this.getState() == type.getState() 
	 *         |           && Temperature.compareTemperature(this.getStandardTemperatureObject(), 
	 *         |					                         type.getStandardTemperatureObject())==0 )
	 */
	public boolean equals(IngredientType type) {
		return (Arrays.equals(this.getSimpleNames(),type.getSimpleNames()) 
				&& this.getSpecialName() == type.getSpecialName()
				&& this.getState() == type.getState() 
				&& Temperature.compareTemperature(this.getStandardTemperatureObject(), 
						                          type.getStandardTemperatureObject())==0);
	}

}
