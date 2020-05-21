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
	 * Initialize a cooling box with the given temperature and startIngredient.
	 * 
	 * @param	temperature
	 * 			The new temperature object to give to this new cooling box.
	 * @param	ingredient
	 * 			The new ingredient container to load in this new cooling box.
	 * @effect	The new cooling box is a temperature device with a the given temperature and startIngredient.
	 * 			| super(temperature,startIngredient)
	 */
	@Raw
	public CoolingBox(Temperature temperature, IngredientContainer startIngredient) {
		super(temperature,startIngredient);
	}
	
	/**
	 * Initialize a cooling box with the given temperature and startIngredient.
	 * 
	 * @param	temperature
	 * 			The new temperature object to give to this new cooling box.
	 * @effect	The new cooling box is a temperature device with a the given temperature and startIngredient.
	 * 			| super(temperature)
	 */
	@Raw
	public CoolingBox(Temperature temperature) {
		super(temperature);
	}

	/**
	 * Cool the loaded ingredient in this cooling device and put it in the processedIngredient variable.
	 * 
	 * @post	If the temperature of the ingredient is higher than the temperature of the cooling box,
	 * 			change the temperature to the temperature of the cooling box.
	 * 			Put the loaded ingredient in the processed ingredient slot.
	 * 			| if (Temperature.compareTemperature(getStartIngredient().getTemperatureObject(),
	 * 			|	getTemperatureObject())==-1)
	 * 			|     then (new.getProcessedIngredient().getTemperatureObject() == getTemperatureObject()
	 * 			|			&& new.getStartIngredient() == null)
	 * 			| else
	 * 			|   (new.getProcessedIngredient() == getStartIngredient()
	 * 			|	 && new.getStartIngredient() == null)
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
