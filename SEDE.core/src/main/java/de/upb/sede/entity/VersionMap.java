package de.upb.sede.entity;

import de.upb.sede.util.DefaultMap;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class VersionMap<T> implements Serializable, Map<Long, T> {

	private final Map<Long, T> innerMap;

	public final static Long LATEST = -1L;
	public final static Long STARTING_VERSION = 0L;

	public VersionMap() {
		innerMap = new HashMap<>();
	}

	public VersionMap(VersionMap<T> other) {
		innerMap = new HashMap<Long, T>(other.innerMap);
	}

	@Override
	public synchronized boolean containsKey(Object key) {
		if (key == null) {
			return false;
		} else if(LATEST.equals(key)) {
			return !innerMap.isEmpty();
		} else {
			return innerMap.containsKey(key);
		}
	}

	@Override
	public int size() {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public boolean isEmpty() {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public boolean containsValue(Object value) {
		throw new RuntimeException("Not Implemented");
	}

	public T getLatest() {
		return get(LATEST);
	}

	@Override
	public synchronized T get(Object key) {
		if (key == null) {
			return null;
		} else if(LATEST.equals(key)) {
			Optional<Long> latestVersion = latestVersion();
			return latestVersion.map(this::get).orElse(null);
		} else {
			return innerMap.get(key);
		}
	}

	public  synchronized Optional<Long> latestVersion() {
		return innerMap.keySet().stream().max(Long::compareTo);
	}

	@Override
	public synchronized T put(Long key, T value) {
		if (key == null) {
			throw new IllegalArgumentException("Null keys are not allowed.");
		} else if(key.equals(LATEST)) {
			Optional<Long> latest = latestVersion();
			Long newVersion;
			newVersion = latest.map(lastVersion -> lastVersion + 1).orElse(STARTING_VERSION);
			return innerMap.put(newVersion, value);
		} else {
			return innerMap.put(key, value);
		}
	}

	@Override
	public synchronized T remove(Object key) {
		if (key == null) {
			return null;
		} else if(LATEST.equals(key)) {
			Optional<Long> latestVersion = latestVersion();
			return latestVersion.map(this::remove).orElse(null);
		} else {
			return innerMap.remove(key);
		}
	}

	@Override
	public synchronized  void putAll(Map<? extends Long, ? extends T> m) {
		for(Long key : m.keySet()) {
			this.put(key, m.get(key));
		}
	}

	@Override
	public synchronized void clear() {
		innerMap.clear();
	}

	@Override
	public synchronized Set<Long> keySet() {
		return innerMap.keySet();
	}

	@Override
	public  synchronized Collection<T> values() {
		return keySet().stream().map(this::get).collect(Collectors.toSet());
	}

	@Override
	public synchronized  Set<Entry<Long, T>> entrySet() {
		return keySet().stream()
				.map(version -> new AbstractMap.SimpleImmutableEntry<>(version, get(version)))
				.collect(Collectors.toSet());
	}

	@Override
	public String toString() {
		return "VersionMap{" + keySet() + "}";
	}
}
