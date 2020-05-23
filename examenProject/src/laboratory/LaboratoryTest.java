package laboratory;
import alchemy.*;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.ArrayList;
import java.util.List;

public class LaboratoryTest {

	public static Laboratory laboratory_empty_allDevices, laboratory_empty, laboratory_waterStorage, laboratory_storage, laboratory_big;
	public static Laboratory laboratory, laboratoryCapacityStorageDevices, laboratoryCapacityDevices, laboratoryCapacity;
	
<<<<<<< HEAD
	public static CoolingBox basicCoolingbox, coolingboxAll, storageCoolingbox;
	public static Oven basicOven, ovenAll, storageOven;
	public static Kettle basicKettle, kettleAll, storageKettle;
	public static Transmogrifier basicTransmogrifier, transmogrifierAll;
=======
	public static CoolingBox coolingBox;
	public static CoolingBox staticCoolingBox;
	public static Oven staticOven;
	public static Oven oven;
	public static Kettle emptyKettle;
	public static Transmogrifier emptyTransmogrifier;
>>>>>>> branch 'master' of https://github.com/TimRobensyn/OGP
	
	public static IngredientType ingredientTypeLiquid, ingredientTypePowder;
	public static AlchemicIngredient water, basicWater, ingredientLiquid, ingredientPowder, bigIngredient;
	public static List<AlchemicIngredient> waterStorage, emptyStorage, storage, basicStorage, bigStorage;
	public static IngredientContainer waterContainer, basicLiquidContainer;
	
	@BeforeClass
	public static void setUpImmutableFixture() {
<<<<<<< HEAD
		coolingboxAll = new CoolingBox(new Temperature(0,0));
		ovenAll = new Oven(new Temperature(0,0));
		kettleAll = new Kettle();
		transmogrifierAll = new Transmogrifier();
		basicCoolingbox = new CoolingBox(new Temperature(0,0));
		basicOven = new Oven(new Temperature(0,0));
		basicKettle = new Kettle();
		basicTransmogrifier = new Transmogrifier();
		
=======
		staticCoolingBox = new CoolingBox(new Temperature(0,0));
		staticOven = new Oven(new Temperature(0,0));
		emptyKettle = new Kettle();
		emptyTransmogrifier = new Transmogrifier();
>>>>>>> branch 'master' of https://github.com/TimRobensyn/OGP
		emptyStorage = new ArrayList<AlchemicIngredient>();
		basicStorage = new ArrayList<AlchemicIngredient>();
		bigStorage = new ArrayList<AlchemicIngredient>();
		
		water = new AlchemicIngredient(10);
		basicWater = new AlchemicIngredient(10);
		bigIngredient = new AlchemicIngredient(15000);
		
<<<<<<< HEAD
		laboratory_empty_allDevices = new Laboratory(100, coolingboxAll, ovenAll, kettleAll, transmogrifierAll);
=======
		laboratory_empty_allDevices = new Laboratory(100, staticCoolingBox, staticOven, emptyKettle, emptyTransmogrifier);
>>>>>>> branch 'master' of https://github.com/TimRobensyn/OGP
		laboratory_empty = new Laboratory(100);
		
		waterContainer = new IngredientContainer(water, Unit.VIAL_LIQUID);
		bigStorage.add(bigIngredient);
	}
	
	@Before
	public void setUpFixture() {
<<<<<<< HEAD
		basicLiquidContainer = new IngredientContainer(Unit.BARREL_LIQUID);
		
=======
		coolingBox = new CoolingBox(new Temperature(0,0));
		oven = new Oven(new Temperature(0,0));
>>>>>>> branch 'master' of https://github.com/TimRobensyn/OGP
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
<<<<<<< HEAD
		laboratory_storage = new Laboratory(1000, storage, storageCoolingbox, storageOven, storageKettle, null);
		laboratory_big = new Laboratory(5000, bigStorage, null, null, null, null);
=======
		laboratory_storage = new Laboratory(100, storage, null, null, emptyKettle, null);
		laboratory_big = new Laboratory(5000, bigStorage, coolingBox, oven, emptyKettle, emptyTransmogrifier);
>>>>>>> branch 'master' of https://github.com/TimRobensyn/OGP
	}
	
	@Test
	public void testConstructorFull_Legal() {
<<<<<<< HEAD
		laboratoryCapacityStorageDevices = new Laboratory(100, basicStorage, basicCoolingbox, basicOven, basicKettle, basicTransmogrifier);
=======
		laboratoryCapacityStorageDevices = new Laboratory(100, waterStorage, coolingBox, oven, emptyKettle, emptyTransmogrifier);
>>>>>>> branch 'master' of https://github.com/TimRobensyn/OGP
		
<<<<<<< HEAD
		assertEquals(100, laboratoryCapacityStorageDevices.getCapacity());
		assertEquals(basicStorage, laboratoryCapacityStorageDevices.getStorage());
		assertEquals(basicCoolingbox, laboratoryCapacityStorageDevices.getCoolingbox());
		assertEquals(basicOven, laboratoryCapacityStorageDevices.getOven());
		assertEquals(basicKettle, laboratoryCapacityStorageDevices.getKettle());
		assertEquals(basicTransmogrifier, laboratoryCapacityStorageDevices.getTransmogrifier());
=======
		assertEquals(100, laboratory.getCapacity());
		assertEquals(waterStorage, laboratory.getStorage());
		assertEquals(coolingBox, laboratory.getCoolingbox());
		assertEquals(oven, laboratory.getOven());
		assertEquals(emptyKettle, laboratory.getKettle());
		assertEquals(emptyTransmogrifier, laboratory.getTransmogrifier());
>>>>>>> branch 'master' of https://github.com/TimRobensyn/OGP
	}
	
	@Test
	public void testConstructorCapacityDevices_Legal() {
<<<<<<< HEAD
		laboratoryCapacityDevices = new Laboratory(100, basicCoolingbox, basicOven, basicKettle, basicTransmogrifier);
=======
		laboratoryCapacityDevices = new Laboratory(100, coolingBox, oven, emptyKettle, emptyTransmogrifier);
>>>>>>> branch 'master' of https://github.com/TimRobensyn/OGP
		
<<<<<<< HEAD
		assertEquals(100, laboratoryCapacityDevices.getCapacity());
		assertEquals(new ArrayList<AlchemicIngredient>(), laboratoryCapacityDevices.getStorage());
		assertEquals(basicCoolingbox, laboratoryCapacityDevices.getCoolingbox());
		assertEquals(basicOven, laboratoryCapacityDevices.getOven());
		assertEquals(basicKettle, laboratoryCapacityDevices.getKettle());
		assertEquals(basicTransmogrifier, laboratoryCapacityDevices.getTransmogrifier());
=======
		assertEquals(100, laboratory_empty_allDevices.getCapacity());
		assertEquals(new ArrayList<AlchemicIngredient>(), laboratory_empty_allDevices.getStorage());
		assertEquals(coolingBox, laboratory_empty_allDevices.getCoolingbox());
		assertEquals(oven, laboratory_empty_allDevices.getOven());
		assertEquals(emptyKettle, laboratory_empty_allDevices.getKettle());
		assertEquals(emptyTransmogrifier, laboratory_empty_allDevices.getTransmogrifier());
>>>>>>> branch 'master' of https://github.com/TimRobensyn/OGP
	}
	
	@Test
	public void testConstructorCapacity_Legal() {
		ArrayList<Device> devices = new ArrayList<Device> ();
		devices.add(0, null);
		devices.add(1, null);
		devices.add(2, null);
		devices.add(3, null);
		laboratoryCapacity = new Laboratory(100);
		
		assertEquals(100, laboratoryCapacity.getCapacity());
		assertEquals(new ArrayList<AlchemicIngredient>(), laboratoryCapacity.getStorage());
		assertEquals(devices, laboratoryCapacity.getDevices());
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
		assertEquals(ingredientLiquid, laboratory_storage.getIngredientAt(1));
		assertEquals(ingredientPowder, laboratory_storage.getIngredientAt(2));
	}
	
	@Test
	public void testAddAsIngredient() {
		laboratory_storage.addAsIngredient(basicWater);
		assertEquals(basicWater, laboratory_storage.getIngredientAt(3));
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
		basicLiquidContainer.setContents(basicWater);
		laboratory_storage.store(basicLiquidContainer);
		assertEquals(basicWater, laboratory_storage.getIngredientAt(3));
		assertEquals(basicLiquidContainer, null);
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
	
	@Test //TODO
	public void testStore_LegalCase_WarmIngredient() {
	}
	
	@Test //TODO
	public void testStore_LegalCase_ColdIngredient() {
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
		assertEquals(ingredientTypeLiquid, laboratory_storage.getIngredientAt(2).getType());
		assertEquals(5, laboratory_storage.getIngredientAt(1).getQuantity());
		assertEquals(ingredientPowder, laboratory_storage.getIngredientAt(1));
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
		assertEquals(container.getIngredient().getType(), bigIngredient.getType());
		assertEquals(container.getContentQuantity(), Unit.BARREL_LIQUID);
	}
	
	@Test (expected = CapacityException.class)
	public void testRequestName_IllegalCase_IngredientNotFound() {
		laboratory_storage.request("Water");
	}
	
	@Test //TODO
	public void testGetInventory() {
		
	}
	
	@Test
	public void testGetDevices() {
<<<<<<< HEAD
		ArrayList<Device> devices = new ArrayList<Device>();
		devices.add(coolingboxAll);
		devices.add(ovenAll);
		devices.add(kettleAll);
		devices.add(transmogrifierAll);
=======
		List<Device> devices = new ArrayList<Device>();
		devices.add(coolingBox);
		devices.add(oven);
		devices.add(emptyKettle);
		devices.add(emptyTransmogrifier);
>>>>>>> branch 'master' of https://github.com/TimRobensyn/OGP
		
<<<<<<< HEAD
		List<Device> requestedDevices = laboratory_empty_allDevices.getDevices();
=======
		List<Device> requestedDevices = laboratory_big.getDevices();
>>>>>>> branch 'master' of https://github.com/TimRobensyn/OGP
		assertEquals(requestedDevices, devices);
	}
	
	@Test
	public void testGetNbDevices() {
		assertEquals(4, laboratory_empty_allDevices.getNbDevices());
		assertEquals(0, laboratory_empty.getNbDevices());
	}
	
	@Test
	public void testGetDeviceAt() {
<<<<<<< HEAD
		assertEquals(coolingboxAll, laboratory_empty_allDevices.getDeviceAt(1));
		assertEquals(ovenAll, laboratory_empty_allDevices.getDeviceAt(2));
		assertEquals(kettleAll, laboratory_empty_allDevices.getDeviceAt(3));
		assertEquals(transmogrifierAll, laboratory_empty_allDevices.getDeviceAt(4));
=======
		assertEquals(coolingBox, laboratory_big.getDeviceAt(1));
		assertEquals(oven, laboratory_big.getDeviceAt(2));
		assertEquals(emptyKettle, laboratory_big.getDeviceAt(3));
		assertEquals(emptyTransmogrifier, laboratory_big.getDeviceAt(4));
>>>>>>> branch 'master' of https://github.com/TimRobensyn/OGP
	}
	
	@Test
	public void testIsValidDeviceAt_LegalCase() { //TODO NOT VALID IF ALREADY IN ANOTHER LAB
<<<<<<< HEAD
		assertTrue(laboratory_empty.isValidDeviceAt(basicCoolingbox, 1));
		assertTrue(laboratory_empty.isValidDeviceAt(basicOven, 2));
		assertTrue(laboratory_empty.isValidDeviceAt(basicKettle, 3));
		assertTrue(laboratory_empty.isValidDeviceAt(basicTransmogrifier, 4));
=======
		assertTrue(Laboratory.isValidDeviceAt(coolingBox, 1));
		assertTrue(Laboratory.isValidDeviceAt(oven, 2));
		assertTrue(Laboratory.isValidDeviceAt(emptyKettle, 3));
		assertTrue(Laboratory.isValidDeviceAt(emptyTransmogrifier, 4));
>>>>>>> branch 'master' of https://github.com/TimRobensyn/OGP
		
	}
	
	@Test
<<<<<<< HEAD
	public void testIsValidDeviceAt_IllegalCase_InvalidIndex() { //TODO TEST NOT VALID IF ALREADY IN ANOTHER LAB
		assertFalse(laboratory_empty.isValidDeviceAt(basicCoolingbox, 2));
		assertFalse(laboratory_empty.isValidDeviceAt(basicCoolingbox, 3));
		assertFalse(laboratory_empty.isValidDeviceAt(basicCoolingbox, 4));
=======
	public void testIsValidDeviceAt_IllegalCase() { //TODO TEST NOT VALID IF ALREADY IN ANOTHER LAB
		assertFalse(Laboratory.isValidDeviceAt(coolingBox, 2));
		assertFalse(Laboratory.isValidDeviceAt(coolingBox, 3));
		assertFalse(Laboratory.isValidDeviceAt(coolingBox, 4));
>>>>>>> branch 'master' of https://github.com/TimRobensyn/OGP
		
		assertFalse(laboratory_empty.isValidDeviceAt(basicOven, 1));
		assertFalse(laboratory_empty.isValidDeviceAt(basicOven, 3));
		assertFalse(laboratory_empty.isValidDeviceAt(basicOven, 4));
		
		assertFalse(laboratory_empty.isValidDeviceAt(basicKettle, 1));
		assertFalse(laboratory_empty.isValidDeviceAt(basicKettle, 2));
		assertFalse(laboratory_empty.isValidDeviceAt(basicKettle, 4));
		
		assertFalse(laboratory_empty.isValidDeviceAt(basicTransmogrifier, 1));
		assertFalse(laboratory_empty.isValidDeviceAt(basicTransmogrifier, 2));
		assertFalse(laboratory_empty.isValidDeviceAt(basicTransmogrifier, 3));
	}
	
	@Test (expected = CapacityException.class) //TODO
	public void testIsValidDeviceAt_IllegalCase_DeviceInOtherLab() {
		
	}
	
	@Test //TODO
	public void testHasProperDevices_LegalCase() {
		
	}
	
	@Test //TODO
	public void testHasProperDevices_IllegalCase() {
		
	}
	
	@Test
	public void testAddAsDevice_LegalCase() {
<<<<<<< HEAD
		laboratory_empty.addAsDevice(basicCoolingbox);
		assertEquals(laboratory_empty.getCoolingbox(), basicCoolingbox);
		assertEquals(laboratory_empty.getDeviceAt(1), basicCoolingbox);
=======
		laboratory_empty.addAsDevice(coolingBox);
		assertEquals(laboratory_empty.getCoolingbox(), coolingBox);
		assertEquals(laboratory_empty.getDeviceAt(1), coolingBox);
>>>>>>> branch 'master' of https://github.com/TimRobensyn/OGP
	}
	
	@Test (expected = CapacityException.class)
	public void testAddAsDevice_IllegalCase() {
<<<<<<< HEAD
		laboratory_big.addAsDevice(basicCoolingbox);
=======
		laboratory_big.addAsDevice(coolingBox);
>>>>>>> branch 'master' of https://github.com/TimRobensyn/OGP
	}
	
	@Test
	public void testRemoveAsDevice() {
<<<<<<< HEAD
		laboratory_empty_allDevices.removeAsDevice(coolingboxAll);
		assertEquals(null, laboratory_empty_allDevices.getDeviceAt(1));
=======
		laboratory_big.removeAsDevice(coolingBox);
		assertEquals(null, laboratory_big.getDeviceAt(1));
>>>>>>> branch 'master' of https://github.com/TimRobensyn/OGP
	}
	
	@Test
	public void testGetCoolingbox_LegalCase() {
		CoolingBox requestedCoolingbox = laboratory_empty_allDevices.getCoolingbox();
		assertEquals(requestedCoolingbox, coolingboxAll);
	}
	
	@Test (expected = CapacityException.class)
	public void testGetCoolingbox_IllegalCase() {
		laboratory_empty.getCoolingbox();
	}
	
	@Test
	public void testGetOven_LegalCase() {
		Oven requestedOven = laboratory_empty_allDevices.getOven();
		assertEquals(requestedOven, ovenAll);
	}
	
	@Test (expected = CapacityException.class)
	public void testGetOven_IllegalCase() {
		laboratory_empty.getOven();
	}
	
	@Test
	public void testGetKettle_LegalCase() {
		Kettle requestedKettle = laboratory_empty_allDevices.getKettle();
		assertEquals(requestedKettle, kettleAll);
	}
	
	@Test (expected = CapacityException.class)
	public void testGetKettle_IllegalCase() {
		laboratory_empty.getKettle();
	}
	
	@Test
	public void testGetTransmogrifier_LegalCase() {
		Transmogrifier requestedTransmogrifier = laboratory_empty_allDevices.getTransmogrifier();
		assertEquals(requestedTransmogrifier, transmogrifierAll);
	}
	
	@Test (expected = CapacityException.class)
	public void testGetTransmogrifier_IllegalCase() {
		laboratory_empty.getTransmogrifier();
	}
}
