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

	public static AlchemicIngredient water, water_full, crumbs;
	
	public static Map<IngredientType,Integer> storage,storage_full;
	
	public static CoolingBox coolingBox;
	public static Oven oven;
	public static Kettle kettle;
	public static Transmogrifier transmogrifier;
	
	public static Set<Device> devices;
	
	public static Laboratory laboratory_empty,laboratory,laboratory_full;
	
	public static Laboratory laboratoryCapacityStorageDevices, laboratoryCapacityStorage,
							 laboratoryCapacityDevices, laboratoryCapacity;

	@BeforeClass
	public static void setUpImmutableFixture() {
		water = new AlchemicIngredient(24);
		water_full = new AlchemicIngredient(50400);
		crumbs = new AlchemicIngredient(new IngredientType("Crumbs",State.POWDER,
				new Temperature(0,40)),30);
		storage = new HashMap<IngredientType,Integer>();
		storage.put(water.getType(),water.getQuantity());
		storage.put(crumbs.getType(), crumbs.getQuantity());
		storage_full = new HashMap<IngredientType,Integer>();
		storage_full.put(water_full.getType(),water_full.getQuantity());
		
		coolingBox = new CoolingBox(new Temperature(0,0));
		oven = new Oven(new Temperature(0,0));
		kettle = new Kettle();
		transmogrifier = new Transmogrifier();
		devices = new HashSet<Device>();
		devices.add(coolingBox);
		devices.add(oven);
		devices.add(kettle);
		devices.add(transmogrifier);
		
		laboratory_empty = new Laboratory(5);
		laboratory = new Laboratory(5,storage,devices);
		laboratory_full = new Laboratory(1,storage_full,devices);
		
	}
	
	@Before
	public void setUpFixture() {
//		laboratory_empty = new Laboratory(100);
//		laboratory = new Laboratory(1000, storage, devices);
//		basicLiquidContainer = new IngredientContainer(Unit.BARREL_LIQUID);
//		


//		
//		laboratory_waterStorage = new Laboratory(100, waterStorage);

		
//		laboratory_big = new Laboratory(5000, bigStorage);
//		
//		laboratory_empty_allDevices = new Laboratory(100, allDevices);
	}
	
	@Test
	public void testConstructorFull_Legal() {
		laboratoryCapacityStorageDevices = new Laboratory(100,storage,devices);

		assertEquals(100, laboratoryCapacityStorageDevices.getCapacity());
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
		laboratoryCapacityDevices = new Laboratory(100, devices);

		assertEquals(100, laboratoryCapacityDevices.getCapacity());
		assertEquals(0, laboratoryCapacityDevices.getInventory()[0].length);
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
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetQuantityOf_IllegalCase() {
		laboratory_empty.getQuantityOf(water.getType());
	}
	
	@Test
	public void testAddIngredientType() {
		assertFalse(laboratory_empty.hasAsIngredientType(water.getType()));
		laboratory.addIngredientType(water.getType(),water.getQuantity());
		assertTrue(laboratory_empty.hasAsIngredientType(water.getType()));
	}
	
	@Test
	public void testRemoveIngredientType() {
		assertTrue(laboratory.hasAsIngredientType(water.getType()));
		laboratory.removeIngredientType(water.getType());
		assertFalse(laboratory.hasAsIngredientType(water.getType()));
	}
	
	@Test
	public void testStore_legalCase_NewIngredientType() {
		
		basicLiquidContainer.setContents(basicWater);
		laboratory_storage.store(basicLiquidContainer);
		assertEquals(basicWater, laboratory_storage.getIngredientAt(3));
		assertEquals(basicLiquidContainer, null);
	}
	
//	@Test
//	public void testStore_LegalCase_OldIngredientType() {
//		AlchemicIngredient ingredientLiquid2 = new AlchemicIngredient(ingredientTypeLiquid, 5);
//		IngredientContainer container2 = new IngredientContainer(ingredientLiquid2, Unit.VIAL_LIQUID);
//
//		laboratory_storage.store(container2);
//		assertEquals(ingredientPowder, laboratory_storage.getIngredientAt(1));
//		assertEquals(ingredientTypeLiquid, laboratory_storage.getIngredientAt(2).getType());
//		assertEquals(laboratory_storage.getIngredientAt(2).getQuantity(), 20);
//		assertEquals(container2, null);
//	}
//	
//	@Test //TODO
//	public void testStore_LegalCase_WarmIngredient() {
//	}
//	
//	@Test //TODO
//	public void testStore_LegalCase_ColdIngredient() {
//	}
//	
//	@Test (expected = CapacityException.class)
//	public void testStore_IllegalCase_InvalidCapacity() {
//		AlchemicIngredient bigIngredient = new AlchemicIngredient(ingredientTypeLiquid, 200);
//		IngredientContainer bigContainer = new IngredientContainer(bigIngredient, Unit.getContainer(State.LIQUID, 200));
//		
//		laboratory_storage.store(bigContainer);
//	}
//	
//	@Test
//	public void testRequestNameAmount_LegalCase_NoLeftovers() {
//		IngredientContainer ingredientContainer = laboratory_storage.request("ingredientTypeLiquid", 15);
//		
//		assertEquals(ingredientLiquid, ingredientContainer.getContents());
//		assertEquals(ingredientPowder, laboratory_storage.getIngredientAt(1));
//	}
//	
//	@Test
//	public void testRequestNameAmount_LegalCase_Leftovers() {
//		IngredientContainer ingredientContainer = laboratory_storage.request("ingredientTypeLiquid", 10);
//		
//		assertEquals(ingredientTypeLiquid, ingredientContainer.getContents().getType());
//		assertEquals(10, ingredientContainer.getContents().getQuantity());
//		assertEquals(ingredientTypeLiquid, laboratory_storage.getIngredientAt(2).getType());
//		assertEquals(5, laboratory_storage.getIngredientAt(1).getQuantity());
//		assertEquals(ingredientPowder, laboratory_storage.getIngredientAt(1));
//	}
//	
//	@Test (expected = CapacityException.class)
//	public void testRequestNameAmount_IllegalCase_IngredientNotFound() {
//		laboratory_storage.request("water",10);
//	}
//	
//	@Test (expected = CapacityException.class)
//	public void testRequestNameAmount_IllegalCase_NotEnoughIngredient() {
//		laboratory_storage.request("ingredientTypeLiquid", 20);
//	}
//	
//	@Test (expected = CapacityException.class)
//	public void testRequestNameAmount_IllegalCase_AmountTooBig() {
//		laboratory_big.request("Water", 15000);
//	}
//	
//	@Test
//	public void testRequestName_LegalCase() {
//		IngredientContainer container = laboratory_big.request("Water");
//		
//		assertEquals(container.getCapacity(), Unit.BARREL_LIQUID);
//		assertEquals(container.getContents().getType(), bigIngredient.getType());
//		assertEquals(container.getContentQuantity(), Unit.BARREL_LIQUID);
//	}
//	
//	@Test (expected = CapacityException.class)
//	public void testRequestName_IllegalCase_IngredientNotFound() {
//		laboratory_storage.request("Water");
//	}
//	
//	@Test //TODO
//	public void testGetInventory() {
//		
//	}
//	
//	@Test
//	public void testGetDevices() {
//
//		List<Device> devices = new ArrayList<Device>();
//		devices.add(coolingboxAll);
//		devices.add(ovenAll);
//		devices.add(kettleAll);
//		devices.add(transmogrifierAll);
//
//		List<Device> requestedDevices = laboratory_empty_allDevices.getDevices();
//
//		assertEquals(requestedDevices, devices);
//	}
//	
//	@Test
//	public void testGetNbDevices() {
//		assertEquals(4, laboratory_empty_allDevices.getNbDevices());
//		assertEquals(0, laboratory_empty.getNbDevices());
//	}
//	
//	@Test
//	public void testGetDeviceAt() {
//		assertEquals(coolingboxAll, laboratory_empty_allDevices.getDeviceAt(1));
//		assertEquals(ovenAll, laboratory_empty_allDevices.getDeviceAt(2));
//		assertEquals(kettleAll, laboratory_empty_allDevices.getDeviceAt(3));
//		assertEquals(transmogrifierAll, laboratory_empty_allDevices.getDeviceAt(4));
//	}
//	
//	@Test
//	public void testIsValidDeviceAt_LegalCase() {
//		assertTrue(laboratory_empty.isValidDeviceAt(basicCoolingbox, 1));
//		assertTrue(laboratory_empty.isValidDeviceAt(basicOven, 2));
//		assertTrue(laboratory_empty.isValidDeviceAt(basicKettle, 3));
//		assertTrue(laboratory_empty.isValidDeviceAt(basicTransmogrifier, 4));
//	}
//	
//	@Test
//
//	public void testIsValidDeviceAt_IllegalCase_InvalidIndex() { 
//		assertFalse(laboratory_empty.isValidDeviceAt(basicCoolingbox, 2));
//		assertFalse(laboratory_empty.isValidDeviceAt(basicCoolingbox, 3));
//		assertFalse(laboratory_empty.isValidDeviceAt(basicCoolingbox, 4));
//		
//		assertFalse(laboratory_empty.isValidDeviceAt(basicOven, 1));
//		assertFalse(laboratory_empty.isValidDeviceAt(basicOven, 3));
//		assertFalse(laboratory_empty.isValidDeviceAt(basicOven, 4));
//		
//		assertFalse(laboratory_empty.isValidDeviceAt(basicKettle, 1));
//		assertFalse(laboratory_empty.isValidDeviceAt(basicKettle, 2));
//		assertFalse(laboratory_empty.isValidDeviceAt(basicKettle, 4));
//		
//		assertFalse(laboratory_empty.isValidDeviceAt(basicTransmogrifier, 1));
//		assertFalse(laboratory_empty.isValidDeviceAt(basicTransmogrifier, 2));
//		assertFalse(laboratory_empty.isValidDeviceAt(basicTransmogrifier, 3));
//	}
//	
//	@Test (expected = CapacityException.class) //TODO
//	public void testIsValidDeviceAt_IllegalCase_DeviceInOtherLab() {
//		
//	}
//	
//	@Test //TODO
//	public void testHasProperDevices_LegalCase() {
//		
//	}
//	
//	@Test //TODO
//	public void testHasProperDevices_IllegalCase() {
//		
//	}
//	
//	@Test
//	public void testAddAsDevice_LegalCase() {
//		laboratory_empty.addAsDevice(basicCoolingbox);
//		assertEquals(laboratory_empty.getCoolingbox(), basicCoolingbox);
//		assertEquals(laboratory_empty.getDeviceAt(1), basicCoolingbox);
//	}
//	
//	@Test (expected = CapacityException.class)
//	public void testAddAsDevice_IllegalCase() {
//
//		laboratory_big.addAsDevice(basicCoolingbox);
//	}
//	
//	@Test
//	public void testRemoveAsDevice() {
//		laboratory_empty_allDevices.removeAsDevice(coolingboxAll);
//		assertEquals(null, laboratory_empty_allDevices.getDeviceAt(1));
//	}
//	
//	@Test
//	public void testGetCoolingbox_LegalCase() {
//		CoolingBox requestedCoolingbox = laboratory_empty_allDevices.getCoolingbox();
//		assertEquals(requestedCoolingbox, coolingboxAll);
//	}
//	
//	@Test (expected = CapacityException.class)
//	public void testGetCoolingbox_IllegalCase() {
//		laboratory_empty.getCoolingbox();
//	}
//	
//	@Test
//	public void testGetOven_LegalCase() {
//		Oven requestedOven = laboratory_empty_allDevices.getOven();
//		assertEquals(requestedOven, ovenAll);
//	}
//	
//	@Test (expected = CapacityException.class)
//	public void testGetOven_IllegalCase() {
//		laboratory_empty.getOven();
//	}
//	
//	@Test
//	public void testGetKettle_LegalCase() {
//		Kettle requestedKettle = laboratory_empty_allDevices.getKettle();
//		assertEquals(requestedKettle, kettleAll);
//	}
//	
//	@Test (expected = CapacityException.class)
//	public void testGetKettle_IllegalCase() {
//		laboratory_empty.getKettle();
//	}
//	
//	@Test
//	public void testGetTransmogrifier_LegalCase() {
//		Transmogrifier requestedTransmogrifier = laboratory_empty_allDevices.getTransmogrifier();
//		assertEquals(requestedTransmogrifier, transmogrifierAll);
//	}
//	
//	@Test (expected = CapacityException.class)
//	public void testGetTransmogrifier_IllegalCase() {
//		laboratory_empty.getTransmogrifier();
//	}
}
