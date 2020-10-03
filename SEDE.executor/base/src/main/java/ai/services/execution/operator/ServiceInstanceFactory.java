package ai.services.execution.operator;

import ai.services.composition.types.IServiceInstanceType;
import ai.services.core.ServiceInstance;

import java.util.UUID;

public class ServiceInstanceFactory {

    private final String executorId;

    public ServiceInstanceFactory(String executorId) {
        this.executorId = executorId;
    }

    /**
     * Create new new service instance handle with the given service instance.
     *
     * @return a new ServiceInstanceHandle
     */
    public ServiceInstance createServiceInstanceHandle(IServiceInstanceType serviceInstanceType, Object newServiceInstance) {
        String serviceInstanceId = UUID.randomUUID().toString();
        String classpath = serviceInstanceType.getTypeQualifier();
        return new ServiceInstance(executorId, classpath, serviceInstanceId, newServiceInstance);
    }

}
