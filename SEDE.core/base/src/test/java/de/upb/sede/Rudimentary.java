package de.upb.sede;

import static org.junit.Assert.assertTrue;

/*
 * A rudimentary test case.
 */
import de.upb.sede.util.WebUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.sede.util.FileUtil;

public class Rudimentary {

	private static final Logger logger = LoggerFactory.getLogger(Rudimentary.class);

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
	public void testIpRetrievers() {
		System.out.println("LOCAL: " + WebUtil.HostIpAddress());
		System.out.println("PUBLIC: " + WebUtil.HostPublicIpAddress());
	}

}
