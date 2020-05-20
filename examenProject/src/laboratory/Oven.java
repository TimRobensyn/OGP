package laboratory;

import alchemy.*;
import be.kuleuven.cs.som.annotate.*;

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
