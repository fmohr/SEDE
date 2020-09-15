package ai.services.channels;

import ai.services.IServiceRef;
import ai.services.exec.IExecutorContactInfo;

public interface ChannelService {

    ExecutorCommChannel interExecutorCommChannel(IExecutorContactInfo contactInfo);

    ServiceStorageChannel serviceStorageChannel(IServiceRef serviceRef);

    default ServiceStorageChannel serviceStorageChannel(String serviceQualifier) {
        return serviceStorageChannel(IServiceRef.of(null, serviceQualifier));
    }

}
