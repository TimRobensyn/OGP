package laboratory.device;

import be.kuleuven.cs.som.annotate.*;
import java.util.ArrayList;

import alchemy.*;

/**
 * An abstract subclass of Device for devices that can hold a limitless
 * amount of ingredients.
 * 
 * @invar   The start ingredients of a bottomless device must be proper start ingredients
 * 			for this bottomless device.
 * 			| hasProperStartIngredients()
 * @invar   The processed ingredients of a bottomless device must be proper processed ingredients
 * 			for this bottomless device.
 * 			| hasProperProcessedIngredients()
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
	protected BottomlessDevice(IngredientContainer[] ingredientArray) {
		for (IngredientContainer container: ingredientArray) {
			loadIngredient(container);
		}
	}
	
	/**
	 * Initialize a new, empty bottomless device.
	 */
	@Model @Raw
	protected BottomlessDevice() {}
	
	
	/**************************************************
	 * Start ingredients
	 **************************************************/
	
	
	/**
	 * Return the list containing the start ingredients.
	 */
	@Basic @Raw
	public ArrayList<AlchemicIngredient> getStartIngredients() {
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
	 * 		   The index of the wanted alchemic ingredient.
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
	 * Check whether the given ingredient is a valid start ingredient for any bottomless device.
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
	 * Check whether this bottomless device has proper start ingredients associated with it.
	 * 
	 * @return True if and only if the start ingredients list of this bottomless device is effective
	 * 		   and the ingredients in the list are valid for any bottomless device.
	 * 		   | result == 
	 *         |   getStartIngredients()!=null
	 *  	   |   && for each ingredient in getStartIngredients()
	 *         |         isValidStartIngredient(ingredient)
	 */
	public boolean hasProperStartIngredients() {
		for (AlchemicIngredient ingredient: getStartIngredients()) {
			if (!BottomlessDevice.isValidStartIngredient(ingredient))
				return false;
		}
		return (getStartIngredients()!=null);
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
	@Raw
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
	private ArrayList<AlchemicIngredient> startIngredients = new ArrayList<AlchemicIngredient>();
	
	
	/**************************************************
	 * Processed ingredients
	 **************************************************/
	
	/**
	 * Return the list with the processedIngredients.
	 */
	@Basic
	protected ArrayList<AlchemicIngredient> getProcessedIngredients() {
		return this.processedIngredients;
	}
	
	/**
	 * Return the amount of processed ingredients in this bottomless device.
	 */
	@Basic
	public int getNbProcessedIngredients() {
		return getProcessedIngredients().size();
	}
	
	/**
	 * Return the alchemic ingredient at the given index of the processed ingredients list.
	 * 
	 * @param  index
	 * 		   The index of the wanted alchemic ingredient.
	 * @return The alchemic ingredient of the processed ingredients list of this bottomless device at the given index.
	 *         | return getStartIngredients().get(index-1)
	 * @throws IndexOutOfBoundsException
	 * 		   The given index is lesser than or equal to zero or greater than the size of 
	 * 		   the processed ingredients list.
	 *         | (index<=0 || index>getNbStartIngredients())
	 */
	public AlchemicIngredient getProcessedIngredientAt(int index) throws IndexOutOfBoundsException {
		return getProcessedIngredients().get(index-1);
	}
	
	/**
	 * Check whether the given ingredient is a valid processed ingredient for any bottomless device.
	 * 
	 * @param  ingredient
	 * 		   The ingredient to check.
	 * @return True if and only if the ingredient is effective.
	 * 		   | result == (ingredient!=null)
	 */
	public static boolean isValidProcessedIngredient(AlchemicIngredient ingredient) {
		return (ingredient!=null);
	}
	
	/**
	 * Check whether this bottomless device has proper processed ingredients associated with it.
	 * 
	 * @return True if and only if the processed ingredients list of this bottomless device is effective
	 * 		   and the ingredients in the list are valid for any bottomless device.
	 * 		   | result == 
	 *         |   getProcessedIngredients()!=null
	 *  	   |   && for each ingredient in getProcessedIngredients()
	 *         |         isValidProcessedIngredient(ingredient)
	 */
	public boolean hasProperProcessedIngredients() {
		for (AlchemicIngredient ingredient: getProcessedIngredients()) {
			if (!BottomlessDevice.isValidProcessedIngredient(ingredient))
				return false;
		}
		return (getProcessedIngredients()!=null);
	}
	
	
	/**
	 * A protected method for adding processed Ingredients to the processed ingredients list. 
	 * 
	 * @param ingredient
	 * 	      The given ingredient that needs to be added to the processed ingredients list.
	 * @post  If the given ingredient is valid, the number of processed ingredients associated with 
	 *        this bottomless device is incremented by one.
	 *        | if (isValidProcessedIngredient(ingredient))
	 * 		  |   then new.getNbProcessedIngredients() == getNbProcessedIngredients()+1
	 * @post  If the given ingredient is valid, this bottomless device has the given ingredient at the end
	 * 		  of its processed ingredients list.
	 *        | if (isValidProcessedIngredient(ingredient))
	 *        |   then new.getProcessedIngredientAt(getNbProcessedIngredients()+1) == ingredient
	 */
	protected void addAsProcessedIngredient(AlchemicIngredient ingredient) {
		if (isValidProcessedIngredient(ingredient))
			getProcessedIngredients().add(ingredient);
	}
	
	/**
	 * Remove the processed ingredient for this bottomless device at the given index.
	 * 
	 * @param index
	 *        The index of the processed ingredient to be removed.
	 * @post  If the given index is one, this bottomless device no longer has the processed ingredient 
	 *        at the given index as one of its processed ingredients.
	 *        | if (index==1)
	 *        |   then !new.getProcessedIngredientAt(index)==getProcessedIngredient(index)
	 * @post  If the given index is one, the number of processed ingredients associated with this bottomless device 
	 * 		  is decremented by 1.
	 * 		  | if (index==1)
	 *        |   then new.getNbProcessedIngredients() == this.getNbProcessedIngredients()-1
	 * @post  If the given index is one, all processed ingredients associated with this bottomless device at 
	 *        an index exceeding the given index, are registered as processed ingredient at one index lower.
	 *        | if (index==1)
	 *        |   then for each I in index+1..getNbProcessedIngredients()
	 *        |          (new.getProcessedIngredientAt(I-1) == this.getProcessedIngredientAt(I))
	 */
	private void removeProcessedIngredientAt(int index) {
		if (index==1)
			processedIngredients.remove(index-1);
	}
	
	/**
	 * A variable for the processed ingredients still in the device
	 */
	private ArrayList<AlchemicIngredient> processedIngredients = new ArrayList<AlchemicIngredient>();

	
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
	public void loadIngredient(IngredientContainer container) {
		addAsStartIngredient(container.getContents());
		container = null;
	}

	/**
	 * Empties the first ingredient from this device into a new container. 
	 * This container is the smallest container that can contain the ingredient.
	 *
	 * @effect A new container is created containing the outputIngredient and the outputIngredient gets removed from the
	 * 		   processed ingredients list. This container is the smallest container that can contain the outputIngredient.
	 * 		   | outputContainer = new IngredientContainer(outputIngredient, 
	 * 		   |                   Unit.getContainer(outputIngredient.getState(), outputIngredient.getQuantity()))
	 * 		   | removeProcessedIngredientAt(1);
	 */
	@Override
	public IngredientContainer emptyDevice() {
		AlchemicIngredient outputIngredient = getProcessedIngredientAt(1);
		IngredientContainer outputContainer = new IngredientContainer(outputIngredient,
				Unit.getContainer(outputIngredient.getState(), outputIngredient.getQuantity()));
		removeProcessedIngredientAt(1);
		return outputContainer;
	}
	
	/**
	 * This bottomless device processes the ingredients in the start ingredients list. 
	 * The processed ingredients gets added to the processed ingredients list.
	 */
	@Override
	public abstract void process();

}
