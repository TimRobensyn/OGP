package laboratory;
import alchemy.*;
import laboratory.device.*;
import temperature.Temperature;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LaboratoryTest {

	public static Laboratory laboratory_empty_allDevices, laboratory_empty, laboratory_waterStorage, laboratory_storage, laboratory_big;
	public static Laboratory laboratory, laboratoryCapacityStorageDevices, laboratoryCapacityDevices, laboratoryCapacity;
	

	public static CoolingBox basicCoolingBox, allCoolingBox, storageCoolingBox;
	public static Oven basicOven, allOven, storageOven;
	public static Kettle basicKettle, allKettle, storageKettle;
	public static Transmogrifier basicTransmogrifier, allTransmogrifier;
	public static Set<Device> basicDevices, allDevices, storageDevices;

	public static CoolingBox coolingBox;
	public static CoolingBox staticCoolingBox;
	public static Oven staticOven;
	public static Oven oven;
	public static Kettle emptyKettle;
	public static Transmogrifier emptyTransmogrifier;

	
	public static IngredientType ingredientTypeLiquid, ingredientTypePowder;
	public static AlchemicIngredient water;
	public static AlchemicIngredient basicWater, ingredientLiquid, ingredientPowder, bigIngredient;
	public static Map<IngredientType,Integer> waterStorage, emptyStorage, storage, basicStorage, bigStorage;
	public static IngredientContainer waterContainer, basicLiquidContainer;
	
	@BeforeClass
	public static void setUpImmutableFixture() {
		basicCoolingBox = new CoolingBox(new Temperature(0,0));
		basicOven = new Oven(new Temperature(0,0));
		basicKettle = new Kettle();
//		basicTransmogrifier = new Transmogrifier();
//		allCoolingBox = new CoolingBox(new Temperature(0,0));
//		allOven = new Oven(new Temperature(0,0));
//		allKettle = new Kettle();
//		allTransmogrifier = new Transmogrifier();
		
		basicWater = new AlchemicIngredient(10);
//		water = new AlchemicIngredient(0);
//		bigIngredient = new AlchemicIngredient(15000);
		
		basicStorage	= new HashMap<IngredientType,Integer>();
		basicStorage.put(basicWater.getType(),basicWater.getQuantity());
//		emptyStorage	= new HashMap<IngredientType,Integer>();
//		bigStorage		= new HashMap<IngredientType,Integer>();
//		bigStorage.put(bigIngredient.getType(), bigIngredient.getQuantity());
//		waterStorage	= new HashMap<IngredientType,Integer>();
//		waterStorage.put(water.getType(),water.getQuantity());
//		storage			= new HashMap<IngredientType,Integer>();
//		storage.put(ingredientLiquid.getType(),ingredientLiquid.getQuantity());
//		storage.put(ingredientPowder.getType(),ingredientPowder.getQuantity());

//		waterContainer = new IngredientContainer(water, Unit.VIAL_LIQUID);
		
		basicDevices = new HashSet<Device>();
		basicDevices.add(basicCoolingBox);
		basicDevices.add(basicOven);
		basicDevices.add(basicKettle);
		basicDevices.add(basicTransmogrifier);
//		storageDevices = new HashSet<Device>();
//		storageDevices.add(storageCoolingBox);
//		storageDevices.add(storageOven);
//		storageDevices.add(storageKettle);
//		allDevices = new HashSet<Device>();
//		allDevices.add(allCoolingBox);
//		allDevices.add(allOven);
//		allDevices.add(allKettle);
//		allDevices.add(allKettle);
		
	}
	
	@Before
	public void setUpFixture() {
//		basicLiquidContainer = new IngredientContainer(Unit.BARREL_LIQUID);
//		
//		ingredientTypeLiquid = new IngredientType("ingredientTypeLiquid", State.LIQUID, new Temperature(0,15));
//		ingredientLiquid = new AlchemicIngredient(ingredientTypeLiquid,15);
//		ingredientTypePowder = new IngredientType("ingredientTypePowder", State.POWDER, new Temperature(0,15));
//		ingredientPowder = new AlchemicIngredient(ingredientTypePowder,15);
//		
//		laboratory_waterStorage = new Laboratory(100, waterStorage);
//		laboratory_empty = new Laboratory(100);
//		laboratory_storage = new Laboratory(1000, storage, storageDevices);
//		laboratory_big = new Laboratory(5000, bigStorage);
//		
//		laboratory_empty_allDevices = new Laboratory(100, allDevices);
	}
	
	@Test
	public void testConstructorFull_Legal() {
		laboratoryCapacityStorageDevices = new Laboratory(100,basicStorage,basicDevices);

		assertEquals(100, laboratoryCapacityStorageDevices.getCapacity());
		for (IngredientType type:basicStorage.keySet()) {
			assertTrue(laboratoryCapacityStorageDevices.hasAsIngredientType(type));
			assertEquals(laboratoryCapacityStorageDevices.getQuantityOf(type),
					basicStorage.get(type).intValue());
		}
		for (Device device:basicDevices) {
			assertTrue(laboratoryCapacityStorageDevices.hasAsDevice(device));
		}

	}
	
	@Test
	public void testConstructorCapacityDevices_Legal() {
		laboratoryCapacityDevices = new Laboratory(100, basicDevices);

		assertEquals(100, laboratoryCapacityDevices.getCapacity());
		assertEquals(0, laboratoryCapacityDevices.getInventory()[0].length);
		for (Device device:basicDevices) {
			assertTrue(laboratoryCapacityDevices.hasAsDevice(device));
		}
	}
	
	@Test
	public void testConstructorCapacity_Legal() {
		laboratoryCapacity = new Laboratory(100);
		
		assertEquals(100, laboratoryCapacity.getCapacity());
		assertEquals(0, laboratoryCapacity.getInventory()[0].length);
		assertEquals(0, laboratoryCapacity.)
	}
//	
//	@Test (expected = CapacityException.class)
//	public void testConstructor_Illegal_invalidCapacity() {
//		laboratory = new Laboratory(-1);
//	}
//	
//	@Test
//	public void testIsValidCapacity_LegalCase() {
//		assertTrue(Laboratory.isValidCapacity(10));
//	}
//	
//	@Test
//	public void testIsValidCapacity_IllegalCase() {
//		assertFalse(Laboratory.isValidCapacity(-1));
//		assertFalse(Laboratory.isValidCapacity(Integer.MAX_VALUE + 1));
//	}
//	
//	@Test
//	public void testGetStorageQuantity() {
//		assertEquals(laboratory_empty.getStorageQuantity(), 0);
//		assertEquals(laboratory_storage.getStorageQuantity(), 30);
//	}
//	
//	@Test
//	public void testGetNbOfIngredients() {
//		assertEquals(laboratory_storage.getNbIngredients(), 2);
//	}
//	
//	@Test
//	public void testGetIngredientAt() {
//		assertEquals(ingredientLiquid, laboratory_storage.getIngredientAt(1));
//		assertEquals(ingredientPowder, laboratory_storage.getIngredientAt(2));
//	}
//	
//	@Test
//	public void testAddAsIngredient() {
//		laboratory_storage.addAsIngredient(basicWater);
//		assertEquals(basicWater, laboratory_storage.getIngredientAt(3));
//	}
//	
//	@Test
//	public void testRemoveAsIngredient() {
//		laboratory_storage.removeAsIngredient(ingredientLiquid);
//		assertEquals(laboratory_storage.getIngredientAt(1), ingredientPowder);
//	}
//	
//	@Test
//	public void testHasProperIngredients_LegalCase() {
//		assertTrue(laboratory_waterStorage.hasProperIngredients());
//	}
//	
//	@Test
//	public void testHasProperIngredients_IllegalCase() {
//		AlchemicIngredient ingredientLiquid2 = new AlchemicIngredient(ingredientTypeLiquid, 5);
//		laboratory_storage.addAsIngredient(ingredientLiquid2);
//		assertFalse(laboratory_storage.hasProperIngredients());
//	}
//	
//	@Test
//	public void testStore_legalCase_NewIngredientType() {
//		basicLiquidContainer.setContents(basicWater);
//		laboratory_storage.store(basicLiquidContainer);
//		assertEquals(basicWater, laboratory_storage.getIngredientAt(3));
//		assertEquals(basicLiquidContainer, null);
//	}
//	
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
