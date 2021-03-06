package laboratory;
import alchemy.*;
import laboratory.device.*;
import temperature.Temperature;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LaboratoryTest {

	public static AlchemicIngredient water, water_full, crumbs, coke;
	
	public static Map<IngredientType,Integer> storage,storage_full;
	
	public static CoolingBox coolingBox, coolingBox_full, coolingBox_empty;
	public static Oven oven, oven_full;
	public static Kettle kettle, kettle_full;
	public static Transmogrifier transmogrifier, transmogrifier_full;
	
	public static Set<Device> devices, devices_full;
	
	public static Laboratory laboratory_empty,laboratory,laboratory_full, laboratory_terminated;
	
	public static Laboratory laboratoryCapacityStorageDevices, laboratoryCapacityStorage,
							 laboratoryCapacityDevices, laboratoryCapacity;

	@BeforeClass
	public static void setUpImmutableFixture() {
		laboratory_terminated = new Laboratory(100);
	}
	
	@Before
	public void setUpFixture() {
		water_full = new AlchemicIngredient(50400);
		
		storage_full = new HashMap<IngredientType,Integer>();
		storage_full.put(water_full.getType(),water_full.getQuantity());
		
		coolingBox_full = new CoolingBox(new Temperature(0,0));
		oven_full = new Oven(new Temperature(0,0));
		kettle_full = new Kettle();
		transmogrifier_full = new Transmogrifier();
		
		devices_full = new HashSet<Device>();
		devices_full.add(coolingBox_full);
		devices_full.add(oven_full);
		devices_full.add(kettle_full);
		devices_full.add(transmogrifier_full);

		water = new AlchemicIngredient(24);
		crumbs = new AlchemicIngredient(new IngredientType("Crumbs",State.POWDER,
				new Temperature(0,40)),30);
		coke = new AlchemicIngredient(new IngredientType("Dough",State.LIQUID,
				new Temperature(10,0)),80);
		storage = new HashMap<IngredientType,Integer>();
		storage.put(water.getType(),water.getQuantity());
		storage.put(crumbs.getType(), crumbs.getQuantity());
		
		coolingBox = new CoolingBox(new Temperature(0,0));
		oven = new Oven(new Temperature(0,0));
		kettle = new Kettle();
		transmogrifier = new Transmogrifier();
		
		coolingBox_empty = new CoolingBox(new Temperature(0,0));
		
		devices = new HashSet<Device>();
		devices.add(coolingBox);
		devices.add(oven);
		devices.add(kettle);
		devices.add(transmogrifier);
		
		laboratory_empty = new Laboratory(5);
		laboratory = new Laboratory(5,storage,devices);
		laboratory_full = new Laboratory(1,storage_full,devices_full);
	}
	
	@Test
	public void testConstructorFull_Legal() {
		laboratoryCapacityStorageDevices = laboratory;

		assertEquals(5, laboratoryCapacityStorageDevices.getCapacity());
		for (IngredientType type:storage.keySet()) {
			assertTrue(laboratoryCapacityStorageDevices.hasAsIngredientType(type));
			assertEquals(laboratoryCapacityStorageDevices.getQuantityOf(type),
					storage.get(type).intValue());
		}
		for (Device device:devices) {
			assertTrue(laboratoryCapacityStorageDevices.hasAsDevice(device));
		}

	}
	
	@Test
	public void testConstructorCapacityDevices_Legal() {
		laboratoryCapacityDevices = laboratory;

		assertEquals(5, laboratoryCapacityDevices.getCapacity());
		assertEquals(2, laboratoryCapacityDevices.getInventory()[0].length);
		for (Device device:devices) {
			assertTrue(laboratoryCapacityDevices.hasAsDevice(device));
		}
	}
	
	@Test
	public void testConstructorCapacity_Legal() {
		laboratoryCapacity = new Laboratory(100);
		
		assertEquals(100, laboratoryCapacity.getCapacity());
		assertEquals(0, laboratoryCapacity.getInventory()[0].length);
		assertFalse(laboratoryCapacity.hasAsDevice(CoolingBox.class));
		assertFalse(laboratoryCapacity.hasAsDevice(Oven.class));
		assertFalse(laboratoryCapacity.hasAsDevice(Kettle.class));
		assertFalse(laboratoryCapacity.hasAsDevice(Transmogrifier.class));
	}

	@Test
	public void testHasAsDevice() {
		assertTrue(laboratory.hasAsDevice(coolingBox));
		assertFalse(laboratory_full.hasAsDevice(coolingBox));
	}
	
	@Test
	public void testHasAsDeviceClass() {
		assertTrue(laboratory.hasAsDevice(CoolingBox.class));
		assertFalse(laboratory_empty.hasAsDevice(CoolingBox.class));
	}

	
	@Test
	public void testHasProperDevices_LegalCase() {
		assertTrue(laboratory.hasProperDevices());
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
	public void testGetUsedCapacity() {
		assertEquals(0,laboratory_empty.getUsedCapacity(),0.00001);
		assertEquals(8.0,laboratory.getUsedCapacity(),0.00001);
	}

	
	@Test
	public void testGetQuantityOf() {
		assertEquals(24, laboratory.getQuantityOf(water.getType()));
		assertEquals(30, laboratory.getQuantityOf(crumbs.getType()));
	}
	
	@Test (expected = CapacityException.class)
	public void testGetQuantityOf_IllegalCase() {
		laboratory_empty.getQuantityOf(water.getType());
	}
	
	@Test
	public void testStore_legalCase_NewIngredientType() {
		IngredientContainer container = new IngredientContainer(coke,Unit.getContainer(
				coke.getState(), coke.getQuantity()));
		laboratory.store(container);
		assertTrue(laboratory.hasAsIngredientType(coke.getType()));
	}
	
	@Test
	public void testStore_LegalCase_OldIngredientType() {
		IngredientContainer container = new IngredientContainer(water, Unit.getContainer(
				water.getState(), water.getQuantity()));

		laboratory.store(container);
		assertTrue(laboratory.hasAsIngredientType(water.getType()));
		assertEquals(48,laboratory.getQuantityOf(water.getType()));
	}
	


	@Test (expected = CapacityException.class)
	public void testStore_IllegalCase_InvalidCapacity() {
		AlchemicIngredient ingredient = new AlchemicIngredient(1);
		IngredientContainer container = new IngredientContainer(
				ingredient, Unit.getBiggestContainer(State.LIQUID));
		laboratory_full.store(container); //Deze error kregen we er niet meer op tijd uit.
	}

	
	@Test
	public void testRequestNameAmount_LegalCase_NoLeftovers() {
		assertTrue(laboratory.hasAsIngredientType(crumbs.getType()));
		IngredientContainer ingredientContainer = laboratory.request("Crumbs",30);
		assertFalse(laboratory.hasAsIngredientType(crumbs.getType()));
		assertEquals(crumbs.getType(),ingredientContainer.getContents().getType());
	}
	
	@Test
	public void compareIngredientTypes() {
		assertEquals(new IngredientType("Water",State.LIQUID,new Temperature(0,20)),water.getType());
	}
	
	@Test
	public void vergelijkTypes() {
		assertEquals(new IngredientType("Water",State.LIQUID,new Temperature(0,20)),water.getType());
	}

	
	@Test
	public void testRequestNameAmount_LegalCase_Leftovers() {
		IngredientContainer ingredientContainer = laboratory.request("Crumbs", 10);
		
		assertTrue(laboratory.hasAsIngredientType(crumbs.getType()));
		assertEquals(10, ingredientContainer.getContents().getQuantity());
		assertEquals(20, laboratory.getQuantityOf(ingredientContainer.getContents().getType()));
	}
	
	@Test (expected = CapacityException.class)
	public void testRequestNameAmount_IllegalCase_IngredientNotFound() {
		laboratory.request("water",10);
	}
	
	@Test (expected = CapacityException.class)
	public void testRequestNameAmount_IllegalCase_NotEnoughIngredient() {
		laboratory.request("Crumb", 31);
	}
	
	@Test (expected = CapacityException.class)
	public void testRequestNameAmount_IllegalCase_AmountTooBig() {
		laboratory_full.request("Water", 50401);
	}
	
	@Test
	public void testRequestName_LegalCase() {
		IngredientContainer container = laboratory.request("Water");
		
		assertEquals(Unit.VIAL_LIQUID,container.getCapacity());
		assertEquals(water.getType(), container.getContents().getType());
	}
	
	@Test (expected = CapacityException.class)
	public void testRequestName_IllegalCase_IngredientNotFound() {
		laboratory_empty.request("Water");
	}
	
	@Test
	public void testAddAsDevice_LegalCase() {
		laboratory_empty.addAsDevice(coolingBox_empty);
		assertEquals(laboratory_empty.getDevice(CoolingBox.class), coolingBox_empty);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testAddAsDevice_IllegalCase_FullLaboratory() {
		laboratory_full.addAsDevice(coolingBox_empty);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddAsDevice_IllegalCase_InvalidDevice() {

		laboratory_full.addAsDevice(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddAsDevice_IllegalCase_DeviceInLaboratory() {

		laboratory_empty.addAsDevice(coolingBox);
	}
	
	
	@Test
	public void testGetDevice_LegalCase() {
		assertEquals(coolingBox, laboratory.getDevice(CoolingBox.class));
		assertEquals(oven, laboratory.getDevice(Oven.class));
		assertEquals(kettle, laboratory.getDevice(Kettle.class));
		assertEquals(transmogrifier, laboratory.getDevice(Transmogrifier.class));
	}
	
	@Test (expected = CapacityException.class)
	public void testGetDevice_IllegalCase() {
		laboratory_empty.getDevice(CoolingBox.class);
	}
	
	@Test
	public void testRemoveAsDevice() {
		laboratory.removeAsDevice(coolingBox);
		laboratory.removeAsDevice(oven);
		laboratory.removeAsDevice(kettle);
		laboratory.removeAsDevice(transmogrifier);
		assertFalse(laboratory.hasAsDevice(coolingBox));
		assertFalse(laboratory.hasAsDevice(oven));
		assertFalse(laboratory.hasAsDevice(kettle));
		assertFalse(laboratory.hasAsDevice(transmogrifier));
	}
	
	@Test
	public void testTerminate() {
		assertFalse(laboratory_terminated.isTerminated());
		laboratory_terminated.terminate();
		assertTrue(laboratory_terminated.isTerminated());
	}
	
	@After
	public void tearDownFixture() {
		laboratory.terminate();
	}
}
