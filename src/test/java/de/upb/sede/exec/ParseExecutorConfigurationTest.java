package de.upb.sede.exec;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.upb.sede.exec.AvailableResources;
import de.upb.sede.exec.ExecutorConfiguration;

public class ParseExecutorConfigurationTest {
	@Test
	public void parseAvailableResources() throws Exception {
		String configurationFile = "testrsc/exec/ExecutorConfiguration_Test_1.xml";
		ExecutorConfiguration parsedConfiguration = ExecutorConfiguration.parse(configurationFile);
		AvailableResources resources = parsedConfiguration.getAvailableResources();
		assertEquals(1, resources.getCPUNumber());
		assertEquals(1, resources.getFPGANumber());
		assertEquals(1, resources.getGPUNumber());
	}

	@Test
	public void parseThreadNumber() throws Exception {
		String configurationFile = "testrsc/exec/ExecutorConfiguration_Test_2.xml";
		ExecutorConfiguration parsedConfiguration = ExecutorConfiguration.parse(configurationFile);
		int threadNumber = parsedConfiguration.getThreadNumber();
		assertEquals(8, threadNumber);
	}

	@Test
	public void parseServiceStoreLocation() throws Exception {
		String configurationFile = "testrsc/exec/ExecutorConfiguration_Test_3.xml";
		ExecutorConfiguration parsedConfiguration = ExecutorConfiguration.parse(configurationFile);
		String location = parsedConfiguration.getServiceStoreLocation();
		assertEquals("service_storage/", location);
	}

	@Test
	public void parseConfiguration() throws Exception {
		String configurationFile = "testrsc/exec/ExecutorConfiguration_Test_4.xml";
		ExecutorConfiguration parsedConfiguration = ExecutorConfiguration.parse(configurationFile);
		AvailableResources resources = parsedConfiguration.getAvailableResources();
		int threadNumber = parsedConfiguration.getThreadNumber();
		String location = parsedConfiguration.getServiceStoreLocation();
		assertEquals(4, resources.getCPUNumber());
		assertEquals(0, resources.getFPGANumber());
		assertEquals(2, resources.getGPUNumber());
		assertEquals(8, threadNumber);
		assertEquals("service_storage/", location);
	}
}
