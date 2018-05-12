package de.upb.sede.gateway;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import de.upb.sede.composition.graphs.CompositionGraph;
import de.upb.sede.composition.graphs.GraphConstruction;
import de.upb.sede.composition.graphs.serialization.GraphJsonSerializer;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.interfaces.IGateway;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.requests.resolve.GatewayResolution;
import de.upb.sede.requests.resolve.ResolveRequest;

/**
 * Implementation of IGateway. 
 * 
 * This gateway implementation doesn't load services onto the executor.
 * 
 * @author aminfaez
 *
 */
public class Gateway implements IGateway{
	/**
	 * basic logger
	 */
	private final static Logger logger = LogManager.getLogger();

	/**
	 * Has the task to offer coordination over registered executors.
	 */
	private final ExecutorCoordinator execCoordinator;
	/**
	 * classes configuration.
	 */
	private final ClassesConfig classesConfig;
	/**
	 * type configuration.
	 */
	private final OnthologicalTypeConfig typeConfig;
	
	/**
	 * Constructor that accepts defined classes and type configuration.
	 */
	public Gateway(ClassesConfig classesConfig,
			OnthologicalTypeConfig typeConfig) {
		this.execCoordinator = new ExecutorCoordinator();
		this.classesConfig = classesConfig;
		this.typeConfig = typeConfig;
	}
	
	/**
	 * Constructs the resolved graphs using the graph construction algorithm and returns the resolved graphs in the graph construction algorithm.
	 */
	public final GraphConstruction constructGraphs(ResolveRequest resolveRequest) {
		/*
		 * gather all the information to resolve the composition:
		 */
		ResolveInfo resolveInfo = resolveInfoFromRequest(resolveRequest);
		/*
		 * Resolve the composition by calculating the client graph:
		 */
		GraphConstruction gc = GraphConstruction.constructFromFMComp(resolveRequest.getComposition(), resolveInfo);
		
		return gc;
	}
	
	@Override
	public final GatewayResolution resolve(ResolveRequest resolveRequest) {
		GraphConstruction gc = constructGraphs(resolveRequest);
		CompositionGraph clientGraph = gc.getResolvedClientGraph();
		/*
		 * Serializae the graph to json:
		 */
		GraphJsonSerializer gjs = new GraphJsonSerializer();
		JSONObject jsonClientGraph = gjs.toJson(clientGraph);

		GatewayResolution gatewayResolution = new GatewayResolution(jsonClientGraph.toJSONString());
		
		return gatewayResolution;
	}

	private final ExecutorHandle createExecHandle(ExecutorRegistration execRegister){
		ExecutorHandle execHandle = new ExecutorHandle(execRegister.getId(), execRegister.getContactInfo(),
				execRegister.getCapabilities().toArray(new String[0]));
		/*
		 * Remove all the supported Services from the executor that are not supported by
		 * this gateway:
		 */
		List<String> supportedServices = new ArrayList<>(execRegister.getSupportedServices());
		supportedServices.removeIf(classesConfig::classunknown);
		execHandle.getExecutionerCapabilities().addAllServiceClasses(supportedServices.toArray(new String[0]));
		return execHandle;
	}
	

	@Override
	public final boolean register(ExecutorRegistration execRegister) {
		/*
		 * TODO load services onto the executor.
		 */
		ExecutorHandle execHandle = createExecHandle(execRegister);
		if(execHandle.getExecutionerCapabilities().supportedServices().isEmpty()) {
			/*
			 * as this implementation doesn't support loading services onto the executor, registration with empty services are denied.
			 */
			logger.warn("Executor tried to register with 0 amount of supported services. Denied registration. Executors host: {}", execHandle.getExecutorId());
			return false;
			
		} if(execCoordinator.hasExecutor(execHandle.getExecutorId())) {
			/*
			 * dont accept double registration.
			 */
			logger.warn("ExecutorRegistration with a host that has already been registered: {}",  execHandle.getExecutorId());
			return false;
		} else {
			execCoordinator.addExecutor(execHandle);
			logger.info("Executor registered successfully with {} services. Executor's Host: {}", execHandle.getExecutionerCapabilities().supportedServices().size(), execRegister.getId());
			logger.trace("Supported service of executor with host {} are {}.", execRegister.getId(), execHandle.getExecutionerCapabilities().supportedServices());
			return true;
		}
	}


	/**
	 * Builds and returns a new instance of ResolveInfo from the given
	 * ResolveRequest.
	 */
	private final ResolveInfo resolveInfoFromRequest(ResolveRequest resolveRequest) {
		ResolveInfo info = new ResolveInfo();
		info.setClassesConfiguration(classesConfig);
		info.setExecutorCoordinator(execCoordinator);
		info.setTypeConfig(typeConfig);
		info.setResolvePolicy(resolveRequest.getPolicy());
		info.setInputFields(resolveRequest.getInputFields());
		ExecutorRegistration clientExecRegistration = resolveRequest.getClientExecutorRegistration();

		ExecutorHandle clientExecHandle = createExecHandle(clientExecRegistration);
		info.setClientExecutor(clientExecHandle);
		return info;
	}
	/**
	 * @return classes configuration of this gateway. can be changed to have an effect on 'resolve'.
	 */
	public final ClassesConfig getClassesConfig() {
		return classesConfig;
	}

	/**
	 * @return type configuration of this gateway. can be changed to have an effect on 'resolve'.
	 */
	public final OnthologicalTypeConfig getTypeConfig() {
		return typeConfig;
	}
	
}
