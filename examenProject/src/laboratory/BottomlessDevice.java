package laboratory;

import be.kuleuven.cs.som.annotate.*;
import alchemy.*;

/**
 * An abstract subclass of Device for devices that can hold a limitless
 * amount of ingredients.
 * 
 * @version	1.0
 * @author Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

public abstract class BottomlessDevice extends Device {
	
	

	@Override
	public final void loadIngredient(IngredientContainer container) throws DeviceFullException {
		// TODO Auto-generated method stub

	}

	@Override
	public final IngredientContainer emptyDevice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub

	}

}
