package laboratory;

import be.kuleuven.cs.som.annotate.*;
import laboratory.device.Device;
import alchemy.*;

/**
 * A class indicating the item cannot load more ingredients.
 */

public class CapacityException extends RuntimeException{
	
	/**
	 * Required because this class inherits from Exception
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Initialize a new capacity exception with the given device, container, laboratory and error message.
	 * 
	 * @param	device
	 * 			The device that caused the exception because it was full or empty.
	 * @param	container
	 * 			The container that could not be loaded.
	 * @param 	laboratory
	 * 		    The laboratory in which the exception happened.
	 * @param	errorMessage
	 * 			The error message that gives additional information.
	 * @post	The device variable of this capacity exception is set to the given device.
	 * 			| new.getDevice == device
	 * @post	The container variable of this capacity exception instance is set to the given container.
	 * 			| new.getContainer == container
	 * @post	The laboratory variable of this capacity exception is set to the given laboratory.
	 * 			| new.getLaboratory == laboratory
	 * @post	The errorMessage variable of this capacity exception is set to the given error message.
	 * 			| new.getErrorMessage == errorMessage
	 */
	@Raw
	public CapacityException(Device device, IngredientContainer container, Laboratory laboratory, String errorMessage) {
		this.fullDevice = device;
		this.container = container;
		this.laboratory = laboratory;
		this.errorMessage = errorMessage;
	}
	
	/**
	 * Initialize a new capacity exception with the given device and error message (but no container or laboratory).
	 * 
	 * @param	device
	 * 			The device that caused the exception because it was full or empty.
	 * @param	errorMessage
	 * 			The error message that gives additional information.
	 * @effect	The device variable of this capacity exception is set to the given device.
	 * 			| new.getDevice == device
	 * @effect	The errorMessage variable of this capacity exception is set to the given error message.
	 * 			| new.getErrorMessage == errorMessage
	 */
	@Raw
	public CapacityException(Device device, String errorMessage) {
		this(device, null, null, errorMessage);
	}
	
	/**
	 * Initialize a new capacity exception with the given laboratory and error message.
	 * 
	 * @param 	laboratory
	 * 		    The laboratory in which the exception happened.
	 * @param	errorMessage
	 * 			The error message that gives additional information.
	 * @effect	The laboratory variable of this capacity exception is set to the given laboratory.
	 * 			| new.getLaboratory == laboratory
	 * @effect	The errorMessage variable of this capacity exception is set to the given error message.
	 * 			| new.getErrorMessage == errorMessage
	 */
	@Raw
	public CapacityException(Laboratory laboratory, String errorMessage) {
		this(null, null, laboratory, errorMessage);
	}
	
	/**
	 * Initialize a new capacity exception with the given device, container and error message.
	 * 
	 * @param	device
	 * 			The device that caused the exception because it was full or empty.
	 * @param	container
	 * 			The container that could not be loaded.
	 * @param	errorMessage
	 * 			The error message that gives additional information.
	 * @effect	The device variable of this capacity exception is set to the given device.
	 * 			| new.getDevice == device
	 * @effect	The container variable of this capacity exception instance is set to the given container.
	 * 			| new.getContainer == container
	 * @effect	The errorMessage variable of this capacity exception is set to the given error message.
	 * 			| new.getErrorMessage == errorMessage	
	 */
	@Raw
	public CapacityException(Device device, IngredientContainer container, String errorMessage) {
		this(device, container, null, errorMessage);
	}
	
	/**
	 * Initialize a new capacity exception with the given laboratory, container and error message.
	 * 

	 * @param	container
	 * 			The container that could not be loaded.
	 * @param 	laboratory
	 * 		    The laboratory in which the exception happened.
	 * @param	errorMessage
	 * 			The error message that gives additional information.
	 * @effect	The container variable of this capacity exception instance is set to the given container.
	 * 			| new.getContainer == container
	 * @effect	The laboratory variable of this capacity exception is set to the given laboratory.
	 * 			| new.getLaboratory == laboratory
	 * @effect	The errorMessage variable of this capacity exception is set to the given error message.
	 * 			| new.getErrorMessage == errorMessage
	 */
	@Raw
	public CapacityException(IngredientContainer container, Laboratory laboratory, String errorMessage) {
		this(null, container, laboratory, errorMessage);
	}
	
	/**
	 * Initialize a new capacity exception with the given laboratory, device and error message.
	 * 

	 * @param	device
	 * 			The device involved in the exception.
	 * @param 	laboratory
	 * 		    The laboratory in which the exception happened.
	 * @param	errorMessage
	 * 			The error message that gives additional information.
	 * @effect	The device variable of this capacity exception instance is set to the given device.
	 * 			| new.getDevice == device
	 * @effect	The laboratory variable of this capacity exception is set to the given laboratory.
	 * 			| new.getLaboratory == laboratory
	 * @effect	The errorMessage variable of this capacity exception is set to the given error message.
	 * 			| new.getErrorMessage == errorMessage
	 */
	@Raw
	public CapacityException(Device device, Laboratory laboratory, String errorMessage) {
		this(device, null, laboratory, errorMessage);
	}
	
	/**
	 * Return the device which caused this exception.
	 */
	@Raw @Immutable @Basic
	public Device getFullDevice() {
		return fullDevice;
	}
	
	/**
	 * A variable indicating the device.
	 */
	private final Device fullDevice;
	
	/**
	 * Return the container involved in the exception.
	 */
	@Raw @Immutable @Basic
	public IngredientContainer getContainer() {
		return container;
	}
	
	/**
	 * A variable indicating the ingredientContainer involved in the exception.
	 */
	private final IngredientContainer container;

	/**
	 * Return the laboratory which caused this exception.
	 */
	@Raw @Immutable @Basic
	public Laboratory getFullLaboratory() {
		return laboratory;
	}
	
	/**
	 * A variable indicating the laboratory.
	 */
	private final Laboratory laboratory;
	
	/**
	 * Return the error message of this exception.
	 */
	@Raw @Immutable @Basic
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	/**
	 * A variable containing the string with the error message of this exception.
	 */
	private String errorMessage;

}
