package laboratory.device;

import alchemy.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class for Transmogrifier devices which change the state of the ingredients
 * involving start ingredient list and a processed ingredients list.
 * We assume there are only two states.
 * 
 * @version	1.0
 * @author	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

public class Transmogrifier extends BottomlessDevice {

	/**
	 * Initialize a Transmogrifier device with a start ingredient array.
	 * 
	 * @param	ingredientArray
	 * 			The array of ingredient containers to be loaded in the device.
	 * @effect	The new transmogrifier is initialized as a bottomless device with the given array of ingredient containers.
	 * 			| super(ingredientArray)
	 */
	@Raw
	public Transmogrifier(IngredientContainer[] ingredientArray) {
		super(ingredientArray);
	}

	/**
	 * Initialize a Transmogrifier device without start ingredients.
	 * 
	 * @effect	This new transmogrifier is initialized as an empty bottomless device.
	 *          | super()
	 */
	@Raw
	public Transmogrifier() {
		super();
	}

	/**
	 * Assuming there are only two states, change the state of all the start ingredients and put them in the processed ingredients arrayList.
     *
	 * @post   A new ingredient type is created with the same simple names, the new state and the same standard temperature.
	 *         A new alchemic ingredient is created with the new ingredient type and a new quantity. The new quantity is the
	 *         quantity of the ingredient converted to the other state rounded down to an integer.
	 *         | newType = new IngredientType(ingredient.getType().getSimpleNames(), newState, ingredient.getStandardTemperatureObject())
	 *         | newQuantity = Math.floor(ingredient.getQuantity()*Unit.getRatio(newState, oldState))
	 *         | newIngredient = new AlchemicIngredient(newType, newQuantity)
	 * @effect The new ingredient gets added to the processed ingredients list and the start ingredients are deleted.
	 *         | addProcessedIngredient(newIngredient)
	 *         | clearStartIngredients()
	 */
	@Override
	public void process() {
		for (AlchemicIngredient ingredient:getStartIngredients()) {
			State oldState = ingredient.getState();
			State newState = ingredient.getState().otherState();
			IngredientType newType = new IngredientType(ingredient.getType().getSimpleNames(),
					newState,ingredient.getStandardTemperatureObject());
			int newQuantity = (int) Math.floor(ingredient.getQuantity()*Unit.getRatio(newState,oldState));
			AlchemicIngredient newIngredient = new AlchemicIngredient(newType,newQuantity);
			addAsProcessedIngredient(newIngredient);
		}
		clearStartIngredients();
	}

}
