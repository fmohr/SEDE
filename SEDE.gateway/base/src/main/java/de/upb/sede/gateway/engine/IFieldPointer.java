package de.upb.sede.gateway.engine;

import de.upb.sede.FieldAccess;
import de.upb.sede.IFieldAccess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public interface IFieldPointer extends Serializable {

	boolean isLatest();

	IFieldPointer newVersion();

	@Override
	boolean equals(Object o);

	@Override
	public int hashCode();

	List<String> path();

	Long version();

	@Deprecated
	static IFieldPointer fromField(IFieldAccess field) {
		return fromField(field, FieldVersion.LATEST);
	}

	static IFieldPointer fromField(IFieldAccess field, Long version) {
		if(version.equals(FieldVersion.LATEST)) {
			throw new IllegalArgumentException("FieldPointer cannot point to implicit latest version. " +
					"Explicitly give the latest version.");
		}
		List<String> path = new ArrayList<>();
		path.add(field.getFieldName());
		while(field.isDereference()) {
			field = field.getMember();
			path.add(field.getFieldName());
		}

		IFieldPointer current = new FieldVersion(path.get(path.size()-1), version);

		for(int i = path.size() -2; i >=0 ; i--) {
			FieldNode before = new FieldNode(current, path.get(i));
			current = before;
		}

		return current;
	}

	public default IFieldAccess toField() {
	    List<String> path = this.path();
	    if(path == null || path.isEmpty()) {
	        throw new IllegalStateException("The path of this field pointer is empty");
        }
	    IFieldAccess access = null;
        for (int i = path.size()-1; i >= 0 ; i--) {
            String field = path.get(i);
            boolean isDereference = access != null;
            FieldAccess.Builder accessBuilder = FieldAccess.builder()
                .fieldName(field)
                .member(access)
                .isDereference(isDereference);
            access = accessBuilder.build(); // replace the old access
        }
        return access;
    }

	final static class FieldNode implements IFieldPointer {
		final IFieldPointer nextNode;
		final String fieldname;

		public FieldNode(IFieldPointer nextNode, String fieldname) {
			this.nextNode = Objects.requireNonNull(nextNode);
			this.fieldname = Objects.requireNonNull(fieldname);
		}

		@Override
		public boolean isLatest() {
			return nextNode.isLatest();
		}

		@Override
		public IFieldPointer newVersion() {
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


