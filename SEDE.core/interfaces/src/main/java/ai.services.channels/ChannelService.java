package ai.services.channels;

import de.upb.sede.IServiceRef;
import de.upb.sede.exec.IExecutorContactInfo;

public interface ChannelService {

    ExecutorCommChannel interExecutorCommChannel(IExecutorContactInfo contactInfo);

    ServiceStorageChannel serviceStorageChannel(IServiceRef serviceRef);

    default ServiceStorageChannel serviceStorageChannel(String serviceQualifier) {
        return serviceStorageChannel(IServiceRef.of(null, serviceQualifier));
    }

}
