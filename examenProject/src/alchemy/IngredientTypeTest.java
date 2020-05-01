package alchemy;
import static org.junit.Assert.*;
import org.junit.*;

/**
 * A test class for IngredientType.
 * 
 * @version	1.0
 * @author 	Tim Lauwers, Tim Robensyn, Robbe Van Biervliet *
 */

public class IngredientTypeTest {
	
	/*private IngredientType legalIngredientType;
	private IngredientType typeWithNumber;
	
	@Before
	public void setupMutableFixture() {
		legalIngredientType = new IngredientType("Rat Shit");
		typeWithNumber = new IngredientType("Rat Shit 2");
	}
	*/
	@Test
	public void nameTest() {
		assertTrue(IngredientType.isValidName("Rat Shit"));
		assertFalse(IngredientType.isValidName("Rat Shit2"));
	}

}
