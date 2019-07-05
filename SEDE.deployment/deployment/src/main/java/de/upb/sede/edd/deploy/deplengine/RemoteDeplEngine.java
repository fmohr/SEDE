package de.upb.sede.edd.deploy.deplengine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.edd.deploy.AscribedService;
import de.upb.sede.edd.deploy.DeploymentException;
import de.upb.sede.util.DynTypeObject;
import de.upb.sede.util.Uncheck;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public void addServices(List<AscribedService> services) {
        OkHttpClient client = new OkHttpClient();

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
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        Map<String, Boolean> requestContent = new HashMap<>();
        requestContent.put("update", update);
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
    public List<InstallationState> getCurrentState() {
        OkHttpClient client = new OkHttpClient();

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
        JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, HashMap.class);
        List<HashMap<String, Object>> remoteStateList;
        try {
            remoteStateList = mapper.readValue(remoteState, type);
        } catch (IOException e) {
            throw Uncheck.throwAsUncheckedException(e);
        }
        return remoteStateList.stream().map(map -> {
            InstallationState state = new InstallationState();
            state.setServiceCollectionName((String) map.get("serviceCollectionName"));
            state.setIncludedServices((List<String>) map.get("includedServices"));
            state.setRequestedServices((List<String>) map.get("requestedServices"));
            if((Boolean)map.get("success")) {
                state.setState(InstallationState.State.Success);
            }
            state.setOut((String) map.get("out"));
            state.setErr((String) map.get("errOut"));
            return state;
        }).collect(Collectors.toList());
    }

    public String getAddress() {
        return address;
    }

    /*
      @JsonProperty("serviceCollectionName")
      private String serviceCollectionName = null;

      @JsonProperty("includedServices")
      @Valid
      private List<String> includedServices = null;

      @JsonProperty("requestedServices")
      @Valid
      private List<String> requestedServices = null;

      @JsonProperty("success")
      private Boolean success = null;

      @JsonProperty("out")
      private String out = null;

        @JsonProperty("errOut")
        private String errOut = null;

        @JsonProperty("machine")
        private String machine = null;
     */



    @Override
    public void removeServices(List<AscribedService> services) {

    }
}
