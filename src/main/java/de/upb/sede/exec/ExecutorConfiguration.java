package de.upb.sede.exec;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import de.upb.sede.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ExecutorConfiguration {
	private static final String UNDEFINED_SERVICE_STORE_LOC = "No location defined to store services in.";

	private static Logger logger = LogManager.getLogger(ExecutorConfiguration.class);

	private AvailableResources resources = new AvailableResources();
	private int threadNumber = 4;
	private String serviceStoreLocation = UNDEFINED_SERVICE_STORE_LOC;
	private String executorId = UUID.randomUUID().toString();

	private List<String> capabilties = new ArrayList<>();
	private List<String> services = new ArrayList<>();

	public static ExecutorConfiguration parse(String executorConfigurationPath) throws Exception {
		File configFile = getConfigurationFile(executorConfigurationPath);
		SAXParserFactory saxFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxFactory.newSAXParser();
		ExecutorConfigurationHandler executorConfigurationHandler = new ExecutorConfigurationHandler();
		saxParser.parse(configFile, executorConfigurationHandler);
		return executorConfigurationHandler.getConfiguration();
	}

	public static ExecutorConfiguration parseJSON(String configPath) {
		ExecutorConfiguration configuration = new ExecutorConfiguration();
		JSONParser jsonParser = new JSONParser();
		String fileContent = FileUtil.readFileAsString(configPath);
		try {
			jsonParser.parse(fileContent); // TODO
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return configuration;
	}

	private static File getConfigurationFile(String executorConfigurationPath) {
		File executorConfigurationFile = new File(executorConfigurationPath);
		if (!executorConfigurationFile.exists()) {
			RuntimeException e = new RuntimeException("File: \"" + executorConfigurationPath + "\" does not exist.");
			logger.error(e);
			throw e;
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

	public String getExecutorId() {
		return executorId;
	}

	public List<String> getExecutorCapabilities() {
		return capabilties;
	}

	public List<String> getSupportedServices(){
		return services;
	}

	public void setExecutorId(String core_client) {
		executorId = core_client;
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
