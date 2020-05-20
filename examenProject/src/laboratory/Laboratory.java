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
	
	public Laboratory(int capacity, ArrayList<AlchemicIngredient> storage) {
		this.capacity = capacity;
		this.storage = storage;
	}
	
	public Laboratory(int capacity) {
		this(capacity, new ArrayList<AlchemicIngredient>());
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
	public void store(IngredientContainer container) throws ItemFullException {
		if(this.getStorageQuantity() + container.getContentQuantity() > this.capacity) {
			throw new ItemFullException(container, this);
		}
		AlchemicIngredient ingredient = container.getIngredient();
		makeStandardTemp(ingredient);

		AlchemicIngredient newIngredient = null;
		for(AlchemicIngredient storedIngredient : storage) {
			if(storedIngredient.getType() == ingredient.getType()) {
				IngredientContainer storedContainer = request(storedIngredient.getType().getSimpleName()); //TODO ALS DIT MEER IS DAN EEN CHEST OF BARREL KAN DIT FOUT LOPEN 
				this.kettle.loadIngredient(storedContainer);
				this.kettle.loadIngredient(container);
				this.kettle.process();
				newIngredient = this.kettle.getProcessedIngredients().get(kettle.getProcessedIngredients().size() -1);
				//ingredient = new AlchemicIngredient(ingredient.getType(), ingredient.getQuantity() + storedIngredient.getQuantity()); //TODO DIT DOEN MET KETTLE WANNEER KETTLE KLAAR IS
				//storage.remove(storedIngredient);
			}
		}
		storage.add(newIngredient);
	}
	
	/**
	 * Request a given amount of an alchemic ingredient by giving either the special of the simple name.
	 * If the given amount of greater than the capacity of a barrel or chest depending on the state of the requested ingredient,
	 * this returns a barrel or chest and the leftovers are deleted.
	 * The given amount cannot be greater than the quantity this laboratory contains and the laboratory has to contain the requested name
	 * 
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
	 * 		   Theislaboratory does not contain enough of the requested item
	 * 		   | storedIngredient.getQuantity() < amount
	 * @throws IllegalArgumentException
	 * 		   This laboratory does not contain an ingredient with the given name
	 * 		   | !((storedIngredient.getType().getSimpleName() == name) ||
	 * 		   |  (storedIngredient.getType().getSpecialName() == name))
	 */
	public IngredientContainer request(String name, int amount) throws ItemEmptyException, IllegalArgumentException{
		IngredientContainer newContainer = null;
		Container containerType = null;
		for(AlchemicIngredient storedIngredient : storage) {
			if((storedIngredient.getType().getSimpleName() == name) || (storedIngredient.getType().getSpecialName() == name)){
				if(storedIngredient.getQuantity() < amount) {
					throw new ItemEmptyException(this);
				}
				
				if(storedIngredient.getType().getState() == State.LIQUID) {
					if(amount > 10080) {
						storedIngredient = new AlchemicIngredient(storedIngredient.getType(), 10080);
						amount = 10080;
					}
					containerType = LiquidQuantity.getContainer(amount);
				}

				
				if(storedIngredient.getType().getState() == State.POWDER) {
					if(amount > 7560) {
						storedIngredient = new AlchemicIngredient(storedIngredient.getType(), 7560);
						amount = 7560;
					}
					containerType = PowderQuantity.getContainer(amount);
				}
				AlchemicIngredient newIngredient = new AlchemicIngredient(storedIngredient.getType(), amount);
				if(storedIngredient.getQuantity()-amount > 0) {
					AlchemicIngredient newStoredIngredient = new AlchemicIngredient(storedIngredient.getType(), storedIngredient.getQuantity()-amount);
					storage.add(newStoredIngredient);
				}
				storage.remove(storedIngredient);
				newContainer = new IngredientContainer(newIngredient, containerType);
			}
			break;
		}
		if(newContainer == null) {
			throw new IllegalArgumentException("Ingredient not in laboratory");
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
		for(AlchemicIngredient storedIngredient : storage) {
			if((storedIngredient.getType().getSimpleName() == name) || (storedIngredient.getType().getSpecialName() == name)){
				request(name, storedIngredient.getQuantity());
			}
			break;
		}
		return null;
	}
	
	public void getOverview() {
		
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
	private void makeStandardTemp(AlchemicIngredient ingredient) {
		long tempDiff = Temperature.temperatureDifference(ingredient.getStandardTemperatureObject(), ingredient.getTemperatureObject());
		if(tempDiff > 0) {
			oven.setTemperature(ingredient.getStandardTemperature());
			oven.setStartIngredient(ingredient);
			oven.process();
		} else if(tempDiff < 0) {
			coolingbox.setTemperature(ingredient.getStandardTemperature());
			coolingbox.setStartIngredient(ingredient);
			coolingbox.process();
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
	
	private CoolingBox coolingbox = new CoolingBox(new Temperature(0,0));
	
	private Oven oven = new Oven(new Temperature(0,0));
	
	private Kettle kettle = new Kettle();
	
	private Transmogrifier transmogrifier = new Transmogrifier();
}
