package laboratory;

import java.util.ArrayList;
import alchemy.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class describing a laboratory for storing and handling alchemic ingredients and devices
 * 
 * @invar	Each laboratory must have a valid capacity
 * 			| isValidCapacity(getCapacity())
 * @invar 	The storage of a laboratory cannot contain duplicate ingredient types
 * 			| hasProperIngredients()
 * 
 * @version  1.0
 * @author   Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

public class Laboratory {
	
	/**************************************************
	 * CONSTRUCTORS
	 **************************************************/
	/**
	 * Initialize a new laboratory with the given capacity, storage and devices
	 * 
	 * @param capacity
	 * 	      The given capacity in storerooms
	 * @param storage
	 * 		  The given ArrayList with the storage in this laboratory
	 * @param coolingbox
	 * 	      The cooling box in this laboratory
	 * @param oven
	 * 		  The oven in this laboratory
	 * @param kettle
	 * 		  The kettle in this laboratory
	 * @param transmogrifier
	 * 		  The transmogrifier in this laboratory
	 * @post  The capacity of this laboratory is set to the given capacity in storerooms
	 * 		  | getCapacity() == capacity
	 * @post  The storage of this laboratory is set to the given capacity
	 *        | getCoolingbox == coolingbox
	 *        | getOven == oven
	 *        | getKettle == kettle
	 *        | getTransmogrifier == transmogrifier
	 * @throws CapacityException
	 * 		   The given capacity is invalid
	 * 		   | !isValidCapacity(capacity)
	 */
	@Raw
	public Laboratory(int capacity, ArrayList<AlchemicIngredient> storage, CoolingBox coolingbox, Oven oven, Kettle kettle, Transmogrifier transmogrifier) throws CapacityException {
		if(!isValidCapacity(capacity)) {
			throw new CapacityException(this);
		}
		this.capacity = capacity;
		setStorage(storage);
		
		addAsDevice(coolingbox);
		addAsDevice(oven);
		addAsDevice(kettle);
		addAsDevice(transmogrifier);
	}
	
	/**
	 * Initialize a new laboratory with the given capacity, an empty storage and no devices
	 * 
	 * @param capacity
	 * 	      The given capacity
	 * 		  | getCapacity() == capacity
	 * @effect The new laboratory is initialized with the given capacity
	 * 		   it's storage is empty and the devices are set to null
	 * 		   | this(capacity, new ArrayList<AlchemicIngredient>(), null, null, null, null)
	 */
	@Raw
	public Laboratory(int capacity) {
		this(capacity, new ArrayList<AlchemicIngredient>(), null, null, null, null);
	}
	
	/**************************************************
	 * CAPACITY
	 **************************************************/
	
	/**
	 * Return the capacity of this laboratory in storerooms
	 */
	@Basic
	public int getCapacity() {
		return this.capacity;
	}

	/**
	 * Check whether the given capacity is a valid capacity for a laboratory
	 * 
	 * @param capacity
	 * 		  The given capacity
	 * @return True if and only if the given capacity is not less than 0 or greater than the maximum interger value
	 *         | result == ((capacity >= 0) && (capacity <= Integer.MAX_VALUE))
	 */
	public static boolean isValidCapacity(int capacity) {
		return((capacity >= 0) && (capacity <= Integer.MAX_VALUE));
	}
	
	/**
	 * Variable storing the capacity of this laboratory in storerooms
	 */
	private final int capacity;
	
	/**************************************************
	 * STORAGE
	 **************************************************/

	/**
	 * Return the storage of this laboratory
	 */
	@Basic
	public ArrayList<AlchemicIngredient> getStorage(){
		return this.storage;
	}
	
	/**
	 * Set the storage list of this laboratory to the given storage list
	 * 
	 * @param storage
	 * 		  The given storage list
	 */
	private void setStorage(ArrayList<AlchemicIngredient> storage) {
		this.storage = storage;
	}
	
	/**
	 * Return the quantity that is contained in this laboratory
	 */
	public int getStorageQuantity() {
		int quantity = 0;
		for(AlchemicIngredient ingredient : storage) {
			quantity = quantity + ingredient.getQuantity();
		}
		return quantity;
	}
	
	/**
	 * Return the number of ingredients in this laboratory
	 */
	@Basic
	public int getNbIngredients() {
		return getStorage().size();
	}
	
	@Basic
	public AlchemicIngredient getIngredientAt(int index) {
		return getStorage().get(index);
	}
	
	public void addAsIngredient(AlchemicIngredient ingredient) {
		getStorage().add(ingredient);
	}
	
	public void removeAsIngredient(AlchemicIngredient ingredient) {
		getStorage().remove(ingredient);
	}
	
	public boolean hasProperIngredients() {
		boolean bool = true;
		for(int i=0; i <= getNbIngredients()-2; i++) {
			for(int j = i+1; j < getNbIngredients(); j++) {
				if(getIngredientAt(i).getType().equals(getIngredientAt(j).getType())) {
					bool = false;
				}
			}
		}
		return bool;
	}

	/**
	 * Store the ingredient contained by the given container in this laboratory. The ingredient gets heated or cooled to it's standard temperature
	 * using the oven or cooling box in this laboratory. If this laboratory already contains an ingredient with the same type, the ingredients get
	 * mixed using the kettle.
	 * 
	 * @param container
	 * 		  The given container
	 * 
	 */
	public void store(IngredientContainer container) throws CapacityException {
		if(getStorageQuantity() + container.getContentQuantity() > this.capacity) {
			throw new CapacityException(container, this);
		}

		makeStandardTemp(container);
		AlchemicIngredient ingredient = container.getIngredient();

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
	 * @param name
	 * 		  The special or simple name of the requested ingredient
	 * @param amount
	 * 		  The given amount
	 * @effect If this laboratory contains an alchemic ingredient with the given special or simple name and
	 * 		   if the given amount is not greater than the quantity of this alchemic ingredient and
	 * 		   if the given amount is not greater than a barrel or chest depending on the state of the requested ingredient,
	 * 		   the smallest possible container containing this ingredient with the given amount is created
	 * 		   | for(storedIngredient : storage)
	 * 		   |  if((requestedIngredientName == storedIngredientName) && (storedIngredient.getQuantity() >= amount) &&
	 *         |     (amount <= Barrel or Chest quantity))
	 * 		   | 		newContainer = new IngredientContainer(newIngredient, containerType);
	 * 		   |        storage.remove(requested storedIngredient)
	 *  	   If the given amount is greater than a barrel or chest depending on the state of the requested ingredient,
	 *  	   A barrel or chest is created and the leftovers are deleted
	 *  	   | if(amount > barrel or chest quantity)
	 *  	   |  storedIngredient = new AlchemicIngredient(storedIngredient.getType(), barrel or chest quantity)
	 *  	   |  newIngredient = new AlchemicIngredient(storedIngredient.getType(), barrel or chest quantity)
	 *  	   |  newContainer = new IngredientContainer(newIngredient, containerType)
	 *         |  storage.remove(requested storedIngredient)
	 * @throws ItemEmptyException
	 * 		   This laboratory does not contain enough of the requested item
	 * 		   | storedIngredient.getQuantity() < amount
	 * @throws IllegalArgumentException
	 * 		   This laboratory does not contain an ingredient with the given name
	 * 		   | !((storedIngredient.getType().getSimpleName() == name) ||
	 * 		   |  (storedIngredient.getType().getSpecialName() == name))
	 */
	public IngredientContainer request(String name, int amount) throws CapacityException{
		IngredientContainer newContainer = null;
		Container containerType = null;
		for(AlchemicIngredient storedIngredient : storage) {
			if((storedIngredient.getType().getSimpleName() == name) || (storedIngredient.getType().getSpecialName() == name)){
				
				if(storedIngredient.getType().getState() == State.LIQUID) {
					if(amount > LiquidQuantity.BARREL.getNbOfSmallestUnit()) {
						amount = LiquidQuantity.BARREL.getNbOfSmallestUnit();
					}
					containerType = LiquidQuantity.getContainer(amount);
				}

				if(storedIngredient.getType().getState() == State.POWDER) {
					if(amount > PowderQuantity.CHEST.getNbOfSmallestUnit()) {
						amount = PowderQuantity.CHEST.getNbOfSmallestUnit();
					}
					containerType = PowderQuantity.getContainer(amount);
				}
				
				if(storedIngredient.getQuantity() < amount) {
					throw new CapacityException(this);
				}
				
				AlchemicIngredient newIngredient = new AlchemicIngredient(storedIngredient.getType(), amount);
				if(storedIngredient.getQuantity()-amount > 0) {
					AlchemicIngredient newStoredIngredient = new AlchemicIngredient(storedIngredient.getType(), storedIngredient.getQuantity()-amount);
					addAsIngredient(newStoredIngredient);
				}
				removeAsIngredient(storedIngredient);
				newContainer = new IngredientContainer(newIngredient, containerType);
				
				break;
			}
		}
		if(newContainer == null) {
			throw new CapacityException(this);
		}
		return newContainer;
	}
	
	/**
	 * Request the full amount of an ingredient with the given simple or special name that this laboratory contains
	 * @param name
	 * 		  The given special of simple name of the ingredient
	 * @effect The full quantity of the requested ingredient gets requested
	 * 		   | request(name, storedIngredient.getQuantity())
	 */
	public IngredientContainer request(String name) {
		IngredientContainer newContainer = null;
		for(AlchemicIngredient storedIngredient : getStorage()) {
			if((storedIngredient.getType().getSimpleName() == name) || (storedIngredient.getType().getSpecialName() == name)){
				if(storedIngredient.getType().getState() == State.LIQUID) {
					if(storedIngredient.getQuantity() > LiquidQuantity.BARREL.getNbOfSmallestUnit()) {
						storedIngredient = new AlchemicIngredient(storedIngredient.getType(), LiquidQuantity.BARREL.getNbOfSmallestUnit());
					}
				}
				if(storedIngredient.getType().getState() == State.POWDER) {
					if(storedIngredient.getQuantity() > PowderQuantity.CHEST.getNbOfSmallestUnit()) {
						storedIngredient = new AlchemicIngredient(storedIngredient.getType(), PowderQuantity.CHEST.getNbOfSmallestUnit());
					}
				}
				newContainer = request(name, storedIngredient.getQuantity());
				break;
			}
		}
		if(newContainer == null) {
			throw new CapacityException(this);
		}
		
		return newContainer;
	}
	
	public Object[][] getInventory() {
		Object[][] inventory = new Object[2][getNbIngredients()];
		int index = 0;
		while(index < getNbIngredients()) {
			inventory[0][index] = getIngredientAt(index).getFullName();
			inventory[1][index] = getIngredientAt(index).getQuantity();
		}
		return inventory;
	}
	
	
	/**
	 * Variable storing the contents of this laboratory in drops or pinches
	 */
	private ArrayList<AlchemicIngredient> storage = new ArrayList<AlchemicIngredient>();
	
	/**************************************************
	 * DEVICES
	 **************************************************/
	
	/**
	 * Return the list of devices in this laboratory
	 */
	@Basic
	public ArrayList<Device> getDevices(){
		return this.devices;
	}
	
	@Basic
	public int getNbDevices() {
		return getDevices().size();
	}
	
	@Basic
	public Device getDeviceAt(int index) {
		return getDevices().get(index);
	}
	
	public static boolean isValidDeviceAt(Device device, int index) {
		if(device.getClass() == CoolingBox.class) {
			return(index == 0);
		} else if(device.getClass() == Oven.class) {
			return(index == 1);
		} else if(device.getClass() == Kettle.class) {
			return(index == 2);
		} else if(device.getClass() == Transmogrifier.class) {
			return(index == 3);
		} else {
			return false;
		}
	}
	
	public boolean hasProperDevices() {
		boolean bool = true;
		if((getDeviceAt(0).getClass() != CoolingBox.class) && (getDeviceAt(0) != null)) {
			bool = false;
		}
		if((getDeviceAt(1).getClass() != Oven.class) && (getDeviceAt(1) != null)) {
			bool = false;
		}
		if((getDeviceAt(2).getClass() != Kettle.class) && (getDeviceAt(2) != null)) {
			bool = false;
		}
		if((getDeviceAt(3).getClass() != Transmogrifier.class) && (getDeviceAt(3) != null)) {
			bool = false;
		}
		return bool;
	}
	
	public void addAsDevice(Device device) throws CapacityException {
		if((device.getClass() == CoolingBox.class) && (getDeviceAt(0) == null)) {
			getDevices().add(0, device);
		}
		else if((device.getClass() == Oven.class) && (getDeviceAt(1) == null)) {
			getDevices().add(1, device);
		}
		else if((device.getClass() == Kettle.class) && (getDeviceAt(2) == null)) {
			getDevices().add(2, device);
		}
		else if((device.getClass() == Transmogrifier.class) && (getDeviceAt(3) == null)) {
			getDevices().add(3, device);
		} else {
			throw new CapacityException(this);
		}
	}
	
	public void removeAsDevice(Device device) {
			getDevices().remove(device);
	}
	
	/**
	 * Return the coolingbox in this laboratory
	 * 
	 * @throws CapacityException
	 * 		   This laboratory does not contain a coolingbox
	 *         | if(devices.get(0) ==
	 */
	public CoolingBox getCoolingbox() throws CapacityException {
		if(getDeviceAt(0) == null) {
			throw new CapacityException(getDeviceAt(0));
		}
		return (CoolingBox) getDeviceAt(0);
	}
	
	public Oven getOven() {
		if(getDeviceAt(1) == null) {
			throw new CapacityException(getDeviceAt(1));
		}
		return (Oven) getDeviceAt(1);
	}
	
	public Kettle getKettle() {
		if(getDeviceAt(2) == null) {
			throw new CapacityException(getDeviceAt(2));
		}
		return (Kettle) getDeviceAt(2);
	}
	
	public Transmogrifier getTransmogrifier() {
		if(getDeviceAt(3) == null) {
			throw new CapacityException(getDeviceAt(3));
		}
		return (Transmogrifier) getDeviceAt(3);
	}
	
	/**
	 * Heat or cool the given ingredient to it's standard temperature using the oven or cooling box in this laboratory
	 * If the ingredient is already at it's standard temperature nothing needs to happen
	 * 
	 * @param ingredient
	 * 		  The given ingredient
	 * @effect If the ingredient is hotter than it's standard temperature it gets cooled to it's standard temperature
	 *         | if(temperatureDifference(ingredient.getStandardTemperature(), ingredient.getTemperature()) > 0)
	 *         | 	oven.setTemperature(ingredient.getStandardTemperature())
	 *         | 	oven.setStartIngredient(ingredient)
	 *         | 	oven.process()
	 *         If the ingredient is colder than it's standard temperature it gets heated to it's standard temperature
	 *         | if(temperatureDifference(ingredient.getStandardTemperature(), ingredient.getTemperature()) < 0)
	 *         | 	coolingbox.setTemperature(ingredient.getStandardTemperature())
	 *         |	coolingbox.setStartIngredient(ingredient)
	 *         |	coolingbox.process()
	 */
	private void makeStandardTemp(IngredientContainer container) {
		long tempDiff = Temperature.temperatureDifference(container.getIngredient().getStandardTemperatureObject(), container.getIngredient().getTemperatureObject());
		if(tempDiff != 0) {
			if(tempDiff > 0) {
				getOven().setTemperature(new Temperature ((long) (container.getIngredient().getStandardTemperatureObject().getColdness()*1.05d),
						(long) (container.getIngredient().getStandardTemperatureObject().getHotness()*1.05d)));
				getOven().loadIngredient(container);
				getOven().process();
				container = getOven().emptyDevice();
			}
			getCoolingbox().setTemperature(container.getIngredient().getStandardTemperatureObject());
			getCoolingbox().loadIngredient(container);
			getCoolingbox().process();
			container = getCoolingbox().emptyDevice();
		}
	}

	private ArrayList<Device> devices = new ArrayList<Device>();
}
