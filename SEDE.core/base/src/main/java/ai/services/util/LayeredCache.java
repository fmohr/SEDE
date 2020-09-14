package de.upb.sede.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LayeredCache<C> implements SettableCache<C>{

    private List<Cache<C>> layers = new ArrayList<>();

    public LayeredCache() {

    }

    public LayeredCache(Cache<C>... layers) {
        Collections.addAll(this.layers, layers);
    }

    public List<Cache<C>> getLayers() {
        return layers;
    }
    public List<Cache<C>> layers() {
        return layers;
    }

    public void setLayers(List<Cache<C>> layers) {
        this.layers = layers;
    }

    @Override
    public boolean set(C c) {
        return setUntil(c, null);
    }

    public boolean setUntil(C c, Cache<C> stop) {
        boolean set = false;
        for(Cache<C> cache  : layers) {
            if(cache == stop) {
                break;
            }
            if(cache instanceof SettableCache) {
                set = set | ((SettableCache<C>) cache).set(c);
            }
        }
        return set;
    }

    @Override
    public C access() {
        for(Cache<C> cache  : layers) {
            C content = cache.access();
            if(content != null) {
                setUntil(content, cache);
                return content;
            }
        }
        return null;
    }
}
