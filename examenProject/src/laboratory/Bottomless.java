package laboratory;

import alchemy.IngredientContainer;

public abstract class Bottomless extends Device {

	@Override
	public void loadIngredient(IngredientContainer container) throws DeviceFullException {
		// TODO Auto-generated method stub

	}

	@Override
	public IngredientContainer emptyDevice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public abstract void process();

}
