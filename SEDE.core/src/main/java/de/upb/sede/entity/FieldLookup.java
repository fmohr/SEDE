package de.upb.sede.entity;

import de.upb.sede.dsl.seco.Field;

import java.util.*;

public class FieldLookup<K> {

	private final FieldLinker<VersionMap<K>> rootLinker;

	public FieldLookup() {
		rootLinker = new FieldLinker<>();
	}

	public FieldLookup(FieldLinker<VersionMap<K>> linker) {
		rootLinker = linker;
	}

	public synchronized Optional<FieldLookup<K>> lookup(Field field) {
		Optional<FieldLinker<VersionMap<K>>> link = rootLinker.getLink(field);
		return link.map(FieldLookup::new);
	}

	public Optional<K> readLatest(Field field) {
		return read(field, VersionMap.LATEST);
	}

	public synchronized Optional<K> read(Field field, Long version) {
		Optional<VersionMap<K>> link = rootLinker.get(field);
		return link.map(map -> map.get(version));
	}

	public synchronized Optional<Long> latestVersion(Field field) {
		return rootLinker.get(field).flatMap(VersionMap::latestVersion);
	}

	public synchronized void link(Field field, FieldLookup<K> linkTarget) {
		rootLinker.setLink(field, linkTarget.rootLinker);
	}

	public Long write(Field field, K content) {
		return write(field, VersionMap.LATEST, content);
	}

	public synchronized Long write(Field field, Long version, K newContent) {
		Optional<VersionMap<K>> verionMapOpt = rootLinker.get(field);
		VersionMap<K> versionMap;
		if(!verionMapOpt.isPresent()) {
			versionMap = new VersionMap<>();
			rootLinker.set(field, versionMap);
		} else {
			versionMap = verionMapOpt.get();
		}
		versionMap.put(version, newContent);
		if(VersionMap.LATEST.equals(version))
			return versionMap.latestVersion().get();
		else
			return version;
	}

	public synchronized Optional<VersionMap<K>> deleteAll(Field field) {
		return rootLinker.set(field, new VersionMap<>());
	}

	public synchronized Optional<K> deleteLatest(Field field) {
		return delete(field, VersionMap.LATEST);
	}

	public synchronized Optional<K> delete(Field field, Long version) {
		return rootLinker.get(field).map(versionMap -> versionMap.remove(version));
	}
}
