package ai.services.channels;

import de.upb.sede.IServiceRef;
import de.upb.sede.exec.ExecutorConfiguration;
import de.upb.sede.exec.IExecutorContactInfo;
import de.upb.sede.util.ExpiringCache;
import de.upb.sede.util.NullableCache;
import de.upb.sede.util.SystemSettingLookup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class StdChannelService implements ChannelService {

    private static final String EXPIRATION_TIME_STR = new SystemSettingLookup("60",
        "ai.services.channels.StdChannelService.channelExpirationTime",
        "CHANNEL_EXPIRATION_TIME").lookup();

    private static final int EXPIRATION_TIME_SECONDS = Integer.parseInt(EXPIRATION_TIME_STR);

    private final StdFSServiceStorageChannel fsServiceStorageChannel;

    private final Map<IExecutorContactInfo, ExpiringCache<StdRESTExecutorCommChannel>> eccCache = new ConcurrentHashMap<>();

    public StdChannelService(ExecutorConfiguration execConfig) {
        this.fsServiceStorageChannel = new StdFSServiceStorageChannel(execConfig.getServiceStoreLocation());
    }

    @Override
    public ExecutorCommChannel interExecutorCommChannel(IExecutorContactInfo contactInfo) {
        if(!contactInfo.isReachable()) {
            throw new IllegalArgumentException("The given contact info is marked non reachable: " + contactInfo);
        }
        ExpiringCache<StdRESTExecutorCommChannel> cache = eccCache.computeIfAbsent(contactInfo,
            ci -> new ExpiringCache<>(EXPIRATION_TIME_SECONDS, TimeUnit.SECONDS,
                new NullableCache<>(new StdRESTExecutorCommChannel(contactInfo))));

        synchronized (this) {
            if(cache.isExpired()) {
                StdRESTExecutorCommChannel stdRESTExecutorCommChannel = cache.forceAccess();
                stdRESTExecutorCommChannel.shutdownQuietly();
                cache.set(new StdRESTExecutorCommChannel(contactInfo));
            }
            cache.prolong();
            return cache.get();
        }
    }

    @Override
    public ServiceStorageChannel serviceStorageChannel(IServiceRef serviceRef) {
        return fsServiceStorageChannel;
    }





}
