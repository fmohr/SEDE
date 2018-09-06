package de.upb.sede.gateway;

import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.exec.Executor;
import de.upb.sede.webinterfaces.client.HttpURLConnectionClientRequest;
import de.upb.sede.webinterfaces.server.CommandTree;
import de.upb.sede.webinterfaces.server.ServerCommandListeners;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static de.upb.sede.webinterfaces.server.CommandTree.*;
import static de.upb.sede.webinterfaces.server.Command.*;

public class GatewayCommands {
	private final Gateway gateway;
	private final static Logger logger = LogManager.getLogger();

	public static GatewayCommands enablePlugin(Gateway gateway, ServerCommandListeners scl){
		return new GatewayCommands(gateway, scl);
	}

	public GatewayCommands(Gateway gateway, ServerCommandListeners scl){
		this.gateway = gateway;
		CommandTree services = new CommandTree(
				node(new Strings(false, "services"),
						node(new Strings("ls").addExe(t->this.listServices())),
						node(new Strings("load"),
								node(new File().addExe(t->{
									String path = lastMatch(t);
									for(String rest: rest(t)) {
										path += "/" + rest;
									}
									return this.loadServices(path);
								}))
						)
				)
		);
		CommandTree types = new CommandTree(
				node(new Strings(false, "types"),
						node(new Strings("ls").addExe(t->this.listTypes())),
						node(new Strings("load"),
								node(new File().addExe(t->{
									String path = lastMatch(t);
									for(String rest: rest(t)) {
										path += "/" + rest;
									}
									return this.loadTypes(path);
								}))
						)
				)
		);

		CommandTree heartbeat = new CommandTree(
				node(new Strings(false, "executors"),
					node(new Strings(false, "heartbeat")
							.addExe(t-> heartbeat())),
					node(new Strings("ls"),
							node(new Strings("ids")
									.addExe(t -> listExecutors(ExecutorHandle::getExecutorId))),
							node(new Strings("addresses")
									.addExe(t -> listExecutors(executor -> {
										Map contactInfo = executor.getContactInfo();
										if (contactInfo.containsKey("host-address")) {
											return executor.getExecutorId() + "\t " + (String) contactInfo.get("host-address");
										} else {
											return "No address available for executor with id '" + executor.getExecutorId() + "'.";
										}
									}))),
							node(new Strings("services")
									.addExe(t -> listExecutors(executor ->
										 executor.getExecutorId() + "\n\t\t"+
												executor.getExecutionerCapabilities().supportedServices().stream().collect(Collectors.joining("\n\t\t")))
									))
							)));

		scl.addCommandHandle(services);
		scl.addCommandHandle(types);
		scl.addCommandHandle(heartbeat);
	}

	private String listServices() {
		ClassesConfig classesConfig = gateway.getClassesConfig();
		if(classesConfig.isEmpty()) {
			return "Gateway supports no services yet.";
		}
		String knownClasses = classesConfig.classesKnown().stream().sorted()
				.collect(Collectors.joining("\n"));
		return "Known services are: \n" + knownClasses;
	}

	private String loadServices(String configPath) {
		gateway.getClassesConfig().appendConfigFromFiles(configPath);
		return "Loaded service configurations from " + configPath;
	}

	private String listTypes() {
		OnthologicalTypeConfig typeConfig = gateway.getTypeConfig();
		if(typeConfig.isEmpty()) {
			return "Gateway supports no types yet.";
		}
		String knownTypes = typeConfig.knownTypes()
				.stream().sorted()
				.map(type -> type + " <-> " + typeConfig.getOnthologicalType(type))
				.collect(Collectors.joining("\n"));
		return "Known types are: \n" + knownTypes;
	}

	private String listExecutors(Function<ExecutorHandle, String> mapFunction){
		return "Registered executors: \n\t" +
				gateway.getExecutorCoord().getExecutors().stream().map(mapFunction).collect(Collectors.joining("\n\t"));
	}


	private String loadTypes(String configPath) {
		gateway.getTypeConfig().appendConfigFromFiles(configPath);
		return "Loaded types configurations from " + configPath;
	}

	private synchronized String heartbeat()  {
		logger.info("Gateway is performing HEARTBEAT. Removing every registered executor who doesn't respond.");
		String removedExecutors = "Removed executors: ";
		for(ExecutorHandle executorHandle : gateway.getExecutorCoord().getExecutors()){
			Map contactInfo = executorHandle.getContactInfo();
			if(contactInfo.containsKey("host-address")) {
				String address =(String) contactInfo.get("host-address");
				String heartbeatUrl = address + "/cmd/heartbeat";
				HttpURLConnectionClientRequest requestHeartbeat = new HttpURLConnectionClientRequest(heartbeatUrl);
				requestHeartbeat.setTimeout(200);
				boolean executoraintalive = false;
				try {
					String response = requestHeartbeat.send("");
					if(!response.equals("true")) {
						logger.info("Gateway is performing HEARTBEAT. Heartbeat failed for executor with id: {}. Response: {}",
								executorHandle.getExecutorId(),response);
						executoraintalive = true;
					}
				} catch(Exception ex) {
					logger.info("Gateway is performing HEARTBEAT. Heartbeat failed for executor with id: {}. Error: {}",
							executorHandle.getExecutorId(), ex.getMessage());
					executoraintalive = true;
				}
				if(executoraintalive) {
					removedExecutors += "\n" + executorHandle.getExecutorId();
					gateway.getExecutorCoord().removeExecutor(executorHandle.getExecutorId());
				}
			} else {
				logger.warn("Gateway is performing HEARTBEAT. Cannot contact executor with id: {}. His contact information is: {}.",
						executorHandle.getExecutorId(), contactInfo);
			}
		}
		return removedExecutors;
	}

}
