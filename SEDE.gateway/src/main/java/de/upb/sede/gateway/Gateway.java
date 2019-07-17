package de.upb.sede.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import de.upb.sede.config.DeploymentConfig;
import de.upb.sede.gateway.edd.CachedExecutorHandleSupplier;
import de.upb.sede.requests.deploy.EDDRegistration;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.sede.composition.graphs.CompositionGraph;
import de.upb.sede.composition.graphs.GraphConstruction;
import de.upb.sede.composition.graphs.serialization.GraphJsonSerializer;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.interfaces.IGateway;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.requests.resolve.GatewayResolution;
import de.upb.sede.requests.resolve.ResolveRequest;
import de.upb.sede.util.GraphToDot;

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
	private final static Logger logger = LoggerFactory.getLogger(Gateway.class);

	/**
	 * Has the task to offer coordination over registered executors.
	 */
	private final ExecutorSupplyCoordinator execCoordinator;
	/**
	 * classes configuration.
	 */
	private final ClassesConfig classesConfig;
	/**
	 * type configuration.
	 */
	private final OnthologicalTypeConfig typeConfig;
    /**
     * deployment configuration
     */
    private final DeploymentConfig deploymentConfig;

	/**
	 * Constructor that accepts defined classes and type configuration.
	 */
	public Gateway(ClassesConfig classesConfig,
				   OnthologicalTypeConfig typeConfig) {
		this.execCoordinator = new ExecutorSupplyCoordinator();
		this.classesConfig = classesConfig;
		this.typeConfig = typeConfig;
		this.deploymentConfig = new DeploymentConfig();
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
		CompositionGraph clientGraph = gc.getClientGraph();
		List<String> returnFields = gc.getReturnFields();
		/*
		 * Serializae the graph to json:
		 */
		GraphJsonSerializer gjs = new GraphJsonSerializer();
		JSONObject jsonClientGraph = gjs.toJson(clientGraph);
		/* Generate return resolution: */
		GatewayResolution gatewayResolution = new GatewayResolution(jsonClientGraph.toJSONString(), returnFields);
		/* If the client needs the visualisation of the graph appen it to the resolution */
		if(resolveRequest.getPolicy().isToReturnDotGraph()) {
			try{
				String svg = GraphToDot.GCToSVG(gc);
				gatewayResolution.setDotSvg(svg);
			} catch(Exception ex) {
				logger.error("Error trying to calculate the dot from graph: ", ex);
			}
		}
		logger.debug("Resolved graph. RequestId: {}", resolveRequest.getRequestID());
		return gatewayResolution;
	}

	private final ExecutorHandle createExecHandle(ExecutorRegistration registration){
		ExecutorHandle execHandle = ExecutorHandle.fromRegistration(registration);

        /*
         * Remove all the supported Services from the executor that are not supported by
         * this gateway:
         */
        boolean unsupportedServicesFound = execHandle.getExecutionerCapabilities().removeServices(classesConfig::classunknown);
		if(logger.isWarnEnabled() && unsupportedServicesFound) {
			logger.warn("Executor registered with services that are unknown to the gateway. " +
                    "These services will be ignored:\n\t{}",
                    registration.getSupportedServices()
                        .stream().filter(classesConfig::classunknown)
                        .collect(Collectors.joining("\n\t")));
		}
		return execHandle;
	}


	@Override
	public synchronized final boolean register(ExecutorRegistration execRegister) {
		if(execCoordinator.hasExecutor(execRegister.getId())) {
			/*
			 * Update the internal data for the executorId.
			 * An executor may have changed some its informations
			 * like a new address in contact info map or has dropped support for a service.
			 * Delete the internal representation of the executor.
			 */
			logger.warn("ExecutorRegistration with an id that has already been registered: {} \nReplacing executor handle.",  execRegister.getId());
			execCoordinator.removeExecutor(execRegister.getId());
		}
		ExecutorHandle execHandle = createExecHandle(execRegister);

		if(execHandle.getExecutionerCapabilities().supportedServices().isEmpty()) {
			/*
			 * as this implementation doesn't support loading services onto the executor, registration with empty services are denied.
			 */
			logger.warn("Executor tried to register with 0 amount of supported services. Denied registration. Executors host: {}", execHandle.getExecutorId());
			return false;

		}  else {
		    StandaloneExecutor standaloneExecutor = new StandaloneExecutor(execHandle);
			execCoordinator.addSupplier(standaloneExecutor);
			logger.info("Executor registered successfully with {} services. Executor's id: {}", execHandle.getExecutionerCapabilities().supportedServices().size(), execRegister.getId());
			logger.trace("Supported service of executor with id {} are {}.", execRegister.getId(), execHandle.getExecutionerCapabilities().supportedServices());
			return true;
		}
	}

	public synchronized final boolean registerEDD(EDDRegistration registration) {
        Objects.requireNonNull(registration, "EDD registration was null..");
        if(execCoordinator.hasExecutor(registration.getEddId())) {
            logger.warn("EDD registration with an known id. {} \n Replacing the registration.", registration.toString());
            execCoordinator.removeExecutor(registration.getEddId());
        }

        EDDRegistration sanitisedReg = registration.copy();
        boolean unknownServicesFound =
            sanitisedReg.getOfferedServices().removeIf(classesConfig::classunknown);

        if(unknownServicesFound) {
            List<String> unknownClasses = registration.getOfferedServices()
                .stream()
                .filter(classesConfig::classunknown)
                .collect(Collectors.toList());

            logger.warn("EDD {} registered with unknown services: {}. " +
                "Removed them from the registration.", registration, unknownClasses);
        }

        if(sanitisedReg.getOfferedServices().isEmpty()) {
            logger.error("Couldn't register EDD {} with 0 known services.", sanitisedReg);
            return false;
        }

        CachedExecutorHandleSupplier cachedExecutorHandleSupplier =
            new CachedExecutorHandleSupplier(sanitisedReg);
	    execCoordinator.addSupplier(cachedExecutorHandleSupplier);
	    if(!logger.isTraceEnabled()){
            logger.info("EDD successfully registered: {}.", registration);
        } else {
            logger.trace("EDD successfully registered: {}." +
                "\n Supported services are: {}.", registration, registration.getOfferedServices());
        }
	    return true;

    }


	/**
	 * Builds and returns a new instance of ResolveInfo from the given
	 * ResolveRequest.
	 */
	private final ResolveInfo resolveInfoFromRequest(ResolveRequest resolveRequest) {
		ResolveInfo info = new ResolveInfo();
		info.setClassesConfiguration(classesConfig);
		info.setExecutorSupplyCoordinator(execCoordinator);
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

	public final ExecutorSupplyCoordinator getExecutorCoord() {
		return execCoordinator;
	}

	public final DeploymentConfig getDeploymentConfig() {
	    return deploymentConfig;
    }

}
