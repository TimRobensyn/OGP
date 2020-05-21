package alchemy;
import laboratory.*;

import static org.junit.Assert.*;
import org.junit.*;
import be.kuleuven.cs.som.*;

/**
 * Don't mind me, just trying to understand Capacity and other shit
 * @author	Tim Robensyn
 */
public class CapacityTest {
	
	@Test
	public void noInspirationForANameTest() {
		int cap = LiquidQuantity.BARREL.getNbOfSmallestUnit();
		assertEquals(cap, 12*LiquidQuantity.JUG.getNbOfSmallestUnit());
		System.out.println(LiquidQuantity.values());
		int geheleDeling = 17/4;
		int rest = 17%4;
		int geheleDeling2 = 18/4;
		int rest2 = 18%4;
		System.out.println(geheleDeling);
		System.out.println(rest);
		System.out.println(geheleDeling2);
		System.out.println(rest2);
		
		int jugAmount = LiquidQuantity.JUG.getNbOfSmallestUnit();
		System.out.println(jugAmount);
		assertEquals(jugAmount,840);
	}
	
	@Test
	public void recipeTest() {
		Recipe newRecipe = new Recipe(new String[] {"add","mix"}, new AlchemicIngredient[] {new AlchemicIngredient(25)});
		System.out.println(newRecipe);
	}

}
