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
	
	private static IngredientType typePowder;
	private static AlchemicIngredient ingredient1, ingredient2, ingredientPowder;
	
	private static IngredientContainer container1, container2, containerPowder;
	private static IngredientContainer[] ingredientArrayTm, ingredientArrayKettle;
	
	private static CoolingBox coolingBox;
	private static Oven oven;
		
	private static Transmogrifier transmogrifier;
	private static Kettle kettle;

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
		// both ingredients have type water and thus temperature {0,20}
		ingredient1 = new AlchemicIngredient(30);
		ingredient2 = new AlchemicIngredient(200);
		// Set the temperature of ingredient1 to tempIngredient1
		ingredient1.cool(2020);
		// Set the temperature of ingredient2 to tempIngredient2
		ingredient2.heat(2980);
		
		
		container1 = new IngredientContainer(ingredient1, Unit.VIAL_LIQUID);
		container2 = new IngredientContainer(ingredient2, Unit.JUG_LIQUID);
		
		typePowder = new IngredientType("Crumbs",State.POWDER,new Temperature(0,100));
		ingredientPowder = new AlchemicIngredient(typePowder,750);
		containerPowder = new IngredientContainer(ingredientPowder,Unit.SACK_POWDER);
		
		coolingBox = new CoolingBox(tempCoolingBox);
		oven = new Oven(tempOven);
		
		ingredientArrayTm = new IngredientContainer[] {container1, containerPowder};
		transmogrifier = new Transmogrifier(ingredientArrayTm);
		
		ingredientArrayKettle = new IngredientContainer[] {container1,container2,containerPowder};
		kettle = new Kettle(ingredientArrayKettle);
		
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
	
	@Test
	public void testCoolingBoxProcess_coldIngredient() {
		// This container has an ingredient that is already colder than the cooling box.
		coolingBox.loadIngredient(container1);
		coolingBox.process();
		assertEquals(coolingBox.getProcessedIngredient(),ingredient1);
		assertEquals(coolingBox.getStartIngredient(),null);
				
		// The temperature of the processed ingredient is still its own.
		assertEquals(0,Temperature.compareTemperature(tempIngredient1, 
				                                      coolingBox.getProcessedIngredient().getTemperatureObject()));
	}
	
	@Test
	public void testCoolingBoxProcess_hotIngredient() {
		// This container has an ingredient that is hotter than the cooling box.
		coolingBox.loadIngredient(container2);
		coolingBox.process();
		assertEquals(coolingBox.getProcessedIngredient(),ingredient2);
		assertEquals(coolingBox.getStartIngredient(),null);
				
		// The temperature of the processed ingredient is the same as of the cooling box.
		assertEquals(0,Temperature.compareTemperature(tempCoolingBox, 
				                                      coolingBox.getProcessedIngredient().getTemperatureObject()));
	}
	
	@Test
	public void testOvenProcess_hotIngredient() {
		// This container has an ingredient that is already hotter than the oven.
		oven.loadIngredient(container2);
		oven.process();
		assertEquals(oven.getProcessedIngredient(),ingredient2);
		assertEquals(oven.getStartIngredient(),null);
				
		// The temperature of the processed ingredient is still its own.
		assertEquals(0,Temperature.compareTemperature(tempIngredient2, 
				                                      oven.getProcessedIngredient().getTemperatureObject()));
	}
	
	
	@Test
	public void testOvenProcess_coldIngredient() {
		// This container has an ingredient that is cooler than the oven.
		oven.loadIngredient(container1);
		oven.process();
		assertEquals(oven.getProcessedIngredient(),ingredient1);
		assertEquals(oven.getStartIngredient(),null);
		
		// The chance is small enough to assume the processed ingredient doesn't have the exact same temperature as
		// the oven.
		assertNotEquals(0,Temperature.compareTemperature(tempIngredient1, 
				          oven.getProcessedIngredient().getTemperatureObject()));
		// It is within a five percent range though.
		assertTrue(oven.getProcessedIngredient().getTemperatureObject().getHotness()
				   < oven.getTemperatureObject().getHotness()*1.05);
		assertTrue(oven.getProcessedIngredient().getTemperatureObject().getHotness()
				   > oven.getTemperatureObject().getHotness()*0.95);
	}
	
	@Test
	public void testTemperatureDevice_empty() {
		coolingBox.loadIngredient(container2);
		coolingBox.process();
		
		assertEquals(coolingBox.emptyDevice().getCapacity(),Unit.JUG_LIQUID);
	}
	
	
	
	
	@Test
	public void testConstructorTransmogrifier() {
		Transmogrifier transmogrifierTest = new Transmogrifier(ingredientArrayTm);
		assertEquals(transmogrifierTest.getNbStartIngredients(),2);
		assertTrue(transmogrifierTest.getStartIngredients().contains(ingredient1));
		assertTrue(transmogrifierTest.getStartIngredients().contains(ingredientPowder));
	}
	
	@Test
	public void testBottomlessDevice_LoadIngredient() {
		Transmogrifier transmogrifierTest = new Transmogrifier();
		transmogrifierTest.loadIngredient(container1);
		assertEquals(1,transmogrifierTest.getNbStartIngredients());
		assertTrue(transmogrifierTest.getStartIngredients().contains(ingredient1));
	}
	
	@Test
	public void testTransmogrifier_process() {
		transmogrifier.process();
		assertEquals(0, transmogrifier.getNbStartIngredients());
		assertEquals(2, transmogrifier.getNbProcessedIngredients());
		
		//The first ingredient is changed from state to powder.
		AlchemicIngredient firstIngredient = transmogrifier.getProcessedIngredientAt(1);
		assertEquals(State.POWDER, firstIngredient.getState());
		assertEquals("Water", firstIngredient.getType().getSimpleName());
		int quantity1 = (int) Math.floor(30*Unit.getRatio(State.POWDER,State.LIQUID));
		assertEquals(quantity1, firstIngredient.getQuantity());
		
		//The second ingredient is changed from state to liquid.
		AlchemicIngredient secondIngredient = transmogrifier.getProcessedIngredientAt(2);
		assertEquals(State.LIQUID, secondIngredient.getState());
		assertEquals("Crumbs", secondIngredient.getType().getSimpleName());
		int quantity2 = (int) Math.floor(750*Unit.getRatio(State.LIQUID,State.POWDER));
		assertEquals(quantity2, secondIngredient.getQuantity());
	}
	
	@Test
	public void testKettle_process() {
		kettle.process();
		AlchemicIngredient resultIngredient = kettle.getProcessedIngredientAt(1);
		
		assertEquals("Crumbs mixed with Water", resultIngredient.getType().getSimpleName());
		// Ingredient1 en ingredient2 hebben de dichtste standaard temperatuur bij water.
		assertEquals(ingredient1.getState(), resultIngredient.getState());
		assertEquals(0,Temperature.compareTemperature(ingredient1.getStandardTemperatureObject(), 
				                                      resultIngredient.getStandardTemperatureObject()));
		int expectedQuantity = ingredient1.getQuantity()+ingredient2.getQuantity()+
				(int) Math.floor(750*Unit.getRatio(ingredient1.getState(), ingredientPowder.getState()));
		assertEquals(expectedQuantity,resultIngredient.getQuantity());
		
		// Calculating the weighted average of the temperatures.
		double nbOfSpoonsIngredient1 = (double) ingredient1.getQuantity()/Unit.SPOON_LIQUID.getAbsoluteCapacity();
		double nbOfSpoonsIngredient2 = (double) ingredient2.getQuantity()/Unit.SPOON_LIQUID.getAbsoluteCapacity();
		double nbOfSpoonsIngredientPowder = (double) ingredientPowder.getQuantity()/Unit.SPOON_POWDER.getAbsoluteCapacity();
		double totalSpoons = nbOfSpoonsIngredient1 + nbOfSpoonsIngredient2 + nbOfSpoonsIngredientPowder;
		
		double totalHotness = ingredient1.getHotness()*nbOfSpoonsIngredient1
				            + ingredient2.getHotness()*nbOfSpoonsIngredient2
							+ ingredientPowder.getHotness()*nbOfSpoonsIngredientPowder;
		double totalColdness = ingredient1.getColdness()*nbOfSpoonsIngredient1
				             + ingredient2.getColdness()*nbOfSpoonsIngredient2
							 + ingredientPowder.getColdness()*nbOfSpoonsIngredientPowder;
		long difference = (long) ((totalHotness-totalColdness)/totalSpoons);
		// This number is positive and greater than 20 (= the standard hotness of ingredient1 and ingredient2)
		// The temperature of the mix was set to this number.
		
		assertEquals(0, Temperature.compareTemperature(new Temperature(0L,difference),
				                                       resultIngredient.getTemperatureObject()) );
	}
	
	
	
	@Test
	public void testBottomlessDevice_empty() {
		transmogrifier.process();
		AlchemicIngredient firstIngredient = transmogrifier.getProcessedIngredientAt(1);
		AlchemicIngredient secondIngredient = transmogrifier.getProcessedIngredientAt(2);
		
		assertEquals(transmogrifier.emptyDevice().getCapacity(),Unit.SACHET_POWDER);
		assertFalse(transmogrifier.getProcessedIngredients().contains(firstIngredient));
		assertEquals(1, transmogrifier.getNbProcessedIngredients());
		assertEquals(secondIngredient, transmogrifier.getProcessedIngredientAt(1));
		
		assertEquals(transmogrifier.emptyDevice().getCapacity(),Unit.BARREL_LIQUID);
		assertFalse(transmogrifier.getProcessedIngredients().contains(secondIngredient));
		assertEquals(0, transmogrifier.getNbProcessedIngredients());
	}

	
}
