package hackerrank.month1;

import static hackerrank.month1.CounterGame.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CounterGameTest {
	@Test
	void testZeroCounting() {
		assertEquals(1, countTrailing0s(6));
		assertEquals(0, countTrailing0s(15));
		assertEquals(33, countTrailing0s(0xE00000000L));
		assertEquals(63, countTrailing0s(0x8000000000000000L));
	}
	
	@Test
	void testOneCounting() {
		assertEquals(0, count1s(0));
		assertEquals(1, count1s(1));
		assertEquals(1, count1s(2));
		assertEquals(2, count1s(6));
		assertEquals(64, count1s(-1L));
	}
	
	@Test
	void testCounterGame() {
		assertEquals("Louise", counterGame(132));
		assertEquals("Richard", counterGame(1));
	}
}
