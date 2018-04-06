package de.upb.sede.composition.graphs.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import de.upb.sede.composition.gc.ResolveInformation;

public class ParseConstantNode extends BaseNode {
	
	private final String constantValue;
	public ParseConstantNode(String constantValue) {
		this.constantValue = Objects.requireNonNull(constantValue);
	}
	
	@Override
	public
	boolean producesField(String fieldname, ResolveInformation resolveInfo) {
		return constantValue.equals(fieldname);
	}

	@Override
	public
	Collection<String> consumingFields(ResolveInformation resolveInfo) {
		return Collections.EMPTY_LIST;
	}

	@Override
	public
	Collection<String> producingFields(ResolveInformation resolveInfo) {
		return new ArrayList<String>(1) {{add(constantValue);}};
	}


}
