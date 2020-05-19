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
	
	/**
	 * Store the ingredient contained by the given container in this laboratory. The ingredient gets heated or cooled to it's standard temperature
	 * using the oven or coolingbox in this laboratory. If this laboratory already contains an ingredient with the same type, the ingredients get
	 * mixed using the kettle.
	 * 
	 * @param container
	 * 		  The given container
	 * //TODO Exception voor een volle laboratory
	 */
	public void store(IngredientContainer container) {
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
	
	//TODO Commentaar
	//TODO Exception wanneer deze hoeveelheid niet aanwezig is of als dit ingredient niet aanwezig is
	//TODO Wanneer amount groter is dan een barrel of chest gaat de overschot verloren
	public IngredientContainer request(String name, int amount) {
		IngredientContainer newContainer = null;
		for(AlchemicIngredient storedIngredient : storage) {
			if((storedIngredient.getType().getSimpleName() == name) || (storedIngredient.getType().getSpecialName() == name)){
				Container containerType = LiquidQuantity.getContainer(amount);
				if(storedIngredient.getType().getState() == State.POWDER) {
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
	private final Container capacity;
	
	private CoolingBox coolingbox = new CoolingBox(new Temperature(0,0));
	
	private Oven oven = new Oven(new Temperature(0,0));
	
	private Kettle kettle = new Kettle();
	
	private Transmogrifier transmogrifier = new Transmogrifier();
}
