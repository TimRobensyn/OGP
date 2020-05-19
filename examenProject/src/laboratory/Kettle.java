package laboratory;

import alchemy.*;
import java.util.ArrayList;
import java.util.Collections;

//TODO DOCUMENTATIE

public class Kettle extends BottomlessDevice {

	public Kettle(IngredientContainer[] ingredientArray) {
		super(ingredientArray);
	}

	public Kettle() {}

	/**
	 * Rip documentatie hiervoor
	 * Naam is combinatie van alle unieke simpleNames in the ingredienten
	 * closestToWater zijn alle ingredienten waarvan de standaardTemperatuur dichtst bij water zit (array want sommige zitten even ver)
	 * State is liquid behalve als alle elementen in closestToWater powder zijn.
	 * StandardTemperature is de hoogste standaardTemperatuur van alle ingredienten in closestToWater
	 * Quantity is zoals opgave beveelt (ingewikkeld dus)
	 */
	@Override
	public void process() throws DeviceFullException{
		if (!getProcessedIngredients().isEmpty())
			throw new DeviceFullException(this);
		if (!getStartIngredients().isEmpty()) {
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
				
				//Quantity & temperature
				if (ingredient.getType().getState()==State.LIQUID) {
					quantityOfLiquids += ingredient.getQuantity();
					cumulativeColdness += ingredient.getColdness()*ingredient.getQuantity()*LiquidQuantity.SPOON.getQuantity();
					totalNbOfSpoons += ingredient.getQuantity()/LiquidQuantity.SPOON.getQuantity();
				} else if (ingredient.getType().getState()==State.POWDER) {
					quantityOfPowders += ingredient.getQuantity();
					cumulativeHotness += ingredient.getHotness()*ingredient.getQuantity()*PowderQuantity.SPOON.getQuantity();
					totalNbOfSpoons += ingredient.getQuantity()/PowderQuantity.SPOON.getQuantity();
				}
				
			}
			
			//State
			State newState = State.POWDER;
			for (AlchemicIngredient ingredient : closestToWater) {
				if (ingredient.getType().getState()==State.LIQUID) {
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
				int powderToLiquid = LiquidQuantity.SPOON.getQuantity()*( (int) Math.floor(quantityOfPowders*LiquidQuantity.getPowderRatio()));
				newQuantity = quantityOfLiquids + powderToLiquid;
			} else if (newState==State.POWDER) {
				int liquidToPowder = PowderQuantity.SPOON.getQuantity()*( (int) Math.floor(quantityOfPowders*PowderQuantity.getLiquidRatio()));
				newQuantity = quantityOfPowders + liquidToPowder;
			}
			
			//Temperature
			long temperature = (long) ((-cumulativeColdness+cumulativeHotness)/totalNbOfSpoons);
			//TODO nog een klein stukje
			//Toewijzing
			Collections.sort(newNameList);
			String[] newSimpleNames = (String[]) newNameList.toArray();
			IngredientType newType = new IngredientType(newSimpleNames, newState, newStandardTemperature);
			AlchemicIngredient newIngredient = new AlchemicIngredient(newType, newQuantity);
			clearStartIngredients();
			addProcessedIngredient(newIngredient);
		}
	}
}