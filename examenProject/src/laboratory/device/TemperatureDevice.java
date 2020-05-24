package laboratory.device;

import be.kuleuven.cs.som.annotate.*;
import laboratory.CapacityException;
import temperature.Temperature;
import alchemy.*;

/**
 * A subclass of Device for devices with a temperature and limited capacity.
 * 
 * @invar	The temperature of this temperature device is a valid temperature.
 * 			| Temperature.isValidTemperature(getTemperature())
 * 
 * @version	1.0
 * @author	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 * 
 * @note    The start ingredient and processed ingredient have no restrictions imposed on them.
 */

public abstract class TemperatureDevice extends Device {
	
	/**
	 * @param  temperature
	 * 	       The temperature of this new temperature device.
	 * @param  startIngredient
	 * 		   The new alchemic ingredient to be used in this temperature device.
	 * @effect The temperature of this temperature device is set to the given value.
	 * 		   | setTemperature(temperature)
	 * @effect The given start ingredient is loaded into the temperature device.
	 * 		   | loadIngredient(startIngredient)
	 */
	@Model @Raw
	protected TemperatureDevice(Temperature temperature, IngredientContainer startIngredient) {
		setTemperature(temperature);
		loadIngredient(startIngredient);
	}
	
	/**
	 * @param  temperature
	 * 	       The temperature of this new temperature device.
	 * @effect The temperature of this temperature device is set to the given value.
	 * 		   | setTemperature(temperature)
	 */
	@Model @Raw
	protected TemperatureDevice(Temperature temperature) {
		setTemperature(temperature);
	}
	
	
	/**************************************************
	 * Ingredient
	 **************************************************/
	
	/**
	 * Return the loaded ingredient in this device.
	 */
	@Basic
	protected AlchemicIngredient getStartIngredient() {
		return this.startIngredient;
	}
	
	/**
	 * Set the startIngredient variable to the given ingredient.
	 */
	protected void setStartIngredient(AlchemicIngredient ingredient) {
		this.startIngredient = ingredient;
	}
	
	/**
	 * A variable containing the loaded ingredient of this temperature device.
	 */
	private AlchemicIngredient startIngredient = null;
	
	/**
	 * Get the processed ingredient of this device.
	 */
	@Basic
	public AlchemicIngredient getProcessedIngredient() {
		return this.processedIngredient;
	}
	
	/**
	 * Set the processedIngredient variable to the given Alchemic Ingredient.
	 */
	protected void setProcessedIngredient(AlchemicIngredient ingredient) {
		this.processedIngredient = ingredient;
	}
	
	/**
	 * A variable for the processed ingredient still in the device
	 */
	private AlchemicIngredient processedIngredient = null;
	
	/**
	 * Loads the ingredients in a given container into this temperature device.
	 * 
	 * @param  container
	 * 		   The given container to be loaded in the device.
	 * @post   The ingredient in the given container is set as the start ingredient of this device 
	 *         and the container gets deleted.
	 * 		   | new.getStartIngredient() == container.getContents()
	 * 		   | container = null
	 * @throws CapacityException
	 * 		   This device is full.
	 * 		   | (getStartIngredient() != null)
	 */
	@Override @Raw
	public void loadIngredient(IngredientContainer container) throws CapacityException{
		if(getStartIngredient() != null) {
			throw new CapacityException(this, container, "Device has already been loaded.");
		}
		setStartIngredient(container.getContents());
		container = null;
	}

	/**
	 * Empties the device and creates a new container containing the processed ingredient of this device. 
	 * The container is the smallest container that can contain the processed ingredient.
	 * 
	 * @return If the processed ingredient of this temperature device is not effective, return null.
	 * 		   | if (getProcessedIngredient() == null)
	 * 		   |   result == null
	 * 		   Otherwise, return the smallest possible ingredient container that can containt the processed ingredient.
	 * 		   | outputIngredient == getProcessedIngredient()
	 *         | result == new IngredientContainer(outputIngredient,
			   |           Unit.getContainer(outputIngredient.getState(), outputIngredient.getQuantity()))
	 * @effect If the processed ingredient of this temperature device is effective, the processed ingredient
	 * 		   is set to null.
	 * 		   | setProcessedIngredient(null)
	 */
	@Override
	public IngredientContainer emptyDevice() {
		AlchemicIngredient outputIngredient = getProcessedIngredient();
		if (outputIngredient == null)
			return null;
		IngredientContainer outputContainer = new IngredientContainer(outputIngredient,
				Unit.getContainer(outputIngredient.getState(), outputIngredient.getQuantity()));
		setProcessedIngredient(null);
		return outputContainer;
	}
	
	/**************************************************
	 * Temperature
	 **************************************************/
	
	/**
	 * Return the temperature of this device in an array of two long values.
	 */
	public long[] getTemperature() {
		return getTemperatureObject().getTemperature();
	}
	
	/**
	 * Return the temperature object of this device.
	 */
	@Basic
	public Temperature getTemperatureObject() {
		return this.temperature;
	}

	/**
	 * Set the temperature of this device using a Temperature object.
	 * 
	 * @param	newTemperature
	 * 			The new temperature for the device.
	 * @post	If the given temperature is valid, the temperature of
	 * 			this device is set to the given temperature.
	 * 			| if (Temperature.isValidTemperature(newTemperature))
	 * 		    |   then new.getTemperature() == newTemperature
	 */
	public void setTemperature(Temperature newTemperature) {
		if (Temperature.isValidTemperature(newTemperature)) {
			this.temperature = newTemperature;
		}
	}
	
	/**
	 * A variable for the temperature of this temperature device.
	 */
	private Temperature temperature;
	
	/**************************************************
	 * Process
	 **************************************************/
	
	@Override
	public abstract void process();

}

