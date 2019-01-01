package de.upb.sede.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public final class FieldVersion implements FieldPointer {
	final String name;
	final Long version;

	static final Long LATEST = -1L;


	public FieldVersion(FieldVersion versionBefore, Long newVersion) {
		this(versionBefore.name, newVersion);
	}

	public FieldVersion(String name, Long version) {
		this.name = Objects.requireNonNull(name);
		this.version = Objects.requireNonNull(version);
	}

	public FieldVersion(String name) {
		this(name, LATEST);
	}

	public boolean isLatest() {
		return version.equals(LATEST);
	}

	public FieldVersion newVersion() {
		return new FieldVersion(this, this.version + 1);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if(o == null) return false;
//			if(o.getClass() == String.class) {
//				return this.name.equals(o);
//			}
		if ( getClass() != o.getClass()) return false;
		FieldVersion that = (FieldVersion) o;
		return Objects.equals(name, that.name) &&
				Objects.equals(version, that.version);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, version);
	}

	@Override
	public List<String> path() {
		LinkedList<String> s = new LinkedList<>();
		s.add(name);
		return s;
	}

	@Override
	public Long version() {
		return version;
	}

	public String toString() {
		return name+ ":" + version;
	}
}
