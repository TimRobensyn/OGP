package laboratory;

import be.kuleuven.cs.som.annotate.*;
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
	 * Initialize a new capacity exception with the given device container and laboratory.
	 * 
	 * @param	fullDevice
	 * 			The device that caused the exception because it was full.
	 * @param	container
	 * 			The container that could not be loaded.
	 * @param 	laboratory
	 * 		    The laboratory that caused the exception because it was full.
	 * @post	The device variable of this item full exception is set to the given device.
	 * 			| new.getFullDevice == device
	 * @post	The container variable of this item full exception instance is set to the given container.
	 * @post	The laboratory variable of this item full exception is set to the given laboratory.
	 * 			| new.getFullLaboratory == laboratory
	 */
	@Raw
	public CapacityException(Device device, IngredientContainer container, Laboratory laboratory) {
		this.fullDevice = device;
		this.container = container;
		this.laboratory = laboratory;
	}
	
	/**
	 * Initialize a new capacity exception with the given device (but no container or laboratory).
	 * 
	 * @param	device
	 * 			The device that caused the exception because it was full.
	 * @post	The device variable of this item full exception is set to the given device.
	 * 			| new.getFullDevice == device
	 */
	@Raw
	public CapacityException(Device device) {
		this(device, null, null);
	}
	
	/**
	 * Initialize a new capacity exception with the given laboratory (but no device or laboratory)
	 * 
	 * @param laboratory
	 * 		  The laboratory that caused the exception because it was full.
	 * @post  The laboratory variable of this item full exception is set to the given device.
	 * 		  | new.getFullLaboratory == laboratory
	 */
	@Raw
	public CapacityException(Laboratory laboratory) {
		this(null, null, laboratory);
	}
	
	/**
	 * Initialize a new capacity exception with the given device and container
	 * 
	 * @param device
	 * 		  The device that caused the exception because it was full.
	 * @param container
	 * 		  The container that could not be loaded.
	 * @post  The device variable of this item full exception is set to the given device.
	 * 		  | new.getFullDevice == device
	 * @post  The container variable of this item full exception instance is set to the given container.
	 */
	@Raw
	public CapacityException(Device device, IngredientContainer container) {
		this(device, container, null);
	}
	
	/**
	 * Initialize a new capacity exception with the given laboratory and container
	 * 
	 * @param laboratory
	 * 		  The laboratory that caused the exception because it was full.
	 * @param container
	 * 		  The container that could not be loaded.
	 * @post  The laboratory variable of this item full exception is set to the given laboratory.
	 * 		  | new.getFullLaboratory == laboratory
	 * @post  The container variable of this item full exception instance is set to the given container.
	 */
	@Raw
	public CapacityException(IngredientContainer container, Laboratory laboratory) {
		this(null, container, laboratory);
	}
	
	/**
	 * Return the full device which caused this exception.
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
	 * Return the container that could not be loaded into the device.
	 */
	@Raw @Immutable @Basic
	public IngredientContainer getContainer() {
		return container;
	}
	
	/**
	 * A variable indicating the ingredientContainer that couldn't be loaded.
	 */
	private final IngredientContainer container;

	/**
	 * Return the laboratory which caused this exception.
	 * @return
	 */
	@Raw @Immutable @Basic
	public Laboratory getFullLaboratory() {
		return laboratory;
	}
	
	/**
	 * A variable indicating the laboratory.
	 */
	private final Laboratory laboratory;

}
