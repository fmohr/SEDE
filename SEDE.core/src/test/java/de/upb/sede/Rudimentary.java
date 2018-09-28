package de.upb.sede;

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

}