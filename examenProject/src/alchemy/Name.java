package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class for the name of an ingredient type.
 * 
 * @version	1.0		
 * @author	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

public class Name {
	
	/********************************************************************
	 * CONSTRUCTOR
	 ********************************************************************/
	
	/**
	 * 
	 */
	public Name(String[] simpleName, String specialName) {
		
	}
	
	/**
	 * 
	 */
	public Name(String simpleName) {
		setSimpleName(simpleName);
	}
	
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
	
	public static boolean isValidName(Name name) {
		return true; //TODO
	}
	
	/**
	 * A string containing the special characters that can be used in an ingredient's name.
	 */
	private final static String specialCharacters = "'()";

}
