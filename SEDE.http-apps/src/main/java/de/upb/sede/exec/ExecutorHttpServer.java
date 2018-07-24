package de.upb.sede.exec;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.core.PrimitiveDataField;
import de.upb.sede.core.SemanticStreamer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.net.httpserver.HttpServer;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.procedure.FinishProcedure;
import de.upb.sede.procedure.SendGraphProcedure;
import de.upb.sede.procedure.TransmitDataProcedure;
import de.upb.sede.requests.DataPutRequest;
import de.upb.sede.requests.ExecRequest;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.util.Streams;
import de.upb.sede.webinterfaces.client.BasicClientRequest;
import de.upb.sede.webinterfaces.client.HttpURLConnectionClientRequest;
import de.upb.sede.webinterfaces.server.HTTPServerResponse;
import de.upb.sede.webinterfaces.server.ImServer;
import de.upb.sede.webinterfaces.server.StringServerResponse;
import de.upb.sede.webinterfaces.server.SunHttpHandler;

public class ExecutorHttpServer extends Executor implements ImServer {

	private static final Logger logger = LogManager.getLogger();

	private final String hostAddress;

	private final HttpServer server;

	private final Pattern PUT_DATA_URL_PATTERN = Pattern.compile("/put/(?<executionId>[-\\w]+)/(?<fieldname>(?:[&_a-zA-Z][&\\w]*+))/(?<semtype>\\w+)");
	private final Pattern INTERRUPT_URL_PATTERN = Pattern.compile("/interrupt/(?<executionId>\\w+)");

	public ExecutorHttpServer(ExecutorConfiguration execConfig, String hostAddress, int port) {
		super(execConfig);
		this.hostAddress = hostAddress + ":" + port;
		bindHttpProcedures();

		try {
			server = HttpServer.create(new InetSocketAddress(port), 0);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}

		addHandle("/put", PutDataHandler::new);
		addHandle("/execute", ExecuteGraphHandler::new);
		addHandle("/interrupt", InterruptHandler::new);

		server.setExecutor(null); // creates a default executor
		server.start();
	}

	public ExecutorHttpServer(String pathToExecutionConfig, String hostAddress, int port) {
		this(ExecutorConfiguration.parseJSONFromFile(pathToExecutionConfig), hostAddress, port);
	}

	public void addHandle(String context, Supplier<HTTPServerResponse> serverResponder) {
		server.createContext(context, new SunHttpHandler(serverResponder));
	}

	public Map<String, String> contactInfo() {
		Map<String, String> contactInfo = super.contactInfo();
		contactInfo.put("host-address", this.hostAddress);
		return contactInfo;
	}

	public void registerToGateway(String gatewayHost) {
		List<String> capibilities = getExecutorConfiguration().getExecutorCapabilities();
		List<String> supportedServices = getExecutorConfiguration().getSupportedServices();
		ExecutorRegistration registration = new ExecutorRegistration(contactInfo(), capibilities, supportedServices);

		HttpURLConnectionClientRequest httpRegistration = new HttpURLConnectionClientRequest(gatewayHost + "/register");

		String registrationAnswer = httpRegistration.send(registration.toJsonString());
		if (!registrationAnswer.isEmpty()) {
			throw new RuntimeException("Registration to gateway \"" + gatewayHost
					+ "\" failed with non empty return message:\n" + registrationAnswer);
		}
		getExecutorConfiguration().getGateways().add(gatewayHost);
		logger.debug("Registered to gateway: " + gatewayHost);
	}

	private void bindHttpProcedures() {
		WorkerPool wp = super.getWorkerPool();
		wp.bindProcedure("TransmitData", TransmitDataOverHttp::new);
		wp.bindProcedure("SendGraph", SendGraphOverHttp::new);
		wp.bindProcedure("Finish", FinishOverHttp::new);
	}

	@Override
	public void shutdown() {
		interruptAll();
		getWorkerPool().shutdown();
		server.stop(1);
	}

	private static BasicClientRequest createPutDataRequest(String host, String fieldname, String semType, String executionId, boolean failed) {
		if (host == null || fieldname == null) {
			throw new RuntimeException(
					"Cannot create a put data request without host or fieldname");
		}
		String dataPutUrl = host + "/put/" + executionId + "/" + fieldname;
		if(failed) {
			dataPutUrl += "/unavailable";
		} else {
			dataPutUrl += "/" + semType;
		}
		BasicClientRequest clientRequest = new HttpURLConnectionClientRequest(dataPutUrl);
		return clientRequest;

	}

	static class TransmitDataOverHttp extends TransmitDataProcedure {

		@Override
		public BasicClientRequest getPutDataRequest(Task task, boolean unavailable) {
			Map<String, String> contactInfo = (Map<String, String>) task.getAttributes().get("contact-info");
			String host = contactInfo.get("host-address");
			String fieldname = (String) task.getAttributes().get("fieldname");
			String semType = (String) task.getAttributes().get("semantic-type");
			String executionId = task.getExecution().getExecutionId();
			if (!unavailable && semType == null) {
				SEDEObject sedeObject = task.getExecution().getEnvironment().get(fieldname);
				if (sedeObject.isReal()) {
					throw new RuntimeException("The task doesn't contain the 'semantic-type' field "
							+ "but when transmitting real data the semantic type needs to be defined. \n" + "task: "
							+ task.getAttributes().toString() + "\nfield to be sent: " + sedeObject.toString());
				} else {
					semType = sedeObject.getType();
				}
			}
			String dataPutUrl = host + "/put/" + executionId + "/" + fieldname;
			if(unavailable) {
				dataPutUrl += "/unavailable";
			} else {
				dataPutUrl += "/" + semType;
			}
			BasicClientRequest clientRequest = new HttpURLConnectionClientRequest(dataPutUrl);
			return clientRequest;
		}
	}

	static class FinishOverHttp extends FinishProcedure {

		@Override
		public BasicClientRequest getFinishFlagRequest(Task task) {
			Map<String, String> contactInfo = (Map<String, String>) task.getAttributes().get("contact-info");
			String host = contactInfo.get("host-address");
			String fieldname = (String) task.getAttributes().get("fieldname");
			String semType = PrimitiveDataField.PrimitiveType.Bool.name();
			String executionId = task.getExecution().getExecutionId();
			boolean failed = false;
			return createPutDataRequest(host, fieldname, semType, executionId, failed);
		}
	}

	static class SendGraphOverHttp extends SendGraphProcedure {

		@Override
		public BasicClientRequest getExecRequest(Task task) {
			Map<String, String> contactInfo = (Map<String, String>) task.getAttributes().get("contact-info");
			String host = contactInfo.get("host-address");
			if (host == null) {
				throw new RuntimeException(
						"The task doesn't contain all necessary fields: " + task.getAttributes().toString());
			}
			String executeGraphUrl = host + "/execute";
			BasicClientRequest clientRequest = new HttpURLConnectionClientRequest(executeGraphUrl);
			return clientRequest;
		}

		@Override
		public void handleInterrupt(Task task) {
			Map<String, String> contactInfo = (Map<String, String>) task.getAttributes().get("contact-info");
			String host = contactInfo.get("host-address");
			String interruptUrl = host + "/interrupt/" + task.getExecution().getExecutionId();
			BasicClientRequest interruptRequest = new HttpURLConnectionClientRequest(interruptUrl);
			logger.debug("Interrupting remote execution: {}", contactInfo);
			String response = interruptRequest.send("");
			if(!response.isEmpty()) {
				logger.error("Interrupting remote execution with an http request failed. The response is not empty: {}\nexec Id: {}\ncontact info: {}", response, task.getExecution().getExecutionId(), contactInfo.toString());
			}
		}
	}

	class PutDataHandler implements HTTPServerResponse {

		@Override
		public void receive(Optional<String> url, InputStream payload, OutputStream answer) {
			String response;
			try {
				if (!url.isPresent()) {
					throw new RuntimeException("Put data needs to specify URL with fieldname and execution handle");
				}

				Matcher matcher = PUT_DATA_URL_PATTERN.matcher(url.get());
				if(!matcher.matches()){
					// pattern: put/(?<executionId>\w+)/(?<fieldname>\w+)/(?<semtype>\w+)
					throw new RuntimeException("URL syntax error: " + url.get());
				}
				String execId = matcher.group("executionId");
				String fieldname = matcher.group("fieldname");
				String semanticType = matcher.group("semtype");

				DataPutRequest putRequest;
				if(semanticType.equals("unavailable")) {
					putRequest = DataPutRequest.unavailableData(execId, fieldname);
				} else{
					SEDEObject inputObject = SemanticStreamer.readFrom(payload, semanticType);
					putRequest = new DataPutRequest(execId, fieldname, inputObject);
				}
				ExecutorHttpServer.this.put(putRequest);
				response = "";
			} catch (Exception ex) {
				logger.error("Error at put request: {}\n", url.get(), ex);
				response = ex.getMessage();
			}
			Streams.OutWriteString(answer, response, true);
		}
	}

	class ExecuteGraphHandler extends StringServerResponse {
		@Override
		public String receive(String payload) {
			try {
				ExecRequest request = new ExecRequest();
				request.fromJsonString(payload);
				exec(request);
				return "";
			} catch (Exception ex) {
				return ex.getMessage();
			}
		}
	}
	class InterruptHandler implements HTTPServerResponse {
		@Override
		public void receive(Optional<String> url, InputStream payload, OutputStream answer) {
			try {
				if (!url.isPresent()) {
					throw new RuntimeException("Put data needs to specify URL with fieldname and execution handle");
				}
				Streams.InReadString(payload);
				Matcher matcher = INTERRUPT_URL_PATTERN.matcher(url.get());
				if(!matcher.matches()){
					// pattern: /interrupt/(?<executionId>\\w+)
					throw new RuntimeException("URL syntax error: " + url.get());
				}
				String executionId = matcher.group("executionId");
				ExecutorHttpServer.this.interrupt(executionId);
				answer.close();
			} catch (Exception ex) {
				Streams.OutWriteString(answer, ex.getMessage(), true);
			}
		}
	}

}
