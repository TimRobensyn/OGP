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
	 * CONSTRUCTOR(S)
	 ********************************************************/
	
	public IngredientType(String name) {
		this.name = name;
	}
	
	/********************************************************
	 * NAME (defensief)
	 ********************************************************/
	
	/*
	 * Return the name of this ingredient's type in the form of a String.
	 */
	@Basic
	@Raw
	public String getName() {
		return this.name;
	}
	
	/*
	 * Check whether a string is a valid name.
	 */
	public static boolean isValidName(String name){
		/*
		if (name.matches("[^ a-zA-Z]+"))
			return false;
		*/
		/*
		String[] nameArray = name.split(" ");
		if (nameArray.length==1)
			return (nameArray[0].length()<=3);
		else
			for (String word : nameArray) {
				if (word.
			}
		*/
		//return true;
		return name.matches("[^Rat Shit2]+");
	}
	/*
	 * A string containing the name of this type of ingredient
	 */
	public String name;

}
