package alchemy;

import static org.junit.Assert.*;
import org.junit.*;
import be.kuleuven.cs.som.*;

/**
 * Don't mind me, just trying to understand Capacity
 * @author	Tim Robensyn
 */
public class CapacityTest {
	
	@Test
	public void noInspirationForANameTest() {
		int cap = Capacity.BARREL.getCapacity();
		assertEquals(cap, 12*Capacity.JUG.getCapacity());
		System.out.println(Capacity.values());
	}

}
