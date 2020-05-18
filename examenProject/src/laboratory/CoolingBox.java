package laboratory;

import be.kuleuven.cs.som.annotate.*;
import alchemy.*;

/**
 * A subclass of Device for cooling boxes.
 * 
 * @version	1.0
 * @author	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

public class CoolingBox extends TemperatureDevice {
	
	/**
	 * Prachtige constructor //TODO
	 * @param temperature
	 * @param ingredient
	 */
	@Raw
	public CoolingBox(Temperature temperature, IngredientContainer startIngredient) {
		super(temperature,startIngredient);
	}
	
	/**
	 * Andere constructor //TODO
	 * @param temperature
	 */
	@Raw
	public CoolingBox(Temperature temperature) {
		super(temperature);
	}

	/**
	 * If the ingredient is hotter than //TODO
	 */
	@Override
	public void process() {
		long difference = Temperature.temperatureDifference(getStartIngredient().getTemperatureObject(), getTemperatureObject());
		if(difference>0) {
			AlchemicIngredient newIngredient = getStartIngredient();
			newIngredient.cool(difference);
			setProcessedIngredient(newIngredient);
			setStartIngredient(null);
		}
	}

}
