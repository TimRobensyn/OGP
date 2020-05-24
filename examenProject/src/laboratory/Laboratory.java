package laboratory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import alchemy.*;
import be.kuleuven.cs.som.annotate.*;
import laboratory.device.*;
import recipe.Recipe;

/**
 * A class describing a laboratory for storing and handling alchemic ingredients and devices.
 * 
 * @invar	Each laboratory must have a valid capacity.
 * 			| isValidCapacity(getCapacity())
 * @invar 	The storage of a laboratory must be proper.
 * 			| hasProperStorage()
 * @invar   The device list of a laboratory must be proper. There can only be at most one device of every type.
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
	 * 		  The given storage map of this laboratory which maps an ingredient type to an integer as the quantity.
	 * @param devices
	 * 		  The set of devices that will be stored in this laboratory
	 * @post  The capacity of this laboratory is set to the given capacity in storerooms.
	 * 		  | getCapacity() == capacity
	 * @post  The storage of this laboratory is set to the storage map.
	 *        | this.storage = storage
	 * @post  The set of devices gets set to the given set of devices.
	 * 		  | this.devices = devices
	 * @throws CapacityException
	 * 		   The given capacity is invalid.
	 * 		   | !isValidCapacity(capacity)
	 */
	@Raw
	public Laboratory(int capacity, Map<IngredientType,Integer> storage, Set<Device> devices)
			throws CapacityException{
		if (!isValidCapacity(capacity)) {
			throw new CapacityException(this, "The given capacity is invalid.");
		}
		if (!canHaveAsStorage(storage)) {
			throw new CapacityException(this, "The given storage is invalid.");
		}
		this.capacity = capacity;
		this.storage = storage;
		this.devices = devices;
	}
	
	/**
	 * Initialize a new laboratory with the given capacity and set of devices with an empty storage
	 * 
	 * @param 	capacity
	 * 			The given capacity
	 * @param	devices
	 * 			The set of devices to be added in this new laboratory.
	 * @effect The new laboratory has the given capacity and devices and an empty storage
	 * 		   | this(capacity,new HashMap<IngredientType,Integer>(),devices)
	 */
	@Raw
	public Laboratory(int capacity, Set<Device> devices) {
		this(capacity,new HashMap<IngredientType,Integer>(),devices);
	}
	
	/**
	 * Initialize a new laboratory with the given capacity, an empty storage and no devices.
	 * 
	 * @param capacity
	 * 	      The given capacity.
	 * @effect The new laboratory is initialized with the given capacity.
	 * 		   It's storage is empty and the devices are set to null.
	 * 		   | this(capacity, new HashMap<IngredientType,Integer>(), new HashSet<Device>())
	 */
	@Raw
	public Laboratory(int capacity) {
		this(capacity, new HashMap<IngredientType,Integer>(), new HashSet<Device>());
	}
	
	/**
	 * Initialize a new laboratory with the given capacity and devices with an empty storage
	 * 
	 * @param 	capacity
	 * 			The given capacity
	 * @param	devices //TODO klopt nog niet
	 * 			The set of devices to be added in this new laboratory.
	 * @effect The new laboratory has the given capacity and devices and an empty storage
	 * 		   | this(capacity,new HashMap<IngredientType,Integer>(),devices)
	 */
	@Raw
	public Laboratory(int capacity, Map<IngredientType,Integer> storage) {
		this(capacity,storage,new HashSet<Device>());
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
	 * 
	 * @post The capacity in storerooms of this laboratory is converted to this capacity in spoons.
	 *       | result == (getCapacity()*Unit.STOREROOM_LIQUID.getAbsoluteCapacity())/Unit.SPOON_LIQUID.getCapacity()
	 *       
	 * @note In this case, we make use of the storeroom capacity for liquids, 
	 * 		 but the result is the same if we were to use the storeroom capacity for powders
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
	 * 
	 * @throws	CapacityException
	 * 			The given type is not found in this laboratory's storage.
	 * 			| ! hasAsIngredientType(type)
	 */
	@Basic @Raw
	public int getQuantityOf(IngredientType type) throws CapacityException{
		if (!hasAsIngredientType(type))
			throw new CapacityException(this,"Type not found.");
		return this.storage.get(type).intValue();
	}
	
	/**
	 * Return the number of ingredients in this laboratory.
	 */
	@Basic
	public int getNbIngredients() {
		return this.storage.size();
	}
	
	/**
	 * Return the used capacity of this laboratory in spoons in the form of a double.
	 * 
	 * @return The quantity of the used capacity in spoons.
	 * 		   | for(IngredientType type:this.storage.keySet())
	 * 		   |     usedCapacity += (getQuantityOf(type)/Unit.SPOON_TYPESTATE.getCapacity())
	 * 	 	   | result == usedCapacity
	 */
	@Raw
	public double getUsedCapacity() {
		double usedCapacity = 0;
		for (IngredientType type:this.storage.keySet()) {
			if (type.getState().equals(State.LIQUID)) {
				usedCapacity += ((double)(getQuantityOf(type))/((double)(Unit.SPOON_LIQUID.getCapacity())));
			}
			else if (type.getState().equals(State.POWDER)){
				usedCapacity += ((double)(getQuantityOf(type))/((double)(Unit.SPOON_POWDER.getCapacity())));
			}
		}
		return usedCapacity;
	}
	
	/**
	 * Check whether this class contains the given ingredient type.
	 * 
	 * @param type
	 * 		  The ingredient type to check.
	 * @return True if and only if the storage contains this type.
	 * 		   | this.storage.containsKey(type)
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
	public static boolean isValidIngredientType(IngredientType type) {
		return (type!=null);
	}
	
	/**
	 * Check whether the given quantity is a valid quantity for this type in this laboratory.
	 * 
	 * @param	type
	 * 			The type to check the quantity with.
	 * @param	quantity
	 * 			The quantity for this type to check.
	 * @return	True if and only if the quantity of the given type is bigger
	 * 			than zero and if the capacity of this laboratory isn't
	 * 			exceeded after setting the quantity of the given type to the given quantity.
	 * 			| (quantity>0)
	 * 			|	&& (Sum(getQuantityOf(other types)) + quantity) <= getCapacityInSpoons()
	 */
	@Raw
	public boolean canHaveAsQuantity(IngredientType type,int quantity) {
		if (quantity<=0) return false;
		double usedCapacity = 0;
		for (IngredientType storedType:this.storage.keySet()) {
			if (storedType!=type) {
				if (storedType.getState()==State.LIQUID)
					usedCapacity += getQuantityOf(storedType)/(double)Unit.SPOON_LIQUID.getCapacity();
				else
					usedCapacity += getQuantityOf(storedType)/(double)Unit.SPOON_POWDER.getCapacity();
			}
			else {
				if (storedType.getState()==State.LIQUID)
					usedCapacity += quantity/(double)Unit.SPOON_LIQUID.getCapacity();
				else
					usedCapacity += quantity/(double)Unit.SPOON_POWDER.getCapacity();
			}
		}
		if (usedCapacity>getCapacityInSpoons()) return false;
		return true;
	}
	
	/**
	 * Check whether the given storage is a valid storage for this laboratory.
	 * 
	 * @return	True if and only if the storage is effective, 
	 * 			if all the stored ingredient types and their quantities are valid
	 * 			ingredient types and quantities,
	 * 			and if the total capacity taken by the ingredient's quantities
	 * 			is less than the available capacity of the laboratory.
	 *          | if(this.storage == null) then result == false
	 * 			| for each type in Storage:
	 * 			|	((!isValidIngredientType(type))
	 * 			|	  ||(!canHaveAsQuantity(type)))
	 * 			|     result == false
	 * 			| result == true
	 */
	public boolean canHaveAsStorage(Map<IngredientType,Integer> storage) {
		if(storage == null) return false;
		for (IngredientType type:storage.keySet()) {
			if (!isValidIngredientType(type)) return false;
			if (!canHaveAsQuantity(type,storage.get(type))) return false;
		}
		return true;
	}
	
	/**
	 * Check whether the storage of this laboratory is a valid
	 * storage for this laboratory.
	 * 
	 * @return	True if and only if the storage is effective, 
	 * 			if all the stored ingredient types and their quantities are valid
	 * 			ingredient types and quantities,
	 * 			and if the total capacity taken by the ingredient's quantities
	 * 			is less than the available capacity of the laboratory.
	 *          | if(this.storage == null) then result == false
	 * 			| for each type in Storage:
	 * 			|	((!isValidIngredientType(type))
	 * 			|	  ||(!canHaveAsQuantity(type)))
	 * 			|     result == false
	 * 			| result == true
	 */
	public boolean hasProperStorage() {
		if(this.storage == null) return false;
		for (IngredientType type:this.storage.keySet()) {
			if (!isValidIngredientType(type)) return false;
			if (!canHaveAsQuantity(type,getQuantityOf(type))) return false;
		}
		return true;
	}
	
	/**
	 * Change the quantity of an ingredient type in the storage of this
	 * laboratory.
	 * 
	 * @param	type
	 * 			The ingredient type to change the quantity of.
	 * @param	newQuantity
	 * 			The new quantity to be given to this ingredient type.
	 * @post	If the storage of this laboratory has the given type as an ingredient type and if it can contain the given quantity,
	 * 			the quantity of this ingredient type in the storage gets set to the given quantity.
	 *          | this.storage.put(type, newQuantity)
	 * @throws	CapacityException
	 * 			The given type is not found in this laboratory's storage or the storage cannot contain the given quantity.
	 * 			| ! hasAsIngredientType(type)
	 * 			| ! canHaveAsQuantity(type, newQuantity)
	 */
	private void setIngredientQuantity(IngredientType type,int newQuantity) throws CapacityException{
		if (!hasAsIngredientType(type))
			throw new CapacityException(this, "Type not found.");
		if (!canHaveAsQuantity(type,newQuantity))
			throw new CapacityException(this, "This type cannot have the given quantity.");
		this.storage.put(type, newQuantity);
		
	}
	
	/**
	 * Add an ingredient type with an associated quantity to this
	 * laboratory's storage or add a given quantity to an ingredient type
	 * in this storage.
	 * 
	 * @param	type
	 * 			The ingredient type to be added.
	 * @param	quantity
	 * 			The quantity of the ingredient type to be added.
	 * @post	If this laboratory already contains a quantity of the given ingredient type,
	 * 			the quantity of this type is incremented with the given quantity.
	 * 			| if(hasAsIngredientType(type))
	 * 			|	 then setIngredientQuantity(type, getQuantityOf(type)+quantity)
	 * 			If this laboratory does not yet contain the given ingredient type, the ingredient type and quantity get added to the storage.
	 * 			| this.storage.put(type, quantity)
	 * @throws  CapacityException
	 * 			This laboratory cannot contain the given quantity
	 * 			| !canHaveAsQuantity(type, quantity)
	 */
	private void addIngredientType(IngredientType type, int quantity) 
			throws CapacityException {
		if (hasAsIngredientType(type)) {
			setIngredientQuantity(type, getQuantityOf(type)+quantity);
		}
		else {
			if (!canHaveAsQuantity(type, quantity))
				throw new CapacityException(this, "Invalid quantity");
			this.storage.put(type, quantity);
		}
		//TODO
	}
	
	/**
	 * Remove a given amount of a given ingredient type from this laboratory's storage.
	 * 
	 * @param	type
	 * 			The ingredient type to remove.
	 * @param	quantity
	 * 			The quantity of the ingredient type to be removed.
	 * @post	If the new quantity is 0, the ingredient type is removed
	 * 			from the storage.
	 * 			| if (getQuantityOf(type)==quantity)
	 * 			| 	  then this.storage.remove(type)
	 * 			If there is some quantity left, the new quantity of the
	 * 			ingredient type in this storage is the old quantity
	 * 			decremented with the given quantity.
	 * 			| if (getQuantityOf(type)>quantity)
	 * 			|     then (new.getQuantityOf(type)==old.getQuantityOf(type)-quantity)
	 * @throws	CapacityException
	 * 			The given quantity is greater than the quantity of the given type.
	 * 			| getQuantityOf(type)<quantity
	 */
	private void removeIngredientType(IngredientType type, int quantity) throws CapacityException {
		if (getQuantityOf(type)==quantity) {
			this.storage.remove(type);
		}
		else if (getQuantityOf(type)>quantity) {
			this.storage.put(type, getQuantityOf(type)-quantity);
		}
		else
			throw new CapacityException(this, "Tried to remove too much of an ingredient");
	}
	
	/**
	 * Remove a given ingredient type from this laboratory's storage.
	 * 
	 * @param	type
	 * 			The ingredient type to remove.
	 * @effect  The full quantity of the given ingredient type gets removed from the storage.
	 * 			| removeIngredientType(type, getQuantityOf(type)) 
	 */
	private void removeIngredientType(IngredientType type) {
		removeIngredientType(type,getQuantityOf(type));
	}
	
	/**
	 * A map containing the ingredient types of this laboratory as keys
	 * and their quantities as values.
	 * 
	 * @invar	The map is effective.
	 * 			| storage != null
	 * @invar	All the ingredient types and their quantities are valid for this
	 * 			laboratory.
	 * 			| for each type in storage:
	 * 			| 	(isValidIngredientType())
	 * 			|	  && (canHaveAsQuantity(getQuantityOf(type)))
	 * @invar	The capacity taken up by all the ingredients does not exceed
	 * 			the capacity of this laboratory.
	 * 			| getUsedCapacity()<=getCapacityInSpoons()
	 */
	private Map<IngredientType,Integer> storage = new HashMap<IngredientType,Integer>();
	
	
	/**
	 * Store the ingredient contained by the given container in this laboratory.
	 * The old container is destroyed.
	 * 
	 * @param	container
	 * 			The given container.
	 * @effect  The ingredient type of the ingredient in the given container gets added as an ingredient type to the storage
	 * 		    of this laboratory and the given container gets deleted.
	 * 			| addIngredientType(ingredient.getType(), ingredient.getQuantity())
	 * 		 	| container = null
	 * @throws  CapacityException
	 * 			The temperature of the ingredient in the given container is not equal to the standard temperature
	 * 			of this ingredient and this laboratory does not contain an oven and coolingbox.
	 * 			| (!ingredient.getTemperatureObject().equals(ingredient.getStandardTemperatureObject()))
	 * 			|  && (! (hasAsDevice(Oven.class) && hasAsDevice(CoolingBox.class)))
	 * @throws	CapacityException
	 *			This laboratory already contains an ingredient of the same type and there is no kettle present.
	 *			| (hasAsIngredientType(ingredient.getType()) && !hasAsDevice(Kettle.class))
	 */
	public void store(IngredientContainer container) throws CapacityException {
		AlchemicIngredient ingredient = container.getContents();
		if ( !ingredient.getTemperatureObject().equals(ingredient.getStandardTemperatureObject()))
			if (! ((hasAsDevice(Oven.class))
					&& hasAsDevice(CoolingBox.class)))
				throw new CapacityException(this,"This laboratory doesn't have the necessary devices "
						+ "to bring this ingredient to its standard temperature for storage.");
		//TODO splits dit nog op
		if (hasAsIngredientType(ingredient.getType()))
			if (! hasAsDevice(Kettle.class))
				throw new CapacityException(this,"This laboratory doesn't have the necessary devices "
						+ "to mix this ingredient with the stored ingredient.");
		addIngredientType(ingredient.getType(),ingredient.getQuantity());
		container = null;
	}
	
	/**
	 * Request a given amount of an alchemic ingredient by giving either the special or simple name.
	 * 
	 * @param	name
	 * 			The special or simple name of the requested ingredient.
	 * @param	amount
	 * 			The given amount.
	 * @effect  If this laboratory contains an alchemic ingredient with the given special or simple name
	 * 			a new container is created containing a new alchemic ingredient with the ingredient type of the ingredient with the
	 * 			given special or simple name and the given quantity. The given amount of this ingredient type gets removed from the stored ingredient types
	 * 			| for each type in storage.ketSet()
	 * 			|     if((type.getSimpleName().equals(name)) || (type.getSpecialName().equals(name)))
	 * 			|        then newIngredient == new AlchemicIngredient(type, amount)
	 * 			|			  newContainer == Unit.getContainer(type.getState(), amount)
	 * 			|             removeIngredientType(type, amount)
	 * 			|			  new IngredientContainer(newIngredient, newContainer)
	 * @throws	CapacityException
	 * 			This laboratory does not contain enough of the requested item or no container is big enough to hold
	 * 			the requested amount.
	 * 			| getQuantityOf(type) < amount
	 * 			| Unit.getBiggestContainer(type.getState()).getAbsoluteCapacity() < amount
	 * @throws	CapacityException
	 * 			This laboratory does not contain an ingredient with the given name.
	 * 			| for each type in storage.ketSet()
	 * 			|     (!type.getSimpleName().equals(name)) && (!type.getSpecialName().equals(name))
	 */
	public IngredientContainer request(String name, int amount) throws CapacityException{
		for(IngredientType type : this.storage.keySet()) {
			if((type.getSimpleName().equals(name)) || (type.getSpecialName().equals(name))){
				
				if(getQuantityOf(type) < amount) {
					throw new CapacityException(this, "Not enough of this ingredient.");
				}
				
				if (Unit.getBiggestContainer(type.getState()).getAbsoluteCapacity() < amount) {
					throw new CapacityException(this, "No container for an amount this big.");
				}
				
				AlchemicIngredient newIngredient = new AlchemicIngredient(type, amount);
				Unit newContainer = Unit.getContainer(type.getState(), amount);
				
				removeIngredientType(type,amount);
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
	 * 		   If the quantity of the requested ingredient exceeds the capacity of a barrel or chest depending on the state
	 * 		   a barrel or chest is returned and the leftovers are deleted.
	 * 		   | for each type in storage.keySet()
	 * 		   |    if(Unit.getBiggestContainer(type.getState()).getAbsoluteCapacity() < getQuantityOf(type))
	 *  	   |    then
	 *  	   |       newContainer = Unit.getBiggestContainer(type.getState())
	 *  	   |       newIngredient = new AlchemicIngredient(type, Unit.getBiggestContainer(type.getState()).getAbsoluteCapacity())
	 * 		   |       !hasAsIngredientType(type)
	 * 		   |       new IngredientContainer(newIngredient, newContainer)
	 * 		   |    newContainer = Unit.getContainer(type.getState(), getQuantityOf(type))
	 * 		   |    newIngredient = new AlchemicIngredient(type, getQuantityOf(type))
	 * 		   |    !hasAsIngredientType(type)
	 * 		   |    new IngredientContainer(newIngredient, newContainer) 
	 * @throws CapacityException
	 * 		   This laboratory does not contain an ingredient with the given special or simple name
	 *         | for each type in storage.keySet()
	 *         |    (!type.getSimpleName().equals(name)) && (!type.getSpecialName.equals(name))
	 */
	public IngredientContainer request(String name) throws CapacityException{
		for(IngredientType type : this.storage.keySet()) {
			if((type.getSimpleName().equals(name)) || (type.getSpecialName().equals(name))){
				
				if (Unit.getBiggestContainer( type.getState() ).getAbsoluteCapacity() < getQuantityOf(type)) {
					Unit newContainer = Unit.getBiggestContainer(type.getState());
					AlchemicIngredient newIngredient = new AlchemicIngredient(type,
							Unit.getBiggestContainer(type.getState()).getAbsoluteCapacity());
					removeIngredientType(type);
					return new IngredientContainer(newIngredient,newContainer);					
				}
				else {				
					Unit newContainer = Unit.getContainer(type.getState(), getQuantityOf(type));
					AlchemicIngredient newIngredient = new AlchemicIngredient(type, getQuantityOf(type));
					removeIngredientType(type);
					return new IngredientContainer(newIngredient, newContainer);
				}
			}
		}
		throw new CapacityException(this, "Ingredient not found.");
	}
	
	/**
	 * Return an two dimensional object array with the inventory of this laboratory. 
	 * The first row contains the simple name of the ingredients, the second row contains their quantity.
	 */
	public Object[][] getInventory() {
		IngredientType[] inventoryTypes = this.storage.keySet().toArray(new IngredientType[0]);
		Object[][] inventory = new Object[2][inventoryTypes.length];
		for (int i = 0; i<inventoryTypes.length;i++) {
			inventory[0][i] = inventoryTypes[i].getSimpleName();
			inventory[1][i] = getQuantityOf(inventoryTypes[i]);
		}
		return inventory;
	}

	
	/**************************************************
	 * DEVICES
	 **************************************************/
	
	/**
	 * Check whether the given device can be found in this laboratory.
	 * 
	 * @param	device
	 * 			The device to be checked.
	 * @return  True if and only if the set of devices contains this device
	 * 			| result == this.devices.contains(device)
	 */
	@Basic @Raw
	public boolean hasAsDevice(Device device) {
		return this.devices.contains(device);
	}
	
	/**
	 * Check whether the given kind of device can be found in this
	 * laboratory.
	 * 
	 * @param	deviceClass
	 * 			The class of the devices to check.
	 * @return  True if and only if this laboratory contains a device from the given deviceClass
	 * 			| for each device in devices
	 * 			|    if device.getClass() == deviceClass.asSubclass(Device.class)
	 * 			|       then result == true
	 * 			| result == false
	 */
	public boolean hasAsDevice(Class<?> deviceClass) {
		for (Device device:this.devices) {
			if (device.getClass()==(deviceClass.asSubclass(Device.class))) {
				return true;
			}
		}
		return false;
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
	 * 			| for each device in Devices:
	 * 			|	  if((!isValidDevice(device)) && (device.getLaboratory() == this))
	 * 			|     then result == false
	 * 			| result == true
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
		device.setLaboratory(this);
	}
	
	/**
	 * Remove the given device from the set of devices
	 * attached to this laboratory.
	 * 
	 * @param	device
	 * 			The device to be removed.
	 * @post	This laboratory does not have the given device as
	 * 			one of its devices.
	 * 			| !new.hasAsDevice(device)
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
	 * Return the device in the set of devices in this laboratory with the given deviceClass.
	 * 
	 * @throws CapacityException
	 * 		   This laboratory does not contain a device of this class
	 * 		   | for each device in devices
	 * 		   |     device.getClass() != deviceClass.asSubclass(Device.class)
	 */
	public Device getDevice(Class<?> deviceClass) throws CapacityException {
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
	 * 			| 	isValidDevice(device)
	 * @invar	Each device in the set of devices references
	 * 			this laboratory as its laboratory.
	 * 			| for each device in devices:
	 * 			|	(devices.getLaboratory() == this)
	 */
	private Set<Device> devices = new HashSet<Device>();
	
	
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
	 *          | for each device in devices
	 *          |     device.getLaboratory() == null
	 *          |     !hasAsDevice(device)
	 */
	@Basic @Raw
	public void terminate() {
		Set<Device> toRemove = new HashSet<Device>();
		for (Device device: this.devices) {
			device.setLaboratory(null);
			toRemove.add(device);
		}
		this.devices.removeAll(toRemove);
		this.isTerminated = true;
	}
	
	/**
	 * Check whether this laboratory is terminated.
	 */
	@Basic
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	/**
	 * A variable for the termination of this laboratory.
	 */
	private boolean isTerminated = true;
}
