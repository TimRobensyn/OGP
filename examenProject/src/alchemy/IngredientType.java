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
	 * CONSTRUCTOR(EN) (totaal)
	 ********************************************************/
	
	/**
	 * Initialize an ingredient type object with simpleNames, a special name, a state and a standard temperature.
	 * 
	 * @param	simpleNames
	 * 			The array of strings that contains
	 * @param specialName
	 * @param state
	 * @param standardTemperature
	 */
	public IngredientType(String[] simpleNames, String specialName, State state, long[] standardTemperature) {
		
		if (((!areValidSimpleNames(simpleNames))
				||!isValidSimpleName(specialName)
				||!State.isValidState(state))
				||!Temperature.isValidTemperature(standardTemperature)) {
			this.simpleNames = new String[] {"Water"};
			this.specialName = null;
			this.state = State.LIQUID;
			this.standardTemperature = new Temperature(0L,20L);
		} 
		else {
			this.simpleNames = simpleNames;
			this.specialName = specialName;
			this.state = state;
			this.standardTemperature = new Temperature(standardTemperature);
		}

	}
	
	/**
	 * Initialize an ingredient type object without a special name with simple names, a state and a Temperature object.
	 * @param simpleNames
	 * @param state
	 * @param standardTemperature
	 */
	public IngredientType(String[] simpleNames, State state, Temperature standardTemperature) {
		this(simpleNames, null, state, standardTemperature.getTemperature());
	}
	
	
	public IngredientType(String name, State state, Temperature standardTemperature) {
		this(new String[] {name}, null, state, standardTemperature.getTemperature());
	}
	
	/**
	 * 
	 * @param name
	 * @param standardTemperature
	 */
	public IngredientType(String name, Temperature standardTemperature) {
		this(new String[] {name}, State.LIQUID, standardTemperature);
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
	 * Check whether a string is a valid name.
	 * 
	 *  //TODO documentatie
	 */
	public static boolean isValidSimpleName(String name){
		
		boolean valid = true;
		
		//Naam is niet null of leeg.
		if (name==null||name=="")
			valid = false;
		
		String[] choppedUpName = name.split(" ");
		
		//Naam van 1 woord is minstens 3 tekens lang.
		if ( choppedUpName.length<2
		   && choppedUpName[0].length()<3 )
			valid = false;
		
		for (String word : choppedUpName) {
			
			//Geen with, mixed, cooled of heated in de naam (ook niet met speciale characters rond)
			if ((word.matches("^[" + specialCharacters + "]?[Ww]ith[" + specialCharacters + "]?$")
					||(word.matches("^[" + specialCharacters + "]?[Mm]ixed[" + specialCharacters + "]?$")))
					||(word.matches("^[" + specialCharacters + "]?[Cc]ooled[" + specialCharacters + "]?$")
					||(word.matches("^[" + specialCharacters + "]?[Hh]eated[" + specialCharacters + "]?$")))){
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
	 * Check an array of strings to see if it is a valid array of simpleNames
	 * @param 	simpleNames
	 * 			The given array of Strings.
	 * @return	true if the array has at least one valid name and no duplicate strings
	 * 			| (simpleNames.length>0) && (isValidSimpleName(simpleNames[i]) for all i
	 * 			| && for all i and k: (i!=k) ==> (simpleNames[i] != simpleNames[k]  //TODO is dit brak?
	 */
	public static boolean areValidSimpleNames(String[] simpleNames) {
		//At least one name is needed
		if (simpleNames.length<1) return false;
		
		for (int i = 0; i<simpleNames.length; i++) {
			//Each name is valid
			if (!isValidSimpleName(simpleNames[i])) return false;
			for (int k = i+1; k<simpleNames.length; k++) {
				//No duplicates
				if ((i!=k)&&(simpleNames[i]==simpleNames[k])) return false;
			}
		}
		return true;
	}
	
	
//	/**
//	 * Add a simple name of this ingredient type to the given name.
//	 * 
//	 * @param	newName
//	 * 			The new simple name for this ingredient type.
//	 * @post	If the given simple name is not empty and valid, and it has not been added yet,
//	 * 			the simple name of this ingredient type is equal to the given name.
//	 *         | if (( !newName==null
//	 *         |    && isValidSimpleName(newName) )
//	 *         |	&& !getSimpleNames().contains(newName))
//	 *         |  then new.getName().equals(newName)
//	 * @throws	NullPointerException
//	 * 			The given name is empty.
//	 *         | newName==null
//	 * @throws	IllegalNameException
//	 * 			The given name is not valid.
//	 *         | !isValidSimpleName(newName)
//	 */
//	public void addSimpleName(String newName) 
//			throws NullPointerException, IllegalNameException {
//		
//		if (newName == null) throw new NullPointerException();
//		
//		if (!isValidSimpleName(newName)) throw new IllegalNameException(newName);
//		
//		if (!getSimpleNames().contains(newName)) simpleNames.add(newName);
//	}
	
	
	/**
	 * Return the name of this ingredient's type in the form of a formatted String.
	 * 
	 * @return	A string containing all the simple names of this name object formatted according to size
	 * @return	If there's only one simple name, return that name.
	 * 			| if (getSimpleNames().length==1)
	 * 			|   then return getSimpleNames()[0]
	 * @return	If there are 2 simple names, return them in the format 'name1 mixed with name2'
	 * 			| if (getSimpleNames().length==2)
	 * 			|   then return getSimpleNames()[0] + " mixed with " + getSimpleNames()[1]
	 * @return	If there are more than 2 simple names, return them in the format 
	 * 			'firstName mixed with secondName, thirdName... and lastName'
	 * 			| if (getSimpleNames().length>=2)
	 * 			|   then return getSimpleNames()[0] + " mixed with " + getSimpleNames()[1]
	 */
	@Raw
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
	 * @return specialName
	 */
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
	public void ChangeSpecialName(String specialName) throws IllegalNameException {
		if ((!isValidSimpleName(specialName))&&(specialName!=null))
			throw new IllegalNameException(specialName);
		this.specialName = specialName;
	}
	
	/**
	 * A variable for a special name for an ingredient.
	 */
	private String specialName;	


	
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
	
	/**
	 * Returns the temperature object that is associated with the standard temperature of this ingredient type.
	 */
	public Temperature getStandardTemperatureObject() {
		return this.standardTemperature;
	}
	
	
	/**
	 * A variable containing the Temperature object that is the standard temperature of this ingredient type.
	 */
	private final Temperature standardTemperature;

}
