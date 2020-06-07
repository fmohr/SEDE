package de.upb.sede.gateway;

import de.upb.sede.exec.ExecutorHandle;
import de.upb.sede.exec.IExecutorHandle;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;


public interface OnDemandExecutorSupplier {

    boolean isSupported(String service);

    List<IExecutorHandle> supplyWithService(String service);

    Optional<IExecutorHandle> supplyWithExecutorId(String executorId);

    List<String> supportedServices();

    String getIdentifier();

}
