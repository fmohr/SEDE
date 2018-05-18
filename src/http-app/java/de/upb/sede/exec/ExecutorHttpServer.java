package de.upb.sede.exec;

import com.sun.net.httpserver.HttpServer;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.procedure.SendGraphProcedure;
import de.upb.sede.procedure.TransmitDataProcedure;
import de.upb.sede.requests.DataPutRequest;
import de.upb.sede.requests.ExecRequest;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.util.Streams;
import de.upb.sede.webinterfaces.server.ImServer;
import de.upb.sede.webinterfaces.client.BasicClientRequest;
import de.upb.sede.webinterfaces.client.HTTPClientRequest;
import de.upb.sede.webinterfaces.server.HTTPServerResponse;
import de.upb.sede.webinterfaces.server.StringServerResponse;
import de.upb.sede.webinterfaces.server.SunHttpHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ExecutorHttpServer extends Executor implements ImServer {

	private static final Logger logger = LogManager.getLogger();

	private final String hostAddress;


	private final HttpServer server;

	public ExecutorHttpServer(ExecutorConfiguration execConfig, String hostAddress, int port) {
		super(execConfig);
		this.hostAddress = hostAddress + ":" + port;
		bindHttpProcedures();

		try {
			server = HttpServer.create(new InetSocketAddress(port), 0);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}

		server.createContext("/put", new SunHttpHandler(PutDataHandler::new));
		server.createContext("/execute", new SunHttpHandler(ExecuteGraphHandler::new));
		server.setExecutor(null); // creates a default executor
		server.start();
	}


	public ExecutorHttpServer(String pathToExecutionConfig, String hostAddress, int port) throws Exception {
		this(ExecutorConfiguration.parse(pathToExecutionConfig), hostAddress, port);
	}

	public Map<String, String> contactInfo(){
		Map<String, String> contactInfo = super.contactInfo();
		contactInfo.put("host-address", this.hostAddress);
		return contactInfo;
	}

	public void registerToGateway(String gatewayHost) {
		List<String> capibilities = getExecutorConfiguration().getExecutorCapabilities();
		List<String> supportedServices = getExecutorConfiguration().getSupportedServices();
		ExecutorRegistration registration = new ExecutorRegistration(contactInfo(), capibilities, supportedServices);

		HTTPClientRequest httpRegistration = new HTTPClientRequest(gatewayHost + "/register");

		String registrationAnswer = httpRegistration.send(registration.toJsonString());
		if(!registrationAnswer.isEmpty()) {
			throw new RuntimeException("Registration to gateway \"" + gatewayHost + "\" failed with non empty return message:\n" + registrationAnswer);
		}
		logger.debug("Registered to gateway: " + gatewayHost);
	}


	private void bindHttpProcedures(){
		WorkerPool wp = super.getWorkerPool();
		wp.bindProcedure("TransmitData", TransmitDataOverHttp::new);
		wp.bindProcedure("SendGraph", SendGraphOverHttp::new);
	}

	@Override
	public void shutdown() {
		interruptAll();
		getWorkerPool().shutdown();
		server.stop(0);
	}

	static class TransmitDataOverHttp extends TransmitDataProcedure {

		@Override
		public BasicClientRequest getPutDataRequest(Task task) {
			Map<String, String> contactInfo = (Map<String, String>) task.getAttributes().get("contact-info");
			String host = contactInfo.get("host-address");
			String fieldname = (String) task.getAttributes().get("fieldname");
			String semType = (String) task.getAttributes().get("semantic-type");
			String executionId = task.getExecution().getExecutionId();
			if(host == null || fieldname == null) {
				throw new RuntimeException("The task doesn't contain all necessary fields: " + task.getAttributes().toString());
			}
			if(semType == null) {
				SEDEObject sedeObject = task.getExecution().getEnvironment().get(fieldname);
				if(sedeObject.isReal()) {
					throw new RuntimeException("The task doesn't contain the 'semantic-type' field "
							+ "but when transmitting real data the semantic type needs to be defined. \n"
							+ "task: " + task.getAttributes().toString()
							+ "\nfield to be sent: " + sedeObject.toString());
				} else {
					semType = sedeObject.getType();
				}
			}
			String dataPutUrl = host + "/put/" + executionId + "/" + fieldname + "/" + semType;
			BasicClientRequest clientRequest = new HTTPClientRequest(dataPutUrl);
			return clientRequest;
		}
	}


	static class SendGraphOverHttp extends SendGraphProcedure {

		@Override
		public BasicClientRequest getExecRequest(Task task) {
			Map<String, String> contactInfo = (Map<String, String>) task.getAttributes().get("contact-info");
			String host = contactInfo.get("host-address");
			if(host == null) {
				throw new RuntimeException("The task doesn't contain all necessary fields: " + task.getAttributes().toString());
			}
			String executeGraphUrl = host + "/execute";
			BasicClientRequest clientRequest = new HTTPClientRequest(executeGraphUrl);
			return clientRequest;
		}
	}

	class PutDataHandler implements HTTPServerResponse {

		@Override
		public void receive(Optional<String> url, InputStream payload, OutputStream answer) {
			String response;
			try{
				if(!url.isPresent()){
					throw new RuntimeException("Put data needs to specify URL with fieldname and execution handle");
				}
				String[] urlPaths = url.get().split("/");
				int pathIndex = 1;
				if(urlPaths.length < 5 || !urlPaths[pathIndex++].equalsIgnoreCase("put")){
					throw new RuntimeException("URL syntax error: "  + url.get());
				}
				String execId = urlPaths[pathIndex++];
				String fieldname = urlPaths[pathIndex++];
				String semanticType = urlPaths[pathIndex++];
				SEDEObject inputObject = SemanticStreamer.readFrom(payload, semanticType);
				DataPutRequest putRequest = new DataPutRequest(execId, fieldname, inputObject);
				put(putRequest);
				response = "";
			} catch(Exception ex) {
				logger.error("Error at put request: {}\n", url.get(), ex);
				response =  ex.getMessage();
			}
			Streams.OutWriteString(answer, response, true);
		}
	}

	class ExecuteGraphHandler extends StringServerResponse {
		@Override
		public String receive(String payload) {
			try{
				ExecRequest request = new ExecRequest();
				request.fromJsonString(payload);
				exec(request);
				return "";
			} catch (Exception ex){
				return ex.getMessage();
			}
		}
	}


}
