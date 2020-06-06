package de.upb.sede.gateway.edd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.exec.IExecutorHandle;
import de.upb.sede.gateway.OnDemandExecutorSupplier;
import de.upb.sede.requests.deploy.EDDRegistration;
import de.upb.sede.requests.deploy.ExecutorDemandFulfillment;
import de.upb.sede.requests.deploy.ExecutorDemandRequest;
import de.upb.sede.util.URIMod;
import de.upb.sede.util.UnmodifiableURI;
import okhttp3.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.upb.sede.requests.deploy.ExecutorDemandRequest.SatMode.*;


public class EDDExecutorSupplier implements OnDemandExecutorSupplier {

    private final EDDRegistration registration;

    public EDDExecutorSupplier(EDDRegistration registration) {
        this.registration = Objects.requireNonNull(registration);
    }

    public boolean isSupported(String service) {
        return registration.getOfferedServices().contains(Objects.requireNonNull(service));
    }

    @Override
    public List<IExecutorHandle> supply(String service) {
        List<IExecutorHandle> handles = supplyList(Collections.singletonList(service));
        List<IExecutorHandle> supportingExecutors = handles.stream()
            .filter(h -> h.getCapabilities().getServices().contains(service))
            .collect(Collectors.toList());
        if(!supportingExecutors.isEmpty()) {
            return supportingExecutors;
        }
        throw new UnsuppliedExecutorException("No executor can be supplied for service '" + service
            + "' by " + getEDDDisplayName());
    }


    public List<IExecutorHandle> supplyList(List<String> services) {
        /*
         * Request a handle from the edd server using the demand request.
         */
        String url = registration.getAddress();
        url = URIMod.setHTTPScheme(url);
        url = URIMod.addPath(url, "demandUnit");


        ExecutorDemandRequest demandRequest = new ExecutorDemandRequest(services);
        UnmodifiableURI namespace = registration.getNamespaceURI();
        demandRequest.setServiceNamespace(namespace);
        demandRequest.setModi(Spawn, Reuse, AllAvailable);
        ObjectMapper mapper = new ObjectMapper();
        String requestBodyContent;
        try {
            requestBodyContent = mapper.writeValueAsString(demandRequest);
        } catch (JsonProcessingException e) {
            // this shouldn't be reached.
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

        Response response;
        String responseBody;
        try {
            response = client.newCall(request).execute();
            responseBody = response.body().string();
        } catch (IOException e) {
            throw new UnsuppliedExecutorException("Error connecting to edd " + registration.getEddId()
                + " with address: " + registration.getAddress(), e);
        }


        if(response.isSuccessful()) {
            try {
                ExecutorDemandFulfillment fulfillment = mapper.readValue(responseBody, ExecutorDemandFulfillment.class);
                return fulfillment.getExecutors().stream()
                    .map(IExecutorHandle::fromRegistration)
                    .collect(Collectors.toList());
            } catch (IOException e) {
                throw new UnsuppliedExecutorException(getEDDDisplayName()
                    + ", returned body of executor fulfillment cannot be parsed. Body:\n"
                    + responseBody, e);
            }
        } else {
            Exception serverSideError = new Exception("Edd return message: \n" + responseBody);
            throw new UnsuppliedExecutorException(
                getEDDDisplayName()
                + ", doesn't fulfill demand for the services it registered for: " + services,
                serverSideError);
        }
    }

    public String getEDDDisplayName() {
        return "Edd id=" + registration.getEddId()
            + ", address=" + registration.getAddress();
    }

    @Override
    public List<String> supportedServices() {
        return registration.getOfferedServices();
    }

    @Override
    public String getIdentifier() {
        return registration.getEddId();
    }

    @Override
    public List<IExecutorHandle> allHandles() {
        return supplyList(Collections.emptyList());
    }

    @Override
    @Deprecated
    public Optional<IExecutorHandle> getHandle(String executorId) {
        try {
            return supplyList(Collections.emptyList()).stream()
                .filter(handle -> handle.getQualifier().equals(executorId))
                .findAny();
        } catch (UnsuppliedExecutorException ex) {
            //TODO log the exception
            return Optional.empty();
        }
    }

}
