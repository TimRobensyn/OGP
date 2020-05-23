package recipe;
import alchemy.*;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.ArrayList;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class for testing the recipe class.
 * 
 * @author Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 *
 */
public class RecipeTest {

	public static Recipe recipe;
	public static AlchemicIngredient ingredient1, ingredient2;
	
	@Before
	public void setUpFixture() {
		ingredient1 = new AlchemicIngredient(50);
		ingredient2 = new AlchemicIngredient(30);
		
		ArrayList<Process> processen = new ArrayList<Process>();
		processen.add(Process.add);
		processen.add(Process.heat);
		processen.add(Process.cool);
		processen.add(Process.add);
		processen.add(Process.heat);
		processen.add(Process.add);
		processen.add(Process.mix);
		
		ArrayList<AlchemicIngredient> ingredienten = new ArrayList<AlchemicIngredient>();
		ingredienten.add(ingredient1);
		ingredienten.add(ingredient2);
		ingredienten.add(ingredient1);
		
		recipe = new Recipe(processen, ingredienten);
	}
	
	
	@Test
	public void test_getIndexOfNthAdd() {
		assertEquals(4, recipe.getIndexOfNthAdd(2));
		assertEquals(3, recipe.getNbOfAdd());
		
		recipe.removeIngredientAt(2);
		assertEquals(5, recipe.getIndexOfNthAdd(2));
		assertEquals(2, recipe.getNbOfAdd());
	}
	
	@Test
	public void test_removeIngredient() {
		recipe.removeIngredientAt(2);
		assertEquals(ingredient1, recipe.getIngredientAt(2));
		assertEquals(5, recipe.getIndexOfNthAdd(2));
		assertEquals(2, recipe.getNbOfAdd());
	}
	
	@Test
	public void test_addIngredient() {
		recipe.addIngredientAt(ingredient2,2);
		assertEquals(ingredient2, recipe.getIngredientAt(2));
		assertEquals(5, recipe.getIndexOfNthAdd(3));
		assertEquals(4, recipe.getNbOfAdd());
	}
}
