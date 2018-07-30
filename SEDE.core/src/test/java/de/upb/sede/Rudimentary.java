package de.upb.sede;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import de.upb.sede.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
/*
 * A rudimentary test case.
 */
import org.junit.Test;

public class Rudimentary {

	private final static Logger logger = LogManager.getLogger();

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