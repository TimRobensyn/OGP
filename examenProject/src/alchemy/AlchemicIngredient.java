package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of alchemic ingredients involving a name, temperature, type, quantity and a liquid or powder state.
 * 
 * @version  1.0
 * @author   Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

public class AlchemicIngredient {
	
	/**************************************************
	 * CONSTRUCTORS
	 **************************************************/
	public AlchemicIngredient(IngredientType type, int quantity, long temperature) {
		//TODO
	}
	public AlchemicIngredient(String name, State state, long standardTemperature, int quantity, long temperature) {
		this(new IngredientType(name, state, standardTemperature), quantity, temperature);
		//TODO
	}
}
