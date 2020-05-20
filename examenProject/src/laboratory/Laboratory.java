package laboratory;

import java.util.ArrayList;
import alchemy.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class describing a laboratory for storing and handling alchemic ingredients and devices
 * 
 * @version  1.0
 * @author   Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

public class Laboratory {
	
	public Laboratory(int capacity, ArrayList<AlchemicIngredient> storage, CoolingBox coolingbox, Oven oven, Kettle kettle, Transmogrifier transmogrifier) {
		this.capacity = capacity;
		this.storage = storage;
		
		devices.add(coolingbox);
		devices.add(oven);
		devices.add(kettle);
		devices.add(transmogrifier);
	}
	
	public Laboratory(int capacity) {
		this(capacity, new ArrayList<AlchemicIngredient>(), null, null, null, null);
	}
	
	@Basic
	public CoolingBox getCoolingbox() throws CapacityException {
		if(devices.get(0) == null) {
			throw new CapacityException(devices.get(0));
		}
		return (CoolingBox) devices.get(0);
	}
	
	@Basic
	public Oven getOven() {
		if(devices.get(1) == null) {
			throw new CapacityException(devices.get(1));
		}
		return (Oven) this.devices.get(1);
	}
	
	@Basic
	public Kettle getKettle() {
		if(devices.get(2) == null) {
			throw new CapacityException(devices.get(2));
		}
		return (Kettle) this.devices.get(2);
	}
	
	@Basic
	public Transmogrifier transmogrifier() {
		if(devices.get(3) == null) {
			throw new CapacityException(devices.get(3));
		}
		return (Transmogrifier) this.devices.get(3);
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
				storage.remove(storedIngredient);
				break;
			}
		}
		storage.add(ingredient);
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
					storage.add(newStoredIngredient);
				}
				storage.remove(storedIngredient);
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
		for(AlchemicIngredient storedIngredient : storage) {
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
		Object[][] inventory = new Object[2][storage.size()];
		int index = 0;
		while(index < storage.size()) {
			inventory[0][index] = storage.get(index).getFullName();
			inventory[1][index] = storage.get(index).getQuantity();
		}
		return inventory;
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
			getCoolingbox().setTemperature(container.getIngredient().getStandardTemperature());
			getCoolingbox().loadIngredient(container);
			getCoolingbox().process();
			container = getCoolingbox().emptyDevice();
		}
	}
	
	/**
	 * Variable storing the contents of this laboratory in drops or pinches
	 */
	private ArrayList<AlchemicIngredient> storage = new ArrayList<AlchemicIngredient>();
	
	/**
	 * Variable storing the capacity of this laboratory in storerooms
	 */
	private final int capacity;
	
	private ArrayList<Device> devices = new ArrayList<Device>();
}
