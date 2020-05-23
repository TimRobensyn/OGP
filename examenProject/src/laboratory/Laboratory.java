package laboratory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public Laboratory(int capacity, List<AlchemicIngredient> storage, Set<Device> devices)
			throws CapacityException {
		if(!isValidCapacity(capacity)) {
			throw new CapacityException(this, "The given capacity is invalid.");
		}
		this.capacity = capacity;
		setStorage(storage);
		this.devices = new HashSet<Device>();
		this.devices.addAll(devices);
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
	public Laboratory(int capacity, Set<Device> devices) {
		this(capacity,new ArrayList<AlchemicIngredient>(),devices);
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
		this(capacity, new ArrayList<AlchemicIngredient>(), new HashSet<Device>());
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
	 * Return the total capacity in spoons.
	 */
	public int getCapacityInSpoons() {
		return (getCapacity()*Unit.STOREROOM_LIQUID.getAbsoluteCapacity())
				/Unit.SPOON_LIQUID.getCapacity();
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
	 * Get the quantity of the given ingredient type.
	 */
	@Basic @Raw
	public int getQuantityOf(IngredientType type) {
		return this.storage.get(type);
	}
	
	/**
	 * Check whether this class contains the given ingredient type.
	 */
	@Basic @Raw
	public boolean hasAsIngredientType(IngredientType type) {
		return this.storage.containsKey(type);
	}
	
	/**
	 * Check whether the given ingredient type is a valid type.
	 * 
	 * @param	type
	 * 			The ingredient type to check.
	 * @return	True if the given type is effective.
	 * 			| (type!=null)
	 */
	@Raw
	public boolean isValidIngredientType(IngredientType type) {
		return (type!=null);
	}
	
	/**
	 * Check whether the given ingredient type has a valid quantity.
	 * 
	 * @param	type
	 * 			The type to check the quantity of.
	 * @return	True if the quantity of the given type is bigger
	 * 			than zero.
	 */
	@Raw
	public boolean canHaveAsQuantity(IngredientType type) {
		return (getQuantityOf(type)>0);
	}
	
	/**
	 * Return the used capacity of this laboratory in spoons in the form of a double.
	 */
	@Raw
	public double getUsedCapacity() {
		double usedCapacity = 0;
		for (IngredientType type:this.storage.keySet()) {
			if (type.getState()==State.LIQUID)
				usedCapacity += (getQuantityOf(type)/Unit.SPOON_LIQUID.getCapacity());
			else if (type.getState()==State.POWDER){
				usedCapacity += (getQuantityOf(type)/Unit.SPOON_POWDER.getCapacity());
			}
		}
		return usedCapacity;
	}
	
	/**
	 * Check whether the storage of this laboratory is a valid
	 * storage for this laboratory.
	 * 
	 * @return	True if and only if all the stored ingredients and their
	 * 			quantities are valid ingredients and quantities,
	 * 			and if the total capacity taken by the ingredient's quantities
	 * 			is less than the available capacity of the laboratorium.
	 * 			| for each type in Storage:
	 * 			|	((!isValidIngredientType(type))
	 * 			|	  ||(!canHaveAsQuantity(type)))
	 * 			| && (getUsedCapacity()>getCapacityInSpoons()
	 */
	public boolean hasProperStorage() {
		for (IngredientType type:this.storage.keySet()) {
			if (!isValidIngredientType(type)) return false;
			if (!canHaveAsQuantity(type)) return false;
		}
		if (getUsedCapacity()>getCapacityInSpoons())
			return false;
		return true;
	}
	
	//TODO fix storage
	
	/**
	 * A map containing the ingredient types of this laboratory as keys
	 * and their quantities as values.
	 */
	private Map<IngredientType,Integer> storage = new HashMap<IngredientType,Integer>();
	
	

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
				getDevice(Kettle.class);
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
	
//	
//	/**
//	 * Variable storing the contents of this laboratory in drops or pinches
//	 */
//	private List<AlchemicIngredient> storage = new ArrayList<AlchemicIngredient>();
	
	/**************************************************
	 * DEVICES
	 **************************************************/
	
	/**
	 * Check whether the given device is in the set or not.
	 * 
	 * @param	device
	 * 			The device to be checked.
	 */
	@Basic @Raw
	public boolean hasAsDevice(Device device) {
		return this.devices.contains(device);
	}
	
	/**
	 * Check whether a laboratory can have the given device
	 * in its set of devices.
	 * 
	 * @param	device
	 * 			The device to be checked.
	 * @return	True if the device is effective.
	 * 			| device != null
	 */
	@Raw
	public static boolean isValidDevice(Device device) {
		return (device != null);
	}
	
	/**
	 * Check whether the set of this laboratory has proper
	 * devices.
	 * 
	 * @return	True if and only if this laboratory can have each of
	 * 			its devices as a device, and if each of these devices
	 * 			reference this laboratory as their laboratory.
	 * 			| result ==
	 * 			|	for each device in Devices:
	 * 			|	   ( if (this.hasAsDevice(device))
	 * 			|			then isValidDevice(device)
	 * 			|			  && (device.getLaboratory() == this))
	 */
	@Raw
	public boolean hasProperDevices() {
		for (Device device:this.devices) {
			if (! (isValidDevice(device)
					&& device.getLaboratory() == this))
				return false;
		}
		return true;
	}
	
	/**
	 * Add the given device to the set of devices attached to this
	 * laboratory.
	 * 
	 * @param	device
	 * 			The device to be added.
	 * @post	This laboratory has the given device as one of
	 * 			its devices.
	 * 			| new.hasAsDevice(device)
	 * @post	The given device references this laboratory as the
	 * 			laboratory to which it is attached.
	 * 			| (new device).getLaboratory() == this
	 * @throws	IllegalArgumentException
	 * 			This laboratory cannot have the given device as
	 * 			one of its devices.
	 * 			| ! isValidDevice(device)
	 * @throws	IllegalArgumentException
	 * 			The given device is already attached to some
	 * 			laboratory.
	 * 			| (device != null)
	 * 			|	&& (device.getLaboratory() != null))
	 */
	public void addAsDevice(Device device) throws IllegalArgumentException{
		if (!isValidDevice(device))
			throw new IllegalArgumentException("Device invalid");
		if (device.getLaboratory()!=null)
			throw new IllegalArgumentException("Device is already in another laboratory");
		this.devices.add(device);
	}
	
	/**
	 * Remove the given device from the set of devices
	 * attached to this laboratory.
	 * 
	 * @param	device
	 * 			The device to be removed.
	 * @post	This device does not have the given device as
	 * 			one of its devices.
	 * 			| ! new.hasAsDevice(device)
	 * @post	If this laboratory has the given device in it,
	 * 			the given device is no longer attached to any laboratory.
	 * 			| if (hasAsDevice(device))
	 * 			|	then ((new device).getLaboratory() == null)
	 */
	public void removeAsDevice(Device device) {
		if (hasAsDevice(device))
			this.devices.remove(device);
	}
	
	/**
	 * Return the of this laboratory.
	 * 
	 * @throws CapacityException
	 * 		   This laboratory does not contain a cooling box
	 *         | getDeviceAt(0) == null
	 */
	public Device getDevice(Class<?> deviceClass) throws ClassCastException, CapacityException {
		for (Device device:this.devices) {
			if (device.getClass()==(deviceClass.asSubclass(Device.class))) {
				return device;
			}
		}
		throw new CapacityException(this,"Device not found.");
	}
	
	/**
	 * A set containing the devices of this laboratory.
	 * 
	 * @invar	The set of devices is effective.
	 * 			| devices != null
	 * @invar	Each device in the set of devices references
	 * 			a device that is an acceptable device for
	 * 			this laboratory.
	 * 			| for each device in devices:
	 * 			| 	canHaveAsDevice(device)
	 * @invar	Each device in the set of devices references
	 * 			this laboratory as its laboratory.
	 * 			| for each device in devices:
	 * 			|	(devices.getLaboratory() == this)
	 */
	private Set<Device> devices = new HashSet<Device>();
	
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
				Oven oven = (Oven) (getDevice(Oven.class));
				oven.setTemperature(new Temperature ((long) (container.getContents().getStandardTemperatureObject().getColdness()*1.05d),
						(long) (container.getContents().getStandardTemperatureObject().getHotness()*1.05d)));
				oven.loadIngredient(container);
				oven.process();
				container = oven.emptyDevice();
			}
			CoolingBox coolingBox = (CoolingBox) getDevice(CoolingBox.class);
			coolingBox.setTemperature(container.getContents().getStandardTemperatureObject());
			coolingBox.loadIngredient(container);
			coolingBox.process();
			container = coolingBox.emptyDevice();
		}
	}

	/**
	 * Variable storing the list of devices in this laboratory
	 */
	//private List<Device> devices = new ArrayList<Device>(4);
	
	
	/**************************************************
	 * Execute
	 **************************************************/
	
	/**
	 * Execute the given recipe a given amount of times.
	 *  
	 * @param recipe
	 * 		  The given recipe to execute.
	 * @param amount
	 * 		  The given amount of times.
	 */
	public void execute(Recipe recipe, int amount) {
		
	}
	
	/**************************************************
	 * Termination
	 **************************************************/
	
	/**
	 * Terminate this laboratory.
	 * 
	 * @post	This laboratory is terminated.
	 * 			| new.isTerminated()
	 * @post	No device is attached to this laboratory anymore.
	 * 			| new.getNbDevices() == 0
	 * @effect	Each non-terminated device is removed from this
	 * 			laboratory.
	 */
	@Basic @Raw
	public void terminate() {
		
		for (Device device: this.devices) {
			device.setLaboratory(null);
			removeAsDevice(device);
		}
		this.terminated = true;
	}
	
	/**
	 * Check whether this laboratory is terminated.
	 */
	@Basic
	public boolean isTerminated() {
		return this.terminated;
	}
	
	/**
	 * A variable for the termination of this laboratory.
	 */
	private boolean terminated = true;
}
