package de.upb.sede.composition.graphs;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.upb.sede.composition.graphconstructioninformation.ClassesConfig;
import de.upb.sede.exceptions.UnassignedFieldException;

/**
 * 
 * @author aminfaez
 *
 *         This node represents an instruction in a graphComposition which was
 *         parsed from a fmInstruction. This can either be a method invocation
 *         or a Service creation.
 */
public class InstructionNode extends BaseNode {

	private static final String unassignedValue = "UNDEFINED";

	private String fmInstruction;
	private String leftsideFieldname;
	private String host;
	private String context;
	private String method;

	/**
	 * Parameters for method or constructor invocation. The order of the parameters
	 * has to be kept unchanged. May either contain field-names referencing to data
	 * or else constants like numbers or strings. e.g.: ["a1", "b1", "10", "\"a\""]
	 * The first two are fieldnames. The third is a constant number. The fourth is a
	 * constant string.
	 * 
	 * 
	 * The list itself is read-only.
	 */
	private List<String> parameterFields;

	@SuppressWarnings("unchecked")
	public InstructionNode(String basedOnInstruction, String context, String method) {
		this.fmInstruction = Objects.requireNonNull(basedOnInstruction);
		leftsideFieldname = unassignedValue;
		host = unassignedValue;
		parameterFields = Collections.EMPTY_LIST;
		this.context = Objects.requireNonNull(context);
		this.method = Objects.requireNonNull(method);
	}

	public String getFmInstruction() {
		return this.fmInstruction;
	}

	/*
	 * standard get, set, isAssigned methods.
	 */

	public String getLeftSideFieldname() {
		if (!isAssignedLeftSideFieldname()) {
			throw new UnassignedFieldException(this, "leftSideFieldName");
		}
		return leftsideFieldname;
	}

	public void setLeftSideFieldname(String leftsideFieldname) {
		this.leftsideFieldname = Objects.requireNonNull(leftsideFieldname);
	}

	public boolean isAssignedLeftSideFieldname() {
		return leftsideFieldname != unassignedValue;
	}

	public String getHost() {
		if (!isAssignedHost()) {
			throw new UnassignedFieldException(this, "host");
		}
		return host;
	}

	public void setHost(String host) {
		this.host = Objects.requireNonNull(host);
	}

	public boolean isAssignedHost() {
		return host != unassignedValue;
	}

	public String getContext() {
		if (!isAssignedContext()) {
			throw new UnassignedFieldException(this, "context");
		}
		return context;
	}

	public boolean isAssignedContext() {
		return context != unassignedValue;
	}

	public String getMethod() {
		if (!isAssignedMethod()) {
			throw new UnassignedFieldException(this, "method");
		}
		return method;
	}

	public boolean isAssignedMethod() {
		return method != unassignedValue;
	}

	public List<String> getParameterFields() {
		return parameterFields;
	}

	/**
	 * Sets the parameter field
	 */
	public void setParameterFields(List<String> parameterFields) {
		if (parameterFields.getClass().getName().equals("java.util.Collections$UnmodifiableRandomAccessList")
				|| parameterFields.getClass().getName().equals("java.util.Collections$EmptyList")) {
			this.parameterFields = parameterFields;
		} else {
			this.parameterFields = Collections.unmodifiableList(parameterFields);
		}
	}


	@Override
	boolean producesField(String fieldname, ClassesConfig configuration) {
		if (isAssignedLeftSideFieldname()) {
			return getLeftSideFieldname().equals(fieldname);
		} else {
			return false;
		}
	}




	@Override
	Collection<String> consumingFields() {
		return null;
	}
}