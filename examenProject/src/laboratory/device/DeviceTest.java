package laboratory.device;

import alchemy.*;
import temperature.Temperature;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * A class for testing all devices.
 * 
 * @author  Tim Lauwers, Tim Robensyn, Robbe Van BierVliet
 * @version 1.0
 */

public class DeviceTest {
	
	private static Temperature tempCoolingBox, tempOven;
	private static Temperature tempIngredient1, tempIngredient2, invalidTemp;
	
	private static AlchemicIngredient ingredient1, ingredient2;
	
	private static IngredientContainer container1, container2;
	
	private static CoolingBox coolingBox;
	private static Oven oven;
	

	@BeforeClass
	public static void setUpImmutableFixture() {
		tempCoolingBox = new Temperature(1000L,0L);
		tempOven = new Temperature(0L,2000L);
		invalidTemp = new Temperature(0L,10001L);
		
		tempIngredient1 = new Temperature(2000L, 0L);
		tempIngredient2 = new Temperature(0L, 3000L);
		
		
		
	}
	
	@Before
	public void setUpFixture() {
		ingredient1 = new AlchemicIngredient(30);
		ingredient2 = new AlchemicIngredient(200);
		// Set the temperature of ingredient1 to tempIngredient1
		ingredient1.cool(2020);
		// Set the temperature of ingredient2 to tempIngredient2
		ingredient2.heat(2980);
		
		container1 = new IngredientContainer(ingredient1, Unit.VIAL_LIQUID);
		container2 = new IngredientContainer(ingredient2, Unit.JUG_LIQUID);
		
		coolingBox = new CoolingBox(tempCoolingBox);
		oven = new Oven(tempOven);
		
	}
	
	@Test
	public void testSetUp() {
		assertEquals(0,Temperature.compareTemperature(tempIngredient1, ingredient1.getTemperatureObject()));
		assertEquals(0,Temperature.compareTemperature(tempIngredient2, ingredient2.getTemperatureObject()));
	}
	
	@Test
	public void testConstructorCoolingBox_Legal() {
		assertEquals(tempCoolingBox, coolingBox.getTemperatureObject());
		coolingBox.setTemperature(tempOven);
		assertEquals(tempOven, coolingBox.getTemperatureObject());
		
		coolingBox.loadIngredient(container1);
		assertEquals(ingredient1, coolingBox.getStartIngredient());
	}
	
	@Test
	public void testConstructorCoolingBox_IllegalTemp() {
		coolingBox.setTemperature(invalidTemp);
		// The temperature is invalid so it is set to {0,0}.		
		assertEquals(0,Temperature.compareTemperature(new Temperature(0,0), coolingBox.getTemperatureObject()));
	}
	
	
	
}
