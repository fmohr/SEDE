package de.upb.sede.exec;

import de.upb.sede.procedure.Procedure;
import de.upb.sede.procedure.TransmitDataProcedure;
import de.upb.sede.util.Streams;
import de.upb.sede.webinterfaces.client.BasicClientRequest;
import de.upb.sede.webinterfaces.client.HttpURLConnectionClientRequest;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class ProxySetup {


	private static final Logger logger = LoggerFactory.getLogger(ProxySetup.class);

	private final Map<String, String> fetchedMapping = new HashMap<>();
	private Long lastMappingFetch = Long.MIN_VALUE;
	private final Long mappingTTL = 5000L;
	private final String proxyAddress;

	private static String PROXY_ADDRESS = null;

	static {
		Optional<String> proxyAddressOpt = Optional.ofNullable(System.getenv("PROXY_ADDRESS"));
		proxyAddressOpt.ifPresent(s -> PROXY_ADDRESS = s);
	}

	public static void setGlobalProxyAddress(String proxyAddress){
		ProxySetup.PROXY_ADDRESS = proxyAddress;
	}

	public ProxySetup(Executor executor, String proxyAddress) {
		this.proxyAddress = proxyAddress;

		final Supplier<Procedure> defaultTransmitProcedure =
				executor.getWorkerPool().getBoundedProducer("TransmitData");

		executor.getWorkerPool().bindProcedure("TransmitData",
				() -> new TransmitDataOverLocalNetwork(defaultTransmitProcedure));

	}

	public static void enablePlugin(HttpExecutor executor, Executor basis) {
		if(PROXY_ADDRESS == null) {
			logger.info("PROXY_ADDRESS was not set.");
			return;
		}
		/*
		 * Setup proxy server:
		 */
		String proxyAddress = PROXY_ADDRESS;
		String executorId;
		String executorLocalAddress;
		try {
			executorId = URLEncoder.encode(executor.getExecutorId(), "UTF-8");
			executorLocalAddress = URLEncoder.encode(executor.getHostAddress(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e); // This cant occur anyway
		}

		String signupUrl = Paths.get(proxyAddress, "signup", executorId, executorLocalAddress).toString();
		logger.info("Signing up to proxy server at {}.", proxyAddress);
		HttpURLConnectionClientRequest signUpRequest = new HttpURLConnectionClientRequest(signupUrl);
		try{
			signUpRequest.receive();
		} catch(Exception ex) {
			logger.error("Error signing-up at proxy server at {}.", proxyAddress, ex);
			logger.warn("Ignoring proxy setup.");
			return;
		}
		logger.info("Successfully signed up at proxy server. Changing executor address to proxies address.");
		executor.setHostAddress(proxyAddress);
		executor.registerToEveryGateway();

		new ProxySetup(basis, proxyAddress);
	}

	private synchronized Optional<String> getLocalExecutorAddress(String executorId) {
		Long currentTime = System.currentTimeMillis();
		if(currentTime - lastMappingFetch > mappingTTL) {
			fetchMappings();
			lastMappingFetch = currentTime;
		}
		if(fetchedMapping.containsKey(executorId)) {
			return Optional.of(fetchedMapping.get(executorId));
		} else {
			return Optional.empty();
		}
	}

	private synchronized void fetchMappings() {
		String getMappingUrl = Paths.get(proxyAddress, "mapping").toString();
		logger.debug("Fetching proxy mappings from {}.", getMappingUrl);
		try (HttpURLConnectionClientRequest signUpRequest = new HttpURLConnectionClientRequest(getMappingUrl)) {
			String jsonMap = Streams.InReadString(signUpRequest.receive());
			JSONParser parser = new JSONParser();
			Map<String, String> proxyMapping = (Map<String, String>) parser.parse(jsonMap);
			fetchedMapping.putAll(proxyMapping);
		} catch (IOException | ParseException e) {
			logger.error("Error while fetching mappings from proxy server with url {}.", getMappingUrl, e);
		}
	}

	private class  TransmitDataOverLocalNetwork extends TransmitDataProcedure {

		private final Supplier<Procedure> defaultProcedure;

		TransmitDataOverLocalNetwork(Supplier<Procedure> defaultProcedure) {
			this.defaultProcedure = defaultProcedure;
		}

		@Override
		public BasicClientRequest getPutDataRequest(Task task, boolean unavailable) {
			Map<String, String> contactInfo = task.getAttribute("contact-info");
			String targetExecutorId = contactInfo.get("id");
			Optional<String> localNetworkAddress = getLocalExecutorAddress(targetExecutorId);
			if(localNetworkAddress.isPresent()){
				logger.debug("Executor `{}` is within the local network. Replace the host-address to `{}` to transmit data directly.", targetExecutorId, localNetworkAddress.get());
				contactInfo.put("host-address", localNetworkAddress.get());
			}
			// forward call to default transmit data implementation
			return ((TransmitDataProcedure) defaultProcedure.get()).getPutDataRequest(task, unavailable);
		}
	}

}
