package de.upb.sede.gateway;

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

    Optional<ExecutorHandle> getHandle(String executorOrGroupId);

}
