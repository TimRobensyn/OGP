package laboratory;

import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.*;
import alchemy.*;

/**
 * A class defining a recipe using a list of processes (add, heat, cool and mix)
 * and another list with the ingredients needes in the add process.
 * 
 * @invar	The list of processes must be valid.
 * 			| isValidProcessList(getProcesses())
 * @invar	Each recipe can have its ingredient list as its ingredient list.
 * 			| canHaveAsIngredients(getIngredients())
 * 
 * @version	1.0
 * @author	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

public class Recipe {

	/**
	 * Initialize a recipe with an array of processes and an array of ingredients.
	 * 
	 * @param	processes
	 * 			The array of processes to be given to the new recipe.
	 * @param	ingredients
	 * 			The array of ingredients to be given to the new recipe.
	 * @post	The new array of processes of this new recipe is equal
	 * 			to the given array of processes.
	 * 			| new.getProcesses() == processes
	 * @post	The new array of ingredients of this new recipe is equal
	 * 			to the given array of ingredients.
	 * 			| new.getIngredients() == ingredients.
	 * @throws 	IllegalArgumentException
	 * 			The given array of processes or the given array of ingredients is not valid.
	 * 			| (!isValidProcessList)||(!canHaveAsIngredients(ingredients))
	 */
	@Raw
	public Recipe(String[] processes, AlchemicIngredient[] ingredients) throws IllegalArgumentException{
		Process[] newProcesses = new Process[processes.length];
		for (String process:processes) {
			newProcesses[process.indexOf(process)] = Process.valueOf(process);
		}
		if (!isValidProcessList(newProcesses))
			throw new IllegalArgumentException("Invalid processes");
		this.processes = newProcesses;
		if (!canHaveAsIngredients(ingredients))
			throw new IllegalArgumentException("Not the right amount of ingredients");
		this.ingredients = ingredients;
	}
	
	/**
	 * Check if this process is a valid process.
	 */
	public static boolean isValidProcess(Process process) {
		if (process.)
	}
	/**
	 * Check if the given 
	 */
	/**
	 * A local enumeration containing the possible processes in a recipe.
	 */
	private enum Process{
		add,heat,cool,mix
	}
	
	/**
	 * Get the array containing the processes of this recipe.
	 */
	public Process[] getProcesses(){
		return this.processes;
	}
	
	/**
	 * Get the amount of times the add process is used in this recipe.
	 */
	@Raw
	public int getNbOfAdd() {
		int result = 0;
		for (Process process:getProcesses()) {
			if (process.equals(Process.add)) result += 1;
		}
		return result;
	}
	
	/**
	 * Check if the given list is a valid list of processes.
	 */
	public static boolean isValidProcessList(Process[] processList) {
		if (processList[processList.length-1]==Process.mix) return true;
		return false;
	}
	
	/**
	 * An array containing the processes of this recipe.
	 */
	private final Process[] processes;
	
	
	/**
	 * Get the (ordered) array of ingredients of this recipe. 
	 */
	public AlchemicIngredient[] getIngredients() {
		return this.ingredients;
	}
	
	/**
	 * Get the number of ingredients for this recipe. Each duplicate counts.
	 */
	@Raw
	public int getNbOfIngredients() {
		return getIngredients().length;
	}
	
	/**
	 * Check if the given array is a valid array of ingredients for this recipe.
	 * @param	ingredients
	 * 			The array of ingredients to check.
	 * @return	If the amount of ingredients is not equal to the amount of add in processes,
	 * 			return false.
	 * 			| result == (this.getNbOfIngredients()==this.getNbOfAdd)
	 */
	@Raw
	public boolean canHaveAsIngredients(AlchemicIngredient[] ingredients) {
		return (getNbOfIngredients()==getNbOfAdd());
	}
	
	/**
	 * An array containing the ingredients needed by the add-process.
	 */
	private final AlchemicIngredient[] ingredients;

}
