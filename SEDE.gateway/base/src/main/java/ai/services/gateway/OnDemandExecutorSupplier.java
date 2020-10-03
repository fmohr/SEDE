package ai.services.gateway;

import ai.services.exec.IExecutorHandle;

import java.util.List;
import java.util.Optional;


public interface OnDemandExecutorSupplier {

    boolean isSupported(String service);

    List<IExecutorHandle> supplyWithService(String service);

    Optional<IExecutorHandle> supplyWithExecutorId(String executorId);

    List<String> supportedServices();

    String getIdentifier();

}
