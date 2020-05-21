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

	POWDER, LIQUID;
	
	/*
	 * A static checker checking whether an object is a valid state.
	 */
	public static boolean isValidState(Object state) {
		for (State validState: State.values()) {
			if (state.equals(validState))
				return true;
		}
		return false;
	}
	
	/**
	 * Return the other state. //TODO Moet dit beter gedocumenteerd worden?
	 */
	public State otherState() {
		if (this==State.LIQUID)
			return State.POWDER;
		else 
			return State.LIQUID;
	}

	
}
