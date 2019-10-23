package de.upb.sede.util;

import java.util.LinkedHashMap;
import java.util.function.Supplier;

public interface Cache<Content> extends Supplier<Content> {

    Content access();

    default Content get() {
        return access();
    }

    static <C> LayeredCache<C> nullableDefaultCache(Supplier<C> cSupplier) {
        LayeredCache<C> cache = new LayeredCache<>();
        cache.layers().add(new NullableCache<>());
        cache.layers().add(new Uncache<>(cSupplier));
        return cache;
    }
}
