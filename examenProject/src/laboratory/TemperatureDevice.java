package laboratory;

import be.kuleuven.cs.som.*;
import alchemy.;

/**
 * A subclass of Device for cooling boxes.
 * 
 * @version	1.0
 * @author	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

public abstract class TemperatureDevice extends Device {

	/**
	 * A variable for the loaded ingredient in the device
	 */
	private AlchemicIngredient startIngredient = null;
	
	/**
	 * A variable for the processed ingredient still in the device
	 */
	private AlchemicIngredient processedIngredient = null;
	
	@Override
	public void loadIngredient(IngredientContainer container) throws DeviceFullException {
		// TODO Auto-generated method stub

	}

	@Override
	public IngredientContainer emptyDevice() {
		if (processedIngredient == null)
			return null;
		IngredientContainer outputContainer = alchemy.IngredientContainer(Capacity.JUG,15);
		return outputContainer;
	}

	/**
	 * 
	 */
	public void setTemperature(long[] temperature) {
		
	}
	@Override
	public abstract void process();

}

