package de.upb.sede.composition.graphs.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import de.upb.sede.composition.gc.ResolveInfo;

public class ParseConstantNode extends BaseNode {
	
	private final String constantValue;
	public ParseConstantNode(String constantValue) {
		this.constantValue = Objects.requireNonNull(constantValue);
	}
	
	@Override
	public
	boolean producesField(String fieldname, ResolveInfo resolveInfo) {
		return constantValue.equals(fieldname);
	}

	@Override
	public
	Collection<String> consumingFields(ResolveInfo resolveInfo) {
		return Collections.EMPTY_LIST;
	}

	@Override
	public
	Collection<String> producingFields(ResolveInfo resolveInfo) {
		return new ArrayList<String>(1) {{add(constantValue);}};
	}


}
