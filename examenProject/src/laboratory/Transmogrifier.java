package laboratory;

import alchemy.*;
import be.kuleuven.cs.som.annotate.*;

//TODO DOCUMENTATIE

public class Transmogrifier extends BottomlessDevice {

	@Raw
	public Transmogrifier(IngredientContainer[] ingredientArray) {
		super(ingredientArray);
	}

	@Raw
	public Transmogrifier() {
		super(null);
	}

	@Override
	public void process() {
		for (AlchemicIngredient ingredient:getStartIngredients()) {
			State oldState = ingredient.getState();
			State newState = ingredient.getState().otherState();
			IngredientType newType = new IngredientType(ingredient.getType().getSimpleNames(),
					newState,ingredient.getStandardTemperatureObject());
			int newQuantity = (int) Math.floor(ingredient.getQuantity()*Unit.getRatio(newState,oldState));
			AlchemicIngredient newIngredient = new AlchemicIngredient(newType,newQuantity);
			addProcessedIngredient(newIngredient);
		}
		clearStartIngredients();
	}

}
