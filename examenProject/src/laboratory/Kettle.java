package laboratory;

import alchemy.*;
import java.util.ArrayList;
import java.util.Collections;
import be.kuleuven.cs.som.*;

//TODO DOCUMENTATIE

public class Kettle extends BottomlessDevice {

	public Kettle(IngredientContainer[] ingredientArray) {
		super(ingredientArray);
	}

	public Kettle() {}

	/**
	 * Rip documentatie hiervoor
	 * Naam is combinatie van alle unieke simpleNames in the ingredienten, alfabetisch gerangschikt
	 * closestToWater zijn alle ingredienten waarvan de standaardTemperatuur dichtst bij water zit (array want sommige zitten even ver)
	 * State is liquid behalve als alle elementen in closestToWater powder zijn.
	 * StandardTemperature is de hoogste standaardTemperatuur van alle ingredienten in closestToWater
	 * Quantity is zoals opgave beveelt (ingewikkeld dus)
	 * Temperature is gewogen gemiddelde van temperatures van gegeven ingredienten
	 * 
	 * Mix the ingredients in the device and empty the loading area.
	 * 
	 * Welke tags moet ik hier gebruiken help
	 */
	@Override
	public void process() throws CapacityException {
		if (!getProcessedIngredients().isEmpty())
			throw new CapacityException(this,"This kettle isn't empty.");
		if (!getStartIngredients().isEmpty()) {
			throw new CapacityException(this,"This kettle has no ingredients to work with.");
		}
		
		ArrayList<String> newNameList = new ArrayList<>(0);
		ArrayList<AlchemicIngredient> closestToWater = new ArrayList<>(0);
		Temperature waterTemperature = new Temperature(0L,20L);
		long diffClosest = Math.abs(Temperature.temperatureDifference(getStartIngredients().get(0).getStandardTemperatureObject(),waterTemperature));
		int quantityOfLiquids=0;
		int quantityOfPowders=0;
		long cumulativeColdness = 0L;
		long cumulativeHotness = 0L;
		double totalNbOfSpoons = 0L;
		
		for (AlchemicIngredient ingredient:getStartIngredients()) {
			//Name
			for (String simpleName:ingredient.getType().getSimpleNames()) {
				if (!newNameList.contains(simpleName)) newNameList.add(simpleName);						
				}
			
			//State & standardTemperature
			long diffIngredient = Math.abs(Temperature.temperatureDifference(ingredient.getStandardTemperatureObject(),waterTemperature));
			if (diffIngredient<diffClosest) {
				closestToWater = new ArrayList<AlchemicIngredient>();
				closestToWater.add(ingredient);
			} else if (diffIngredient==diffClosest) {
				closestToWater.add(ingredient);
			}
			
			//Quantity & temperature //TODO optimaliseren met unit?
			if (ingredient.getState()==State.LIQUID) {
				quantityOfLiquids += ingredient.getQuantity();
				cumulativeColdness += ingredient.getColdness()*ingredient.getQuantity()*Unit.SPOON_LIQUID.getCapacity();
				totalNbOfSpoons += ingredient.getQuantity()/Unit.SPOON_LIQUID.getCapacity();
			} else if (ingredient.getState()==State.POWDER) {
				quantityOfPowders += ingredient.getQuantity();
				cumulativeHotness += ingredient.getHotness()*ingredient.getQuantity()*Unit.SPOON_POWDER.getCapacity();
				totalNbOfSpoons += ingredient.getQuantity()/Unit.SPOON_POWDER.getCapacity();
			}
			
		}
		
		//State
		State newState = State.POWDER;
		for (AlchemicIngredient ingredient : closestToWater) {
			if (ingredient.getState()==State.LIQUID) {
				newState = State.LIQUID;
			}
		}
		
		//StandardTemperature
		Temperature newStandardTemperature = getStartIngredients().get(0).getStandardTemperatureObject();
		for (AlchemicIngredient ingredient:closestToWater) {
			if (Temperature.compareTemperature(ingredient.getStandardTemperatureObject(), newStandardTemperature)==1) {
				newStandardTemperature = ingredient.getStandardTemperatureObject();
			}
		}
		
		//Quantity
		int newQuantity = 0;
		if (newState==State.LIQUID) {
			int powderToLiquid = Unit.SPOON_LIQUID.getCapacity()*( (int) Math.floor(quantityOfPowders*Unit.getRatio(
					newState, newState.otherState())));
			newQuantity = quantityOfLiquids + powderToLiquid;
		} else if (newState==State.POWDER) {
			int liquidToPowder = Unit.SPOON_POWDER.getCapacity()*( (int) Math.floor(quantityOfPowders*Unit.getRatio(
					newState, newState.otherState())));
			newQuantity = quantityOfPowders + liquidToPowder; //TODO optimaliseren met Unit
		}
		
		//Temperature
		long temperature = (long) ((-cumulativeColdness+cumulativeHotness)/totalNbOfSpoons);
		Temperature newTemperature;
		if (temperature<0) {
			newTemperature = new Temperature(-temperature,0);
		} else if (temperature>0) {
			newTemperature = new Temperature(0,temperature);
		} else {
			newTemperature = newStandardTemperature;
		}

		//Toewijzing
		Collections.sort(newNameList);
		String[] newSimpleNames = (String[]) newNameList.toArray();
		IngredientType newType = new IngredientType(newSimpleNames, newState, newStandardTemperature);
		AlchemicIngredient newIngredient = new AlchemicIngredient(newType, newQuantity);
		
		//Temperature
		long difference = Temperature.temperatureDifference(newTemperature, newStandardTemperature);
		if (difference>0) {
			newIngredient.heat(difference);
		}
		else if (difference<0) {
			newIngredient.cool(difference);
		}
		
		clearStartIngredients();
		addProcessedIngredient(newIngredient);
	}
}