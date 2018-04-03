package de.upb.sede.exec;

import java.util.ArrayList;

public abstract class RequestPool {
Iterable<ExecutionRequest> executionRequest = new ArrayList<>();

public abstract Task getNextTask();

public abstract void enqueueRequest(ExecutionRequest request);
}
