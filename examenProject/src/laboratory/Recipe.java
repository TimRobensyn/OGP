package laboratory;

import be.kuleuven.cs.som.annotate.*;
import alchemy.*;

/**
 * A class defining a recipe using a list of processes (add, heat, cool and mix)
 * and another list with the ingredients needed in the add process.
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
	 * @post	When getting the string value of a process, the Process array of processes of this new recipe
	 * 			is elementwise equal to the given String array of processes.
	 * 			| for I in 0..processes.length-1
	 * 			|   new.getProcesses()[I] == Process.valueOf(processes[I])
	 * 			If the last process in the array is not compatible with the mix process object, a mix process is added.
	 * 			| if (!hasValidLastProcess(old.getProcesses()))
	 * 			|   then new.getProcesses() == [old.getProcesses(), Process.mix]
	 * 			Else, nothing is changed.
	 * @post	The new array of ingredients of this new recipe is equal
	 * 			to the given array of ingredients.
	 * 			| new.getIngredients() == ingredients
	 * @throws 	IllegalArgumentException
	 * 			The given array of processes or the given array of ingredients is not valid.
	 * 			| (!isValidProcessList) || (!canHaveAsIngredients(ingredients))
	 *          An element in the processes string array is not compatible with any enum variable.
	 *          | for some process in processes
	 *          |   !(process in Process.values())
	 */
	@Raw
	public Recipe(String[] processes, AlchemicIngredient[] ingredients) throws IllegalArgumentException{
		Process[] newProcesses = new Process[processes.length];
		for (int i=0; i<processes.length; i++) {
			// This throws a illegal argument exception if the string is not compatible with one of the 
			// process instances.
			newProcesses[i] = Process.valueOf(processes[i]);
		}
		
		Process[] processList;
		
		// If the last process is not a mix, add it.
		if (!hasValidLastProcess(newProcesses)) {
			Process[] extraProcess = new Process[newProcesses.length];
			for (int i=0; i<newProcesses.length; i++)
				extraProcess[i] = newProcesses[i];
			extraProcess[newProcesses.length] = Process.mix;
			processList = extraProcess;
		}		
		else processList = newProcesses;
		
		if (!isValidProcesses(processList)) {
			throw new IllegalArgumentException("The list of processes is not valid.");
		}
		this.processes = processList;
		
		if (!canHaveAsIngredients(ingredients))
			throw new IllegalArgumentException("Not the right amount of ingredients");
		this.ingredients = ingredients;
	}
	
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
		for (Process process: getProcesses()) {
			if (process.equals(Process.add))
				result++;
		}
		return result;
	}
	
	
	/**
	 * Check if the given list of processes has a valid last process.
	 * 
	 * @param  processList
	 *         The list of processes to check.
	 * @return True if and only if the last process is a mix process.
	 *         | result ==
	 *         |   (processList[processList.length-1] == Process.mix)
	 */
	@Raw
	public static boolean hasValidLastProcess(Process[] processList) {
		return (processList[processList.length-1]==Process.mix);
	}
	
	/**
	 * Check if the given list of processes is valid.
	 * 
	 * @param  processList
	 * 		   The list of processes to check.
	 * @return True if and only if list has a valid last process.
	 *         | result ==
	 *         |    hasValidLastProcess(processList)
	 * @note   This function is now equivalent with hasValidLastProcess, but if additional constraints are
	 * 		   added on the list of processes, they can easily be added here.
	 */
	@Raw
	public static boolean isValidProcesses(Process[] processList) {
		return hasValidLastProcess(processList);
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
	 * 
	 * @param	ingredients
	 * 			The array of ingredients to check.
	 * @return  True if and only if the amount of ingredients is equal to the amount of the add process
	 * 			in the process list.
	 * 			| result == (ingredients.length==this.getNbOfAdd)
	 */
	@Raw
	public boolean canHaveAsIngredients(AlchemicIngredient[] ingredients) {
		return (ingredients.length==getNbOfAdd());
	}
	
	/**
	 * An array containing the ingredients needed by the add-process.
	 */
	private final AlchemicIngredient[] ingredients;

}
