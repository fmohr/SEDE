package de.upb.sede.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.upb.sede.composition.gc.ExecutorCoordinator;
import de.upb.sede.composition.gc.ExecutorHandle;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.webinterfaces.server.StringServerResponse;

public class ExecutorRegistrationHandler extends StringServerResponse {

	private final static Logger logger = Logger.getLogger("Executor Registration");
	private final ExecutorCoordinator coordinator;
	private final ClassesConfig classesConfig;

	public ExecutorRegistrationHandler(ExecutorCoordinator coordinator, ClassesConfig classesConfig) {
		super();
		this.coordinator = coordinator;
		this.classesConfig = classesConfig;

	}

	@Override
	public String receive(String jsonRegistration) {
		logger.debug("Received executor registration.");
		JSONParser parser = new JSONParser();
		/*
		 * TODO: Do validation before parsing.
		 */
		Map<String, Object> registrationDatamap;
		try {
			registrationDatamap = (Map<String, Object>) parser.parse(jsonRegistration);
		} catch (Exception e) {
			logger.error("The body of the executor registration cannot be parsed as JSON: " + e.getMessage(), e);
			return "Parse error: " + e.getMessage();
		}
		
		ExecutorRegistration execRegister = new ExecutorRegistration();
		execRegister.fromJson(registrationDatamap);
		register(execRegister);
		return "Registration Successfull";
	}
	
	public void register(ExecutorRegistration execRegister) {
		/*
		 * TODO load services onto the executor.
		 */
		ExecutorHandle execHandle = new ExecutorHandle(execRegister.getHost(), execRegister.getCapabilities().toArray(new String[0]));
		/*
		 * Remove all the supported Services from the executor that are not supported by
		 * this gateway:
		 */
		List<String> supportedServices = new ArrayList<>(execRegister.getSupportedServices());
		supportedServices.removeIf(classesConfig::classknown);
		execHandle.getExecutionerCapabilities().addAllServiceClasses(supportedServices.toArray(new String[0]));
		coordinator.addExecutor(execHandle);
		logger.info("Executor registered successfully: " + execRegister.getHost());
	}

}
