package ai.services.channels;

import ai.services.executor.Executor;
import ai.services.executor.local.LocalExecutorRegistry;
import de.upb.sede.IServiceRef;
import de.upb.sede.exec.IExecutorContactInfo;

public class StdLocalChannelService implements ChannelService{

    private final LocalExecutorRegistry localExecutorRegistry;

    private final StdFSServiceStorageChannel fsServiceStorageChannel;

    public StdLocalChannelService(String serviceStoreLocation) {
        this(LocalExecutorRegistry.INSTANCE, serviceStoreLocation);
    }

    public StdLocalChannelService(LocalExecutorRegistry localExecutorRegistry, String serviceStoreLocation) {
        this.localExecutorRegistry = localExecutorRegistry;
        this.fsServiceStorageChannel = new StdFSServiceStorageChannel(serviceStoreLocation);;
    }

    @Override
    public ExecutorCommChannel interExecutorCommChannel(IExecutorContactInfo contactInfo) {
        Executor executor = localExecutorRegistry.get(contactInfo);
        if(executor != null) {
            return new InProcessExecutorChannel(executor);
        } else {
            return new StdRESTExecutorCommChannel(contactInfo);
        }
    }

    @Override
    public ServiceStorageChannel serviceStorageChannel(IServiceRef serviceRef) {
        return fsServiceStorageChannel;
    }
}
