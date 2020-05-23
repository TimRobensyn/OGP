package laboratory;

import be.kuleuven.cs.som.annotate.*;
import java.util.ArrayList;
import java.util.List;

import alchemy.*;

/**
 * An abstract subclass of Device for devices that can hold a limitless
 * amount of ingredients. //TODO Documentatie is nog niet okee
 * 
 * @version	1.0
 * @author  Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

public abstract class BottomlessDevice extends Device {
	
	/**
	 * Constructor enal //TODO
	 */
	@Model @Raw
	public BottomlessDevice(IngredientContainer[] ingredientArray) {
		for (IngredientContainer container : ingredientArray) {
			loadIngredient(container);
		}
	}
	
	/**
	 * Constructor enal //TODO
	 */
	@Model @Raw
	public BottomlessDevice() {}
	
	/**
	 * Return the list containing the start ingredients.
	 */
	public List<AlchemicIngredient> getStartIngredients() {
		return this.startIngredients;
	}
	
	/**
	 * Clear the startIngredients list.
	 */
	protected void clearStartIngredients() {
		getStartIngredients().clear();
	}

	/**
	 * A variable for the loaded ingredients in the device
	 */
	private List<AlchemicIngredient> startIngredients = new ArrayList<AlchemicIngredient>();
	
	/**
	 * Return the list with the processedIngredients.
	 */
	@Basic
	protected List<AlchemicIngredient> getProcessedIngredients() {
		return this.processedIngredients;
	}
	
	
	/**
	 * A protected method for adding processed Ingredients to the processed ingredients list. 
	 */
	protected void addProcessedIngredient(AlchemicIngredient ingredient) {
		getProcessedIngredients().add(ingredient);
	}
	
	/**
	 * A variable for the processed ingredients still in the device
	 */
	private List<AlchemicIngredient> processedIngredients = new ArrayList<AlchemicIngredient>();

	/**
	 * Loads a new ingredient into this device
	 */
	@Override @Raw
	public final void loadIngredient(IngredientContainer container) {
		this.getStartIngredients().add(container.getIngredient());
		container = null;
	}

	/**
	 * Empties the first ingredient from this device into a new container. 
	 * This container is the smallest container that can contain the ingredient.
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

	@Override
	public abstract void process();

}
