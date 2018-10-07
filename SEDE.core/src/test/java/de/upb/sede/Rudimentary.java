package de.upb.sede;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/*
 * A rudimentary test case.
 */
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.sede.util.FileUtil;

public class Rudimentary {

	private final static Logger logger = LoggerFactory.getLogger(Rudimentary.class);

	@Test
	public void testTrue() {
		assertTrue("abc".equalsIgnoreCase("ABC"));
	}

	@Test
	public void readResources() {
		System.out.println(FileUtil.readResourceAsString("config/A-classconf.json"));
	}

	@Test
	public void testLogging() {
		logger.info("• • •   − − −   • • •  ");
	}

	@Test
	public void testShift() {
		Long a = 128L;
		short shifted =  (short) ((a.shortValue() << 8) & 65535);
		assertEquals(-32768, shifted);
		int rightShifted = (shifted >> 8) & 255;
		assertEquals(128, rightShifted);
	}



}