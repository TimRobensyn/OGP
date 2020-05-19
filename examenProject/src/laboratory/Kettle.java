package laboratory;

import alchemy.*;
import java.util.ArrayList;

//TODO DOCUMENTATIE

public class Kettle extends BottomlessDevice {

	public Kettle(IngredientContainer[] ingredientArray) {
		super(ingredientArray);
	}

	public Kettle() {}

	@Override
	public void process() {
		ArrayList<String> newNameList = new ArrayList<String>();
		for (AlchemicIngredient ingredient:getStartIngredients()) {
			for (String simpleName:ingredient.getType().getSimpleNames())
				if (!newNameList.contains(simpleName)) newNameList.add(simpleName);
		}
	}
	
	

}
