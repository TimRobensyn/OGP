package laboratory;

import alchemy.*;
import be.kuleuven.cs.som.*;

//TODO DOCUMENTATIE VAN DE HELE KLASSE
public class Oven extends TemperatureDevice {

	public Oven(Temperature temperature, IngredientContainer startIngredient) {
		super(temperature, startIngredient);
	}

	public Oven(Temperature temperature) {
		super(temperature);
	}

	@Override
	public void process() {
		double randomness = Math.random()*0.05d;
		long difference = Temperature.temperatureDifference(getTemperatureObject(), getStartIngredient().getTemperatureObject());
		if (difference>0) {
			AlchemicIngredient newIngredient = getStartIngredient();
			newIngredient.heat((long) (difference*randomness)); //TODO Dit klopt nog niet
			setProcessedIngredient(newIngredient);
			setStartIngredient(null);
		}

	}

}
