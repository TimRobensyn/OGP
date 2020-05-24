package laboratory.device;

import be.kuleuven.cs.som.annotate.*;
import laboratory.CapacityException;
import laboratory.Laboratory;
import alchemy.*;

/**
 * An abstract class of devices.
 * 
 * @invar   The laboratory that this device is in must be proper
 * 			| hasProperLaboratory()
 * 
 * @version	1.0
 * @author	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

public abstract class Device {
	
	/**
	 * Load an ingredient into the device and destroy the container it was in.
	 * 
	 * @param	container
	 * 			The IngredientContainer to be loaded in the device.
	 * @return	If the device can only hold one ingredient and it is empty,
	 * 			container is loaded into the device.
	 * 			| if (device.getClass() == TemperatureDevice.class 
	 * 			|   && device is empty)
	 * 		    |    then loadIngredient(container)
				If the device can hold an unlimited amount of ingredients,
     * 			the container is loaded into the device.
	 * 			| if (device.getClass() == BottomlessDevice.class)
	 *          |    then loadIngredient(container)
	 * @throws	CapacityException
	 * 			This device has a limited amount of room for ingredients and this amount is already filled.
	 */
	public abstract void loadIngredient(IngredientContainer container) throws CapacityException;
	
	/**
	 * Take the result of the process out of the device and put it into a container.
	 * 
	 * @return 	If the process hasn't been executed yet, return null.
	 *		    If the process has been executed, return the last result of the process in a container.
	 */
	public abstract IngredientContainer emptyDevice();
	
	/**
	 * Execute the alchemic process of the device on the loaded devices.
	 */
	public abstract void process() throws CapacityException;
	
	
	/**************************************************
	 * Laboratory
	 **************************************************/
	
	/**
	 * Return the laboratory this device is in. Null is returned
	 * in case the device has no laboratory.
	 */
	@Basic @Raw
	public Laboratory getLaboratory() {
		return this.laboratory;
	}
	
	/**
	 * Set the laboratory this device is in to the given laboratory
	 * .
	 * @param laboratory
	 * 		  The given laboratory
	 * @post  The laboratory of this device is equal to the given laboratory.
	 * 		  | new.getLaboratory() == laboratory
	 */
	public void setLaboratory(Laboratory laboratory) {
		this.laboratory = laboratory;
	}
	
	/**
	 * Check whether this device can be in the given laboratory.
	 * 
	 * @param  laboratory
	 * 		   The given laboratory
	 * @return True if and only if this device is not in another laboratory than the given laboratory.
	 */
	public boolean canHaveAsLaboratory(Laboratory laboratory) {
		return((getLaboratory() == laboratory) || (getLaboratory() == null));
	}
	
	/**
	 * Check whether the laboratory this device is in is proper.
	 * 
	 * @result True if and only if this laboratory has this device as one of its devices.
	 * 		   | result == getLaboratory().hasAsDevice(this)
	 */
	public boolean hasProperLaboratory() {
		return(getLaboratory().hasAsDevice(this));
	}
	
	/**
	 * Variable storing the laboratory this device is in.
	 */
	private Laboratory laboratory = null;

}
