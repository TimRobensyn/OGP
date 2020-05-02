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
	 * CONSTRUCTOR(S)
	 ********************************************************/
	
	public IngredientType(String name) {
		this.simpleName = name;
	}
	
	/********************************************************
	 * NAME (defensief)
	 ********************************************************/
	
	/*
	 * Return the name of this ingredient's type in the form of a String.
	 */
	@Basic
	@Raw
	public String getSimpleName() {
		return this.simpleName;
	}
	
	/*
	 * Check whether a string is a valid name.
	 */
	public static boolean isValidSimpleName(String name){
		boolean valid = true;
		String[] choppedUpName = name.split(" ");
		if ((choppedUpName.length<2)&&(choppedUpName[0].length()<3))
			valid = false;
		for (String word : choppedUpName) {
			if (word.matches("^[" + specialCharacters + "]?[Ww]ith$")||(word.matches("^[" + specialCharacters + "]?[Mm]ixed$"))) {
				valid = false;
				break;
			}
			if (word.length()<2) {
				valid = false;
				break;
			}
			if (!word.matches("^[" + specialCharacters + "]?[A-Z][a-z'()]*$")){
				valid = false;
				break;
			}
		}
		return valid;
	}
	/*
	 * A basic method for setting the simple name of this ingredient.
	 */
	/*
	 * A string containing the name of this type of ingredient
	 */
	public String simpleName;
	
	/*
	 * A string containing the special characters that can be used in an ingredient's name.
	 */
	private final static String specialCharacters = "'()";

}
