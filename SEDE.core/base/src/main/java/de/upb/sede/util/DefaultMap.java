package de.upb.sede.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class DefaultMap<K, V> {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private transient Supplier<V> defaultValueSupplier;

	private final Map<K, V> innerMap = new HashMap<>();

	public DefaultMap(Supplier<V> defaultValueSupplier) {
		this.defaultValueSupplier = defaultValueSupplier;
	}

	public V get(K key) {
		if (!innerMap.containsKey(key)) {
			innerMap.put(key, getDefaultSupplier().get());
		}
		return innerMap.get(key);
	}

	public void put(K key, V value) {
		innerMap.put(Objects.requireNonNull(key), Objects.requireNonNull(value));
	}

	private Supplier<V> getDefaultSupplier() {
		if (defaultValueSupplier == null) {
			/*
			 * the field is transient thus is null after deserialisation.
			 */
			defaultValueSupplier = () -> null;
		}
		return defaultValueSupplier;
	}

	public String toString() {
		return innerMap.toString();
	}
}
