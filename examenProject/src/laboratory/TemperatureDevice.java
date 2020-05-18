package laboratory;

import be.kuleuven.cs.som.annotate.*;
import alchemy.*;

/**
 * A subclass of Device for cooling boxes.
 * 
 * @version	1.0
 * @author	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

public abstract class TemperatureDevice extends Device {
	
	/**
	 * Return the loaded ingredient in this device.
	 */
	@Basic
	protected AlchemicIngredient getStartIngredient() {
		return startIngredient;
	}
	
	/**
	 * A variable for the loaded ingredient in the device
	 */
	private AlchemicIngredient startIngredient = null;
	
	/**
	 * Set the processedIngredient variable to the given Alchemic Ingredient.
	 */
	protected void setProcessedIngredient(AlchemicIngredient ingredient) {
		processedIngredient = ingredient;
	}
	
	/**
	 * A variable for the processed ingredient still in the device
	 */
	private AlchemicIngredient processedIngredient = null;
	
	/**
	 * Loads the ingredients in a given container into this temperature device
	 * 
	 * @param container
	 * 		  The given container
	 * @throws DeviceFullException
	 * 		   This device is full
	 * 		   | startIngredient != null
	 */
	@Override @Raw
	public final void loadIngredient(IngredientContainer container) throws DeviceFullException{
		if(startIngredient != null) {
			throw new DeviceFullException(this, container);
		}
		this.startIngredient = container.getIngredient();
		container = null;
	}

	/**
	 * Empties the device and creates a new container containing the processed ingredient of this device. 
	 * The container is the smallest container that can contain the processed ingredient.
	 * HELP IK NIET WEET WAT MET DEZE COMMENTAAR TE DOEN MAN
	 */
	@Override
	public final IngredientContainer emptyDevice() {
		if (processedIngredient == null)
			return null;
		Container containerType=null;
		if(processedIngredient.getType().getState() == State.LIQUID) {
			containerType = LiquidQuantity.getContainer(processedIngredient.getQuantity());
		}
		else if (processedIngredient.getType().getState() == State.POWDER) {
			containerType = PowderQuantity.getContainer(processedIngredient.getQuantity());
		}
//		int index = 1;
//		Container containerType=null;
//		if(processedIngredient.getType().getState() == State.LIQUID) {
//			LiquidQuantity Units[] = LiquidQuantity.values();
//			while(processedIngredient.getQuantity() > Units[index].getNbOfSmallestUnit()) {
//				index = index + 1;
//			}
//			containerType = Container.valueOf(Units[index].toString());
//		} else if(processedIngredient.getType().getState() == State.POWDER) {
//			PowderQuantity Units[] = PowderQuantity.values();
//			while(processedIngredient.getQuantity() > Units[index].getNbOfSmallestUnit()) {
//				index = index + 1;
//			}
//			containerType = Container.valueOf(Units[index].toString());
//		}
		
		IngredientContainer outputContainer = new IngredientContainer(processedIngredient, containerType);
		processedIngredient = null;
		return outputContainer;
	}
	
	/**
	 * Return the temperature of this device in an array of two long values
	 */
	public final long[] getTemperature() {
		return temperature.getTemperature();
	}
	
	/**
	 * Return the temperature object of this device.
	 */
	public Temperature getTemperatureObject() {
		return temperature;
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

