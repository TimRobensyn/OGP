package filesystem;

public class Directory extends FileSystemObject {
	
	
	public Directory(Directory dir, String name, boolean writable) {
		super(dir, name, writable);
	}
		
	public Directory(String name, boolean writable) {
		this(null, name, writable);
	}
	
	public Directory(String name) {
		this(null, name, true);
	}
	
	public Directory(Directory dir, String name) {
		this(dir, name, true);
	}
	
	
	
	public FileSystemObject getItemAt(int index) {
		return null;
	}
	
	public FileSystemObject getItem(String name) {
		return null;
	}
	
	public boolean exists(String name) {
		return false;
	}
	
	public int getIndexOf(FileSystemObject object) {
		return 0;
	}
	
	public int getNbItems() {
		return 0;
	}
	
	public boolean hasAsItem(Directory dir) {
		return false;
	}
	
	public boolean isDirectOrIndirectSubdirectoryOf(Directory dir) {
		return false;
	}
}
