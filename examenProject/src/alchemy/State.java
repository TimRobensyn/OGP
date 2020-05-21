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
	 * If there are only two states, return the other state.
	 * 
	 * @return If the state enumeration class only distinguishes between two states, return the
	 * 		   other state. Otherwise, it returns itself.
	 * 		   | if (State.values().length == 2)
	 * 		   |   then if (this==State.values()[0])
	 * 		   |           then result == State.values()[1]
	 * 		   |        else
	 *         |           then result == State.values()[0]
	 *         | else
	 *         |   then result == this  		
	 */
	public State otherState() {
		if (State.values().length == 2) {
			if (this==State.values()[0])
				return State.values()[1];
			else 
				return State.values()[0];
		}
		else return this;
		
	}

	
}
