package laboratory;

import alchemy.*;
import be.kuleuven.cs.som.*;

//TODO DOCUMENTATIE

public class Transmogrifier extends BottomlessDevice {

	public Transmogrifier(IngredientContainer[] ingredientArray) {
		super(ingredientArray);
	}

	public Transmogrifier() {}

	@Override
	public void process() {
		for (AlchemicIngredient ingredient:getStartIngredients()) {
			if (ingredient.getType().getState()==State.LIQUID) {
				IngredientType newType = new IngredientType(ingredient.getType().getSimpleNames(),
						State.POWDER,ingredient.getType().getStandardTemperatureObject());
				int quantityInPowder = (int) Math.floor(ingredient.getQuantity()*PowderQuantity.getLiquidRatio());
				AlchemicIngredient newIngredient = new AlchemicIngredient(newType,quantityInPowder);
				addProcessedIngredient(newIngredient);
			}
			if (ingredient.getType().getState()==State.POWDER) {
				IngredientType newType = new IngredientType(ingredient.getType().getSimpleNames(),
						State.LIQUID,ingredient.getType().getStandardTemperatureObject());
				int quantityInLiquid = (int) Math.floor(ingredient.getQuantity()*LiquidQuantity.getPowderRatio());
				AlchemicIngredient newIngredient = new AlchemicIngredient(newType,quantityInLiquid);
				addProcessedIngredient(newIngredient);
			}
		}
		clearStartIngredients();
	}

}
