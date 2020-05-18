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
		setTemperature(temperature);
		loadIngredient(startIngredient);
	}
	
	/**
	 * Andere constructor //TODO
	 * @param temperature
	 */
	@Raw
	public CoolingBox(Temperature temperature) {
		setTemperature(temperature);
	}

	@Override
	public void process() {
		Temperature.compareTemperature(getStartIngredient().getStandardTemperatureObject(),getTemperatureObject());
	}

}
