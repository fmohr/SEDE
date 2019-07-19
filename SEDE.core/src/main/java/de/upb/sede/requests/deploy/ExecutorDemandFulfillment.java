package de.upb.sede.requests.deploy;

import de.upb.sede.requests.ExecutorRegistration;

import java.util.ArrayList;
import java.util.List;

public class ExecutorDemandFulfillment {

    List<ExecutorRegistration> executors = new ArrayList<>();

    public ExecutorDemandFulfillment() {
    }

    public ExecutorDemandFulfillment(List<ExecutorRegistration> executors) {
        this.executors = executors;
    }

    public List<ExecutorRegistration> getExecutors() {
        return executors;
    }

    public void setExecutors(List<ExecutorRegistration> executors) {
        this.executors = executors;
    }

    public void add(ExecutorDemandFulfillment other) {
        for(ExecutorRegistration reg : other.executors) {
            if(executors.stream().noneMatch(reg1 -> reg1.getId().equals(reg.getId()))) {
                executors.add(reg);
            }
        }
    }
}
