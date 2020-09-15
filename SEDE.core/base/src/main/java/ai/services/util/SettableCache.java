package ai.services.util;

public interface SettableCache<T> extends Cache<T> {

    boolean set(T t);
}
