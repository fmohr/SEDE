package ai.services.util;

import java.util.concurrent.TimeUnit;

public class ExpiringCache<Content> implements SettableCache<Content> {

    private MutableOptionalField<Long> expirationTimeMillis = MutableOptionalField.empty();

    private MutableOptionalField<Long> servicePeriod = MutableOptionalField.empty();

    private Cache<Content> innerCache;

    public ExpiringCache(long servicePeriod, TimeUnit timeUnit, Content content) {
        this.servicePeriod.set(timeUnit.toMillis(servicePeriod));
        this.innerCache = new NullableCache<>(content);
        prolong();
    }

    public ExpiringCache(long expirationTimeMillis, Content content) {
        this.expirationTimeMillis.set(expirationTimeMillis);
        this.innerCache = new NullableCache<>(content);
    }

    public ExpiringCache(long servicePeriod, TimeUnit timeUnit, Cache<Content> innerCache) {
        this.innerCache = innerCache;
        this.servicePeriod.set(timeUnit.toMillis(servicePeriod));
        prolong();
    }

    public ExpiringCache(long expirationTimeMillis, Cache<Content> innerCache) {
        this.expirationTimeMillis.set(expirationTimeMillis);
        this.innerCache = innerCache;
    }

    public MutableOptionalField<Long> getExpirationTimeMillis() {
        return expirationTimeMillis;
    }

    public void setExpirationTimeMillis(MutableOptionalField<Long> expirationTimeMillis) {
        this.expirationTimeMillis = expirationTimeMillis;
    }

    public MutableOptionalField<Long> getServicePeriod() {
        return servicePeriod;
    }

    public void setServicePeriod(MutableOptionalField<Long> servicePeriod) {
        this.servicePeriod = servicePeriod;
    }

    public Cache<Content> getInnerCache() {
        return innerCache;
    }

    public void setInnerCache(SettableCache<Content> innerCache) {
        this.innerCache = innerCache;
    }

    public void prolong() {
        expirationTimeMillis.set(System.currentTimeMillis() + servicePeriod
            .orElseThrow(() -> new IllegalStateException("Cannot prolong the cache " +
                "because no service period is defined.")));
    }

    public boolean isExpired() {
        boolean isExpired = System.currentTimeMillis() > expirationTimeMillis.orElse(0L);
        return isExpired;
    }

    @Override
    public Content access() {
        if(isExpired()) {
            /*
             * Returning null here is necessary
             * because there is no atomic "check and get" method defined.
             *
             * The cache can expire after checking with isExpired and before calling access.
             *
             */
            return null;
        } else {
            return innerCache.access();
        }
    }

    /**
     *  To be used after asking isExpired.
     */
    public Content forceAccess() {
        return innerCache.access();
    }

    @Override
    public boolean set(Content content) {
        if(innerCache instanceof SettableCache) {
            return ((SettableCache<Content>) innerCache).set(content);
        }
        return false;
    }
}
