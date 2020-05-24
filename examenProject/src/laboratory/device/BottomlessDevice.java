package laboratory.device;

import be.kuleuven.cs.som.annotate.*;
import java.util.ArrayList;
import java.util.List;

import alchemy.*;

/**
 * An abstract subclass of Device for devices that can hold a limitless
 * amount of ingredients.
 * 
 * @invar   The start ingredients of a bottomless device must be proper start ingredients
 * 			for any bottomless device.
 * 			| hasProperStartIngredients()
 * 
 * @version	1.0
 * @author  Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

public abstract class BottomlessDevice extends Device {
	
	/**
	 * Initialize a new bottomless device with a given array of ingredients containers.
	 * 
	 * @param  ingredientArray
	 *  	   The given array of ingredient containers to load in this device.
	 * @effect The containers in the given ingredient container array get loaded into this bottomless device.
	 * 		   | for(IngredientContainer container: ingredientArray)
	 * 		   |   loadIngredient(container)
	 */
	@Model @Raw
	public BottomlessDevice(IngredientContainer[] ingredientArray) {
		for (IngredientContainer container: ingredientArray) {
			loadIngredient(container);
		}
	}
	
	/**
	 * Initialize a new, empty bottomless device.
	 */
	@Model @Raw
	public BottomlessDevice() {}
	
	
	/**************************************************
	 * Start ingredients
	 **************************************************/
	
	
	/**
	 * Return the list containing the start ingredients.
	 */
	@Basic @Raw
	public List<AlchemicIngredient> getStartIngredients() {
		return this.startIngredients;
	}
	
	/**
	 * Return the amount of start ingredients loaded into this bottomless device.
	 */
	@Basic
	public int getNbStartIngredients() {
		return getStartIngredients().size();
	}
	
	/**
	 * Return the alchemic ingredient at the given index of the start ingredients list.
	 * 
	 * @param  index
	 * 		   The index of the wanted alchemic ingredients.
	 * @return The alchemic ingredient of the start ingredients list of this bottomless device at the given index.
	 *         | return getStartIngredients().get(index-1)
	 * @throws IndexOutOfBoundsException
	 * 		   The given index is lesser than or equal to zero or greater than the size of 
	 * 		   the start ingredients list.
	 *         | (index<=0 || index>getNbStartIngredients())
	 */
	public AlchemicIngredient getStartIngredientAt(int index) throws IndexOutOfBoundsException {
		return getStartIngredients().get(index-1);
	}
	
	/**
	 * Check whether the given ingredient is a valid ingredient for any bottomless device.
	 * 
	 * @param  ingredient
	 * 		   The ingredient to check.
	 * @return True if and only if the ingredient is effective.
	 * 		   | result == (ingredient!=null)
	 */
	public static boolean isValidStartIngredient(AlchemicIngredient ingredient) {
		return (ingredient!=null);
	}
	
	/**
	 * Check whether the given bottomless device has proper start ingredients associated with it.
	 * 
	 * @param  bottomlessDevice
	 * 		   The bottomless device to check.
	 * @return True if and only if the start ingredients list of this bottomless device is effective
	 * 		   and the ingredients in the list are valid for any bottomless device.
	 * 		   | result == 
	 *         |   bottomlessDevice.getProcesses()!=null
	 *  	   |   && for each ingredient in bottomlessDevice.getStartIngredients()
	 *         |         isValidStartIngredient(recipe.getProcessAt(i),i)
	 */
	public static boolean hasProperStartIngredients(BottomlessDevice bottomlessDevice) {
		for (AlchemicIngredient ingredient: bottomlessDevice.getStartIngredients()) {
			if (!BottomlessDevice.isValidStartIngredient(ingredient))
				return false;
		}
		return (bottomlessDevice.getStartIngredients()!=null);
	}
	
	
	/**
	 * Add a given ingredient to the list of start ingredients.
	 * 
	 * @post If the given ingredient is valid, the number of start ingredients associated with 
	 *       this bottomless device is incremented by one.
	 *       | if (isValidStartIngredient(ingredient))
	 * 		 |   then new.getNbStartIngredients() == getNbStartIngredients()+1
	 * @post If the given ingredient is valid, this bottomless device has the given ingredient at the end
	 * 		 of its start ingredients list.
	 *       | if (isValidStartIngredient(ingredient))
	 *       |   then new.getStartIngredientAt(getNbStartIngredients()+1) == ingredient
	 */
	private void addAsStartIngredient(AlchemicIngredient ingredient) {
		if (isValidStartIngredient(ingredient))
			startIngredients.add(ingredient);
	}
	
	/**
	 * Clear the startIngredients list.
	 * 
	 * @post The start ingredients list of this bottomless device is empty.
	 * 		 | new.getNbStartIngredients() == 0
	 * 
	 * @note There is no dire need for a method that removes one ingredient, as this is not how the device is
	 * 		 supposed to work, namely removing all start ingredients after they are processed.
	 */
	protected void clearStartIngredients() {
		getStartIngredients().clear();
	}

	/**
	 * A variable for the loaded ingredients in the device.
	 * 
	 * @invar The list of start ingredients is effective.
	 * 		  | startIngredients != null
	 * @invar Each ingredient in the start ingredients list is effective.
	 * 		  | for each ingredient in startIngredients:
	 * 		  |    ingredient != null
	 */
	private List<AlchemicIngredient> startIngredients = new ArrayList<AlchemicIngredient>();
	
	
	/**************************************************
	 * Processed ingredients
	 **************************************************/
	
	/**
	 * Return the list with the processedIngredients.
	 */
	@Basic
	protected List<AlchemicIngredient> getProcessedIngredients() {
		return this.processedIngredients;
	}
	
	
	/**
	 * A protected method for adding processed Ingredients to the processed ingredients list. 
	 * 
	 * @param ingredient
	 * 	      The given ingredient that needs to be added to the processed ingredients list.
	 */
	protected void addProcessedIngredient(AlchemicIngredient ingredient) {
		getProcessedIngredients().add(ingredient);
	}
	
	/**
	 * A variable for the processed ingredients still in the device
	 */
	private List<AlchemicIngredient> processedIngredients = new ArrayList<AlchemicIngredient>();

	
	/**************************************************
	 * Methods
	 **************************************************/
	
	/**
	 * Loads a new ingredient in a container into this device.
	 * 
	 * @param  container
	 * 		   The given container containing the ingredient that will be loaded into this device.
	 * @post   The container gets deleted.
	 * 		   | container = null
	 * @effect The contents of the given container are added as start ingredient.
	 * 		   | addAsStartIngredient(container.getContents())
	 */
	@Override @Raw
	public final void loadIngredient(IngredientContainer container) {
		addAsStartIngredient(container.getContents());
		container = null;
	}

	/**
	 * Empties the first ingredient from this device into a new container. 
	 * This container is the smallest container that can contain the ingredient.
	 * 
	 * @effect If the outputIngredient is null, null gets returned.
	 * 		   | if(outputIngredient == null)
	 * 		   |   return null
	 * @effect A new container is created containing the outputIngredient and the outputIngredient gets removed from the
	 * 		   processed ingredients list. This container is the smallest container that can contain the outputIngredient.
	 * 		   | outputContainer = new IngredientContainer(outputIngredient, 
	 * 		   |                   Unit.getContainer(outputIngredient.getState(), outputIngredient.getQuantity()))
	 * 		   | getProcessedIngredients().remove(0)
	 */
	@Override
	public final IngredientContainer emptyDevice() {
		AlchemicIngredient outputIngredient = getProcessedIngredients().get(0);
		if (outputIngredient == null)
			return null;
		IngredientContainer outputContainer = new IngredientContainer(outputIngredient,
				Unit.getContainer(outputIngredient.getState(), outputIngredient.getQuantity()));
		getProcessedIngredients().remove(0);
		return outputContainer;
	}
	
	/**
	 * This bottomless device processes the ingredients in the start ingredients list. 
	 * The processed ingredients gets added to the processed ingredients list.
	 */
	@Override
	public abstract void process();

}
