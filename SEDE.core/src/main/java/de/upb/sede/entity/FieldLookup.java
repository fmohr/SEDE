package de.upb.sede.entity;

import de.upb.sede.dsl.seco.Field;
import de.upb.sede.util.DefaultMap;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class FieldLookup<K> implements Serializable {

	private final FieldDereference<FieldLookup<K>> dereference;
	private final K content;


	FieldLookup(FieldLookup oldContext, K newContent) {
		this.dereference = oldContext.dereference;
		content = newContent;
	}


	public FieldLookup(K content) {
		dereference = new FieldDereference<>();
		this.content = content;
	}

	public FieldLookup() {
		this(null);
	}

	public synchronized Optional<K> lookup(Field field, Long version) {
		Objects.requireNonNull(field, "Field was null.");
		String fieldName = Objects.requireNonNull(field.getName(), "Fieldname was null");
		if(field.isDereference()) {
			FieldLookup<K> innerContext = dereference.get(fieldName);
			if(innerContext == null) {
				return Optional.empty();
			}
			return innerContext.lookup(field.getMember(), version);
		} else {
			FieldLookup<K> innerContext = dereference.get(new FieldVersion(fieldName, version));
			if(innerContext == null || innerContext.content == null) {
				return Optional.empty();
			} else {
				return Optional.of(innerContext.content);
			}
		}
	}

	public Optional<K> lookupLatest(Field field) {
		return lookup(field, FieldVersion.LATEST);
	}

	public Optional<Long> latestVersion(Field field) {
		Objects.requireNonNull(field, "Field was null.");
		String fieldName = Objects.requireNonNull(field.getName(), "Fieldname was null");
		if(field.isDereference()) {
			FieldLookup<K> innerContext = dereference.get(fieldName);
			if(innerContext == null) {
				return Optional.empty();
			}
			return innerContext.latestVersion(field.getMember());
		} else {
			Optional<FieldVersion> latestFieldVersion = dereference.latestVersion(fieldName);
			if(latestFieldVersion.isPresent()) {
				return Optional.ofNullable(latestFieldVersion.get().version);
			} else {
				return Optional.empty();
			}
		}
	}

	public synchronized Long dump(Field field, K content) {
		return dump(field, FieldVersion.LATEST, content);
	}

	public synchronized Long dump(Field field, Long version, K content) {
		Objects.requireNonNull(field);
		String fieldName = field.getName();
		Objects.requireNonNull(fieldName);
		if(!field.isDereference()) {
			FieldVersion updatedVersion = new FieldVersion(fieldName, version);
			FieldLookup<K> innerContext = dereference.get(updatedVersion);
			if(innerContext == null) {
				innerContext = new FieldLookup<>(content);
				dereference.put(updatedVersion, innerContext);
			} else {
				FieldLookup<K> newContext = new FieldLookup<>(innerContext, content);
				dereference.put(updatedVersion, newContext);
			}
			if(updatedVersion.isLatest()) {
				return dereference.latestVersion(fieldName).get().version; // lookup the newest version
			}
			else {
				return updatedVersion.version;
			}
		} else {
			FieldLookup<K> innerContext = dereference.get(fieldName);
			if(innerContext == null) {
				innerContext = new FieldLookup<>();
				dereference.put(new FieldVersion(fieldName), innerContext);
			}
			return innerContext.dump(field.getMember(), version, content);
		}
	}


	private final static class FieldDereference<K> implements Serializable, Map<FieldVersion, K> {

		private final DefaultMap<String, HashMap<Long, K>> nameVersionValueMap =
				new DefaultMap<String, HashMap<Long, K>>(HashMap::new);

		@Override
		public boolean containsKey(Object key) {
			if (key == null) {
				return false;
			}
			if (key instanceof String) {
				HashMap<Long, K> versionValueMap = nameVersionValueMap.get((String) key);
				return !versionValueMap.isEmpty();
			} else if (key instanceof FieldVersion) {
				FieldVersion fieldVersion = (FieldVersion) key;
				HashMap<Long, K> versionValueMap = nameVersionValueMap.get(fieldVersion.name);
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
		public K get(Object key) {
			if (key == null) {
				return null;
			}
			if (key instanceof String) {
				String fieldname = (String) key;
				HashMap<Long, K> versionValueMap = nameVersionValueMap.get(fieldname);
				Optional<FieldVersion> latest = latestVersion(fieldname);
				if(latest.isPresent()) {
					return versionValueMap.get(latest.get().version);
				} else {
					return null;
				}
			} else if (key instanceof FieldVersion) {
				FieldVersion requestedField = (FieldVersion) key;
				HashMap<Long, K> versionValueMap = nameVersionValueMap.get(requestedField.name);
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

		public Optional<FieldVersion> latestVersion(String fieldname) {
			if(containsKey(fieldname)) {
				HashMap<Long, K> versionValueMap = nameVersionValueMap.get(fieldname);
				return Optional.of(new FieldVersion(fieldname,
						versionValueMap.keySet().stream().max(Long::compareTo).get()));
			} else{
				return Optional.empty();
			}
		}

		@Override
		public K put(FieldVersion key, K value) {
			if(key.isLatest()) {
				FieldVersion newKey = (FieldVersion) latestVersion(key.name).orElse(key).newVersion();
				return put(newKey, value);
			} else {
				return nameVersionValueMap.get(key.name).put(key.version, value);
			}
		}

		@Override
		public K remove(Object key) {
			if(key instanceof String) {
				K latestContent = get(key);
				nameVersionValueMap.put((String) key, new HashMap<>());
				return latestContent;
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
					HashMap<Long, K> versionValueMap = nameVersionValueMap.get(fieldVersion.name);
					return versionValueMap.remove(fieldVersion.version);
				}
			} else {
				return null;
			}
		}

		@Override
		public void putAll(Map<? extends FieldVersion, ? extends K> m) {
			for(FieldVersion key : m.keySet()) {
				this.put(key, m.get(key));
			}
		}

		@Override
		public void clear() {
			nameVersionValueMap.clear();
		}

		@Override
		public Set<FieldVersion> keySet() {
			return nameVersionValueMap.keySey().stream()
					.map(this::latestVersion) 		// latest version of each field
					.filter(Optional::isPresent)	// only the ones with content
					.map(Optional::get)
					.collect(Collectors.toSet());
		}

		@Override
		public Collection<K> values() {
			return keySet().stream().map(this::get).collect(Collectors.toSet());
		}

		@Override
		public Set<Entry<FieldVersion, K>> entrySet() {
			return keySet().stream()
					.map(fieldVersion -> new AbstractMap.SimpleImmutableEntry<>(fieldVersion, get(fieldVersion)))
					.collect(Collectors.toSet());
		}

		@Override
		public String toString() {
			return "FieldDereference{" + nameVersionValueMap.toString() + "}";
		}
	}
}
