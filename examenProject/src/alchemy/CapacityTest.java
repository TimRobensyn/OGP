package alchemy;

import static org.junit.Assert.*;
import org.junit.*;
import be.kuleuven.cs.som.*;

/**
 * Don't mind me, just trying to understand Capacity and other shit
 * @author	Tim Robensyn
 */
public class CapacityTest {
	
	@Test
	public void noInspirationForANameTest() {
		int cap = Capacity.BARREL.getCapacity();
		assertEquals(cap, 12*Capacity.JUG.getCapacity());
		System.out.println(Capacity.values());
		int geheleDeling = 17/4;
		int rest = 17%4;
		int geheleDeling2 = 18/4;
		int rest2 = 18%4;
		System.out.println(geheleDeling);
		System.out.println(rest);
		System.out.println(geheleDeling2);
		System.out.println(rest2);
	}

}
