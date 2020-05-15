package laboratory;

import be.kuleuven.cs.som.annotate.*;
import alchemy.*;

/**
 * A class indicating the device cannot load more ingredients.
 */

public class DeviceFullException extends RuntimeException{
	
	/**
	 * Required because this class inherits from Exception
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * A variable indicating the device.
	 */
	private final Device fullDevice;
	
	/**
	 * A variable indicating the ingredientContainer that couldn't be loaded.
	 */
	private final IngredientContainer container;
	
	/**
	 * Initialize a new device full exception with the given device and container.
	 * 
	 * @param	fullDevice
	 * 			The device hat caused the exception because it was full.
	 * @param	container
	 * 			The container that could not be loaded.
	 * @post	The fullDevice variable of this device full exception is set to the given device.
	 * 			| new.getFullDevice == device
	 * @post	The container variable of this device full exception instance is set to the given container.
	 */
	@Raw
	public DeviceFullException(Device device, IngredientContainer container) {
		this.fullDevice = device;
		this.container = container;
	}
	
	/**
	 * Return the full device which caused this exception.
	 */
	@Raw @Immutable @Basic
	public Device getFullDevice() {
		return fullDevice;
	}
	
	/**
	 * Return the container that could not be loaded into the device.
	 */
	@Raw @Immutable @Basic
	public IngredientContainer getContainer() {
		return container;
	}

}
