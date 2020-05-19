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
	 * //TODO Exception voor een volle laboratory
	 */
	public void store(IngredientContainer container) throws ItemFullException {
		if(this.getStorageQuantity() + container.getContentQuantity() > this.capacity) {
			throw new ItemFullException(container, this);
		}
		AlchemicIngredient ingredient = container.getIngredient();
		makeStandardTemp(ingredient);

		for(AlchemicIngredient storedIngredient : storage) {
			if(storedIngredient.getType() == ingredient.getType()) {
				ingredient = new AlchemicIngredient(ingredient.getType(), ingredient.getQuantity() + storedIngredient.getQuantity()); //TODO DIT DOEN MET KETTLE WANNEER KETTLE KLAAR IS
				storage.remove(storedIngredient);
			}
		}
		storage.add(ingredient);
	}
	
	/**
	 * Request a given amount of an alchemic ingredient by giving either the special of the simple name.
	 * If the given amount of greater than the capacity of a barrel or chest depending on the state of the requested ingredient,
	 * this returns a barrel or chest and the leftovers are deleted.
	 * If the given amount cannot be greater than the quantity this laboratory contains and the laboratory has to contain the requested name
	 * 
	 * @param name
	 * 		  The special of simple name of the requested ingredient
	 * @param amount
	 * 		  The given amount
	 * @throws ItemEmptyException
	 * 		   
	 * @throws IllegalArgumentException
	 */
	//TODO COMMENTAAR HIERBOVEN AFWERKEN
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
