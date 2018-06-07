package de.upb.sede.composition.graphs.nodes;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.upb.sede.exceptions.UnassignedFieldException;

/**
 * @author aminfaez
 * <p>
 * This node represents an instruction in a graphComposition which was
 * parsed from a fmInstruction. This can either be a method invocation
 * or a Service creation.
 */
public class InstructionNode extends BaseNode {

	private static final String unassignedValue = "UNDEFINED";

	private String fmInstruction;
	private String leftsideFieldname;
	private String leftsideFieldtype;
	private String host;
	private String context;
	private boolean contextIsField;
	private String method;

	/**
	 * Index of instruction output.
	 * -1 means that the return value of the instruction is the output.
	 * Any other value >= 0 will have the instruction return the ith parameter as output.
	 */
	private int outputIndex = -1;

	/**
	 * Parameters for method or constructor invocation. The order of the parameters
	 * has to be kept unchanged. May either contain field-names referencing to data
	 * or else constants like numbers or strings. e.g.: ["a1", "b1", "10", "\"a\""]
	 * The first two are fieldnames. The third is a constant number. The fourth is a
	 * constant string.
	 * <p>
	 * <p>
	 * The list itself is read-only.
	 */
	private List<String> parameterFields;
	private List<String> parameterTypes;

	@SuppressWarnings("unchecked")
	public InstructionNode(String basedOnInstruction, String context, String method) {
		this.fmInstruction = Objects.requireNonNull(basedOnInstruction);
		leftsideFieldname = unassignedValue;
		leftsideFieldtype = unassignedValue;
		host = unassignedValue;
		parameterFields = Collections.EMPTY_LIST;
		parameterTypes = Collections.EMPTY_LIST;
		this.context = Objects.requireNonNull(context);
		this.method = Objects.requireNonNull(method);
		this.contextIsField = false;
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

	public void unsetLeftSideField() {
		leftsideFieldname = unassignedValue;
		leftsideFieldtype = unassignedValue;
	}

	public String getLeftSideFieldtype() {
		if (!isAssignedLeftSideFieldname()) {
			throw new UnassignedFieldException(this, "leftSideFieldtype");
		}
		return leftsideFieldtype;
	}

	public void setLeftSideFieldtype(String type) {
		this.leftsideFieldtype = Objects.requireNonNull(type);
	}

	public boolean isAssignedLeftSideFieldtype() {
		return leftsideFieldtype != unassignedValue;
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

	public void setContextIsField(boolean isField) {
		this.contextIsField = isField;
	}

	public boolean isContextAFieldname() {
		return this.contextIsField;
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

	public List<String> getParameterTypes() {
		if (parameterTypes == null) {
			throw new RuntimeException("BUG: Parameter types is null.");
		}
		return parameterTypes;
	}

	public void setParameterType(List<String> parameterTypes) {
		this.parameterTypes = parameterTypes;
	}


	public void setOutputIndex(int paramIndex) {
		outputIndex = paramIndex;
	}

	public int getOutputIndex() {
		return outputIndex;
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

	/**
	 * Returns the fm composition representation of the instruction.
	 */
	public String toString() {
		String s = "";
		if (isAssignedLeftSideFieldname()) {
			s += getLeftSideFieldname() + " = ";
		}
		if (isAssignedHost()) {
			s += getHost() + "/";
		}
		s += getContext() + "::";
		s += getMethod() + "(";
		s += getParameterFields().toString() + ")";
		return s;
	}

	public boolean isServiceConstruct() {
		return !isContextAFieldname() && getMethod().equals("__construct");
	}

}