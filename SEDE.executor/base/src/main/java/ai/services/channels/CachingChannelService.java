package ai.services.channels;

import ai.services.IServiceRef;
import ai.services.exec.IExecutorContactInfo;
import ai.services.util.ExpiringCache;
import ai.services.util.NullableCache;
import ai.services.util.SystemSettingLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class CachingChannelService implements ChannelService {

    private static final Logger logger = LoggerFactory.getLogger(CachingChannelService.class);

    private static final String EXPIRATION_TIME_STR = new SystemSettingLookup("60",
        "ai.services.channels.StdChannelService.channelExpirationTime",
        "CHANNEL_EXPIRATION_TIME").lookup();

    private static final int EXPIRATION_TIME_SECONDS = Integer.parseInt(EXPIRATION_TIME_STR);

    private final Map<IExecutorContactInfo, ExpiringCache<ExecutorCommChannel>> eccCache = new ConcurrentHashMap<>();

    private final ChannelService delegate;

    public CachingChannelService(ChannelService delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    @Override
    public ExecutorCommChannel interExecutorCommChannel(IExecutorContactInfo contactInfo) {
        if(!contactInfo.isReachable()) {
            throw new IllegalArgumentException("The given contact info is marked non reachable: " + contactInfo);
        }
        ExpiringCache<ExecutorCommChannel> cache = eccCache.computeIfAbsent(contactInfo,
            ci -> new ExpiringCache<>(EXPIRATION_TIME_SECONDS, TimeUnit.SECONDS,
                new NullableCache<>(delegate.interExecutorCommChannel(contactInfo))));

        synchronized (this) {
            if(cache.isExpired()) {
                ExecutorCommChannel channel = cache.forceAccess();
                if(channel instanceof Closeable) {
                    try {
                        ((Closeable) channel).close();
                    } catch (IOException e) {
                        logger.warn("Error closing executor channel to executor: {}", contactInfo,  e);
                    }
                }
                cache.set(delegate.interExecutorCommChannel(contactInfo));
            }
            cache.prolong();
            return cache.get();
        }
    }

    @Override
    public ServiceStorageChannel serviceStorageChannel(IServiceRef serviceRef) {
        return delegate.serviceStorageChannel(serviceRef);
    }
}
