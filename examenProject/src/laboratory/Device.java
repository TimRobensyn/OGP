package laboratory;

import be.kuleuven.cs.som.annotate.*;
//import be.kuleuven.cs.som.*;
import alchemy.*;

/**
 * An interface for devices
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
	 * 				container is loaded into the device.
	 * 			|if //TODO Formal specs
	 * @return	If the device can hold an unlimited amount of ingredients,
	 * 				container is loaded into the device.
	 * 			|if //TODO Formal specs
	 * @throws	CapacityException
	 * 			This device has a limited amount of room for ingredients and this amount is already filled.
	 */
	public abstract void loadIngredient(IngredientContainer container) throws CapacityException;
	
	/**
	 * Take the result of the process out of the device and put it into a container.
	 * 
	 * @return 	If the process hasn't been executed yet,
	 * 				return null.
	 * 			|if //TODO Formal specs
	 * @return	If the process has been executed,
	 * 				return the last result of the process in a container
	 * 			|if //TODO Formal specs 			
	 */
	public abstract IngredientContainer emptyDevice();
	
	/**
	 * Execute the alchemic process of the device on the loaded devices.
	 * 
	 * 			//TODO Formal specs
	 */
	public abstract void process() throws CapacityException;
	
	/**
	 * Return the laboratory this device is in.
	 */
	@Basic
	public Laboratory getLaboratory() {
		return this.laboratory;
	}
	
	/**
	 * Set the laboratory this device is in to the given laboratory
	 * 
	 * @param laboratory
	 * 		  The given laboratory
	 */
	public void setLaboratory(Laboratory laboratory) {
		this.laboratory = laboratory;
	}
	
	/**
	 * Check whether this device can be in the given laboratory.
	 * @param laboratory
	 * 		  The given laboratory
	 * @return True if and only if this device is not in another laboratory than the given laboratory
	 */
	public boolean canHaveAsLaboratory(Laboratory laboratory) {
		return((getLaboratory() == laboratory) || (getLaboratory() == null));
	}
	
	/**
	 * Check whether the laboratory this device is in is proper
	 * @note This method is for good coding practices
	 */
	//public boolean hasProperLaboratory() {
	//return(?);
	//}
	
	/**
	 * Variable storing the laboratory this device is in.
	 */
	private Laboratory laboratory = null;

}
