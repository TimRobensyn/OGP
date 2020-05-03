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
	
	/*
	 * Return the name of this ingredient's type in the form of a String.
	 */
	@Basic @Raw
	public String getSimpleName() {
		return this.simpleName;
	}
	
	/*
	 * Check whether a string is a valid name.
	 */
	public static boolean isValidSimpleName(String name){
		boolean valid = true;
		String[] choppedUpName = name.split(" ");
		
		if ( (choppedUpName.length<2) 
		     && (choppedUpName[0].length()<3) )
			valid = false;
		
		for (String word: choppedUpName) {
			if ( word.length()<2 ) {
				valid = false;
				break;
			}
			
			
		}
		
		return valid;
	}
	
	/*
	 * A basic method for setting the simple name of this ingredient.
	 */
	private void setSimpleName(String newName) {
		if (isValidSimpleName(newName))
			this.simpleName = newName;
	}
	
	/*
	 * A string containing the name of this type of ingredient
	 */
	public String simpleName;
	
	/*
	 * A string containing the special characters that can be used in an ingredient's name.
	 */
	private final static String specialCharacters = "'()";
	
	/************************************************************************
	 * STATE
	 ************************************************************************/
	
	/*
	 * A basic method returning the state of this ingredient type.
	 * 
	 * @returns	The state of this ingredient type.
	 */
	@Basic @Raw
	public State getState() {
		return this.state;
	}
	
	/*
	 * A private method setting the state of this ingredient type.
	 */
	@Basic
	private void setState(State newState) {
		if (State.isValidState(newState))
			this.state = newState;
	}
	/*
	 * A variable referencing the state of this ingredient type.
	 */
	private State state;
	
	/************************************************************************
	 * STANDARDTEMPERATURE
	 ************************************************************************/
	
	/*
	 * A basic method for getting the standardTemperature
	 */
	public long[] getStandardTemperature() {
		return this.standardTemperature;
	}
	
	/*
	 * A private method for setting the standardTemperature
	 */
	private void setStandardTemperature(long[] temperature) {
		//TODO
	}
	/*
	 * A variable indicating coolness (I have very high values here myself)
	 */
	private long coolness;
	/*
	 * A variable indicating hotness (Unexpectedly, high values here too!)
	 */
	private long hotness;
	/*
	 * An array containing coolness and hotness, thus the standardtemperature of this ingredient type
	 */
	private long[] standardTemperature = {coolness, hotness};

}
