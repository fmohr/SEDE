package de.upb.sede.gateway;

import de.upb.sede.exec.ExecutorHandle;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;


public interface OnDemandExecutorSupplier {

    boolean isSupported(String service);

    ExecutorHandle supply(String service);

    List<String> supportedServices();

    String getIdentifier();

    List<ExecutorHandle> allHandles();

    Optional<ExecutorHandle> getHandle(String executorId);

}
