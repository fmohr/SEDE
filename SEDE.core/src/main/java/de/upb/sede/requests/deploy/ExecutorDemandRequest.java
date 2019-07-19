package de.upb.sede.requests.deploy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ExecutorDemandRequest {

    private List<String> services;

    private List<SatMode> demandSatModes = new ArrayList<>(Arrays.asList(SatMode.Reuse, SatMode.Spawn));

    public enum SatMode {
        Reuse, // This mode allows to reuse already spawned executors to be returned.
        Spawn,  // This mode allows deployment of new executors to satisfy the demand.
        AllAvailable // This mode instructs to return all running executors.
    }

    private String serviceNamespace;

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
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public List<SatMode> getDemandSatModes() {
        return demandSatModes;
    }

    public void setDemandSatModes(List<SatMode> demandSatModes) {
        this.demandSatModes = demandSatModes;
    }

    public boolean isSet(SatMode  mode) {
        return demandSatModes.contains(mode);
    }

    public void setModi(SatMode... demandSatModes) {
        this.demandSatModes.clear();
        Collections.addAll(this.demandSatModes, demandSatModes);
    }

    public String getServiceNamespace() {
        return serviceNamespace;
    }

    public void setServiceNamespace(String serviceNamespace) {
        this.serviceNamespace = serviceNamespace;
    }

    @Override
    public String toString() {
        return "ExecutorDemandRequest{" +
            "services=" + services +
            ", demandSatModes=" + demandSatModes +
            '}';
    }
}
