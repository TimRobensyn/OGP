package laboratory;

import be.kuleuven.cs.som.annotate.*;
import alchemy.*;
import java.util.ArrayList;

/**
 * A class defining a recipe using a list of processes (add, heat, cool and mix)
 * and another list with the ingredients needed in the add process.
 * 
 * @invar	The processes associated with this recipe must be proper processes for each recipe.
 * 		    | hasProperProcesses()
 * @invar	The alchemic ingredients associated with this recipe must be proper ingredients
 * 			for each recipe.
 * 			| hasProperIngredients()
 * 
 * @version	1.0
 * @author	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

public class Recipe {

	/**
	 * Initialize a recipe with an array of processes and an array of ingredients.
	 * 
	 * @param	processes
	 * 			The array list of processes to be given to the new recipe.
	 * @param	ingredients
	 * 			The array of ingredients to be given to the new recipe.
	 * @post	
	 * @post	The new array of ingredients of this new recipe is equal
	 * 			to the given array of ingredients.
	 * 			| new.getIngredients() == ingredients
	 * @throws 	IllegalArgumentException
	 * 			The given array of processes or the given array of ingredients is not valid.
	 * 			| (!isValidProcessList) || (!canHaveAsIngredients(ingredients))
	 */
	@Raw
	public Recipe(ArrayList<Process> processes, ArrayList<AlchemicIngredient> ingredients) throws IllegalArgumentException{
		if (processes.get(processes.size()-1) != Process.mix)
			addProcessAt(Process.mix,processes.size());
		this.processes = processes;
		
		if (!hasProperIngredients())
			throw new IllegalArgumentException("Not the right amount of ingredients");
		this.ingredients = ingredients;
	}
	
	
	
	/**********************************************************
	 * Processes
	 **********************************************************/
	
	
	/**
	 * Variable referencing the ArrayList with the ordered processes to execute in this recipe.
	 * 
	 * @invar The list of processes is effective.
	 *        | processes != null
	 * @invar Each item in the processes list is effective.
	 *        | for each item in processes:
	 *        |   item != null
	 * @invar The list of processes ends with a mix process.
	 *        | processes.get(processes.size()-1) == Process.mix
	 */
	private ArrayList<Process> processes = new ArrayList<Process>();
	
	
	/**
	 * Get the array containing the processes of this recipe.
	 */
	@Basic
	public ArrayList<Process> getProcesses(){
		return this.processes;
	}
	
	/**
	 * Return the number of processes in the process list of thsi recipe.
	 */
	@Basic
	public int getNbProcesses() {
		return processes.size();
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
	 * Return the process at the given index of the processes list.
	 * 
	 * @param  index
	 * 		   The index of the wanted process.
	 * @return The process of the processes list of this recipe at the given index.
	 *         | return getProcesses().get(index)
	 * @throws IndexOutOfBoundsException
	 * 		   The given index is lesser than zero or greater than or equal to the size of 
	 * 		   the processes list.
	 *         | (index<0 || index>=getNbProcesses())
	 */
	public Process getProcessAt(int index) throws IndexOutOfBoundsException {
		return getProcesses().get(index);
	}
	
	/**
	 * Check whether this recipe can have the given process as one of its processes at the given index.
	 * 
	 * @param  process
	 * 		   The process to check.
	 * @param  index
	 * 		   The index to check.
	 * @return False, if the given index is lesser than zero, or it exceeds the number
	 *         of processes.
	 *         | if ((index<0)
	 *         |    || (index > getNbProcesses())
	 *         |   then result == false
	 *         Otherwise, true if and only if the index is not at the end of the list or if it is, if the given
	 * 		   process is 'mix'.
	 * 		   | else if (index == getNbProcesses()-1)
	 * 		   |   then result == (process==Process.mix)
	 * 		   | else
	 * 		   |   then result == true  
	 */
	public boolean canHaveAsProcessAt(Process process, int index) {
		if ((index<0) || (index>getNbProcesses()))
			return false;
		if (index == getNbProcesses()-1)
			return (process==Process.mix);
		else return true;
	}
	
	/**
	 * Check if the given recipe has a valid list of processes.
	 * 
	 * @param  recipe
	 * 		   The recipe to check.
	 * @return True if and only if the given recipe can have each item at its index.
	 *  	   | result ==
	 *  	   |   for each I in 0..getNbProcesses()-1
	 *         |      canHaveAsProcessAt(recipe.getProcessAt(i),i)
	 */
	@Raw
	public boolean hasValidProcesses(Recipe recipe) {
		for (int i=0; i<getNbProcesses(); i++) {
			if (!canHaveAsProcessAt(recipe.getProcessAt(i),i))
				return false;
		}
		return true;
	}
	
	/**
	 * Add the given process as a process for this recipe at the given index. If we want to add a non-mix process
	 * at the end of the list, a mix process is added after it.
	 * 
	 * @param  process
	 * 		   The process to be added.
	 * @param  index
	 * 		   The index of the process to be added.
	 * @post   If the given index is equal to the current size of the process list and the given process is not mix,
	 * 		   the number of processes of this recipe is incremented by two. Else, it is incremented by one.
	 * 		   | if (index==getNbProcesses() && process!=Process.mix)
	 * 		   |   then new.getNbProcesses() == getNbProcesses()+2
	 * 		   | else
	 * 		   |   new.getNbProcesses() == getNbProcesses()+1
	 * @post   If the given index is equal to the current size of the process list and the given process is not mix,
	 *         this recipe has the given process at the given index and the mix process after it. Else, only the given
	 *         process is at the given index.
	 *         | if (index==getNbProcesses() && process!=Process.mix)
	 *         |   then new.getProcessAt(index+1) == Process.mic
	 *         | new.getProcessAt(index) == process
	 * @post   If the given index is not equal to the current size of the process list or the given 
	 * 		   process is mix, all processes for this recipe at an index exceeding the given index, are registered
	 * 		   as process one index higher.
	 *         | if !(index==getNbProcesses() && process!=Process.mix)
	 *         |   then for each I in index..getNbProcesses()
	 *         |          new.getProcessAt(I+1) == getProcessAt(I)
	 * @throws IllegalArgumentException
	 * 		   This recipe cannot have the given process at the given index.
	 *         | !canHaveAsProcessAt(process,index)
	 */
	@Raw
	public void addProcessAt(Process process, int index) throws IllegalArgumentException {
		if (!canHaveAsProcessAt(process,index))
			throw new IllegalArgumentException("Invalid process for this index.");
		if (index==getNbProcesses() && process!=Process.mix) {
			processes.add(index,Process.mix);
			processes.add(index,process);
		}
		else{
			processes.add(index,process);
		}
	}
	
	/**
	 * Remove the process at the given index.
	 * 
	 * @param  index
	 * 		   The index of the process to be removed.
	 * @post   The number of processes in the process list of this recipe is decremented by one.
	 * 		   | new.getNbProcesses() = getNbProcesses()-1
	 * @post   All processes associated with this recipe at an index exceeding the given index,
	 *         are registered as process at one index lower.
	 *         | for each I in index+1..getNbProcesses()
	 *         |   (new.getProcessAt(I-1) == this.getProcessAt(I))
	 * @throws IndexOutOfBoundsException
	 *         The given index is lesser than zero or above or equal to the index of the last element
	 *         in the list (we do not want to remove Process.mix at the end of the list).
	 *         (index<0 || index>=getNbProcesses()-1)
	 */
	public void removeProcessAt(int index) throws IndexOutOfBoundsException {
		if (index<0 || index>=getNbProcesses()-1)
			throw new IndexOutOfBoundsException("The index is not valid.");
		processes.remove(index);
	}


	
	/**********************************************************
	 * Ingredients
	 **********************************************************/
	
	/**
	 * Get the (ordered) array of ingredients of this recipe. 
	 */
	public ArrayList<AlchemicIngredient> getIngredients() {
		return this.ingredients;
	}
	
	/**
	 * Get the number of ingredients for this recipe. Each duplicate counts.
	 */
	@Raw
	public int getNbOfIngredients() {
		return getIngredients().size();
	}
	
	/**
	 * Check if this recipe has a valid list of ingredients
	 * 
	 * @param	ingredients
	 * 			The array of ingredients to check.
	 * @return  True if and only if the amount of ingredients is equal to the amount of the add processes
	 * 			in the process list.
	 * 			| result == (ingredients.getNbOfIngredients()==this.getNbOfAdd())
	 */
	@Raw
	public boolean hasProperIngredients() {
		return (getNbOfIngredients()==getNbOfAdd());
	}
	
	//TODO: addIngredientAt, removeIngredientAt
	
	/**
	 * An array containing the alchemic ingredients needed by the processes of this recipe.
	 * 
	 * @invar The list of alchemic ingredients is effective.
	 *        | processes != null
	 * @invar Each item in the ingredient list is effective.
	 *        | for each item in ingredients:
	 *        |   item != null
	 * @note  There are no restrictions on a single ingredient in the list.
	 */
	private ArrayList<AlchemicIngredient> ingredients = new ArrayList<AlchemicIngredient>();

}
