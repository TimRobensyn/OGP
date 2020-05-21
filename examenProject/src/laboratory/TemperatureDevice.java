package laboratory;

import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.*;
import alchemy.*;

/**
 * A subclass of Device for devices with a temperature and limited capacity.
 * 
 * @invar	
 * 
 * @version	1.0
 * @author	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

public abstract class TemperatureDevice extends Device {
	
	/**
	 * A model constructor for this kind of device, setting a temperature
	 * and loading an ingredient.
	 */
	@Model @Raw
	public TemperatureDevice(Temperature temperature, IngredientContainer startIngredient) {
		setTemperature(temperature);
		loadIngredient(startIngredient);
	}
	
	/**
	 * A model constructor for this kind of device, setting the temperature.
	 */
	@Model @Raw
	public TemperatureDevice(Temperature temperature) {
		setTemperature(temperature);
	}
	
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
	 * A variable for the loaded ingredient in the device
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
	 * Loads the ingredients in a given container into this temperature device
	 * 
	 * @param container
	 * 		  The given container to be loaded in the device.
	 * @throws CapacityException
	 * 		   This device is full.
	 * 		   | (getStartIngredient() != null)
	 */
	@Override @Raw
	public final void loadIngredient(IngredientContainer container) throws CapacityException{
		if(getStartIngredient() != null) {
			throw new CapacityException(this, container);
		}
		setStartIngredient(container.getIngredient());
		container = null;
	}

	/**
	 * Empties the device and creates a new container containing the processed ingredient of this device. 
	 * The container is the smallest container that can contain the processed ingredient.
	 * HELP IK NIET WEET WAT MET DEZE COMMENTAAR TE DOEN MAN
	 */
	@Override
	public final IngredientContainer emptyDevice() {
		if (getProcessedIngredient() == null)
			return null;
		Container containerType=null;
		if(getProcessedIngredient().getType().getState() == State.LIQUID) {
			containerType = LiquidQuantity.getContainer(getProcessedIngredient().getQuantity());
		}
		else if (getProcessedIngredient().getType().getState() == State.POWDER) {
			containerType = PowderQuantity.getContainer(getProcessedIngredient().getQuantity());
		}
		
		IngredientContainer outputContainer = new IngredientContainer(getProcessedIngredient(), containerType);
		setProcessedIngredient(null);
		return outputContainer;
	}
	
	/**
	 * Return the temperature of this device in an array of two long values
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
	 * @param	newTemperature
	 * 			The new temperature for the device.
	 * @post	If the given temperature is valid, the temperature of
	 * 			this device is set to the given temperature.
	 * 			//TODO moet er hier nog iets formeeels bij? Tis denk ik totaal te programmeren
	 */
	public final void setTemperature(Temperature newTemperature) {
		if (Temperature.isValidTemperature(newTemperature)) {
			this.temperature = newTemperature;
		}
	}
	
//	/**
//	 * Set the temperature of this device using an array of long values.
//	 * @param	newTemperature
//	 * 			The new temperature for the device
//	 */
//	public final void setTemperature(long[] newTemperature) {
//			setTemperature(new Temperature(newTemperature));
//	}
	
	/**
	 * A variable for the temperature of this temperature device.
	 */
	private Temperature temperature;
	
	
	@Override
	public abstract void process();

}

