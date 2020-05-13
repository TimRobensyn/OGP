package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of ingredient containers involving a capacity type and contents in spoons.
 * 
 * @version	1.0
 * @author	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 *
 */

public class IngredientContainer {
	
	public IngredientContainer(Quantity containerType, int contents) {
		this.contents = contents;
		this.containerType = containerType;
	}
	
	private int contents = 0;
	private Quantity containerType;
}
