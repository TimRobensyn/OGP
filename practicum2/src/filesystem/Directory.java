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
		
	}
	
	public FileSystemObject getItem(String name) {
		
	}
	
	public boolean exists(String name) {
		
	}
	
	public int getIndexOf(FileSystemObject object) {
		
	}
	
	public int getNbItems() {
		
	}
	
	public boolean hasAsItem(Directory dir) {
		
	}
	
	public boolean isDirectOrIndirectSubdirectoryOf(Directory dir) {
		
	}
}
