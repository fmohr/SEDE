package de.upb.sede.gateway;

import static de.upb.sede.webinterfaces.server.CommandTree.lastMatch;
import static de.upb.sede.webinterfaces.server.CommandTree.node;
import static de.upb.sede.webinterfaces.server.CommandTree.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.stream.Collectors;

import de.upb.sede.util.Streams;
import de.upb.sede.webinterfaces.client.AsyncClientRequest;
import de.upb.sede.webinterfaces.server.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.webinterfaces.client.HttpURLConnectionClientRequest;
import de.upb.sede.webinterfaces.server.Command.File;
import de.upb.sede.webinterfaces.server.Command.Strings;
import de.upb.sede.webinterfaces.server.CommandTree;
import de.upb.sede.webinterfaces.server.ServerCommandListeners;

public class GatewayCommands {
	private final Gateway gateway;
	private final static Logger logger = LoggerFactory.getLogger(GatewayCommands.class);

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

		CommandTree executors = new CommandTree(
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
												 String.join("\n\t\t", executor.getExecutionerCapabilities().supportedServices()))
									))
							),
					node(new Strings("cmd"), node(new Command.ConsumeNothing().addExe(t -> forwardToExecutors(rest(t))))),
					node(new Strings("scheduler"),
							node(new Strings("state").addExe(t -> getScheduleState())),
							node(new Strings("reset").addExe(t -> {
								gateway.getExecutorCoord().getScheduler().reset();
								return  getScheduleState();
							}))
					)
				));



		scl.addCommandHandle(services);
		scl.addCommandHandle(types);
		scl.addCommandHandle(executors);
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

	private String getScheduleState() {
		return gateway.getExecutorCoord().getScheduler().toString();
	}

	private synchronized String heartbeat()  {
		logger.info("Gateway is performing HEARTBEAT. Removing every registered executor who doesn't respond.");
		StringBuilder removedExecutors = new StringBuilder("Removed executors: ");
		Map<String, AsyncClientRequest> pingRequests = new HashMap<>();

		try {
			for(ExecutorHandle executorHandle : gateway.getExecutorCoord().getExecutors()) {
				Map contactInfo = executorHandle.getContactInfo();
				if (contactInfo.containsKey("host-address")) {
					String address = (String) contactInfo.get("host-address");
					String id = (String) contactInfo.get("id");
					String heartbeatUrl = address + "/cmd/" + id + "/heartbeat";
					HttpURLConnectionClientRequest requestHeartbeat = new HttpURLConnectionClientRequest(heartbeatUrl);
					AsyncClientRequest asyncRequestHeartbeat = new AsyncClientRequest(requestHeartbeat, "");
					pingRequests.put(executorHandle.getExecutorId(), asyncRequestHeartbeat);
				} else {
					logger.warn("Gateway is performing HEARTBEAT. Cannot contact executor with id: {}. His contact information is: {}.",
							executorHandle.getExecutorId(), contactInfo);
				}
			}

			AsyncClientRequest.joinAll(pingRequests.values(), 8, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.warn("Error while perfoming HEARTBEAT: " , e);
			return Streams.ErrToString(e);
		}


		for(String executorId : pingRequests.keySet()) {
			AsyncClientRequest pingRequest = pingRequests.get(executorId);
			if(pingRequest.hasFailed() || !pingRequest.isDone()) {
				logger.info("Gateway is performing HEARTBEAT. Heartbeat failed for executor with id: {}",
						executorId);
				removedExecutors.append("\n").append(executorId);
				gateway.getExecutorCoord().removeExecutor(executorId);
			} else {
				logger.info("Gateway is performing HEARTBEAT. Executor responded successfully: {}", executorId);
			}
		}
		return removedExecutors.toString();
	}

	private synchronized String forwardToExecutors(String[] commandTokens) {
		StringBuilder urlBuilder = new StringBuilder("%s/cmd/%s/");
		StringBuilder responseAggregate = new StringBuilder();
		for(String token : commandTokens) {
			urlBuilder.append(token).append("/");
		}
		String forwardUrl = urlBuilder.toString();
		logger.info("Forwarding cmd to all executors: {}", String.format(forwardUrl, "ADRESS", "ID"));
		Map<String, AsyncClientRequest> forwardRequests = new HashMap<>();

		try {
			for(ExecutorHandle executorHandle : gateway.getExecutorCoord().getExecutors()) {
				String executorId = executorHandle.getExecutorId();
				Map contactInfo = executorHandle.getContactInfo();
				if (contactInfo.containsKey("host-address")) {
					String address = (String) contactInfo.get("host-address");
					HttpURLConnectionClientRequest forwardRequest = new HttpURLConnectionClientRequest(String.format(forwardUrl, address, executorId));
					AsyncClientRequest asyncRequestHeartbeat = new AsyncClientRequest(forwardRequest, "");
					forwardRequests.put(executorHandle.getExecutorId(), asyncRequestHeartbeat);
				} else {
					responseAggregate.append(String.format("No address found for executor %s:\n", executorId));
				}
			}


			AsyncClientRequest.joinAll(forwardRequests.values(), 8, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.warn("Error while forwarding request url {}:", forwardUrl , e);
			return Streams.ErrToString(e);
		}

		for(String executorId : forwardRequests.keySet()) {
			AsyncClientRequest forwardRequest = forwardRequests.get(executorId);
			if(forwardRequest.hasFailed() || !forwardRequest.isDone()) {
				responseAggregate.append(String.format("Time-out for executor %s:\n", executorId));
			} else {
				String response;
				try {
					response = forwardRequest.get().orElse("");
				} catch (InterruptedException | ExecutionException e) {
					logger.error("THIS SHOULDN'T HAVE HAPPENED: ", e);
					continue;
				}
				responseAggregate.append(String.format("Executor %s:\n\t", executorId))
						.append(response.replace("\n", "\n\t"))
						.append("\n");

			}
		}

		return responseAggregate.toString();
	}

}
