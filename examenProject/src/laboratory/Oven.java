package laboratory;

import alchemy.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class for Oven devices involving a temperature, a start ingredient and a processed ingredient.
 * 
 * //TODO invarianten?
 * 
 * @version	1.0
 * @author	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

public class Oven extends TemperatureDevice {

	/**
	 * Initialize an Oven device with a given temperature and start ingredient.
	 * 
	 * @param	temperature
	 * 			The new temperature to be given to this Oven.
	 * @param	startIngredient
	 * 			The ingredient to be loaded in this Oven.
	 * @effect	The new temperature is given to the new oven.
	 * 			| (new.getTemperatureObject()==temperature)
	 * @effect	The new ingredient is loaded in this new Oven.
	 * 			| (new.getStartIngredient()==startIngredient)
	 */
	@Raw
	public Oven(Temperature temperature, IngredientContainer startIngredient) {
		super(temperature, startIngredient);
	}

	/**
	 * Initialize an Oven device with a given temperature.
	 * 
	 * @param	temperature
	 * 			The new temperature for the Oven.
	 * @effect	The temperature of this Oven is set to the given temperature.
	 * 			| (new.getTemperatureObject()==temperature)
	 */
	@Raw
	public Oven(Temperature temperature) {
		super(temperature);
	}

	/**
	 * Heat the start ingredient to approximately the temperature of this Oven
	 * and put it in the processed ingredient variable.
	 * 
	 * @effect	The temperature of the ingredient is set to a value within 5% of the temperature of the Oven
	 * 			and it is put in the processed ingredient variable.
	 * 			| (((new.getProcessedIngredient().getTemperature() < getTemperature()*1.05)
	 * 			| 	&&(new.getProcessedIngredient().getTemperature() > getTemperature()*.95))
	 * 			| 	&&(new.getStartIngredient()==null))
	 */
	@Override
	public void process() {
		double randomness = 0.95d - (Math.random()*0.1d);
		Temperature newTemperature = new Temperature((long) (getTemperatureObject().getColdness()*randomness),
				(long) (getTemperatureObject().getHotness()*randomness));
		long difference = Temperature.temperatureDifference(newTemperature, getStartIngredient().getTemperatureObject());
		if (difference>0) {
			AlchemicIngredient newIngredient = getStartIngredient();
			newIngredient.heat((long) (difference));
			setProcessedIngredient(newIngredient);
			setStartIngredient(null);
		}

	}

}
