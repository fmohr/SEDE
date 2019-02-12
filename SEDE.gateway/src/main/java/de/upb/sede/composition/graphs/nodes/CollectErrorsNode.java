package de.upb.sede.composition.graphs.nodes;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectErrorsNode extends BaseNode{

	public final static String EXECUTION_ERRORS_FIELDNAME = "__execution_errors_%s";

	private final List<String> errorFields;
	private final String fieldname;

	public CollectErrorsNode(List<String> errorFields, String executorId) {
		this.errorFields = errorFields;
		this.fieldname = fieldname(executorId);
	}

	public CollectErrorsNode(String executorId) {
		this(Collections.EMPTY_LIST, executorId);
	}

	private static String fieldname(String executorId) {
		return String.format(EXECUTION_ERRORS_FIELDNAME, executorId);
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

	public String getFieldname() {
		return fieldname;
	}
}