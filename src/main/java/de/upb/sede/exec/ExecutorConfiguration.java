package de.upb.sede.exec;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ExecutorConfiguration {
	private static final String UNDEFINED_SERVICE_STORE_LOC = "No location defined to store services in.";

	private static Logger logger = Logger.getLogger(ExecutorConfiguration.class);

	private AvailableResources resources = new AvailableResources();
	private int threadNumber = 0;
	private String serviceStoreLocation = UNDEFINED_SERVICE_STORE_LOC;

	public static ExecutorConfiguration parse(String executorConfigurationPath) throws Exception {
		File configFile = getConfigurationFile(executorConfigurationPath);
		SAXParserFactory saxFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxFactory.newSAXParser();
		ExecutorConfigurationHandler executorConfigurationHandler = new ExecutorConfigurationHandler();
		saxParser.parse(configFile, executorConfigurationHandler);
		return executorConfigurationHandler.getConfiguration();
	}

	private static File getConfigurationFile(String executorConfigurationPath) {
		File executorConfigurationFile = new File(executorConfigurationPath);
		if (!executorConfigurationFile.exists()) {
			logger.error("File: \"" + executorConfigurationPath + "\" does not exist.");
			return null;
		}
		return executorConfigurationFile;
	}

	private void setAvailableResources(AvailableResources resources) {
		this.resources = resources;
	}

	private void setThreadNumber(int threadNumber) {
		this.threadNumber = threadNumber;
	}

	private void setServiceStoreLocation(String serviceStoreLocation) {
		this.serviceStoreLocation = serviceStoreLocation;
	}

	public AvailableResources getAvailableResources() {
		return resources;
	}

	public int getThreadNumber() {
		return threadNumber;
	}

	public String getServiceStoreLocation() {
		return serviceStoreLocation;
	}

	static class ExecutorConfigurationHandler extends DefaultHandler {
		private ExecutorConfiguration configuration = new ExecutorConfiguration();
		private AvailableResources resources = AvailableResources.UNDEFINED_RESOURCES;

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			switch (qName) {
			case "resources": {
				resources = new AvailableResources();
				break;
			}
			case "fpga": {
				resources.setFPGANumber(Integer.parseInt(attributes.getValue("number")));
				break;
			}
			case "cpu": {
				resources.setCPUNumber(Integer.parseInt(attributes.getValue("number")));
				break;
			}
			case "gpu": {
				resources.setGPUNumber(Integer.parseInt(attributes.getValue("number")));
				break;
			}
			case "thread_number": {
				configuration.setThreadNumber(Integer.parseInt(attributes.getValue("number")));
				break;
			}
			case "service_store_location": {
				configuration.setServiceStoreLocation(attributes.getValue("relative_path"));
				break;
			}
			}
		}

		@Override
		public void endDocument() throws SAXException {
			configuration.setAvailableResources(resources);
		}

		public ExecutorConfiguration getConfiguration() {
			return configuration;
		}
	}
}
