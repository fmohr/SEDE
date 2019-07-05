package de.upb.sede.edd.deploy.deplengine;

import java.util.List;

public class InstallationState {

    public enum State {
        Init(false), Success(true), Error(true);

        boolean finished;

        State(boolean finished) {
            this.finished = finished;
        }

        public boolean isFinished() {
            return finished;
        }
    }

    private String serviceCollectionName;

    private List<String> includedServices;

    private List<String> requestedServices;

    private String specSource;

    private State state = State.Init;

    private String out, err;

    public InstallationState(String serviceCollectionName, List<String> includedServices, List<String> requestedServices, String specSource, State state, String out, String err) {
        this.serviceCollectionName = serviceCollectionName;
        this.includedServices = includedServices;
        this.requestedServices = requestedServices;
        this.specSource = specSource;
        this.state = state;
        this.out = out;
        this.err = err;
    }

    public InstallationState() {
    }

    public String getServiceCollectionName() {
        return serviceCollectionName;
    }

    public void setServiceCollectionName(String serviceCollectionName) {
        this.serviceCollectionName = serviceCollectionName;
    }

    public List<String> getIncludedServices() {
        return includedServices;
    }

    public void setIncludedServices(List<String> includedServices) {
        this.includedServices = includedServices;
    }

    public List<String> getRequestedServices() {
        return requestedServices;
    }

    public void setRequestedServices(List<String> requestedServices) {
        this.requestedServices = requestedServices;
    }

    public String getSpecSource() {
        return specSource;
    }

    public void setSpecSource(String specSource) {
        this.specSource = specSource;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }
}
