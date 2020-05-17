package alchemy;

import java.util.ArrayList;
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
		addSimpleName(simpleName);
	}
	
	
	/**
	 * Return the name of this ingredient's type in the form of a String.
	 * 
	 * @return	A string containing all the simple names of this name object.
	 * @throws NullPointerException
	 */
	@Raw
	public String getSimpleName() throws NullPointerException{
		int size = getSimpleNames().size();
		if (size==0) throw new NullPointerException();
		if (size==1)
			return getSimpleNames().get(0);
		else {
			String result = getSimpleNames().get(0) + " mixed with";
			for (int i = 1; i < (size-1); i++) {
				result.concat(" "+getSimpleNames().get(i)+",");
			}
			result.concat(" and"+getSimpleNames().get(size-1));
			return result;
		}
	}
	
	/**
	 * Return the array with all the simple names of this Name.
	 */
	@Basic @Raw
	public ArrayList<String> getSimpleNames() {
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
			
			//Geen with of mixed in de naam
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
	 * Add a simple name of this ingredient type to the given name.
	 * 
	 * @param	newName
	 * 			The new simple name for this ingredient type.
	 * @post	If the given simple name is not empty and valid, and it has not been added yet,
	 * 			the simple name of this ingredient type is equal to the given name.
	 *         | if (( !newName==null
	 *         |    && isValidSimpleName(newName) )
	 *         |	&& !getSimpleNames().contains(newName))
	 *         |  then new.getName().equals(newName)
	 * @throws	NullPointerException
	 * 			The given name is empty.
	 *         | newName==null
	 * @throws	IllegalNameException
	 * 			The given name is not valid.
	 *         | !isValidSimpleName(newName)
	 */
	public void addSimpleName(String newName) 
			throws NullPointerException, IllegalNameException {
		
		if (newName == null) throw new NullPointerException();
		
		if (!isValidSimpleName(newName)) throw new IllegalNameException(newName);
		
		if (!getSimpleNames().contains(newName)) simpleNames.add(newName);
	}
	
	/**
	 * An arraylist of strings containing all the names of this type of ingredient.
	 */
	public ArrayList<String> simpleNames;

	
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
	public void setSpecialName(String specialName) throws IllegalNameException {
		if (!Name.isValidSimpleName(specialName))
			throw new IllegalNameException(specialName);
		this.specialName = specialName;
	}
	
	/**
	 * A variable for a special name for an ingredient.
	 */
	private String specialName = null;

}
