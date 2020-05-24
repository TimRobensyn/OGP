package laboratory.device;

import alchemy.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import be.kuleuven.cs.som.annotate.*;
import laboratory.CapacityException;
import temperature.Temperature;

/**
 * A class of kettles involving an array of ingredients that the kettle can mix.
 * 
 * @author  Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 * @version 1.0
 * 
 * @note    In this class we assume that there are only two possible states for an alchemic ingredient to have.
 */

public class Kettle extends BottomlessDevice {
	
	/**
	 * Initialize a new kettle with the given array of ingredient containers.
	 * 
	 * @param  ingredientArray
	 * 		   The given array with ingredient containers that will be loaded in this kettle.
	 * @effect This new kettle is initialized as a bottomless device with the given array of ingredient containers.
	 * 		   | super(ingredientArray)
	 */
	@Raw
	public Kettle(IngredientContainer[] ingredientArray) {
		super(ingredientArray);
	}
	
	/**
	 * Initialize a new, empty kettle.
	 * 
	 * @effect This new kettle is initialized as an empty bottomless device.
	 * 		   | super()
	 */
	@Raw
	public Kettle() {
		super();
	}

	/**
	 * Process the start ingredients loaded in this kettle.
	 * The kettle mixes its start ingredients into a processed ingredient. 
	 * 
	 * @post The simple names array of the processed ingredient is an array with the simple names of all the
	 * 		 start ingredients in alphabetical order.
	 * 		 | for each ingredient in getStartIngredients()
	 *       |     for each simpleName in  ingredient.getType().getSimpleNames()
	 *       |         if(!newNameList.contains(simpleName)) 
	 *       |            then newNameList.add(simpleName)
	 * 		 | Collections.sort(newNameList)
	 * 		 | newSimpleNames = newNameList.toArray();
	 * 
	 * @post The state of the processed ingredient is set to the state of the start ingredient whose standard temperature
	 * 		 is closest to the standard temperature of water. If multiple start ingredients have a standard temperature closest
	 * 		 to this temperature, then liquid is prioritized over powder.
	 *       | closestToWater.add(getStartIngredient().get(0))
	 *       | for each ingredient in getStartIngredients()
	 *       |     if(ingredient.getStandardTemperature() closer to water.getStandardTemperature())
	 *       |		  then closestToWater = new ArrayList<>()
	 *       |		  closestToWater.add(ingredient)
	 *       |	   else if(ingredient.getStandardTemperature() same difference from water.getStandardTemperature())
	 *       |		  then closestToWater.add(ingredient)
	 *       | newState = State.POWDER
	 *       | for each ingredient in closestToWater
	 *       |     if(ingredient.getState()==State.LIQUID)
	 *       |        newState = State.LIQUID
	 * 
	 * @post If every start ingredient has the same state, the processed ingredient's quantity is equal to the sum of
	 * 		 all the start ingredients' quantities.
	 *       | for each ingredient in getStartIngredients()
	 *       |     quantityOfState += ingredient.getQuantity()
	 *       | newQuantity = quantityOfState
	 *       If not every start ingredient has the same state, all the quantities of the start ingredients that
	 *       have a different state than the eventual processed ingredient are summed as a fraction of spoons.
	 *       This fraction then gets rounded down to a rounded number of spoons and then gets added to the sum of all the
	 *       quantities of the start ingredients that have the same state as the eventual processed ingredient.
	 *       | for each ingredient in getStartIngredients()
	 *       |     if(ingredient.getState() == State.LIQUID)
	 *       |        then quantityOfLiquids += ingredient.getQuantity()
	 *       |     else if(ingredient.getState() == State.Powder)
	 *       |        then quantityOfPowder += ingredient.getQuantity()
	 *       | oldStateToNewState = Unit.SPOON_NEWSTATE.getCapacity() * (Math.floor(quantityOfOldState*Unit.getRatio(newState, oldState)))
	 *       | newQuantity = quantityOfNewState + oldStateToNewState
	 * 
	 * @post The standard temperature of the processed ingredient is set to the standard temperature of the starting ingredient
	 * 	     with the standard temperature closest to that of water. If multiple starting ingredients are closest to this
	 * 		 temperature, the hottest is selected.
	 * 		 | closestToWater.add(getStartIngredient().get(0))
	 *       | for each ingredient in getStartIngredients()
	 *       |     if(ingredient.getStandardTemperature() closer to water.getStandardTemperature())
	 *       |		  then closestToWater = new ArrayList<>()
	 *       |		  closestToWater.add(ingredient)
	 *       |	   else if(ingredient.getStandardTemperature() same difference from water.getStandardTemperature())
	 *       |		  then closestToWater.add(ingredient)
	 *       | newStandardTemperature = closestToWater.get(0).getStandardTemperatureObject()
	 *       | for each ingredient in closestToWater
	 *       |     if(Temperature.compareTemperature(ingredient.getStandardTemperatureObject(), newStandardTemperature) == 1)
	 *       |		  then newStandardTemperature = ingredient.getStandardTemperatureObject()
	 * 
	 * @post The temperature of the processed ingredient is set to the weighted average of the temperatures of the
	 * 		 start ingredients.
	 *       | for each ingredient in getStartIngredients()
	 *       |	   cumulativeColdness += ingredient.getColdness()*ingredient.getQuantity()*Unit.SPOON_STATE.getCapacity()
	 *       |	   cumulativeHotness += ingredient.getHotness()*ingredient.getQuantity()*Unit.SPOON_STATE.getCapacity()
	 *       |     totalNbOfSpoons +=  ingredient.getQuantity()/Unit.SPOON_STATE.getCapacity()
	 *       | temperature = ((-cumulativeColdness + cumulativeHotness)/totalNbOfSpoons)
	 *       | if(temperature < 0)
	 *       |    then newTemperature = new Temperature(-temperature, 0)
	 *       | else if(temperature >= 0)
	 *       |    then newTemperature = new Temperature(0, temperature)
	 *       
	 * @effect A new ingredient type is created with the new simple names, the new state and the new standard temperature
	 *         and a new ingredient is created with the new ingredient type and the new quantity.
	 *         The new Ingredient's temperature is set to the new temperature.
	 *         The start ingredients get deleted and the processed ingredient gets added to the processed ingredients list
	 *         | newType = new IngredientType(newSimpleNames, newState, newStandardTemperature)
	 *         | newIngredient = new AlchemicIngredient(newType, newQuantity)
	 *         | difference = Temperature.temperatureDifference(newTemperature, newStandardTemperature)
	 *         | if(difference > 0)
	 *         |    then newIngredient.heat(difference)
	 *         | else if (difference < 0)
	 *         |    then newIngredient.cool(-difference)
	 *         | new.getNbStartIngredients() == 0
	 *         | addProcessedIngredient(newIngredient)
	 * 
	 * @throws CapacityException
	 * 		   The kettle already contains processed ingredients
	 * 		   | !getProcessedIngredients().isEmpty()
	 * 		   The kettle doesn't contain start ingredients
	 * 		   | getStartIngredients().isEmpty()
	 * 
	 * 		   
	 */
	@Override
	public void process() throws CapacityException {
		if (!getProcessedIngredients().isEmpty())
			throw new CapacityException(this,"This kettle isn't empty.");
		if (getStartIngredients().isEmpty()) {
			throw new CapacityException(this,"This kettle has no ingredients to work with.");
		}
		
		ArrayList<String> newNameList = new ArrayList<>();
		ArrayList<AlchemicIngredient> closestToWater = new ArrayList<>();
		final Temperature waterTemperature = new Temperature(0L,20L);
		
		long diffClosest = Math.abs(Temperature.temperatureDifference(getStartIngredients().get(0).getStandardTemperatureObject(),
																	  waterTemperature));
		int quantityOfLiquids=0;
		int quantityOfPowders=0;
		double cumulativeColdness = 0L;
		double cumulativeHotness = 0L;
		double totalNbOfSpoons = 0L;
		
		for (AlchemicIngredient ingredient: getStartIngredients()) {
			//Name
			for (String simpleName: ingredient.getType().getSimpleNames()) {
				if (!newNameList.contains(simpleName)) newNameList.add(simpleName);						
				}
			
			//State & standardTemperature
			long diffIngredient = Math.abs(Temperature.temperatureDifference(ingredient.getStandardTemperatureObject(),
					                                                         waterTemperature));
			
			if (diffIngredient<diffClosest) {
				closestToWater.clear();
				closestToWater.add(ingredient);
			} else if (diffIngredient==diffClosest) {
				closestToWater.add(ingredient);
			}
			
			//Quantity & temperature
			
			
			if (ingredient.getState()==State.LIQUID) {
				quantityOfLiquids += ingredient.getQuantity();
				cumulativeColdness += (double) ingredient.getColdness()*ingredient.getQuantity()/Unit.SPOON_LIQUID.getCapacity();
				cumulativeHotness += (double) ingredient.getHotness()*ingredient.getQuantity()/Unit.SPOON_LIQUID.getCapacity();
				totalNbOfSpoons += (double) ingredient.getQuantity()/Unit.SPOON_LIQUID.getAbsoluteCapacity();
			} else if (ingredient.getState()==State.POWDER) {
				quantityOfPowders += ingredient.getQuantity();
				cumulativeColdness += (double) ingredient.getColdness()*ingredient.getQuantity()/Unit.SPOON_POWDER.getCapacity();
				cumulativeHotness += (double) ingredient.getHotness()*ingredient.getQuantity()/Unit.SPOON_POWDER.getCapacity();
				totalNbOfSpoons += (double) ingredient.getQuantity()/Unit.SPOON_POWDER.getAbsoluteCapacity();
			}
			
		}
		
		//State
		State newState = State.POWDER;
		for (AlchemicIngredient ingredient: closestToWater) {
			if (ingredient.getState()==State.LIQUID) {
				newState = State.LIQUID;
			}
		}
		
		//StandardTemperature
		Temperature newStandardTemperature = closestToWater.get(0).getStandardTemperatureObject();
		for (AlchemicIngredient ingredient:closestToWater) {
			if (Temperature.compareTemperature(ingredient.getStandardTemperatureObject(), newStandardTemperature)==1) {
				newStandardTemperature = ingredient.getStandardTemperatureObject();
			}
		}
		
		//Quantity
		int newQuantity = 0;
		if (newState==State.LIQUID) {
			int powderToLiquid = (int) Math.floor(quantityOfPowders*Unit.getRatio(newState, newState.otherState()));
			newQuantity = quantityOfLiquids + powderToLiquid;
		} else if (newState==State.POWDER) {
			int liquidToPowder = (int) Math.floor(quantityOfLiquids*Unit.getRatio(newState, newState.otherState()));
			newQuantity = quantityOfPowders + liquidToPowder;
		}
		
		//Temperature
		long temperature = (long) ((-cumulativeColdness+cumulativeHotness)/totalNbOfSpoons);
		Temperature newTemperature;
		if (temperature<0) {
			newTemperature = new Temperature(-temperature,0);
		} else {
			newTemperature = new Temperature(0,temperature);
		}

		//Toewijzing
		Collections.sort(newNameList);
		
		String[] newSimpleNames = newNameList.toArray(new String[0]);
		IngredientType newType = new IngredientType(newSimpleNames, newState, newStandardTemperature);
		
		AlchemicIngredient newIngredient = new AlchemicIngredient(newType, newQuantity);
		
		//Temperature
		long difference = Temperature.temperatureDifference(newTemperature, newStandardTemperature);
		if (difference>0) {
			newIngredient.heat(difference);
		}
		else if (difference<0) {
			newIngredient.cool(-difference);
		}
		
		clearStartIngredients();
		addAsProcessedIngredient(newIngredient);
	}
}