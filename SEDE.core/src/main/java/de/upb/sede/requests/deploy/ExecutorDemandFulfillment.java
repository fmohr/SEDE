package de.upb.sede.requests.deploy;

import de.upb.sede.requests.ExecutorRegistration;

import java.util.ArrayList;
import java.util.List;

public class ExecutorDemandFulfillment {

    List<ExecutorRegistration> executors = new ArrayList<>();

    public ExecutorDemandFulfillment(List<ExecutorRegistration> executors) {
        this.executors = executors;
    }

    public List<ExecutorRegistration> getExecutors() {
        return executors;
    }

    public void setExecutors(List<ExecutorRegistration> executors) {
        this.executors = executors;
    }
}
