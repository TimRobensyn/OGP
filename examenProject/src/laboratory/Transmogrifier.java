package laboratory;

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
	 * 			The array of ingredients to be loaded in the device.
	 * @effect	The array of ingredients is loaded in the new device
	 * 			| for each ingredient in ingredientArray
	 * 			|   (new.getStartIngredients().contains(ingredient))
	 */
	@Raw
	public Transmogrifier(IngredientContainer[] ingredientArray) {
		super(ingredientArray);
	}

	/**
	 * Initialize a Transmogrifier device without start ingredients.
	 * 
	 * @effect	The start ingredients arrayList is empty.
	 * 			| (new.getStartIngredients().isEmpty())
	 */
	@Raw
	public Transmogrifier() {
		super();
	}

	/**
	 * Change the state of all the start ingredients and put them in the processed ingredients arrayList.
	 * 
	 * @effect	The start ingredients arrayList is emptied.
	 * 			| (new.getStartIngredients().isEmpty())
	 * 			The processed ingredients arrayList is filled with the ingredients that were in the start
	 * 			ingredients arrayList but with the other state.
	 * 			| (for each ingredient in new.getProcessedIngredients()
	 * 			|	ingredient screw it //TODO help tis tlaatste
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
			addProcessedIngredient(newIngredient);
		}
		clearStartIngredients();
	}

}
