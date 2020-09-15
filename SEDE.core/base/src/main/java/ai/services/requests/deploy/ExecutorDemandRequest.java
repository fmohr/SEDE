package ai.services.requests.deploy;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import ai.services.util.OptionalField;
import ai.services.util.UnmodifiableURI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ExecutorDemandRequest {

    @JsonProperty("services")
    private List<String> services;

    @JsonProperty("modi")
    private List<SatMode> demandSatModes = new ArrayList<>(Arrays.asList(SatMode.Reuse, SatMode.Spawn));

    public enum SatMode {
        Reuse, // This mode allows to reuse already spawned executors to be returned.
        Spawn,  // This mode allows deployment of new executors to satisfy the demand.
        AllAvailable // This mode instructs to return all running executors.
    }

    @JsonIgnore
    private OptionalField<UnmodifiableURI> serviceNamespace = OptionalField.empty();

    public ExecutorDemandRequest() {
    }

    public ExecutorDemandRequest(List<String> services) {
        this.services = services;
    }

    public ExecutorDemandRequest(List<String> services, List<SatMode> demandSatModes) {
        this.services = services;
        this.demandSatModes = demandSatModes;
    }

    public List<String> getServices() {
        if(serviceNamespace.isPresent()) {
            UnmodifiableURI namespace = serviceNamespace.get();
            return services.stream()
                .map(service -> namespace.mod().fragment(service))
                .map(UnmodifiableURI::buildString)
                .collect(Collectors.toList());
        } else {
            return services;
        }
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    @JsonGetter("modi")
    public List<SatMode> getDemandSatModes() {
        return demandSatModes;
    }

    @JsonSetter("modi")
    public void setDemandSatModes(List<SatMode> demandSatModes) {
        this.demandSatModes = demandSatModes;
    }

    @JsonIgnore
    public boolean isSet(SatMode  mode) {
        return demandSatModes.contains(mode);
    }


    @JsonIgnore
    public void setModi(SatMode... demandSatModes) {
        this.demandSatModes.clear();
        Collections.addAll(this.demandSatModes, demandSatModes);
    }

    @JsonIgnore
    public UnmodifiableURI getServiceNamespace() {
        return serviceNamespace.orElse(null);
    }

    @JsonIgnore
    public void setServiceNamespace(UnmodifiableURI serviceNamespace) {
        this.serviceNamespace = OptionalField.ofNullable(serviceNamespace);
    }

    @Override
    public String toString() {
        return "ExecutorDemandRequest{" +
            "services=" + services +
            ", demandSatModes=" + demandSatModes +
            '}';
    }
}
