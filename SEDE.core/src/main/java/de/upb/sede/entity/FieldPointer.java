package de.upb.sede.entity;

import de.upb.sede.dsl.SecoUtil;
import de.upb.sede.dsl.seco.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public interface FieldPointer extends Serializable {

	boolean isLatest();

	FieldPointer newVersion();

	@Override
	boolean equals(Object o);

	@Override
	public int hashCode();

	List<String> path();

	Long version();

	@Deprecated
	static FieldPointer fromField(Field field) {
		return fromField(field, FieldVersion.LATEST);
	}

	static FieldPointer fromField(Field field, Long version) {
		if(version.equals(FieldVersion.LATEST)) {
			throw new IllegalArgumentException("FieldPointer cannot point to implicit latest version. " +
					"Explicitly give the latest version.");
		}
		List<String> path = new ArrayList<>();
		path.add(field.getName());
		while(field.isDereference()) {
			field = field.getMember();
			path.add(field.getName());
		}

		FieldPointer current = new FieldVersion(path.get(path.size()-1), version);

		for(int i = path.size() -2; i >=0 ; i--) {
			FieldNode before = new FieldNode(current, path.get(i));
			current = before;
		}

		return current;
	}

	static Field toField(FieldPointer addr) {
		return SecoUtil.createField(addr.path().toArray(new String[0]));
	}

	final static class FieldNode implements FieldPointer {
		final FieldPointer nextNode;
		final String fieldname;

		public FieldNode(FieldPointer nextNode, String fieldname) {
			this.nextNode = Objects.requireNonNull(nextNode);
			this.fieldname = Objects.requireNonNull(fieldname);
		}

		@Override
		public boolean isLatest() {
			return nextNode.isLatest();
		}

		@Override
		public FieldPointer newVersion() {
			return new FieldNode(nextNode.newVersion(), fieldname);
		}

		@Override
		public List<String> path() {
			List<String> path = nextNode.path();
			path.add(0, fieldname);
			return path;
		}

		@Override
		public Long version() {
			return nextNode.version();
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			FieldNode fieldNode = (FieldNode) o;
			return Objects.equals(nextNode, fieldNode.nextNode) &&
					Objects.equals(fieldname, fieldNode.fieldname);
		}

		@Override
		public int hashCode() {
			return Objects.hash(nextNode, fieldname);
		}
	}

}


