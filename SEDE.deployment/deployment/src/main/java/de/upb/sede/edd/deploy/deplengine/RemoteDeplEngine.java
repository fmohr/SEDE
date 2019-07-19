package de.upb.sede.edd.deploy.deplengine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.edd.EDD;
import de.upb.sede.edd.deploy.AscribedService;
import de.upb.sede.edd.deploy.DeploymentException;
import de.upb.sede.requests.deploy.EDDRegistration;
import de.upb.sede.requests.deploy.ExecutorDemandFulfillment;
import de.upb.sede.requests.deploy.ExecutorDemandRequest;
import de.upb.sede.util.URIMod;
import de.upb.sede.util.Uncheck;
import okhttp3.*;
import org.eclipse.jgit.transport.Daemon;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static de.upb.sede.requests.deploy.ExecutorDemandRequest.SatMode.*;

public class RemoteDeplEngine extends DeplEngine{

    private String name;
    private String address;
    private ObjectMapper mapper = new ObjectMapper();

    public RemoteDeplEngine(String name, String address) {
        this.name = name;
        if(!address.startsWith("http://")) {
            address = "http://" + address;
        }
        if(address.endsWith("/")) {
            address = address.substring(0, address.length()-1);
        }
        this.address = address;
    }


    @Override
    public String getName() {
        return name;
    }

    private OkHttpClient getClient() {
        return new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.MINUTES)
            .build();
    }

    @Override
    public void addServices(List<AscribedService> services) {
        OkHttpClient client = getClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = null;
        try {
            body = RequestBody.create(mediaType,
                mapper.writeValueAsString(services));
        } catch (JsonProcessingException e) {
            throw Uncheck.throwAsUncheckedException(e);
        }
        Request request = new Request.Builder()
            .url(address + "/requireService")
            .post(body)

            .addHeader("Content-Type", "application/json")
            .build();

        try {
            Response response = client.newCall(request).execute();
            assertSuccess(response);
        } catch (IOException e) {
            throw Uncheck.throwAsUncheckedException(e);
        }
    }

    private void assertSuccess(Response response) throws IOException {
        if(!response.isSuccessful()) {
            throw new DeploymentException("Remote deployment engine error code " + response.code() + " with message: \n" + response.body().string());
        }
    }

    @Override
    public void prepareDeployment(boolean update) {
        OkHttpClient client = getClient();

        MediaType mediaType = MediaType.parse("application/json");


        Map<String, Boolean> requestContent = new HashMap<>();
        requestContent.put("update", update);
        requestContent.put("register", false);
        String bodyContent;
        try {
            bodyContent = mapper.writeValueAsString(requestContent);
        } catch (JsonProcessingException e) {
            throw Uncheck.throwAsUncheckedException(e);
        }
        RequestBody body = RequestBody.create(mediaType, bodyContent);
        Request request = new Request.Builder()
            .url(address + "/prepareDeployment")
            .post(body)
            .addHeader("Content-Type", "application/json")
            .build();

        try {
            Response response = client.newCall(request).execute();
            assertSuccess(response);
        } catch (IOException e) {
            throw Uncheck.throwAsUncheckedException(e);
        }
    }

    @Override
    public List<InstallationReport> getCurrentState() {
        OkHttpClient client = getClient();

        Request request = new Request.Builder()
            .url(address + "/preparations")
            .get()
            .build();

        String remoteState;
        try {
            Response response;
            response = client.newCall(request).execute();
            assertSuccess(response);
            remoteState = response.body().string();
        } catch (IOException e) {
            throw Uncheck.throwAsUncheckedException(e);
        }
        JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, InstallationReport.class);
        List<InstallationReport> remoteStateList;
        try {
            remoteStateList = mapper.readValue(remoteState, type);
        } catch (IOException e) {
            throw Uncheck.throwAsUncheckedException(e);
        }
        return remoteStateList;
//        return remoteStateList.stream().map(map -> {
//            InstallationReport state = new InstallationReport();
//            state.setServiceCollectionName((String) map.get("serviceCollectionName"));
//            state.setIncludedServices((List<String>) map.get("includedServices"));
//            state.setRequestedServices((List<String>) map.get("requestedServices"));
//            if((Boolean)map.get("success")) {
//                state.setState(InstallationState.Success);
//            }
//            state.setOut((String) map.get("out"));
//            state.setErr((String) map.get("errOut"));
//            return state;
//        }).collect(Collectors.toList());
    }

    public String getAddress() {
        return address;
    }



    @Override
    public void removeServices(List<AscribedService> services) {
    }

    @Override
    public ExecutorDemandFulfillment demand(ExecutorDemandRequest demandRequest) {
        /*
         * Request a handle from the edd server using the demand request.
         */
        String url = address;
        url = URIMod.setHTTPScheme(url);
        url = URIMod.addPath(url, "demandUnit");


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
            throw new RuntimeException("Error connecting to edd " + name
                + " with address: " + address, e);
        }


        if(response.isSuccessful()) {
            try {
                ExecutorDemandFulfillment fulfillment = mapper.readValue(responseBody, ExecutorDemandFulfillment.class);
                return fulfillment;
            } catch (IOException e) {
                throw new RuntimeException("edd " + name
                    + " with address: " + address
                    + ", returned body of executor fulfillment cannot be parsed. Body:\n"
                    + responseBody);
            }
        } else {
            Exception serverSideError = new Exception("Edd return message: \n" + responseBody);
            throw new RuntimeException(
                "edd " + name
                + " with address: " + address
                    + ", doesn't fulfill demand for the services it registered for: " + demandRequest.getServices(),
                serverSideError);
        }
    }

}
