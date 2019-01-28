package de.upb.sede.composition.graphs.nodes;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectErrorsNode extends BaseNode{

	private final List<String> errorFields;

	public CollectErrorsNode(List<String> errorFields) {
		this.errorFields = errorFields;
	}

	public CollectErrorsNode() {
		this.errorFields = Collections.EMPTY_LIST;
	}

	public List<String> getErrorFields() {
		return errorFields;
	}

	@Override
	public String toString()
	{
		if(errorFields.isEmpty()) {
			return "Collect errors";
		}
		return "Collect errors from " + errorFields.stream().collect(Collectors.joining(", "));
	}
}