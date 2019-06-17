package de.upb.sede.edd;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class ExecutorHandle {

    private final List<String> services;

    private final ExecutorState state;



    @JsonCreator
    public ExecutorHandle(List<String> services, ExecutorState state) {
        this.services = services;
        this.state = state;
    }

    public List<String> getServices() {
        return services;
    }

    public ExecutorState getState() {
        return state;
    }

}
