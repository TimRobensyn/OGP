package laboratory;

import be.kuleuven.cs.som.annotate.*;
import alchemy.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class defining a recipe using a list of processes (add, heat, cool and mix)
 * and another list with the ingredients needed in the add process.
 * 
 * @invar	The processes associated with this recipe must be proper processes for each recipe.
 * 		    | hasProperProcesses()
 * @invar	The alchemic ingredients associated with this recipe must be proper ingredients
 * 			for each recipe.
 * 			| areProperIngredients()
 * 
 * @version	1.0
 * @author	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

public class Recipe {

	/**
	 * Initialize a recipe with an List of processes and an List of ingredients.
	 * 
	 * @param	processes
	 * 			The list of processes to be given to the new recipe.
	 * @param	ingredients
	 * 			The list of ingredients to be given to the new recipe.
	 * @post	If the given processes list does not end with a mix process, it is added at the end of the list.
	 * 			Else, nothing is added. Either way, the given processes list (with an extra element or not) is set as 
	 * 			the processes list of this recipe.
	 *          | if (processes.get(processes.size()-1) != Process.mix)
	 *		    |    then addProcessAt(Process.mix,processes.size())
	 *			| new.getProcesses() = processes
	 * @post	The new list of ingredients of this new recipe is equal
	 * 			to the given list of ingredients.
	 * 			| new.getIngredients() == ingredients
	 * @throws 	IllegalArgumentException
	 * 			The amount of add processes in the given processes list is not equal to the 
	 * 			amount of ingredients.
	 * 			| (getNbOfAdd() != ingredients.size())
	 */
	@Raw
	public Recipe(List<Process> processes, List<AlchemicIngredient> ingredients) throws IllegalArgumentException{
		if (processes.get(processes.size()-1) != Process.mix)
			addProcessAt(Process.mix,processes.size());
		this.processes = processes;
		
		if (getNbOfAdd() != ingredients.size())
			throw new IllegalArgumentException("Not the right amount of ingredients");
		this.ingredients = ingredients;
	}
	
	/**
	 * Initialize a recipe with empty processes and ingredients lists.
	 * 
	 * @post The processes list of this recipe is empty.
	 * 	     | new.getProcesses.empty()
	 * @post The ingredients list of this recipe is empty.
	 * 	 	 | new.getIngredients.empty()
	 */
	@Raw
	public Recipe() {
	}
	
	/**********************************************************
	 * Processes
	 **********************************************************/
	

	/**
	 * Get the list containing the processes of this recipe.
	 */
	@Basic
	public List<Process> getProcesses(){
		return this.processes;
	}
	
	/**
	 * Return the number of processes in the process list of this recipe.
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
	 * @return False, if the given index is less than zero, or it exceeds the number
	 *         of processes.
	 *         | if ((index<0)
	 *         |    || (index > getNbProcesses())
	 *         |   then result == false
	 *         Otherwise, true if and only if the index is not at the end of the list or if it is, the given
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
	 * Check if the given recipe has a proper list of processes.
	 * 
	 * @param  recipe
	 * 		   The recipe to check.
	 * @return True if and only if the given recipe can have each item at its index.
	 *  	   | result ==
	 *  	   |   for each I in 0..getNbProcesses()-1
	 *         |      canHaveAsProcessAt(recipe.getProcessAt(i),i)
	 */
	@Raw
	public boolean hasProperProcesses(Recipe recipe) {
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
	 */
	@Raw
	public void addProcessAt(Process process, int index) {
		if (index==getNbProcesses() && process!=Process.mix) {
			getProcesses().add(index,Process.mix);
			getProcesses().add(index,process);
		}
		else{
			getProcesses().add(index,process);
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
	 *         The given index is less than zero or above or equal to the index of the last element
	 *         in the list (we do not want to remove Process.mix at the end of the list).
	 *         (index<0 || index>=getNbProcesses()-1)
	 */
	public void removeProcessAt(int index) throws IndexOutOfBoundsException {
		if (index<0 || index>=getNbProcesses()-1)
			throw new IndexOutOfBoundsException("The index is not valid.");
		getProcesses().remove(index);
	}
	
	/**
	 * Variable referencing the List with the ordered processes to execute in this recipe.
	 * 
	 * @invar The list of processes is effective.
	 *        | processes != null
	 * @invar Each item in the processes list is effective.
	 *        | for each item in processes:
	 *        |   item != null
	 * @invar The list of processes ends with a mix process.
	 *        | processes.get(processes.size()-1) == Process.mix
	 */
	private List<Process> processes = new ArrayList<Process>();


	
	/**********************************************************
	 * Ingredients
	 **********************************************************/
	
	/**
	 * Get the (ordered) list of ingredients of this recipe. 
	 */
	@Basic @Raw
	public List<AlchemicIngredient> getIngredients() {
		return this.ingredients;
	}
	
	/**
	 * Get the number of ingredients for this recipe. Each duplicate counts.
	 */
	@Basic @Raw
	public int getNbIngredients() {
		return getIngredients().size();
	}
	
	/**
	 * Check whether this recipe can have the given ingredient in its ingredient list or not.
	 * 
	 * @param 	ingredient
	 * 			The ingredient to check.
	 * @return  True if and only if the ingredient is effective.
	 * 			| result == (ingredient!=null)
	 */
	public static boolean canHaveAsIngredient(AlchemicIngredient ingredient) {
		return (ingredient != null);
	}
	
	/**
	 * Check whether this recipe has proper ingredients associated with it.
	 * 
	 * @param	recipe
	 * 			The recipe to check.
	 * @return  True if and only if the given recipe can have each item at its index and the amount of 
	 *          ingredients in this recipe is equal to or greater than the amount of the add processes 
	 *          in the process list of the given recipe.
	 *  	    | result ==
	 *  		|   ( getNbIngredients()>=getNbOfAdd()
	 *  	    |   && for each ingredient in ingredients
	 *          |         canHaveAsIngredient(ingredient) )
	 */
	@Raw
	public boolean hasProperIngredients(Recipe recipe) {
		for (AlchemicIngredient ingredient: recipe.getIngredients()) {
			if (!canHaveAsIngredient(ingredient))
				return false;
		}
		return (recipe.getNbIngredients()>=recipe.getNbOfAdd());
	}
	
	//TODO: addIngredientAt, removeIngredientAt
	
	/**
	 * A List containing the alchemic ingredients needed by the processes of this recipe.
	 * 
	 * @invar The list of alchemic ingredients is effective.
	 *        | processes != null
	 * @invar Each item in the ingredient list is effective.
	 *        | for each item in ingredients:
	 *        |   item != null
	 * @note  There are no extra restrictions on a single ingredient in the list.
	 */
	private List<AlchemicIngredient> ingredients = new ArrayList<AlchemicIngredient>();

}
