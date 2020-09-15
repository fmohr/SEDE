package ai.services.channels;

import ai.services.channels.local.InProcessExecutorChannel;
import ai.services.executor.Executor;
import ai.services.executor.local.LocalExecutorInstanceRegistry;
import ai.services.IServiceRef;
import ai.services.exec.IExecutorContactInfo;

public class StdLocalChannelService implements ChannelService{

    private final LocalExecutorInstanceRegistry localExecutorInstanceRegistry;

    private final StdFSServiceStorageChannel fsServiceStorageChannel;

    public StdLocalChannelService(String serviceStoreLocation) {
        this(LocalExecutorInstanceRegistry.INSTANCE, serviceStoreLocation);
    }

    public StdLocalChannelService(LocalExecutorInstanceRegistry localExecutorInstanceRegistry, String serviceStoreLocation) {
        this.localExecutorInstanceRegistry = localExecutorInstanceRegistry;
        this.fsServiceStorageChannel = new StdFSServiceStorageChannel(serviceStoreLocation);;
    }

    @Override
    public ExecutorCommChannel interExecutorCommChannel(IExecutorContactInfo contactInfo) {
        Executor executor = localExecutorInstanceRegistry.get(contactInfo);
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
