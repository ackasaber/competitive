package hackerrank.month1;

import static hackerrank.month1.LegoBlocks.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LegoBlocksTest {
	@Test
	void testBlocks() {
		assertEquals(3, legoBlocks(2, 2));
		assertEquals(7, legoBlocks(3, 2));
		assertEquals(9, legoBlocks(2, 3));
		assertEquals(27, legoBlocks(2, 4));
		assertEquals(3375, legoBlocks(4, 4));
		assertEquals(1, legoBlocks(1, 1));
		assertEquals(1, legoBlocks(1, 4));
		assertEquals(0, legoBlocks(1, 5));
		assertEquals(1, legoBlocks(1000, 1));
	}
}
