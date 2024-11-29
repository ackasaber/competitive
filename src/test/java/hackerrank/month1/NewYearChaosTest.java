package hackerrank.month1;

import static hackerrank.month1.NewYearChaos.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NewYearChaosTest {
	@Test
	void testBribes() {
		assertEquals(1, minimumBribes(new int[] { 1, 2, 3, 5, 4, 6, 7, 8 }));
		assertEquals(0, minimumBribes(new int[] { 1, 2, 3, 4, 5 }));
		assertEquals(0, minimumBribes(new int[] { 1 }));
		assertEquals(1, minimumBribes(new int[] { 2, 1 }));
		assertEquals(-1, minimumBribes(new int[] { 4, 1, 2, 3 }));
		assertEquals(3, minimumBribes(new int[] { 2, 1, 5, 3, 4 }));
		assertEquals(-1, minimumBribes(new int[] { 2, 5, 1, 3, 4 }));
	}
}
