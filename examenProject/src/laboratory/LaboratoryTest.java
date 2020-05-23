package laboratory;
import alchemy.*;
import static org.junit.Assert.*;
import org.junit.*;
import java.util.ArrayList;

public class LaboratoryTest {

	public static Laboratory laboratory, laboratory_empty_allDevices, laboratory_empty, laboratory_waterStorage, laboratory_storage, laboratory_big;
	public static Laboratory laboratoryCapacityStorageDevices, laboratoryCapacityDevices, laboratoryCapacity;
	
	public static CoolingBox coolingbox;
	public static Oven oven;
	public static Kettle emptyKettle;
	public static Transmogrifier emptyTransmogrifier;
	
	public static IngredientType ingredientTypeLiquid, ingredientTypePowder;
	public static AlchemicIngredient water, ingredientLiquid, ingredientPowder, bigIngredient;
	public static ArrayList<AlchemicIngredient> waterStorage, emptyStorage, storage, bigStorage;
	public static IngredientContainer waterContainer;
	
	@BeforeClass
	public static void setUpImmutableFixture() {
		emptyKettle = new Kettle();
		emptyTransmogrifier = new Transmogrifier();
		emptyStorage = new ArrayList<AlchemicIngredient>();
		bigStorage = new ArrayList<AlchemicIngredient>();
		
		water = new AlchemicIngredient(10);
		bigIngredient = new AlchemicIngredient(15000);
		
		laboratory_empty_allDevices = new Laboratory(100, coolingbox, oven, emptyKettle, emptyTransmogrifier);
		laboratory_empty = new Laboratory(100);
		
		waterContainer = new IngredientContainer(water, Unit.VIAL_LIQUID);
		bigStorage.add(bigIngredient);
	}
	
	@Before
	public void setUpFixture() {
		coolingbox = new CoolingBox(new Temperature(0,0));
		oven = new Oven(new Temperature(0,0));
		ingredientTypeLiquid = new IngredientType("ingredientTypeLiquid", State.LIQUID, new Temperature(0,15));
		ingredientLiquid = new AlchemicIngredient(ingredientTypeLiquid,15);
		ingredientTypePowder = new IngredientType("ingredientTypePowder", State.POWDER, new Temperature(0,15));
		ingredientPowder = new AlchemicIngredient(ingredientTypePowder,15);
		
		waterStorage = new ArrayList<AlchemicIngredient>();
		waterStorage.add(water);
		storage = new ArrayList<AlchemicIngredient>();
		storage.add(ingredientLiquid);
		storage.add(ingredientPowder);
		laboratory_waterStorage = new Laboratory(100, waterStorage, null, null, null, null);
		laboratory_storage = new Laboratory(100, storage, null, null, emptyKettle, null);
		laboratory_big = new Laboratory(5000, bigStorage, coolingbox, oven, emptyKettle, emptyTransmogrifier);
	}
	
	@Test
	public void testConstructorFull_Legal() {
		laboratoryCapacityStorageDevices = new Laboratory(100, waterStorage, coolingbox, oven, emptyKettle, emptyTransmogrifier);
		
		assertEquals(100, laboratory.getCapacity());
		assertEquals(waterStorage, laboratory.getStorage());
		assertEquals(coolingbox, laboratory.getCoolingbox());
		assertEquals(oven, laboratory.getOven());
		assertEquals(emptyKettle, laboratory.getKettle());
		assertEquals(emptyTransmogrifier, laboratory.getTransmogrifier());
	}
	
	@Test
	public void testConstructorCapacityDevices_Legal() {
		laboratoryCapacityDevices = new Laboratory(100, coolingbox, oven, emptyKettle, emptyTransmogrifier);
		
		assertEquals(100, laboratory_empty_allDevices.getCapacity());
		assertEquals(new ArrayList<AlchemicIngredient>(), laboratory_empty_allDevices.getStorage());
		assertEquals(coolingbox, laboratory_empty_allDevices.getCoolingbox());
		assertEquals(oven, laboratory_empty_allDevices.getOven());
		assertEquals(emptyKettle, laboratory_empty_allDevices.getKettle());
		assertEquals(emptyTransmogrifier, laboratory_empty_allDevices.getTransmogrifier());
	}
	
	@Test
	public void testConstructorCapacity_Legal() {
		laboratoryCapacity = new Laboratory(100);
		
		assertEquals(100, laboratory_empty.getCapacity());
		assertEquals(new ArrayList<AlchemicIngredient>(), laboratory_empty.getStorage());
		assertEquals(laboratory_empty.getCoolingbox(), null); //TODO DIT IS EEN THROW
		assertEquals(laboratory_empty.getOven(), null);
		assertEquals(laboratory_empty.getKettle(),null);
		assertEquals(laboratory_empty.getTransmogrifier(), null);
	}
	
	@Test (expected = CapacityException.class)
	public void testConstructor_Illegal_invalidCapacity() {
		laboratory = new Laboratory(-1);
	}
	
	@Test
	public void testIsValidCapacity_LegalCase() {
		assertTrue(Laboratory.isValidCapacity(10));
	}
	
	@Test
	public void testIsValidCapacity_IllegalCase() {
		assertFalse(Laboratory.isValidCapacity(-1));
		assertFalse(Laboratory.isValidCapacity(Integer.MAX_VALUE + 1));
	}
	
	@Test
	public void testGetStorageQuantity() {
		assertEquals(laboratory_empty.getStorageQuantity(), 0);
		assertEquals(laboratory_storage.getStorageQuantity(), 30);
	}
	
	@Test
	public void testGetNbOfIngredients() {
		assertEquals(laboratory_storage.getNbIngredients(), 2);
	}
	
	@Test
	public void testGetIngredientAt() {
		assertEquals(ingredientPowder, laboratory_storage.getIngredientAt(2));
	}
	
	@Test
	public void testAddAsIngredient() {
		laboratory_storage.addAsIngredient(water);
		assertEquals(water, laboratory_storage.getIngredientAt(3));
	}
	
	@Test
	public void testRemoveAsIngredient() {
		laboratory_storage.removeAsIngredient(ingredientLiquid);
		assertEquals(laboratory_storage.getIngredientAt(1), ingredientPowder);
	}
	
	@Test
	public void testHasProperIngredients_LegalCase() {
		assertTrue(laboratory_waterStorage.hasProperIngredients());
	}
	
	@Test
	public void testHasProperIngredients_IllegalCase() {
		AlchemicIngredient ingredientLiquid2 = new AlchemicIngredient(ingredientTypeLiquid, 5);
		laboratory_storage.addAsIngredient(ingredientLiquid2);
		assertFalse(laboratory_storage.hasProperIngredients());
	}
	
	@Test
	public void testStore_legalCase_NewIngredientType() {
		laboratory_storage.store(waterContainer);
		assertEquals(water, laboratory_storage.getIngredientAt(3));
		assertEquals(waterContainer, null);
	}
	
	@Test
	public void testStore_LegalCase_OldIngredientType() {
		AlchemicIngredient ingredientLiquid2 = new AlchemicIngredient(ingredientTypeLiquid, 5);
		IngredientContainer container2 = new IngredientContainer(ingredientLiquid2, Unit.VIAL_LIQUID);

		laboratory_storage.store(container2);
		assertEquals(ingredientPowder, laboratory_storage.getIngredientAt(1));
		assertEquals(ingredientTypeLiquid, laboratory_storage.getIngredientAt(2).getType());
		assertEquals(laboratory_storage.getIngredientAt(2).getQuantity(), 20);
		assertEquals(container2, null);
	}
	
	
	@Test (expected = CapacityException.class)
	public void testStore_IllegalCase_InvalidCapacity() {
		AlchemicIngredient bigIngredient = new AlchemicIngredient(ingredientTypeLiquid, 200);
		IngredientContainer bigContainer = new IngredientContainer(bigIngredient, Unit.getContainer(State.LIQUID, 200));
		
		laboratory_storage.store(bigContainer);
	}
	
	@Test
	public void testRequestNameAmount_LegalCase_NoLeftovers() {
		IngredientContainer ingredientContainer = laboratory_storage.request("ingredientTypeLiquid", 15);
		
		assertEquals(ingredientLiquid, ingredientContainer.getIngredient());
		assertEquals(ingredientPowder, laboratory_storage.getIngredientAt(1));
	}
	
	@Test
	public void testRequestNameAmount_LegalCase_Leftovers() {
		IngredientContainer ingredientContainer = laboratory_storage.request("ingredientTypeLiquid", 10);
		
		assertEquals(ingredientTypeLiquid, ingredientContainer.getIngredient().getType());
		assertEquals(10, ingredientContainer.getIngredient().getQuantity());
		assertEquals(ingredientTypeLiquid, laboratory_storage.getIngredientAt(1).getType());
		assertEquals(5, laboratory_storage.getIngredientAt(1).getQuantity());
		assertEquals(ingredientPowder, laboratory_storage.getIngredientAt(2));
	}
	
	@Test (expected = CapacityException.class)
	public void testRequestNameAmount_IllegalCase_IngredientNotFound() {
		laboratory_storage.request("water",10);
	}
	
	@Test (expected = CapacityException.class)
	public void testRequestNameAmount_IllegalCase_NotEnoughIngredient() {
		laboratory_storage.request("ingredientTypeLiquid", 20);
	}
	
	@Test (expected = CapacityException.class)
	public void testRequestNameAmount_IllegalCase_AmountTooBig() {
		laboratory_big.request("Water", 15000);
	}
	
	@Test
	public void testRequestName_LegalCase() {
		IngredientContainer container = laboratory_big.request("Water");
		
		assertEquals(container.getCapacity(), Unit.BARREL_LIQUID);
		assertEquals(container.getIngredient(), bigIngredient);
	}
	
	@Test (expected = CapacityException.class)
	public void testRequestName_IllegalCase_IngredientNotFound() {
		laboratory_storage.request("Water");
	}
	
	@Test
	public void testGetInventory() {
		
	}
	
	@Test
	public void testGetDevices() {
		ArrayList<Device> devices = new ArrayList<Device>();
		devices.add(coolingbox);
		devices.add(oven);
		devices.add(emptyKettle);
		devices.add(emptyTransmogrifier);
		
		ArrayList<Device> requestedDevices = laboratory_big.getDevices();
		assertEquals(requestedDevices, devices);
	}
	
	@Test
	public void testGetNbDevices() {
		assertEquals(4, laboratory_big.getNbDevices());
		assertEquals(0, laboratory_waterStorage.getNbDevices());
	}
	
	@Test
	public void testGetDeviceAt() {
		assertEquals(coolingbox, laboratory_big.getDeviceAt(1));
		assertEquals(oven, laboratory_big.getDeviceAt(2));
		assertEquals(emptyKettle, laboratory_big.getDeviceAt(3));
		assertEquals(emptyTransmogrifier, laboratory_big.getDeviceAt(4));
	}
	
	@Test
	public void testIsValidDeviceAt_LegalCase() { //TODO NOT VALID IF ALREADY IN ANOTHER LAB
		assertTrue(Laboratory.isValidDeviceAt(coolingbox, 1));
		assertTrue(Laboratory.isValidDeviceAt(oven, 2));
		assertTrue(Laboratory.isValidDeviceAt(emptyKettle, 3));
		assertTrue(Laboratory.isValidDeviceAt(emptyTransmogrifier, 4));
		
	}
	
	@Test
	public void testIsValidDeviceAt_IllegalCase() { //TODO TEST NOT VALID IF ALREADY IN ANOTHER LAB
		assertFalse(Laboratory.isValidDeviceAt(coolingbox, 2));
		assertFalse(Laboratory.isValidDeviceAt(coolingbox, 3));
		assertFalse(Laboratory.isValidDeviceAt(coolingbox, 4));
		
		assertFalse(Laboratory.isValidDeviceAt(oven, 1));
		assertFalse(Laboratory.isValidDeviceAt(oven, 3));
		assertFalse(Laboratory.isValidDeviceAt(oven, 4));
		
		assertFalse(Laboratory.isValidDeviceAt(emptyKettle, 1));
		assertFalse(Laboratory.isValidDeviceAt(emptyKettle, 2));
		assertFalse(Laboratory.isValidDeviceAt(emptyKettle, 4));
		
		assertFalse(Laboratory.isValidDeviceAt(emptyTransmogrifier, 1));
		assertFalse(Laboratory.isValidDeviceAt(emptyTransmogrifier, 2));
		assertFalse(Laboratory.isValidDeviceAt(emptyTransmogrifier, 3));
	}
	
	@Test
	public void testHasProperDevices_LegalCase() {
		
	}
	
	@Test
	public void testHasProperDevices_IllegalCase() {
		
	}
	
	@Test
	public void testAddAsDevice_LegalCase() {
		laboratory_empty.addAsDevice(coolingbox);
		assertEquals(laboratory_empty.getCoolingbox(), coolingbox);
		assertEquals(laboratory_empty.getDeviceAt(1), coolingbox);
	}
	
	@Test (expected = CapacityException.class)
	public void testAddAsDevice_IllegalCase() {
		laboratory_big.addAsDevice(coolingbox);
	}
	
	@Test
	public void testRemoveAsDevice() {
		laboratory_big.removeAsDevice(coolingbox);
		assertEquals(null, laboratory_big.getDeviceAt(1));
	}
	
	@Test
	public void testGetCoolingbox() {
		
	}
	
	@Test
	public void testGetOven() {
		
	}
	
	@Test
	public void testGetKettle() {
		
	}
	
	@Test
	public void testGetTransmogrifier() {
		
	}
	
	@Test
	public void testMakeStandardTemp() {
		
	}
}
