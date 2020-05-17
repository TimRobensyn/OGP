package laboratory;

import be.kuleuven.cs.som.*;
import alchemy.*;

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
	public final void loadIngredient(IngredientContainer container) throws DeviceFullException{
		if(startIngredient != null) {
			throw new DeviceFullException(this, container);
		}
		this.startIngredient = container.getIngredient();
		container = null;
	}

	@Override
	public final IngredientContainer emptyDevice() {
		if (processedIngredient == null)
			return null;
		IngredientContainer outputContainer = IngredientContainer(//TODO);
		return outputContainer;
	}
	
	/**
	 * Return the temperature of this device in an array of two long values
	 */
	public final long[] getTemperature() {
		return temperature.getTemperature();
	}

	/**
	 * Set the temperature of this device using an array of long values.
	 */
	public final void setTemperature(long[] temperature) {
		if (Temperature.isValidTemperature(temperature)) {
			Temperature newTemperature = new Temperature(temperature);
			setTemperature(newTemperature);
		}
	}
	
	/**
	 * Set the temperature of this device using a Temperature object.
	 */
	public final void setTemperature(Temperature newTemperature) {
		temperature = newTemperature;
	}
	
	/**
	 * A variable for the temperature of this temperature device.
	 */
	private Temperature temperature;
	
	
	@Override
	public abstract void process();

}

