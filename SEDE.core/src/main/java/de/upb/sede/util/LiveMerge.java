package de.upb.sede.util;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class LiveMerge<K,V> implements Map<K, V> {

    private final Cache<Map<K, V>> cachedMerge;

    private Map<K,V> modDelegation;

    private final List<Map<K, V>> maps = new ArrayList<>();

    public LiveMerge(long timeDuration, TimeUnit timeUnit) {
        cachedMerge = new TTLCache<>();
        ((TTLCache<Map<K,V>>) cachedMerge).setTTL(timeDuration, timeUnit);
        ((TTLCache<Map<K,V>>) cachedMerge).setContentSupplier(this.getSupplier());
    }

    public LiveMerge(Map<K,V> modDelegation, long timeDuration, TimeUnit timeUnit) {
        this(timeDuration, timeUnit);
        setModificationDelegation(modDelegation);
    }

    public LiveMerge() {
        cachedMerge = new Uncache<>(this.getSupplier());
    }

    public LiveMerge(Map<K,V> modDelegation) {
        this();
        setModificationDelegation(modDelegation);
    }

    public boolean setModificationDelegation(Map<K,V> modDelegation) {
        this.modDelegation = modDelegation;
        if(modDelegation == null) {
            return false;
        }
        if(maps.contains(modDelegation)) {
            return false;
        } else {
            return maps.add(modDelegation);
        }
    }

    public boolean isModifiable() {
        return modDelegation != null && this.maps.contains(modDelegation);
    }

    private <T> T modify(Callable<T> callable) {
        if(isModifiable()) {
            T returnval =  UncheckedException.call(callable);
            if(cachedMerge instanceof TTLCache)
                ((TTLCache)cachedMerge).unset();
            return returnval;
        } else {
            throw new IllegalStateException("Modification delegation has not been set.");
        }
    }

    public Map<K,V> getCurrentMerge() {
        return cachedMerge.get();
    }

    public List<Map<K, V>> maps() {
        if(cachedMerge instanceof TTLCache) {
            ((TTLCache)cachedMerge).unset();
        }
        return maps;
    }

    @Override
    public synchronized int size() {
        return cachedMerge.get().size();
    }

    @Override
    public synchronized  boolean isEmpty() {
        return cachedMerge.get().isEmpty();
    }

    @Override
    public synchronized  boolean containsKey(Object key) {
        return cachedMerge.get().containsKey(key);
    }

    @Override
    public synchronized  boolean containsValue(Object value) {
        return cachedMerge.get().containsValue(value);
    }

    @Override
    public synchronized  V get(Object key) {
        return cachedMerge.get().get(key);
    }

    @Override
    public synchronized  V put(K key, V value) {
        return modify(() -> modDelegation.put(key, value));
    }


    @Override
    public synchronized V remove(Object key) {
        return modify(() -> modDelegation.remove(key));
    }

    @Override
    public synchronized void putAll(Map<? extends K, ? extends V> m) {
        modify(() -> {modDelegation.putAll(m); return true;});

    }

    @Override
    public synchronized void clear() {
        maps.clear();

    }

    @Override
    public synchronized Set<K> keySet() {
        return cachedMerge.get().keySet();
    }

    @Override
    public Collection<V> values() {
        return cachedMerge.get().values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return cachedMerge.get().entrySet();
    }

    private Supplier<Map<K, V>> getSupplier() {
        return () -> {
            Map<K, V> merge = new HashMap<>();
            for (int i = this.maps.size() - 1; i >= 0; i--) {
                merge.putAll(maps.get(i));
            }
            return merge;
        };
    }
}
