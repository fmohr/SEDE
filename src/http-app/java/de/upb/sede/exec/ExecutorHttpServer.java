package de.upb.sede.exec;

import com.sun.net.httpserver.HttpServer;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.gateway.GatewayHttpServer;
import de.upb.sede.procedure.Procedure;
import de.upb.sede.procedure.SendGraphProcedure;
import de.upb.sede.procedure.TransmitDataProcedure;
import de.upb.sede.requests.DataPutRequest;
import de.upb.sede.requests.ExecRequest;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.util.Streams;
import de.upb.sede.webinterfaces.SunHttpHandler;
import de.upb.sede.webinterfaces.client.BasicClientRequest;
import de.upb.sede.webinterfaces.client.HTTPClientRequest;
import de.upb.sede.webinterfaces.server.HTTPServerResponse;
import de.upb.sede.webinterfaces.server.StringServerResponse;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ExecutorHttpServer extends Executor{

	private final String hostAddress;


	private final HttpServer server;

	public ExecutorHttpServer(ExecutorConfiguration execConfig, String hostAddress, int port) throws Exception {
		super(execConfig);
		this.hostAddress = hostAddress;
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

	protected Map<String, String> getContactInfo(){
		Map<String, String> contactInfo = super.getContactInfo();
		contactInfo.put("host-address", this.hostAddress);
		return contactInfo;
	}

	public void registerToGateway(String gatewayHost) {
		List<String> capibilities = getExecutorConfiguration().getExecutorCapabilities();
		List<String> supportedServices = getExecutorConfiguration().getSupportedServices();
		ExecutorRegistration registration = new ExecutorRegistration(getContactInfo(), capibilities, supportedServices);

		HTTPClientRequest httpRegistration = new HTTPClientRequest(gatewayHost + "/register");

		String registrationAnswer = httpRegistration.send(registration.toJsonString());
		if(!registrationAnswer.isEmpty()) {
			throw new RuntimeException("Registration to gateway \"" + gatewayHost + "\" failed with non empty return message:\n" + registrationAnswer);
		}
	}


	private void bindHttpProcedures(){
		WorkerPool wp = super.getWorkerPool();
		wp.bindProcedure("TransmitData", TransmitDataOverHttp::new);
		wp.bindProcedure("SendGraph", SendGraphOverHttp::new);
	}

	static class TransmitDataOverHttp extends TransmitDataProcedure {

		@Override
		public BasicClientRequest getPutDataRequest(Task task) {
			Map<String, String> contactInfo = (Map<String, String>) task.getAttributes().get("contact-info");
			String host = contactInfo.get("host-address");
			String fieldname = (String) task.getAttributes().get("fieldname");
			String semType = (String) task.getAttributes().get("semantic-type");
			String executionId = task.getExecution().getExecutionId();
			if(host == null || fieldname == null || semType == null) {
				throw new RuntimeException("The task doesn't contain all necessary fields: " + task.getAttributes().toString());
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
				if(urlPaths.length != 5 || urlPaths[pathIndex++].equalsIgnoreCase("put")){
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
