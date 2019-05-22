package de.upb.sede.edd;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class ExecutorHandle {

    public final List<String> services;

    public final ExecutorState state;

    @JsonCreator
    public ExecutorHandle(List<String> services, ExecutorState state) {
        this.services = services;
        this.state = state;
    }
}
