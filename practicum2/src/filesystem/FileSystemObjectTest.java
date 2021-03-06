package filesystem;

import static org.junit.Assert.*;
import java.util.Date;

import org.junit.*;

/**
 * A JUnit test class for testing the public methods of the File Class.
 *  
 * @author  Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 * @version 1.0
 *
 */
public class FileSystemObjectTest {
	
	Directory directoryDirStringBoolean;
	Directory directoryDirString;
	Directory directoryStringBoolean;
	Directory directoryString;
	Directory dir;
	Directory dirdir;
	Directory dirdirdir;
	
	File fileDirStringIntBooleanFileType;
	File fileDirStringFileType;
	File fileStringIntBooleanFileType;
	File fileStringFileType;

	Date timeBeforeConstruction, timeAfterConstruction;
	
	Directory directoryNotWritable;
	Directory directoryTerminated;
	File fileNotWritable;
	File fileTerminated;
	Date timeBeforeConstructionNotWritable, timeAfterConstructionNotWritable;
	
	@Before
	public void setUpMutableFixture(){
		timeBeforeConstruction = new Date();
		directoryStringBoolean = new Directory("directoryStringBoolean", true);
		directoryString = new Directory("directoryString");
		directoryDirStringBoolean = new Directory(directoryStringBoolean, "directoryDirStringBoolean", true);
		directoryDirString = new Directory(directoryString, "directoryDirString");
		dir = new Directory("dir");
		dirdir = new Directory(dir, "dirdir");
		dirdirdir = new Directory(dirdir, "dirdirdir");
		
		fileDirStringIntBooleanFileType = new File(directoryStringBoolean, "fileDirStringIntBooleanFileType", 100, true, FileType.Text);
		fileDirStringFileType = new File(directoryString, "fileDirStringFileType", FileType.Pdf);
		fileStringIntBooleanFileType = new File("fileStringIntBooleanFileType", 100, true, FileType.Java);
		fileStringFileType = new File("fileStringFileType", FileType.Java);
		timeAfterConstruction = new Date();

		timeBeforeConstructionNotWritable = new Date();
		directoryNotWritable = new Directory("directoryNotWritable",false);
		fileNotWritable = new File("fileNotWritable",100,false,FileType.Pdf);
		fileTerminated = new File("fileTerminated",100,true,FileType.Java);
		directoryTerminated = new Directory("directoryTerminated");
		timeAfterConstructionNotWritable = new Date();
	}

	@Test
	public void testDirectoryDirStringBoolean_LegalCase() {
		assertEquals(directoryStringBoolean, directoryDirStringBoolean.getParentDirectory());
		assertEquals("directoryDirStringBoolean",directoryDirStringBoolean.getName());
		assertTrue(directoryDirStringBoolean.isWritable());
		
		assertNull(directoryDirStringBoolean.getModificationTime());
		assertFalse(timeBeforeConstruction.after(directoryDirStringBoolean.getCreationTime()));
		assertFalse(directoryDirStringBoolean.getCreationTime().after(timeAfterConstruction));
	}
	
	@Test
	public void testDirectoryDirStringBoolean_IllegalCase() {
		timeBeforeConstruction = new Date();
		directoryDirStringBoolean = new Directory(directoryStringBoolean, "$IllegalName$", false);
		timeAfterConstruction = new Date();
		assertEquals(directoryStringBoolean, directoryDirStringBoolean.getParentDirectory());
		assertTrue(Directory.isValidName(directoryDirStringBoolean.getName()));
		assertFalse(directoryDirStringBoolean.isWritable());
		assertNull(directoryDirStringBoolean.getModificationTime());
		assertFalse(timeBeforeConstruction.after(directoryDirStringBoolean.getCreationTime()));
		assertFalse(directoryDirStringBoolean.getCreationTime().after(timeAfterConstruction));
	}
	
	@Test
	public void testDirectoryDirString_LegalCase() {
		assertEquals(directoryString, directoryDirString.getParentDirectory());
		assertEquals("directoryDirString",directoryDirString.getName());
		assertTrue(directoryDirString.isWritable());
		
		assertNull(directoryDirString.getModificationTime());
		assertFalse(timeBeforeConstruction.after(directoryDirString.getCreationTime()));
		assertFalse(directoryDirString.getCreationTime().after(timeAfterConstruction));
	}
	
	@Test
	public void testDirectoryDirString_IllegalCase() {
		timeBeforeConstruction = new Date();
		directoryDirString = new Directory(directoryString, "$IllegalName$");
		timeAfterConstruction = new Date();
		assertEquals(directoryString, directoryDirString.getParentDirectory());
		assertTrue(Directory.isValidName(directoryDirString.getName()));
		assertTrue(directoryDirString.isWritable());
		assertNull(directoryDirString.getModificationTime());
		assertFalse(timeBeforeConstruction.after(directoryDirString.getCreationTime()));
		assertFalse(directoryDirString.getCreationTime().after(timeAfterConstruction));
	}
	
	@Test
	public void testDirectoryStringBoolean_LegalCase() {
		assertTrue(directoryStringBoolean.isRoot());
		assertEquals("directoryStringBoolean",directoryStringBoolean.getName());
		assertTrue(directoryStringBoolean.isWritable());
		
		assertNotNull(directoryStringBoolean.getModificationTime()); 
		// Er is een map toegevoegd aan directoryStringBoolean in de setup dus de modification time
		// is ge�pdatet.
		assertFalse(timeBeforeConstruction.after(directoryStringBoolean.getCreationTime()));
		assertFalse(directoryStringBoolean.getCreationTime().after(timeAfterConstruction));
	}
	
	@Test
	public void testDirectoryStringBoolean_IllegalCase() {
		timeBeforeConstruction = new Date();
		directoryStringBoolean = new Directory("$IllegalName$", false);
		timeAfterConstruction = new Date();
		assertTrue(directoryStringBoolean.isRoot());
		assertTrue(Directory.isValidName(directoryStringBoolean.getName()));
		assertFalse(directoryStringBoolean.isWritable());
		assertNull(directoryStringBoolean.getModificationTime());
		assertFalse(timeBeforeConstruction.after(directoryStringBoolean.getCreationTime()));
		assertFalse(directoryStringBoolean.getCreationTime().after(timeAfterConstruction));
	}
	
	@Test
	public void testDirectoryString_LegalCase() {
		assertTrue(directoryString.isRoot());
		assertEquals("directoryString",directoryString.getName());
		assertTrue(directoryString.isWritable());
		
		assertNotNull(directoryString.getModificationTime());
		// Er is een map toegevoegd aan directoryString in de setup dus de modification time
		// is ge�pdatet.
		assertFalse(timeBeforeConstruction.after(directoryString.getCreationTime()));
		assertFalse(directoryString.getCreationTime().after(timeAfterConstruction));
	}
	
	@Test
	public void testDirectoryString_IllegalCase() {
		timeBeforeConstruction = new Date();
		Directory directoryStringIC = new Directory("$IllegalName$");
		timeAfterConstruction = new Date();
		
		assertTrue(directoryStringIC.isRoot());
		assertNotEquals(directoryStringIC.getName(), "$IllegalName$");
		assertTrue(directoryStringIC.isWritable());
		assertNull(directoryStringIC.getModificationTime());
		
	
		
		assertFalse(timeBeforeConstruction.after(directoryStringIC.getCreationTime()));
		assertFalse(directoryStringIC.getCreationTime().after(timeAfterConstruction));
	}
	
	@Test
	public void testFileDirStringIntBooleanFileType_LegalCase() {
		assertEquals(directoryStringBoolean, fileDirStringIntBooleanFileType.getParentDirectory());
		assertEquals("fileDirStringIntBooleanFileType",fileDirStringIntBooleanFileType.getName());
		assertEquals(fileDirStringIntBooleanFileType.getSize(),100);
		assertTrue(fileDirStringIntBooleanFileType.isWritable());
		assertEquals(FileType.Text, fileDirStringIntBooleanFileType.getType());
		
		assertNull(fileDirStringIntBooleanFileType.getModificationTime());
		assertFalse(timeBeforeConstruction.after(fileDirStringIntBooleanFileType.getCreationTime()));
		assertFalse(fileDirStringIntBooleanFileType.getCreationTime().after(timeAfterConstruction));
	}
	
	@Test
	public void testFileDirStringIntBooleanFileType_IllegalCase() {
		timeBeforeConstruction = new Date();
		fileDirStringIntBooleanFileType = new File(directoryStringBoolean,"$IllegalName$",File.getMaximumSize(),false, FileType.Text);
		timeAfterConstruction = new Date();
		assertEquals(directoryStringBoolean, fileDirStringIntBooleanFileType.getParentDirectory());
		assertTrue(File.isValidName(fileDirStringIntBooleanFileType.getName()));
		assertEquals(File.getMaximumSize(),fileDirStringIntBooleanFileType.getSize());
		assertFalse(fileDirStringIntBooleanFileType.isWritable());
		assertEquals(FileType.Text, fileDirStringIntBooleanFileType.getType());
		assertNull(fileDirStringIntBooleanFileType.getModificationTime());
		assertFalse(timeBeforeConstruction.after(fileDirStringIntBooleanFileType.getCreationTime()));
		assertFalse(fileDirStringIntBooleanFileType.getCreationTime().after(timeAfterConstruction));
	}
	
	@Test
	public void testFileDirStringFileType_LegalCase() {
		assertEquals(directoryString, fileDirStringFileType.getParentDirectory());
		assertEquals("fileDirStringFileType", fileDirStringFileType.getName());
		assertEquals(0, fileDirStringFileType.getSize());
		assertTrue(fileDirStringFileType.isWritable());
		assertEquals(FileType.Pdf, fileDirStringFileType.getType());
		
		assertNull(fileDirStringFileType.getModificationTime());
		assertFalse(timeBeforeConstruction.after(fileDirStringFileType.getCreationTime()));
		assertFalse(fileDirStringFileType.getCreationTime().after(timeAfterConstruction));
	}
	
	@Test
	public void testFileDirStringFileType_IllegalCase() {
		timeBeforeConstruction = new Date();
		fileDirStringFileType = new File(directoryString,"$IllegalName$", FileType.Pdf);
		timeAfterConstruction = new Date();
		assertEquals(directoryString, fileDirStringFileType.getParentDirectory());
		assertTrue(File.isValidName(fileDirStringFileType.getName()));
		assertEquals(0,fileDirStringFileType.getSize());
		assertTrue(fileDirStringFileType.isWritable());
		assertEquals(FileType.Pdf, fileDirStringFileType.getType());
		assertNull(fileDirStringFileType.getModificationTime());
		assertFalse(timeBeforeConstruction.after(fileDirStringFileType.getCreationTime()));
		assertFalse(fileDirStringFileType.getCreationTime().after(timeAfterConstruction));
	}
	
	@Test
	public void testFileStringIntBooleanFileType_LegalCase() {
		assertTrue(fileStringIntBooleanFileType.isRoot());
		assertEquals("fileStringIntBooleanFileType", fileStringIntBooleanFileType.getName());
		assertEquals(100, fileStringIntBooleanFileType.getSize());
		assertTrue(fileStringIntBooleanFileType.isWritable());
		assertEquals(FileType.Java, fileStringIntBooleanFileType.getType());
		
		assertNull(fileStringIntBooleanFileType.getModificationTime());
		assertFalse(timeBeforeConstruction.after(fileStringIntBooleanFileType.getCreationTime()));
		assertFalse(fileStringIntBooleanFileType.getCreationTime().after(timeAfterConstruction));
	}
	
	@Test
	public void testFileStringIntBooleanFileType_IllegalCase() {
		timeBeforeConstruction = new Date();
		fileStringIntBooleanFileType = new File("$IllegalName$",File.getMaximumSize(),false, FileType.Java);
		timeAfterConstruction = new Date();
		assertTrue(fileStringIntBooleanFileType.isRoot());
		assertTrue(File.isValidName(fileStringIntBooleanFileType.getName()));
		assertEquals(File.getMaximumSize(),fileStringIntBooleanFileType.getSize());
		assertFalse(fileStringIntBooleanFileType.isWritable());
		assertEquals(FileType.Java, fileStringIntBooleanFileType.getType());
		assertNull(fileStringIntBooleanFileType.getModificationTime());
		assertFalse(timeBeforeConstruction.after(fileStringIntBooleanFileType.getCreationTime()));
		assertFalse(fileStringIntBooleanFileType.getCreationTime().after(timeAfterConstruction));
	}
	
	@Test
	public void testFileStringFileType_LegalCase() {
		assertTrue(fileStringFileType.isRoot());
		assertEquals("fileStringFileType", fileStringFileType.getName());
		assertEquals(0, fileStringFileType.getSize());
		assertTrue(fileStringFileType.isWritable());
		assertEquals(FileType.Java, fileStringFileType.getType());
		
		assertNull(fileStringFileType.getModificationTime());
		assertFalse(timeBeforeConstruction.after(fileStringFileType.getCreationTime()));
		assertFalse(fileStringFileType.getCreationTime().after(timeAfterConstruction));
	}
	
	@Test
	public void testFileStringFileType_IllegalCase() {
		timeBeforeConstruction = new Date();
		fileStringFileType = new File("$IllegalName$", FileType.Java);
		timeAfterConstruction = new Date();
		assertTrue(fileStringFileType.isRoot());
		assertTrue(File.isValidName(fileStringFileType.getName()));
		assertEquals(0 ,fileStringFileType.getSize());
		assertTrue(fileStringFileType.isWritable());
		assertEquals(FileType.Java, fileStringFileType.getType());
		assertNull(fileStringFileType.getModificationTime());
		assertFalse(timeBeforeConstruction.after(fileStringFileType.getCreationTime()));
		assertFalse(fileStringFileType.getCreationTime().after(timeAfterConstruction));
	}
	
	@Test
	public void testIsValidName_LegalCase() {
		assertTrue(File.isValidName("abcDEF123-_."));
	}

	@Test
	public void testIsValidName_IllegalCase() {
		assertFalse(File.isValidName(null));
		assertFalse(File.isValidName(""));
		assertFalse(File.isValidName("%illegalSymbol"));
	}

	@Test
	public void testChangeName_LegalCase() {
		Date timeBeforeSetName = new Date();
		fileStringFileType.changeName("NewLegalName");
		Date timeAfterSetName = new Date();
		
		assertEquals("NewLegalName",fileStringFileType.getName());
		assertNotNull(fileStringFileType.getModificationTime());
		assertFalse(fileStringFileType.getModificationTime().before(timeBeforeSetName));
		assertFalse(timeAfterSetName.before(fileStringFileType.getModificationTime()));
	}
	
	@Test (expected = ObjectNotWritableException.class)
	public void testChangeName_FileNotWritable() {
		fileNotWritable.changeName("NewLegalName");
	}
	
	@Test (expected = IllegalStateException.class)
	public void testChangeName_FileTerminated() {
		fileTerminated.terminate();
		fileTerminated.changeName("NewLegalName");
		
	} 
	
	@Test
	public void testChangeName_IllegalName() {
		fileStringFileType.changeName("$IllegalName$");
		assertEquals("fileStringFileType",fileStringFileType.getName());
		assertNull(fileStringFileType.getModificationTime());
	} 
	
	@Test
	public void testChangeName_TestFileOrder() {
		Directory directory = new Directory("Directory");
		File fileA = new File(directory,"A", FileType.Java);
		File fileB = new File(directory,"B", FileType.Java);
		File fileC = new File(directory,"C", FileType.Java);
		assertEquals(1,directory.getIndexOf(fileA));
		assertEquals(2,directory.getIndexOf(fileB));
		assertEquals(3,directory.getIndexOf(fileC));
		fileB.changeName("D");
		assertEquals(1,directory.getIndexOf(fileA));
		assertEquals(2,directory.getIndexOf(fileC));
		assertEquals(3,directory.getIndexOf(fileB));
		fileA.terminate();
		fileB.terminate();
		fileC.terminate();
		directory.terminate();
	}

	@Test
	public void testIsValidSize_LegalCase() {
		assertTrue(File.isValidSize(0));
		assertTrue(File.isValidSize(File.getMaximumSize()/2));
		assertTrue(File.isValidSize(File.getMaximumSize()));
	}
	
	@Test
	public void testIsValidSize_IllegalCase() {
		assertFalse(File.isValidSize(-1));
		if (File.getMaximumSize() < Integer.MAX_VALUE) {
			assertFalse(File.isValidSize(File.getMaximumSize()+1));
		}
	}

	@Test
	public void testEnlarge_LegalCase() {
		File file = new File("new_object",File.getMaximumSize()-1,true, FileType.Java);
		Date timeBeforeEnlarge = new Date();
		file.enlarge(1);
		Date timeAfterEnlarge = new Date();		
		assertEquals(file.getSize(),File.getMaximumSize());
		assertNotNull(file.getModificationTime());
		assertFalse(file.getModificationTime().before(timeBeforeEnlarge));
		assertFalse(timeAfterEnlarge.before(file.getModificationTime()));  
	}
	
	@Test (expected = ObjectNotWritableException.class)
	public void testEnlarge_FileNotWritable() {
		fileNotWritable.enlarge(1);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testEnlarge_FileTerminated() {
		fileTerminated.terminate();
		fileTerminated.enlarge(1);
	}
	
	@Test
	public void testShorten_LegalCase() {
		fileStringIntBooleanFileType.shorten(1);
		Date timeAfterShorten = new Date();		
		assertEquals(fileStringIntBooleanFileType.getSize(),99);
		assertNotNull(fileStringIntBooleanFileType.getModificationTime());
		assertFalse(fileStringIntBooleanFileType.getModificationTime().before(timeAfterConstruction));
		assertFalse(timeAfterShorten.before(fileStringIntBooleanFileType.getModificationTime()));
	} 
	
	@Test (expected = ObjectNotWritableException.class)
	public void testShorten_FileNotWritable() {
		fileNotWritable.shorten(1);
	}

	@Test (expected = IllegalStateException.class)
	public void testShorten_FileTerminated() {
		fileTerminated.terminate();
		fileTerminated.shorten(1);
	}
	
	@Test
	public void testIsValidCreationTime_LegalCase() {
		Date now = new Date();
		assertTrue(File.isValidCreationTime(now));
	}
	
	@Test
	public void testIsValidCreationTime_IllegalCase() {
		assertFalse(File.isValidCreationTime(null));
		Date inFuture = new Date(System.currentTimeMillis() + 1000*60*60);
		assertFalse(File.isValidCreationTime(inFuture));		
	}
	
	@Test
	public void testcanHaveAsModificationTime_LegalCase() {
		assertTrue(fileStringFileType.canHaveAsModificationTime(null));
		assertTrue(fileStringFileType.canHaveAsModificationTime(new Date()));
	}
	
	@Test
	public void testcanHaveAsModificationTime_IllegalCase() {
		assertFalse(fileStringFileType.canHaveAsModificationTime(new Date(timeAfterConstruction.getTime() - 1000*60*60)));
		assertFalse(fileStringFileType.canHaveAsModificationTime(new Date(System.currentTimeMillis() + 1000*60*60)));
	}

	@Test
	public void testHasOverlappingUsePeriod_UnmodifiedFiles() {
		// one = implicit argument ; other = explicit argument
		File one = new File("one", FileType.Java);
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		File other = new File("other", FileType.Java);
		
		//1 Test unmodified case
		assertFalse(one.hasOverlappingUsePeriod(other));
		
		//2 Test one unmodified case
		other.enlarge(File.getMaximumSize());
		assertFalse(one.hasOverlappingUsePeriod(other));
		
		//3 Test other unmodified case
		//so re-initialise the other file
		other = new File("other", FileType.Java);
		one.enlarge(File.getMaximumSize());
		assertFalse(one.hasOverlappingUsePeriod(other));
		
	}
	
	@Test
	public void testHasOverlappingUsePeriod_ModifiedNoOverlap() {
		// one = implicit argument ; other = explicit argument
		File one, other;
		one = new File("one", FileType.Java);
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		other = new File("other", FileType.Java);
		
		//1 Test one created and modified before other created and modified case
		one.enlarge(File.getMaximumSize());
        sleep();
        //re-initialise the other
        other = new File("other", FileType.Java);
        other.enlarge(File.getMaximumSize());
	    assertFalse(one.hasOverlappingUsePeriod(other));
	    
	    //2 Test other created and modified before one created and modified
		other.enlarge(File.getMaximumSize());
        sleep();
        one = new File("one", FileType.Java);
        one.enlarge(File.getMaximumSize());
        assertFalse(one.hasOverlappingUsePeriod(other));
	
	}
	
	@Test
	public void testHasOverlappingUsePeriod_ModifiedOverlap_A() {
		// one = implicit argument ; other = explicit argument
		//A Test one created before other created before one modified before other modified
	    File one, other;
		one = new File("one", FileType.Java);
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		other = new File("other", FileType.Java);
	
		one.enlarge(File.getMaximumSize());
        sleep();
        other.enlarge(File.getMaximumSize());
        assertTrue(one.hasOverlappingUsePeriod(other));
	}
	
	@Test
	public void testHasOverlappingUsePeriod_ModifiedOverlap_B() {
		// one = implicit argument ; other = explicit argument
		//B Test one created before other created before other modified before one modified
       	File one, other;
		one = new File("one", FileType.Java);
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		other = new File("other", FileType.Java);
	
		other.enlarge(File.getMaximumSize());
        sleep();
        one.enlarge(File.getMaximumSize());
        assertTrue(one.hasOverlappingUsePeriod(other));
	}
	
	@Test
	public void testHasOverlappingUsePeriod_ModifiedOverlap_C() {
		// one = implicit argument ; other = explicit argument
		//C Test other created before one created before other modified before one modified
        File one, other;
		other = new File("other", FileType.Java);
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		one = new File("one", FileType.Java);
		
		other.enlarge(File.getMaximumSize());
        sleep();
        one.enlarge(File.getMaximumSize());
        assertTrue(one.hasOverlappingUsePeriod(other));
	}
	
	@Test
	public void testHasOverlappingUsePeriod_ModifiedOverlap_D() {
		// one = implicit argument ; other = explicit argument
		//D Test other created before one created before one modified before other modified
		File one, other;
		other = new File("one", FileType.Java);
		sleep(); // sleep() to be sure that one.getCreationTime() != other.getCreationTime()
		one = new File("other", FileType.Java);
	
		one.enlarge(File.getMaximumSize());
        sleep();
        other.enlarge(File.getMaximumSize());
        assertTrue(one.hasOverlappingUsePeriod(other));
	}

	@Test
	public void testSetWritable() {
		fileStringFileType.setWritable(false);
		fileNotWritable.setWritable(true);
		assertFalse(fileStringFileType.isWritable());
		assertTrue(fileNotWritable.isWritable());
	}
	
	@Test
	public void testMakeRoot() {
		assertFalse(fileDirStringFileType.isRoot());
		fileDirStringFileType.makeRoot();
		assertTrue(fileDirStringFileType.isRoot());
	}
	
	@Test
	public void testMove_LegalCase() {
		assertEquals(directoryString, fileDirStringFileType.getParentDirectory());
		fileDirStringFileType.move(directoryStringBoolean);
		assertEquals(directoryStringBoolean, fileDirStringFileType.getParentDirectory());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testMove_DestinationNull() {
		fileDirStringFileType.move(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testMove_DestinationIsParent() {
		fileDirStringFileType.move(directoryString);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testMove_DestinationCannotHaveAsItem() {
		fileDirStringFileType.move(directoryNotWritable);
	}
	
	@Test
	public void testGetRoot() {
		File file = new File(directoryDirString,"new_file",100,true,FileType.Java);
		assertEquals(directoryString, file.getRoot());
	}
	
	@Test
	public void testTerminate_LegalCase() {
		assertFalse(directoryTerminated.isTerminated());
		directoryTerminated.terminate();
		assertTrue(directoryTerminated.isTerminated());
	}
	
	@Test (expected = IllegalStateException.class)
	public void testTerminate_IllegalCase() {
		@SuppressWarnings("unused")
		File fileInDirectoryTerminated = new File(directoryTerminated ,"fileInDirectoryTerminated", FileType.Java);
		directoryTerminated.terminate();
	}
	
	@Test
	public void testGetItem() {
		assertEquals(directoryStringBoolean.getItem("directoryDirStringBoolean"), directoryDirStringBoolean);
	}
	
    @Test (expected = IllegalArgumentException.class)
	public void testGetItem_ItemNotInDirectory() {
		directoryStringBoolean.getItem("InvalidItem");
	}
	
	@Test
	public void testCanHaveAsItem_LegalCase() {
		fileTerminated.terminate();
		directoryTerminated.terminate();
		
		assertTrue(directoryString.canHaveAsItem(fileStringFileType));
		assertTrue(directoryString.canHaveAsItem(fileDirStringFileType));
	} 

	@Test
	public void testCanHaveAsItem_IllegalCase() {
		assertFalse(directoryString.canHaveAsItem(null));
		assertFalse(directoryString.canHaveAsItem(directoryString));
		assertFalse(directoryNotWritable.canHaveAsItem(fileStringFileType));
		
		fileTerminated.terminate();
		assertFalse(directoryString.canHaveAsItem(fileTerminated));
		directoryTerminated.terminate();
		assertFalse(directoryTerminated.canHaveAsItem(fileStringFileType));
	}
	
	@Test
	public void testCanHaveAsItemAt_LegalCase() {
		File new_file;
		new_file = new File("new_file", 100, true, FileType.Java);
		
		assertTrue(directoryDirString.canHaveAsItemAt(new_file, 1));
		assertTrue(directoryString.canHaveAsItemAt(new_file, 3));
		
		directoryString.addAsItem(new_file);
		// Current file system objects in directoryString (by name):
		// - directoryDirString
		// - fileDirStringFileType
		// - new_file
		
		assertTrue(directoryString.canHaveAsItemAt(fileStringFileType,3));
		assertTrue(directoryString.canHaveAsItemAt(new_file, 3));
		assertTrue(directoryString.canHaveAsItemAt(fileDirStringFileType, 2));
	} 
	
	@Test
	public void testCanHaveAsItemAt_IllegalCase() {
		assertFalse(directoryStringBoolean.canHaveAsItemAt(fileStringFileType, -1));
		assertFalse(directoryStringBoolean.canHaveAsItemAt(fileStringFileType, 10));
	}
	
	@Test
	public void testHasProperItems() {
		assertTrue(directoryString.hasProperItems());
	}
	
	@Test
	public void testGetIndexOf() {
		File new_file;
		new_file = new File(directoryString, "new_file", 100, true, FileType.Java);
		
		assertEquals(2, directoryString.getIndexOf(fileDirStringFileType));
		assertEquals(3, directoryString.getIndexOf(new_file));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetIndexOf_FileNotInDirectory() {
		directoryString.getIndexOf(fileStringFileType);
	}
	
	@Test
	public void testHasAsItem() {
		assertTrue(directoryString.hasAsItem(fileDirStringFileType));
		assertFalse(directoryString.hasAsItem(fileStringFileType));
	}
	
	@Test
	public void testExists() {
		assertTrue(directoryString.exists("fileDirStringFileType"));
		assertFalse(directoryString.exists("InvalidFile"));
	}
	
	@Test
	public void testAddAsItem_LegalCase() {
		sleep(); // To ensure the modificationTime is different from timeAfterConstruction
		directoryString.addAsItem(fileStringFileType);
		assertEquals("fileStringFileType", directoryString.getItemAt(3).getName());
		assertTrue(directoryString.getModificationTime().after(timeAfterConstruction));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddAsItem_AlreadyHasItem() {
		directoryString.addAsItem(fileDirStringFileType);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddAsItem_NotWritable() {
		directoryNotWritable.addAsItem(fileStringFileType);
	}
	
	@Test
	public void testRemoveAsItem_LegalCase() {
		directoryString.removeAsItem(fileDirStringFileType);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testRemoveAsItem_FileNotInDirectory() {
		directoryString.removeAsItem(fileStringFileType);
	}
	
	@Test (expected = ObjectNotWritableException.class)
	public void testRemoveAsItem_DirectoryNotWritable() {
		directoryString.setWritable(false);
		directoryString.removeAsItem(fileDirStringFileType);
	}
	
	@Test
	public void testIsDirectOrIndirectSubdirectoryOf() {
		assertTrue(dirdirdir.isDirectOrIndirectSubdirectoryOf(dir));
		assertFalse(dir.isDirectOrIndirectSubdirectoryOf(dirdirdir));
		assertFalse(dirdir.isDirectOrIndirectSubdirectoryOf(directoryNotWritable));
	}
	
	private void sleep() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
