package de.upb.sede.edd.deploy.deplengine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.edd.EDD;
import de.upb.sede.edd.deploy.AscribedService;
import de.upb.sede.requests.deploy.EDDRegistration;
import de.upb.sede.requests.deploy.ExecutorDemandFulfillment;
import de.upb.sede.requests.deploy.ExecutorDemandRequest;
import de.upb.sede.util.URIMod;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class DeplEngine {

    private final static Logger logger = LoggerFactory.getLogger(DeplEngine.class);

    public abstract String getName();

    public abstract void addServices(List<AscribedService> services);

    public abstract void prepareDeployment(boolean update);

    public abstract List<InstallationReport> getCurrentState();

    public abstract void removeServices(List<AscribedService> services);

    public abstract ExecutorDemandFulfillment demand(ExecutorDemandRequest request);

    public void registerAll(EDD deamon) {
        List<InstallationReport> currentState = getCurrentState();
        Set<String> sources = new HashSet<>();
        currentState.forEach(state -> sources.add(state.getSpecSource()));
        logger.info("Registering to all sources: {}", sources);
        for(String gwAddress : sources) {
            List<String> services = currentState
                .stream()
                .filter(state -> state.getSpecSource().equals(gwAddress))
                .flatMap(state -> state.getIncludedServices().stream())
                .collect(Collectors.toList());

            EDDRegistration registration = new EDDRegistration(
                deamon.getInfo().getIdentifier(),
                deamon.getInfo().getMachineAddress().get(),
                gwAddress, services);

            String url = null;
            url = URIMod.setHTTPScheme(gwAddress);
            url = URIMod.addPath(url, "registerEDD");


            ObjectMapper mapper = new ObjectMapper();
            String requestBodyContent;
            try {
                requestBodyContent = mapper.writeValueAsString(registration);
            } catch (JsonProcessingException e) {
                // this shouldn't be reachable.
                throw new IllegalArgumentException(e);
            }

            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");

            RequestBody body = RequestBody.create(mediaType, requestBodyContent);

            Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

            logger.info("Registering to {} with registeration: {}.", gwAddress, registration);

            Response response;
            String responseBody;
            try {
                response = client.newCall(request).execute();
                responseBody = response.body().string();
            } catch (IOException e) {
                throw new UncheckedIOException("Error connecting to " + url + ".", e);
            }

            if (!response.isSuccessful() || !responseBody.isEmpty()) {
                throw new RuntimeException("Coulnd't register to " + url +
                    ", returned msg: " +
                    responseBody);
            }
        }
    }
}
