package laboratory;
import alchemy.*;
import static org.junit.Assert.*;
import org.junit.*;
import java.util.ArrayList;

public class LaboratoryTest {

	public static Laboratory laboratory, laboratory_empty_allDevices, laboratory_empty;
	
	public static CoolingBox coolingbox;
	public static Oven oven;
	public static Kettle kettle_empty;
	public static Transmogrifier transmogrifier_empty;
	
	//public static IngredientType ingredientType;
	public static AlchemicIngredient water;
	public static ArrayList<AlchemicIngredient> waterStorage;
	
	@Before
	public void setUpFixture() {
		coolingbox = new CoolingBox(new Temperature(0,0));
		oven = new Oven(new Temperature(0,0));
		kettle_empty = new Kettle();
		transmogrifier_empty = new Transmogrifier();
		water = new AlchemicIngredient(10);
		waterStorage.add(water);
	}
	
	@Test
	public void testConstructorFull_Legal() {
		laboratory = new Laboratory(100, waterStorage, coolingbox, oven, kettle_empty, transmogrifier_empty);
		
		assertEquals(100, laboratory.getCapacity());
		assertEquals(waterStorage, laboratory.getStorage());
		assertEquals(coolingbox, laboratory.getCoolingbox());
		assertEquals(oven, laboratory.getOven());
		assertEquals(kettle_empty, laboratory.getKettle());
		assertEquals(transmogrifier_empty, laboratory.getTransmogrifier());
	}
	
	@Test
	public void testConstructorCapacityDevices_Legal() {
		laboratory_empty_allDevices = new Laboratory(100, coolingbox, oven, kettle_empty, transmogrifier_empty);
		
		assertEquals(100, laboratory_empty_allDevices.getCapacity());
		assertEquals(new ArrayList<AlchemicIngredient>(), laboratory_empty_allDevices.getStorage());
		assertEquals(coolingbox, laboratory_empty_allDevices.getCoolingbox());
		assertEquals(oven, laboratory_empty_allDevices.getOven());
		assertEquals(kettle_empty, laboratory_empty_allDevices.getKettle());
		assertEquals(transmogrifier_empty, laboratory_empty_allDevices.getTransmogrifier());
	}
	
	@Test
	public void testConstructorCapacity_Legal() {
		laboratory_empty = new Laboratory(100);
		
		assertEquals(100, laboratory_empty.getCapacity());
		assertEquals(new ArrayList<AlchemicIngredient>(), laboratory_empty.getStorage());
		assertEquals(laboratory_empty.getCoolingbox(), null);
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
	
	
	
}
