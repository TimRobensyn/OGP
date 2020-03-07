package file;
import file.File;
import static org.junit.Assert.*;
import java.util.Date;
import org.junit.*;

/**
 * A class made for testing the File class.
 * 
 * @version 1.0
 * @author Robbe Van Biervliet, Tim Lauwers, Tim Robensyn
 */
public class FileTest {
	
	/**
	 * Class instances that may change throughout tests:
	 */
	private static File emptyFile, fullFile;
	
	/**
	 * Immutable class instances for testing:
	 */
	private static File staticFile;
	
	@Before
	public void setUpMutableFixture() {
		emptyFile = new File("emptyFile");
		fullFile = new File("fullFile", Long.MAX_VALUE, true);
	}
	
	@BeforeClass
	public static void setUpImmutableFixture() {
		staticFile = new File("staticFile", 500, false);
	}
	
	@Test
	public void extendedConstructor_LegalCase() {
		File legalFile = new File("legalFile", 500, true);
		assertEquals("legalFile", legalFile.getName());
		assertEquals(500, legalFile.getSize());
		assertTrue(legalFile.isWritable());
	}
	
	
	@Test
	public void extendedConstructor_InvalidName() {
		File invalidName1 = new File("");
		File invalidName2 = new File("invalidText!@#$%^&*(){}[];:<>");
		assertEquals("New_file", invalidName1.getName());
		assertEquals("New_file", invalidName2.getName());
	}
	
	@Test
	public void extendedConstructor_InvalidSize() {
		File invalidSize = new File("invalidSize", Long.MAX_VALUE+1, true);
		assertEquals(0,invalidSize.getSize());
	}
	
	@Test
	public void extendedConstructor_Writability() {
		File writable = new File("writable");
		File unwritable = new File("unwritable", 0, false);
		assertTrue(writable.isWritable());
		assertFalse(unwritable.isWritable());
	}
	
	
	
	@Test(expected=FileNotWritableException.class)
	public void setName_UnwritableFile()
			throws FileNotWritableException {
		staticFile.setName("Bart");
	}
	
	
	@Test
	public void enlarge_LegalCase() {
		emptyFile.enlarge(1);
	}
	
	@Test
	public void enlarge_UnacceptableAmount() {
		fullFile.enlarge(1);
		fullFile.enlarge(-1);
	}
	
	@Test(expected=FileNotWritableException.class)
	public void enlarge_UnwritableFile()
			throws FileNotWritableException {
		staticFile.enlarge(50);
	}
	
	
	@Test
	public void shorten_LegalCase() {
		emptyFile.shorten(1);
	}
	
	@Test
	public void shorten_UnacceptableAmount() {
		emptyFile.shorten(1);
		emptyFile.shorten(-1);
	}
	
	@Test(expected=FileNotWritableException.class)
	public void shorten_UnwritableFile()
			throws FileNotWritableException {
		staticFile.shorten(1);
		
	}
	
	@Test(expected=FileNotWritableException.class)
	public void setSize_UnwritableFile()
			throws FileNotWritableException {
		staticFile.setSize(5);
	}
}
