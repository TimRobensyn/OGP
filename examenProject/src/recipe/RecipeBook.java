package recipe;

import java.util.List;
import java.util.ArrayList;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class for books filled with recipes involving a list of recipes.
 * 
 * @invar   The number of pages in this book must be a proper amount for each
 * 		    recipe book.
 * 			| isValidNbPages(getNbPages())
 * @invar	The recipes in this book must be proper recipes for this
 * 			book.
 * 			| hasProperRecipes()
 * 
 * @note	Recipes are stored on exactly one page. A page can be torn out,
 * 			however, this does not change the number of the pages of
 * 			following recipes.
 * 
 * @version 1.0
 * @author	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 */

public class RecipeBook {

	/**********************************************************
	 * Constructor
	 **********************************************************/
	
	/**
	 * Initialize a new recipe book with a given number of pages and a list
	 * of recipes.
	 * 
	 * @param	nbPages
	 * 			The number of pages to be given to this book.
	 * @param	recipes
	 * 			The recipes to be put in the book.
	 * @post	The number of pages of this new book is set to the given
	 * 			number of pages.
	 * 			| new.getNbPages() == nbPages
	 * @effect	Each given recipe is added to the book.
	 * 			| for each recipe in recipes:
	 * 			|	new.hasAsRecipe(recipe)
	 * @throws	IllegalArgumentException
	 * 			The given number of pages is invalid or the given list of
	 * 			recipes contains duplicates or is too long.
	 */
	@Raw
	public RecipeBook(int nbPages, List<Recipe> recipes) throws IllegalArgumentException {
		if (!isValidNbPages(nbPages))
			throw new IllegalArgumentException("Invalid number of pages.");
		this.nbPages = nbPages;
		for (Recipe recipe:recipes) {
			addAsRecipe(recipe);
		}
	}
	
	
	/**********************************************************
	 * Number of pages
	 **********************************************************/
	
	/**
	 * Return the original amount of pages in this book.
	 */
	@Basic @Raw
	public int getNbPages() {
		return this.nbPages;
	}
	
	/**
	 * Check whether the given number of pages is valid.
	 * 
	 * @param  nbPages
	 * 		   The number of pages to check.
	 * @return True if and only if the given value is positive and not above the maximal value for a 
	 * 		   integer.
	 *         | result == (nbPages>0 && nbPages<=Long.MAX_VALUE)
	 */
	@Raw
	public boolean isValidNbPages(int nbPages) {
		return (nbPages>0 && nbPages<=Integer.MAX_VALUE);
	}
	
	/**
	 * A variable for the amount of pages in this book.
	 */
	private final int nbPages;
	
	
	/**********************************************************
	 * Recipes
	 **********************************************************/
	
	
	/**
	 * Return the number of recipes in this book.
	 */
	@Basic @Raw
	public int getNbRecipes() {
		int result = 0;
		for (int i = 1; i<=getNbPages(); i++) {
			if (getRecipeAt(i)!=null)
				result++;
		}
		return result;
	}
	
	/**
	 * Check whether the given number of recipes is valid for this book.
	 * 
	 * @param	number
	 * 			The number of recipes to check.
	 * @return	False if the number is negative or greater than the number of
	 * 			pages in this book.
	 * 			| (number >= 0) && (number <= getNbPages())
	 */
	@Raw
	public boolean canHaveAsNbRecipes(int number) {
		return ((number >= 0) && (number <= getNbPages()));
	}
	
	/**
	 * Change the number of recipes in this book.
	 * 
	 * @param	number
	 * 			The new number of recipes for this book.
	 * @post	The new number of recipes in this book is now the given number.
	 * 			| new.getNbRecipes()==number
	 * @throws	IllegalArgumentException
	 * 			The new number of recipes is invalid for this book.
	 * 			| !canHaveAsNbRecipes(number)
	 * @note	The number of recipes is automatically changed thanks
	 * 			to the usage of ArrayList, this method is merely introduced
	 * 			to follow good coding practice and to better maintainability.
	 */
	@Raw
	private void setNbRecipes(int number) throws IllegalArgumentException{
		if (!canHaveAsNbRecipes(number))
			throw new IllegalArgumentException("Invalid number of recipes");
		
		
	}
	
	/**
	 * Return the recipe on the given page of this book.
	 * 
	 * @param	page
	 * 			The page of the recipe to be returned.
	 * @return	The recipe found at the given page. This is null if
	 * 			the given page has been torn out.
	 * @throws	IndexOutOfBoundsException
	 * 			The given page is not positive or it exceeds the
	 * 			amount of pages in this book.
	 * 			| (page < 1) || (page > getNbPages()
	 */
	@Basic @Raw
	public Recipe getRecipeAt(int page) throws IndexOutOfBoundsException{
		if ((page<1) || (page > getNbPages())) throw new IndexOutOfBoundsException();
		return this.book.get(page-1);
	}
	
	/**
	 * Return the page of this recipe in this book.
	 * 
	 * @param	recipe
	 * 			The recipe to look for.
	 * @return	The given recipe can be found on the returned page
	 * 			in this book.
	 * @throws	IllegalArgumentException
	 * 			The given recipe is not in this book.
	 * 			| ! hasAsRecipe(recipe)
	 */
	public int getPageOfRecipe(Recipe recipe) throws IllegalArgumentException{
		int index = this.book.indexOf(recipe);
		if (index==-1) throw new IllegalArgumentException("Recipe not found");
		return (index+1);
	}
	
	/**
	 * Return the page of the last recipe in this book.
	 * 
	 * @return	Return the page of the last recipe in this book. 
	 * 			Return zero if there are no recipes in this book.
	 */
	@Raw
	public int getPageOfLastRecipe() {
		int result = 0;
		for (int page = 1; page<=getNbPages(); page++) {
			if (getRecipeAt(page)!=null)
				result = page;
		}
		return result;
	}
	
	/**
	 * Check whether this book can have the given recipe.
	 * 
	 * @param	recipe
	 * 			The recipe to check.
	 * @return	False if the given recipe is already in the book.
	 * 			| !hasAsRecipe(recipe)
	 */
	@Raw
	public boolean canHaveAsRecipe(Recipe recipe) {
		return (!hasAsRecipe(recipe));
	}
	
	
	/**
	 * Check whether this book has proper recipes associated with it.
	 * 
	 * @return	True if and only if this book can have
	 * 			each of its recipes at their page.
	 * 			| result ==
	 * 			|	for each recipe in this book :
	 * 			|	   canHaveAsRecipe(recipe)
	 */
	@Raw
	public boolean hasProperRecipes() {
		for (Recipe recipe:this.book) {
			if (!canHaveAsRecipe(recipe))
					return false;
		}
		return true;
	}
	
	/**
	 * Check whether the given recipe is one of the recipes
	 * associated with the book.
	 * 
	 * @param	recipe
	 * 			The recipe to be checked.
	 * @return	True if and only if this book has the given
	 * 			recipe as one of its recipes at some page.
	 * 			| result ==
	 * 			|	for some I in 1..getNbRecipes() :
	 * 			|	   (getRecipeAt(I) == recipe)
	 */
	@Raw
	public boolean hasAsRecipe(Recipe recipe) {
		return this.book.contains(recipe);
	}
	
	/**
	 * Return a list of all recipes associated with this book.
	 * 
	 * @return	The number of elements in the resulting list is
	 * 			equal to the number of pages in this book.
	 * 			| result.size() == getNbPages()
	 * @return	Each recipe at a given page in the resulting list
	 * 			is the same as the recipe associated with this book
	 * 			at the corresponding page.
	 * 			| for each I in 0..getNbRecipes()-1:
	 * 			|	(result.get(I) == getRecipeAt(I+1))
	 */
	@Basic
	public List<Recipe> getAllRecipes() {
		return this.book;		
	}
	

	/**
	 * Remove the recipe in this book at the given page by tearing
	 * the page out.
	 * 
	 * @param	page
	 * 			The page of the recipe to be removed.
	 * @post	This book no longer has the recipe at the given
	 * 			page in it.
	 * 			| new.getRecipeAt(page) == null
	 * @post	The number of recipes associated with this book
	 * 			is decremented by 1.
	 * 			| new.getNbRecipe() == this.getNbRecipes() - 1
	 * @throws	IndexOutOfBoundsException
	 * 			The given page is not positive or it exceeds the
	 * 			number of pages associated with this book.
	 * 			| (page < 1) || (page > getNbPages())
	 * @throws	IllegalArgumentException
	 * 			This page has already been torn out.
	 */
	public void removeRecipeAt(int page) throws IndexOutOfBoundsException, IllegalArgumentException{
		if (getRecipeAt(page)==null)
			throw new IllegalArgumentException("Recipe has already been torn out.");
		setNbRecipes(getNbRecipes()-1);
		this.book.set(page-1, null);
	}
	
	/**
	 * Add the given recipe to this book at the page after the
	 * last recipe.
	 * 
	 * @param	recipe
	 * 			The recipe to put in the book.
	 * @post	This book has the given recipe as its last recipe.
	 * 			| new.getRecipeAt(getPageOfLastRecipe()) == recipe
	 * @post	The number of recipes in this book is incremented
	 * 			by 1.
	 * 			| new.getNbRecipes() == getNbRecipes() + 1
	 * @throws	IllegalArgumentException
	 * 			This book already has the given recipe.
	 * 			| !canHaveAsRecipe(recipe)
	 */
	@Raw
	public void addAsRecipe(Recipe recipe) throws IllegalArgumentException{
		if (!canHaveAsRecipe(recipe))
			throw new IllegalArgumentException("This book already contains this recipe.");
		setNbRecipes(getNbRecipes()+1);
		this.book.set(getPageOfLastRecipe()-1, recipe);
		
	}
	
	/**
	 * Remove the given recipe from this book.
	 * 
	 * @param	recipe
	 * 			The recipe to be removed from the book.
	 * @effect	The given recipe is removed from the book.
	 * 			| removeRecipeAt(getPageOfRecipe(recipe))
	 * @throws	IllegalArgumentException
	 * 			The given recipe could not be found in this book.
	 */
	public void removeAsRecipe(Recipe recipe) throws IllegalArgumentException{
		removeRecipeAt(getPageOfRecipe(recipe));		
	}
	
	/**
	 * List collecting references to the recipes in this book.
	 * 
	 * @invar	The list of recipes is effective.
	 * 			| book != null
	 * @invar	Each element in the list of recipes references a valid recipe.
	 * 			| for each recipe in book:
	 * 			|   canHaveAsRecipe(recipe)
	 */
	private List<Recipe> book = new ArrayList<Recipe>();

}
