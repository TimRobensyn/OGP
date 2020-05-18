package laboratory;

import be.kuleuven.cs.som.annotate.*;
import java.util.ArrayList;

import alchemy.*;

/**
 * An abstract subclass of Device for devices that can hold a limitless
 * amount of ingredients.
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
	 * @return	startIngredients
	 */
	public ArrayList<AlchemicIngredient> getStartIngredients() {
		return startIngredients;
	}
	
	/**
	 * Clear the startIngredients arrayList.
	 */
	protected void clearStartIngredients() {
		startIngredients = new ArrayList<AlchemicIngredient>();
	}

	/**
	 * A variable for the loaded ingredients in the device
	 */
	private ArrayList<AlchemicIngredient> startIngredients = new ArrayList<AlchemicIngredient>();
	
	/**
	 * A variable for the processed ingredients still in the device
	 */
	private ArrayList<AlchemicIngredient> processedIngredients = new ArrayList<AlchemicIngredient>();

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
		if (processedIngredients.get(0) == null)
			return null;
		Container containerType=null;
		if(processedIngredients.get(0).getType().getState() == State.LIQUID) {
			containerType = LiquidQuantity.getContainer(processedIngredients.get(0).getQuantity());
		}
		else if (processedIngredients.get(0).getType().getState() == State.POWDER) {
			containerType = PowderQuantity.getContainer(processedIngredients.get(0).getQuantity());
		}
		
		IngredientContainer outputContainer = new IngredientContainer(processedIngredients.get(0), containerType);
		processedIngredients.remove(0);
		return outputContainer;
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub

	}

}
