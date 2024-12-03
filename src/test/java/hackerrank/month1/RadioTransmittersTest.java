package hackerrank.month1;

import static hackerrank.month1.RadioTransmitters.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RadioTransmittersTest {
	@Test
	void testBlocks() {
		assertEquals(3, hackerlandRadioTransmitters(new int[] {1, 2, 3, 5, 9}, 1));
		assertEquals(2, hackerlandRadioTransmitters(new int[] {1, 2, 3, 4, 5}, 1));
		assertEquals(3, hackerlandRadioTransmitters(new int[] {7, 2, 4, 6, 5, 9, 12, 11}, 2));
	}
}
