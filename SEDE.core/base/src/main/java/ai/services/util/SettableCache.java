package de.upb.sede.util;

public interface SettableCache<T> extends Cache<T> {

    boolean set(T t);
}
