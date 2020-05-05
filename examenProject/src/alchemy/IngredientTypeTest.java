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
	
	/* private IngredientType legalIngredientType;
	private IngredientType typeWithNumber;
	
	@Before
	public void setupMutableFixture() {
		legalIngredientType = new IngredientType("Rat Shit");
		typeWithNumber = new IngredientType("Rat Shit 2");
	} */
	
    @Test
	public void nameTest() {
		assertFalse(IngredientType.isValidSimpleName("Rat with Shit")); 	//has "with"
		assertFalse(IngredientType.isValidSimpleName("Rat 2day Shit"));		//has illegal character "2"
		assertFalse(IngredientType.isValidSimpleName("No"));				//Word length too short
		assertFalse(IngredientType.isValidSimpleName("rat Shit"));		//has incorrect case
		assertTrue(IngredientType.isValidSimpleName("Rat's Shit (Yeah Pretty Disgusting)"));
	} 

}
