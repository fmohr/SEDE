package de.upb.sede.gateway.engine;

import de.upb.sede.util.DefaultMap;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class FieldVersionMap<T> implements Serializable, Map<FieldVersion, T> {

	private final DefaultMap<String, HashMap<Long, T>> nameVersionValueMap =
			new DefaultMap<String, HashMap<Long, T>>(HashMap::new);

	@Override
	public synchronized boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}
		if (key instanceof String) {
			HashMap<Long, T> versionValueMap = nameVersionValueMap.get((String) key);
			return !versionValueMap.isEmpty();
		} else if (key instanceof FieldVersion) {
			FieldVersion fieldVersion = (FieldVersion) key;
			HashMap<Long, T> versionValueMap = nameVersionValueMap.get(fieldVersion.name);
			if(fieldVersion.isLatest()) {
				return !versionValueMap.isEmpty();
			} else {
				return versionValueMap.containsKey(fieldVersion.version);
			}
		} else {
			return false;
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

	@Override
	public synchronized T get(Object key) {
		if (key == null) {
			return null;
		}
		if (key instanceof String) {
			String fieldname = (String) key;
			HashMap<Long, T> versionValueMap = nameVersionValueMap.get(fieldname);
			Optional<FieldVersion> latest = latestVersion(fieldname);
			if(latest.isPresent()) {
				return versionValueMap.get(latest.get().version);
			} else {
				return null;
			}
		} else if (key instanceof FieldVersion) {
			FieldVersion requestedField = (FieldVersion) key;
			HashMap<Long, T> versionValueMap = nameVersionValueMap.get(requestedField.name);
			if(requestedField.isLatest()) {
				Optional<FieldVersion> latestVersion = latestVersion(requestedField.name);
				if(latestVersion.isPresent()) {
					return versionValueMap.getOrDefault(latestVersion.get().version, null);
				} else {
					return null;
				}
			} else {
				return versionValueMap.getOrDefault(requestedField.version, null);
			}
		} else {
			return null;
		}
	}

	public  synchronized Optional<FieldVersion> latestVersion(String fieldname) {
		if(containsKey(fieldname)) {
			HashMap<Long, T> versionValueMap = nameVersionValueMap.get(fieldname);
			return Optional.of(new FieldVersion(fieldname,
					versionValueMap.keySet().stream().max(Long::compareTo).get()));
		} else{
			return Optional.empty();
		}
	}

	@Override
	public synchronized T put(FieldVersion key, T value) {
		if(key.isLatest()) {
			FieldVersion newKey = (FieldVersion) latestVersion(key.name).orElse(key).newVersion();
			return put(newKey, value);
		} else {
			return nameVersionValueMap.get(key.name).put(key.version, value);
		}
	}

	@Override
	public synchronized T remove(Object key) {
		if(key instanceof String) {
			T removedContent = get(key);
			nameVersionValueMap.put((String) key, new HashMap<>());
			return removedContent;
		} else if(key instanceof FieldVersion) {
			FieldVersion fieldVersion = (FieldVersion) key;
			if(fieldVersion.isLatest()) {
				Optional<FieldVersion> latest = latestVersion(fieldVersion.name);
				if(latest.isPresent()) {
					return remove(latest.get());
				} else {
					return null;
				}
			} else {
				HashMap<Long, T> versionValueMap = nameVersionValueMap.get(fieldVersion.name);
				return versionValueMap.remove(fieldVersion.version);
			}
		} else {
			return null;
		}
	}

	@Override
	public synchronized  void putAll(Map<? extends FieldVersion, ? extends T> m) {
		for(FieldVersion key : m.keySet()) {
			this.put(key, m.get(key));
		}
	}

	@Override
	public  synchronized void clear() {
		nameVersionValueMap.clear();
	}

	@Override
	public synchronized Set<FieldVersion> keySet() {
		return nameVersionValueMap.keySey().stream()
				.map(this::latestVersion) 		// latest version of each field
				.filter(Optional::isPresent)	// only the ones with content
				.map(Optional::get)
				.collect(Collectors.toSet());
	}

	@Override
	public  synchronized Collection<T> values() {
		return keySet().stream().map(this::get).collect(Collectors.toSet());
	}

	@Override
	public synchronized Set<Entry<FieldVersion, T>> entrySet() {
		return keySet().stream()
				.map(fieldVersion -> new AbstractMap.SimpleImmutableEntry<>(fieldVersion, get(fieldVersion)))
				.collect(Collectors.toSet());
	}

	@Override
	public String toString() {
		return "FieldDereference{" + keySet() + "}";
	}
}
