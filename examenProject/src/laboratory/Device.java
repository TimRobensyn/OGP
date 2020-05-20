package laboratory;

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
	 * @throws	ItemFullException
	 * 			This device has a limited amount of room for ingredients and this amount is already filled.
	 */
	public abstract void loadIngredient(IngredientContainer container) throws ItemFullException;
	
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
	public abstract void process();

}
