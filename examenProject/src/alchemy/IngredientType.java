package alchemy;

import be.kuleuven.cs.som.annotate.*;
import java.util.regex.*;
import java.lang.*;

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
	 * Return the name of this ingredient type.
	 * 
	 * @return  The name of this ingredient type.
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
		int numberOfWords = choppedUpName.length;
		
		for (String word: choppedUpName) {
			// Check of het woord lang genoeg is naargelang het aantal woorden in de naam string.
			if ( (numberOfWords<=1 && word.length()<=3) 
				 || (numberOfWords>1 && word.length()<=2) ) {
				valid = false;
				break;
			}
				
			String firstChar = word.substring(0,1);
			String rest = word.substring(1);
			
			// Check of de eerste letter een hoofdletter of een speciaal teken is, tenzij 
			// het woord with of mixed is.
			if ( !( firstChar.matches("[A-Z"+specialCharacters+"]+")
				  || word.equals("with") || word.equals("mixed")) ) {
				valid = false;
				break;
			}
			
			// Check of het woord - op de eerste letter na - bestaat uit allemaal kleine letters
			// of een speciaal teken.
			if (! rest.matches("[a-z"+specialCharacters+"]+")) {
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
	
	// OPMERKING, moet kunnen veranderd worden in het labo.
	
	/**
	 * A basic method returning the state of this ingredient type.
	 * 
	 * @return The state of this ingredient type.
	 */
	@Basic @Raw
	public State getState() {
		return this.state;
	}
	
	/**
	 * A private method setting the state of this ingredient type.
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
	 * STANDARDTEMPERATURE
	 ************************************************************************/
	
	/**
	 * A basic method for getting the standardTemperature
	 */
	@Basic
	public long[] getStandardTemperature() {
		return this.standardTemperature;
	}
	
	/**
	 * Set the standard temperature of this ingredient type to the given temperature.
	 * 
	 * @param temperature
	 *        The new standard temperature
	 */
	private void setStandardTemperature(long[] temperature) {
		//TODO
	}
	
	/**
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
