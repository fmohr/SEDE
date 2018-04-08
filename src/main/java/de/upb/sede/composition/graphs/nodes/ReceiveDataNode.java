package de.upb.sede.composition.graphs.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.upb.sede.composition.gc.ResolveInfo;

class ReceiveDataNode extends BaseNode{
	
	private final String fieldname;
	
	ReceiveDataNode(String fieldname){
		this.fieldname = Objects.requireNonNull(fieldname);
	} 
	
	@Override
	public
	boolean producesField(String fieldname, ResolveInfo resolveInfo) {
		return fieldname.equals(this.fieldname);
	}

	@Override
	public
	Collection<String> consumingFields(ResolveInfo resolveInfo) {
		return Collections.EMPTY_LIST;
	}

	@Override
	public
	Collection<String> producingFields(ResolveInfo resolveInfo) {
		return new ArrayList<String>(1) {{add(fieldname);}};
	}
	
	

}