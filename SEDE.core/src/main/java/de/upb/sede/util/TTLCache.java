package de.upb.sede.util;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class TTLCache<Content> implements Cache<Content> {

    private final MutableWobblyField<Long> ttl = MutableWobblyField.empty();

    private final MutableWobblyField<Content> cachedContent = MutableWobblyField.empty();

    private final MutableWobblyField<Supplier<Content>> contentSupplier = MutableWobblyField.empty();

    /**
     * Changed by tests.
     */
    private static Supplier<Long> _TIME_PROVIDER = System::currentTimeMillis;

    private long lastRetrievalTimeStamp;

    public TTLCache() {

    }

    public TTLCache(Content content) {
        setContent(content);
    }

    public TTLCache(Content content, long timeToLive) {
        setContent(content);
        setTTL(timeToLive);
    }

    public TTLCache(Content content, long timeToLive, Supplier<Content> contentSupplier) {
        setContent(content);
        setTTL(timeToLive);
        setContentSupplier(contentSupplier);
    }

    public TTLCache(Content content, long ttlDuration, TimeUnit unit) {
        setContent(content);
        setTTL(ttlDuration, unit);
    }

    public TTLCache(Content content, long ttlDuration, TimeUnit unit, Supplier<Content> contentSupplier) {
        setContent(content);
        setTTL(ttlDuration, unit);
        setContentSupplier(contentSupplier);
    }

    public TTLCache(Supplier<Content> contentSupplier) {
        setContentSupplier(contentSupplier);
    }

    public TTLCache(long timeToLive, Supplier<Content> contentSupplier) {
        setTTL(timeToLive);
        setContentSupplier(contentSupplier);
    }

    public TTLCache(long ttlDuration, TimeUnit unit, Supplier<Content> contentSupplier) {
        setTTL(ttlDuration, unit);
        setContentSupplier(contentSupplier);
    }

    public MutableWobblyField<Long> getTTL() {
        return ttl;
    }

    public MutableWobblyField<Supplier<Content>> getContentSupplier() {
        return contentSupplier;
    }

    public void setTTL(long timeToLive) {
        if(timeToLive <= 0) {
            throw new IllegalArgumentException("Time to live must be positive. Given: " + timeToLive);
        }
        ttl.set(timeToLive);
    }

    public void setTTL(long duration, TimeUnit timeUnit) {
        this.setTTL(timeUnit.toMillis(duration));
    }

    public void unsetTTL() {
        this.ttl.unset();
    }

    public void unsetContent() {
        this.cachedContent.unset();
    }

    public void unsetContentSupplier() {
        this.contentSupplier.unset();
    }

    public void setContent(Content cacheContent) {
        this.cachedContent.set(cacheContent);
        lastRetrievalTimeStamp = _TIME_PROVIDER.get();
    }

    public void setContentSupplier(Supplier<Content> contentSupplier) {
        this.contentSupplier.set(contentSupplier);
    }

    public synchronized void retrieveContent() {
        if(contentSupplier.isAbsent()) {
            throw new IllegalStateException("Cannot retrieve content as content supplier is not given.");
        }
        setContent(contentSupplier.get().get());
    }

    public synchronized Content access() {
        if(cachedContent.isAbsent()) {
            retrieveContent();
        } else if(getTTL().isPresent()) {
            long liveTime = lastRetrievalTimeStamp + getTTL().get();
            long currentTime = _TIME_PROVIDER.get();
            if(liveTime <= currentTime) {
                unsetContent();
                retrieveContent();
                return cachedContent.get();
            }
        }
        return cachedContent.get();
    }

    @Override
    public void set(Content content) {
        this.setContent(content);
    }

    @Override
    public void unset() {
        this.unsetContent();
    }
}
