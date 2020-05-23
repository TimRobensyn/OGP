package laboratory;

import java.util.ArrayList;
import java.util.List;

import alchemy.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class describing a laboratory for storing and handling alchemic ingredients and devices.
 * 
 * @invar	Each laboratory must have a valid capacity.
 * 			| isValidCapacity(getCapacity())
 * @invar 	The storage of a laboratory cannot contain duplicate ingredient types.
 * 			| hasProperIngredients()
 * @invar   The device list of this laboratory cannot contain devices of the wrong type at the wrong index.
 * 			| hasProperDevices()
 * 
 * @version  1.0
 * @author   Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

public class Laboratory {
	
	/**************************************************
	 * CONSTRUCTORS
	 **************************************************/
	/**
	 * Initialize a new laboratory with the given capacity, storage and devices.
	 * 
	 * @param capacity
	 * 	      The given capacity in storerooms.
	 * @param storage
	 * 		  The given List with the storage in this laboratory.
	 * @param coolingbox
	 * 	      The cooling box in this laboratory.
	 * @param oven
	 * 		  The oven in this laboratory.
	 * @param kettle
	 * 		  The kettle in this laboratory.
	 * @param transmogrifier
	 * 		  The transmogrifier in this laboratory.
	 * @post  The capacity of this laboratory is set to the given capacity in storerooms.
	 * 		  | getCapacity() == capacity
	 * @post  The storage of this laboratory is set to the given capacity.
	 *        | getCoolingbox == cooling box
	 *        | getOven == oven
	 *        | getKettle == kettle
	 *        | getTransmogrifier == transmogrifier
	 * @throws CapacityException
	 * 		   The given capacity is invalid.
	 * 		   | !isValidCapacity(capacity)
	 */
	@Raw
	public Laboratory(int capacity, List<AlchemicIngredient> storage, CoolingBox coolingbox,
			Oven oven, Kettle kettle, Transmogrifier transmogrifier) throws CapacityException {

		if(!isValidCapacity(capacity)) {
			throw new CapacityException(this, "The given capacity is invalid.");
		}
		this.capacity = capacity;
		setStorage(storage);
		

		//TODO
		devices.add(coolingbox);
		devices.add(oven);
		devices.add(kettle);
		devices.add(transmogrifier);
		
		//addAsDevice(coolingbox);
		//addAsDevice(oven);
		//addAsDevice(kettle);
		//addAsDevice(transmogrifier);
		
		//addAsDevice(coolingbox);
		//addAsDevice(oven);
		//addAsDevice(kettle);
		//addAsDevice(transmogrifier);
		
		//initializeDevice(coolingbox, oven, kettle, transmogrifier);

	}
	
	/**
	 * Initialize a new laboratory with the given capacity, an empty storage and no devices.
	 * 
	 * @param capacity
	 * 	      The given capacity.
	 * 		  | getCapacity() == capacity
	 * @effect The new laboratory is initialized with the given capacity.
	 * 		   It's storage is empty and the devices are set to null.
	 * 		   | this(capacity, new List<AlchemicIngredient>(), null, null, null, null)
	 */
	@Raw
	public Laboratory(int capacity) {
		this(capacity, new ArrayList<AlchemicIngredient>(), null, null, null, null);
	}
	
	/**
	 * Initialize a new laboratory with the given capacity and devices with an empty storage
	 * 
	 * @param capacity
	 * 		  The given capacity
	 * @param coolingbox
	 * 		  The given coolingbox
	 * @param oven
	 * 		  The given oven
	 * @param kettle
	 * 		  The given kettle
	 * @param transmogrifier
	 * 		  The given transmogrifier
	 * @effect The new laboratory has the given capacity and devices and an empty storage
	 * 		   | this(capacity, new ArrayList<AlchemicIngredient>(), coolingbox, oven, kettle, transmogrifier)
	 */
	@Raw
	public Laboratory(int capacity, CoolingBox coolingbox, Oven oven, Kettle kettle, Transmogrifier transmogrifier) {
		this(capacity,new ArrayList<AlchemicIngredient>(),coolingbox,oven,kettle,transmogrifier);
	}
	
	/**************************************************
	 * CAPACITY
	 **************************************************/
	
	/**
	 * Return the capacity of this laboratory in storerooms.
	 */
	@Basic
	public int getCapacity() {
		return this.capacity;
	}

	/**
	 * Check whether the given capacity is a valid capacity for a laboratory.
	 * 
	 * @param	capacity
	 * 			The given capacity.
	 * @return	True if and only if the given capacity is not less than 0 or greater than the maximum integer value.
	 *			| result == ((capacity >= 0) && (capacity <= Integer.MAX_VALUE))
	 */
	public static boolean isValidCapacity(int capacity) {
		return((capacity >= 0) && (capacity <= Integer.MAX_VALUE));
	}
	
	/**
	 * Variable storing the capacity of this laboratory in storerooms.
	 */
	private final int capacity;
	
	/**************************************************
	 * STORAGE
	 **************************************************/

	/**
	 * Return the storage of this laboratory.
	 */
	@Basic
	public List<AlchemicIngredient> getStorage(){
		return this.storage;
	}
	
	/**
	 * Set the storage list of this laboratory to the given storage list.
	 * 
	 * @param storage
	 * 		  The given storage list.
	 */
	@Raw
	private void setStorage(List<AlchemicIngredient> storage) {
		this.storage = storage;
	}
	
	/**
	 * Return the quantity that is contained in this laboratory.
	 */
	public int getStorageQuantity() {
		int quantity = 0;
		for(AlchemicIngredient ingredient : storage) {
			quantity = quantity + ingredient.getQuantity();
		}
		return quantity;
	}
	
	/**
	 * Return the number of ingredients in this laboratory.
	 */
	@Basic
	public int getNbIngredients() {
		return getStorage().size();
	}
	
	/**
	 * Get the ingredient in this laboratory's storage at the given index.
	 * 
	 * @param index
	 * 		  The given index.
	 */
	@Basic
	public AlchemicIngredient getIngredientAt(int index) {
		return getStorage().get(index-1);
	}
	
	/**
	 * Add an ingredient to this laboratory's storage.
	 * 
	 * @param ingredient
	 * 		  The given ingredient.
	 */
	public void addAsIngredient(AlchemicIngredient ingredient) {
		getStorage().add(ingredient);
	}
	
	/**
	 * Remove an ingredient from this laboratory's storage.
	 * @param ingredient
	 */
	public void removeAsIngredient(AlchemicIngredient ingredient) {
		getStorage().remove(ingredient);
	}
	
	/**
	 * Check if this laboratory has proper ingredients.
	 * 
	 * @return True if and only if this laboratory's storage does not contain two of the same ingredient types.
	 * 		   | for each I in 0..getNbIngredient()-2:
	 * 		   |   for each J in I..getNbIngredient()-1:
	 * 		   |     if(getIngredientAt(I).getType().equals(getIngredientAt(J).getType()))
	 * 		   |       return false
	 *         | return true
	 */
	public boolean hasProperIngredients() {
		boolean bool = true;
		for(int i=0; i <= getNbIngredients()-2; i++) {
			for(int j = i+1; j <= getNbIngredients()-1; j++) {
				if(getIngredientAt(i+1).getType() == getIngredientAt(j+1).getType()) {
					bool = false;
				}
			}
		}
		return bool;
	}

	/**
	 * Store the ingredient contained by the given container in this laboratory. The old container gets deleted.
	 * 
	 * @param	container
	 * 			The given container.
	 * @effect	The ingredient in the given container is heated or cooled to its standard temperature.
	 * 			| makeStandardTemp(container)
	 * @effect	The ingredient in the given container is added to this laboratory's storage.
	 * 			| addAsIngredient(ingredient)
	 * 			If this laboratory already contains an ingredient of the same type a new ingredient is created with the same type
	 * 			and the quantity of the given ingredient counted up with the quantity of the stored ingredient.
	 * 			| if(storedIngredient.getType().equals(ingredient.getType()))
	 *			|   ingredient = new AlchemicIngredient(ingredient.getType(), ingredient.getQuantity() + storedIngredient.getQuantity())
	 *			|   removeAsIngredient(storedIngredient)
	 *			The old container is deleted.
	 *			| container = null
	 * @throws	CapacityException
	 * 			The quantity in the given container exceeds this laboratory's capacity.
	 *			| if(getStorageQuantity() + container.getContentQuantity() > this.capacity)
	 * @throws	CapacityException
	 *			This laboratory already contains an ingredient of the same type and there is no kettle present.
	 *			| if(storedIngredient.getType().equals(ingredient.getType()))
	 *			|   getKettle()
	 */
	public void store(IngredientContainer container) throws CapacityException {
		if(getStorageQuantity() + container.getContentQuantity() > this.capacity) {
			throw new CapacityException(container, this, "Not enough storage left");
		}

		makeStandardTemp(container);
		AlchemicIngredient ingredient = container.getContents();

		for(AlchemicIngredient storedIngredient : storage) {
			if(storedIngredient.getType().equals(ingredient.getType())) {
				getKettle();
				ingredient = new AlchemicIngredient(ingredient.getType(), ingredient.getQuantity() + storedIngredient.getQuantity()); 
				removeAsIngredient(storedIngredient);
				break;
			}
		}
		addAsIngredient(ingredient);
		container = null;
	}
	
	/**
	 * Request a given amount of an alchemic ingredient by giving either the special or simple name.
	 * 
	 * @param	name
	 * 			The special or simple name of the requested ingredient.
	 * @param	amount
	 * 			The given amount.
	 * @effect	If this laboratory contains an alchemic ingredient with the given special or simple name and
	 * 			if the given amount is not greater than the quantity of this alchemic ingredient and
	 * 			if the given amount is not greater than a barrel or chest depending on the state of the requested ingredient,
	 * 			the smallest possible container containing this ingredient with the given amount is created.
	 * 			| for each storedIngredient in storage
	 * 			|  if((requestedIngredientName == storedIngredientName) && (storedIngredient.getQuantity() >= amount) &&
	 * 			|     (amount <= Barrel or Chest quantity))
	 * 			| 		newContainer = new IngredientContainer(newIngredient, containerType);
	 * 			|        removeAsIngredient(requested storedIngredient)
	 * @throws	CapacityException
	 * 			This laboratory does not contain enough of the requested item or no container is big enough to hold
	 * 			the requested amount.
	 * 			| storedIngredient.getQuantity() < amount
	 * @throws	CapacityException
	 * 			This laboratory does not contain an ingredient with the given name.
	 * 			| !((storedIngredient.getType().getSimpleName() == name) ||
	 * 			|  (storedIngredient.getType().getSpecialName() == name))
	 */
	public IngredientContainer request(String name, int amount) throws CapacityException{
		for(AlchemicIngredient storedIngredient : storage) {
			if((storedIngredient.getType().getSimpleName().equals(name)) || (storedIngredient.getType().getSpecialName().equals(name))){
				
				if(storedIngredient.getQuantity() < amount) {
					throw new CapacityException(this, "Not enough of this ingredient.");
				}
				
				if (Unit.getBiggestContainer(storedIngredient.getState()).getAbsoluteCapacity() < amount) {
					throw new CapacityException(this, "No container for an amount this big.");
				}
				
				AlchemicIngredient newIngredient = new AlchemicIngredient(storedIngredient.getType(), amount);
				Unit newContainer = Unit.getContainer(storedIngredient.getState(), amount);
				
				if(storedIngredient.getQuantity()-amount > 0) {
					AlchemicIngredient newStoredIngredient = new AlchemicIngredient(storedIngredient.getType(), storedIngredient.getQuantity()-amount);
					addAsIngredient(newStoredIngredient);
				}
				
				removeAsIngredient(storedIngredient);
				return new IngredientContainer(newIngredient, newContainer);
			}
		}
		throw new CapacityException(this, "Ingredient not found.");
		
	}
	
	/**
	 * Request the full amount of an ingredient with the given simple or special name that this laboratory contains
	 * 
	 * @param name
	 * 		  The given special of simple name of the ingredient
	 * @effect The full quantity of the requested ingredient gets requested
	 * 		   | request(name, storedIngredient.getQuantity())
	 * 		   If the quantity of the requested ingredient exceeds the capacity of a barrel or chest depending on the state
	 * 		   a barrel of chest is returned and the leftovers are deleted
	 *  	   | if(storedIngredient.getQuantity() > barrel or chest quantity)
	 *  	   |   storedIngredient = new AlchemicIngredient(storedIngredient.getType(), barrel or chest quantity)
	 * @throws CapacityException
	 * 		   This laboratory does not contain an ingredient with the given special or simple name
	 *         | if(newContainer == null)
	 */
	public IngredientContainer request(String name) throws CapacityException{
		for(AlchemicIngredient storedIngredient : storage) {
			if((storedIngredient.getType().getSimpleName() == name) || (storedIngredient.getType().getSpecialName() == name)){
				
				if (Unit.getBiggestContainer(storedIngredient.getState()).getAbsoluteCapacity() < storedIngredient.getQuantity()) {
					Unit newContainer = Unit.getBiggestContainer(storedIngredient.getState());
					AlchemicIngredient newIngredient = new AlchemicIngredient(storedIngredient.getType(),
							Unit.getBiggestContainer(storedIngredient.getState()).getAbsoluteCapacity());
					return new IngredientContainer(newIngredient,newContainer);					
				}
				
				Unit newContainer = Unit.getContainer(storedIngredient.getState(), storedIngredient.getQuantity());
				AlchemicIngredient newIngredient = storedIngredient;
				removeAsIngredient(storedIngredient);
				return new IngredientContainer(newIngredient, newContainer);
			}
		}
		throw new CapacityException(this, "Ingredient not found.");
	}
	
	/**
	 * Return an two dimensional object array with the inventory of this laboratory. 
	 * The first row contains the full names of the ingredients, the second row contains their quantity.
	 */
	public Object[][] getInventory() {
		Object[][] inventory = new Object[2][getNbIngredients()];
		int index = 0;
		while(index < getNbIngredients()) {
			inventory[0][index] = getIngredientAt(index+1).getFullName();
			inventory[1][index] = getIngredientAt(index+1).getQuantity();
		}
		return inventory;
	}
	
	
	/**
	 * Variable storing the contents of this laboratory in drops or pinches
	 */
	private List<AlchemicIngredient> storage = new ArrayList<AlchemicIngredient>();
	
	/**************************************************
	 * DEVICES
	 **************************************************/
	
	/**
	 * Return the list of devices in this laboratory
	 */
	@Basic
	public List<Device> getDevices(){
		return this.devices;
	}
	
	/**
	 * Return the number of devices in this laboratory
	 */
	@Basic
	public int getNbDevices() {
		int number = 0;
		for(int i=0; i<4; i++) {
			if(getDeviceAt(i+1) != null) {
				number += 1;
			}
		}
		return number;
	}
	
	/**
	 * Return the device at the given index in this laboratory's device list
	 * @param index
	 * 		  The given index
	 */
	@Basic @Raw
	public Device getDeviceAt(int index) {
		return this.devices.get(index-1);
	}
	
	/**
	 * Check whether the given device is a valid device at the given index in this laboratory's device list
	 * 
	 * @param device
	 * 	      The given device
	 * @param index
	 * 		  The given index
	 * @return True if and only if the device is not in another laboratory yet and the device is a cooling box 
	 * 		   and the index is 1, the device is an oven and the index is 2, the device is a kettle and the index
	 *         is 3 or the device is a transmogrifier and the index is 4
	 *         | if(device.getClass() == CoolingBox.class)
	 *		   |	result == (index == 1)
	 *		   | else if(device.getClass() == Oven.class)
	 *		   |	result == (index == 2)
	 *		   | else if(device.getClass() == Kettle.class)
	 *		   |	result == (index == 3)
	 *		   | else if(device.getClass() == Transmogrifier.class)
	 *		   |	result == (index == 4)
	 *		   | else 
	 *		   |	result == false
	 */
	public boolean isValidDeviceAt(Device device, int index) {
		if((device.getLaboratory() != this) && (device.getLaboratory() != null)) {
			return false;
		} else if(((device.getClass() == CoolingBox.class)) && ((getDeviceAt(1) == null) || (getDeviceAt(1) == device))) {
			return(index == 1);
		} else if(((device.getClass() == Oven.class)) && ((getDeviceAt(2) == null) || (getDeviceAt(2) == device))) {
			return(index == 2);
		} else if(((device.getClass() == Kettle.class)) && ((getDeviceAt(3) == null) || (getDeviceAt(3) == device))) {
			return(index == 3);
		} else if(((device.getClass() == Transmogrifier.class)) && ((getDeviceAt(4) == null) || (getDeviceAt(4) == device))) {
			return(index == 4);
		} else {
			return false;
		}
	}
	
	/**
	 * Check whether this laboratory has proper devices in its devices list.
	 * 
	 * @return True if and only if the first device is either null or a cooling box, the second device is either null or an over,
	 * 		   the third device is either null or a kettle and the fourth device is either null or a transmogrifier.
	 *         | if((getDeviceAt(0).getClass() != CoolingBox.class) && (getDeviceAt(0) != null))
	 *         |   result == false
	 *         | if((getDeviceAt(1).getClass() != Oven.class) && (getDeviceAt(1) != null))
	 *         |   result == false
	 *         | if((getDeviceAt(2).getClass() != Kettle.class) && (getDeviceAt(2) != null))
	 *         |   result == false
	 *         | if((getDeviceAt(3).getClass() != Transmogrifier.class) && (getDeviceAt(3) != null))
	 *         |   result == false
	 *         | result == true
	 */
	public boolean hasProperDevices() {
		boolean bool = true;
		if((getDeviceAt(1).getClass() != CoolingBox.class) && (getDeviceAt(1) != null)) {
			bool = false;
		}
		if((getDeviceAt(2).getClass() != Oven.class) && (getDeviceAt(2) != null)) {
			bool = false;
		}
		if((getDeviceAt(3).getClass() != Kettle.class) && (getDeviceAt(3) != null)) {
			bool = false;
		}
		if((getDeviceAt(4).getClass() != Transmogrifier.class) && (getDeviceAt(4) != null)) {
			bool = false;
		}
		return bool;
	}
	
	/**
	 * Initialize the given device in the devices list with the given index.
	 * 
	 * @param device
	 * 		  The given device
	 * @param index
	 * 		  The given index
	 * @post  The position in the devices list with the given index gets set to the given device
	 * 		  | devices.set(index, device)
	 * @effect If the given device is not null, the laboratory of this device gets set to this laboratory
	 * 		   | if(device != null)
	 *         |   device.setLaboratory(this)
	 * @throws CapacityException
	 * 		   The device is already in another laboratory
	 * 		   | (device != null) && (device.getLaboratory() != null)
	 */
	private void initializeDevice(CoolingBox coolingbox, Oven oven, Kettle kettle, Transmogrifier transmogrifier) throws CapacityException {
		if(coolingbox != null) {
			if(coolingbox.getLaboratory() != null) {
				throw new CapacityException(coolingbox, "Coolingbox is already in another laboratory.");
			} else {
				coolingbox.setLaboratory(this);
			}
		}
		if(oven != null) {
			if(oven.getLaboratory() != null) {
				throw new CapacityException(oven, "Oven is already in another laboratory.");
			} else {
				oven.setLaboratory(this);
			}
		}
		if(kettle != null) {
			if(kettle.getLaboratory() != null) {
				throw new CapacityException(kettle, "Kettle is already in another laboratory.");
			} else {
				kettle.setLaboratory(this);
			}
		}
		if(transmogrifier != null) {
			if(transmogrifier.getLaboratory() != null) {
				throw new CapacityException(transmogrifier, "Coolingbox is already in another laboratory.");
			} else {
				transmogrifier.setLaboratory(this);
			}
		}

		devices.add(null);
		devices.add(null);
		devices.add(null);
		devices.add(null);
		
		devices.set(0, coolingbox);
		devices.set(1, oven);
		devices.set(2, kettle);
		devices.set(3, transmogrifier);
	}
	
	/**
	 * Add a device to this laboratory's device list.
	 * 
	 * @param device
	 * 		  The given device
	 * @throws CapacityException
	 * 		   This laboratory already contains a device of this type
	 * 		   | (device.getClass() == deviceInDevices.getClass()) && (deviceInDevices != null)
	 */
	@Raw
	public void addAsDevice(Device device) throws CapacityException {
		if(isValidDeviceAt(device,1)) {
			getDevices().set(0, device);
		}
		else if(isValidDeviceAt(device,2)) {
			getDevices().set(1, device);
		}
		else if(isValidDeviceAt(device,3)) {
			getDevices().set(2, device);
		}
		else if(isValidDeviceAt(device,4)) {
			getDevices().set(3, device);
		} else {
			throw new CapacityException(device,this,"This laboratory cannot accept this device.");
		}
		if(device != null) {
			device.setLaboratory(this);
		}
	}
	
	/**
	 * Remove the given device from the device list.
	 * If the given device is not in the device list nothing happens.
	 * @effect The entry in the device list gets set to null at the index of the given device.
	 * 	       | getDevices().set(deviceIndex, null)
	 */
	public void removeAsDevice(Device device) {
		device.setLaboratory(null);
		if(device.getClass() == CoolingBox.class) {
			getDevices().set(0, null);
		}
		if(device.getClass() == Oven.class) {
			getDevices().set(1, null);
		}
		if(device.getClass() == Kettle.class) {
			getDevices().set(2, null);
		}
		if(device.getClass() == Transmogrifier.class) {
			getDevices().set(3, null);
		}
	}
	
	/**
	 * Return the cooling box of this laboratory.
	 * 
	 * @throws CapacityException
	 * 		   This laboratory does not contain a cooling box
	 *         | getDeviceAt(0) == null
	 */
	public CoolingBox getCoolingbox() throws CapacityException {
		if(getDeviceAt(1) == null) {
			throw new CapacityException(this,"Cooling box not found.");
		}
		return (CoolingBox) getDeviceAt(1);
	}
	
	/**
	 * Return the oven of this laboratory.
	 * 
	 * @throws CapacityException
	 * 		   This laboratory does not contain an oven
	 * 		   | getDeviceAt(1) == null
	 */
	public Oven getOven() {
		if(getDeviceAt(2) == null) {
			throw new CapacityException(this,"Oven not found.");
		}
		return (Oven) getDeviceAt(2);
	}
	
	/**
	 * Return the kettle of this laboratory
	 * 
	 * @throws CapacityException
	 * 		   This laboratory does not contain a kettle
	 * 		   | getDeviceAt(2) == null
	 */
	public Kettle getKettle() {
		if(getDeviceAt(3) == null) {
			throw new CapacityException(this,"Kettle not found.");
		}
		return (Kettle) getDeviceAt(3);
	}
	
	/**
	 * Return the transmogrifier of this laboratory
	 * 
	 * @throws CapacityException
	 * 		   This laboratory does not contain a transmogrifier
	 * 		   | getDeviceAt(3) == null
	 */
	public Transmogrifier getTransmogrifier() {
		if(getDeviceAt(4) == null) {
			throw new CapacityException(this,"Transmogrifier not found.");
		}
		return (Transmogrifier) getDeviceAt(4);
	}
	
	/**
	 * Heat or cool the given ingredient to it's standard temperature using the oven or cooling box in this laboratory
	 * If the ingredient is already at it's standard temperature nothing needs to happen
	 * 
	 * @param ingredient
	 * 		  The given ingredient
	 * @effect If the ingredient is hotter than it's standard temperature it gets cooled to it's standard temperature
	 *         | if(temperatureDifference(ingredient.getStandardTemperature(), ingredient.getTemperature()) < 0)
	 *         | 	getCoolingbox().setTemperature(ingredient.getStandardTemperature())
	 *         | 	getCoolingbox().loadIngredient(ingredient)
	 *         | 	getCoolingbox().process()
	 *         If the ingredient is colder than it's standard temperature it gets heated to 5% above it's standard temperature and then gets cooled down
	 *         | if(temperatureDifference(ingredient.getStandardTemperature(), ingredient.getTemperature()) > 0)
	 *         | 	getOven().setTemperature(Temperature (container.getIngredient().getStandardTemperatureObject().getColdness()*1.05, container.getIngredient().getStandardTemperatureObject().getHotness()*1.05)
	 *         |	getOven().loadIngredient(ingredient)
	 *         |	getOven().process()
	 */
	private void makeStandardTemp(IngredientContainer container) {
		long tempDiff = Temperature.temperatureDifference(container.getContents().getStandardTemperatureObject(), container.getContents().getTemperatureObject());
		if(tempDiff != 0) {
			if(tempDiff > 0) {
				getOven().setTemperature(new Temperature ((long) (container.getContents().getStandardTemperatureObject().getColdness()*1.05d),
						(long) (container.getContents().getStandardTemperatureObject().getHotness()*1.05d)));
				getOven().loadIngredient(container);
				getOven().process();
				container = getOven().emptyDevice();
			}
			getCoolingbox().setTemperature(container.getContents().getStandardTemperatureObject());
			getCoolingbox().loadIngredient(container);
			getCoolingbox().process();
			container = getCoolingbox().emptyDevice();
		}
	}

	/**
	 * Variable storing the list of devices in this laboratory
	 */
	private List<Device> devices = new ArrayList<Device>(4);
	
	
	/**************************************************
	 * Execute
	 **************************************************/
	
	/**
	 * Execute the given recipe a given amount of time.
	 *  
	 * @param recipe
	 * 		  The given recipe to execute .
	 * @param amount
	 * 		  The given amount .
	 */
	public void execute(Recipe recipe, int amount) {
		
	}
	}
