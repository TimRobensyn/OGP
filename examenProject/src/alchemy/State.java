package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * An enumeration class containing the possible states of an ingredient.
 * In its current definition, the class only distinguishes between either powder or liquid.
 * With each state a name is associated.
 * 
 * @version	1.0
 * @author 	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

@Value
public enum State {
	
	// POWDER("Powder"), LIQUID("Liquid");
	
	/**
	 * Initialize a new state with given name.
	 * 
	 * @param name
	 *        The name of the new state.
	 * @pre	  The provided name is valid.
	 * 		  | isValidStateName(name)
	 * @post  The name of this new state is set to the given name.
	 *        | new.getStateName().equals(name)
	 */
	// @Raw
	// private State(String name) {
	//	 this.name = name;
	// }
	
	/**
	 * Return the name of this state.
	 */
	// @Raw @Basic @Immutable
	// public String getStateName(){
	//    return this.name;
	// }
	
	// private final String name;
	
	POWDER, LIQUID;
	
	/*
	 * A static checker checking whether an object is a valid state.
	 */
	public static boolean isValidState(Object state) {
		for (State validState : State.values()) {
			if (state.equals(validState))
				return true;
		}
		return false;
	}
	
	
	
}
