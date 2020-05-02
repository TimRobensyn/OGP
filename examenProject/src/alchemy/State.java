package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * An enumeration class containing the possible states of an ingredient, either powder or liquid.
 * 
 * @version	1.0
 * @author 	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

public enum State {
	
	POWDER, LIQUID;
	
	/*
	 * A static checker checking whether an object is a valid state.s
	 */
	public static boolean isValidState(Object state) {
		for (State validState : State.values()) {
			if (state.equals(validState))
				return true;
		}
		return false;
	}
}
