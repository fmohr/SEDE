package de.upb.sede.composition.graphs.nodes;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import de.upb.sede.gateway.ResolveInfo;

public class DeleteFieldNode extends BaseNode {

	private final String fieldname;

	public String getFieldname() {
		return fieldname;
	}

	public DeleteFieldNode(String fieldname) {
		this.fieldname = fieldname;
	}

	@Override
	public boolean producesField(String fieldname, ResolveInfo resolveInfo) {
		return false;
	}

	@Override
	public Collection<String> consumingFields(ResolveInfo resolveInfo) {
		return Arrays.asList(getFieldname());
	}

	@Override
	public Collection<String> producingFields(ResolveInfo resolveInfo) {
		return Collections.EMPTY_LIST;
	}

	public String toString() {
		return "del \"" + fieldname + "\"";
	}

}
