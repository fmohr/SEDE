package de.upb.sede.composition.graphs.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import de.upb.sede.composition.gc.ResolveInfo;
import de.upb.sede.composition.gc.ServiceInstanceHandle;
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
	private boolean contextIsField;
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
	public
	boolean producesField(String fieldname, ResolveInfo resolveInfo) {
		if (isAssignedLeftSideFieldname() && getLeftSideFieldname().equals(fieldname)) {
			return true;
		} else if (isContextAFieldname() && getContext().equals(fieldname)) {
			/*
			 * Lookup if the method changes the state of the service:
			 */
			if (resolveInfo.getClassesConfiguration().stateMutational(getServiceClass(resolveInfo), getMethod())) {
				/*
				 * The method changes the state of the service:
				 */
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public String getServiceClass(ResolveInfo resolveInfo) {
		String serviceClasspath;
		if(isContextAFieldname()) {
			/*
			 * the context is a fieldname. Get its class path information from the serviceInstancehandle:
			 */
			serviceClasspath = resolveInfo.getInputFields().getServiceInstanceHandle(getContext()).getClasspath();
		} else {
			/*
			 * if the context is class path:
			 */
			serviceClasspath = getContext();
		}
		return serviceClasspath;
	}

	@Override
	public
	Collection<String> consumingFields(ResolveInfo resolveInfo) {
		List<String> consumingFields = new ArrayList<>();
		if (isContextAFieldname()) {
			consumingFields.add(getContext());
		}
		consumingFields.addAll(getParameterFields());
		return consumingFields;

	}

	@Override
	public
	Collection<String> producingFields(ResolveInfo resolveInfo) {
		ArrayList<String> producingFields = new ArrayList<>();
		if(isAssignedLeftSideFieldname()) {
			producingFields.add(getLeftSideFieldname());
		}
		if(isContextAFieldname()) {
			String serviceClass = getServiceClass(resolveInfo);
			resolveInfo.getClassesConfiguration().stateMutational(serviceClass, getMethod());
		}
		return producingFields;
	}
	
	/**
	 * Returns the fm composition representation  of the instruction.
	 */
	public String toString() {
		String s = "";
		if(isAssignedLeftSideFieldname()) {
			s +=  getLeftSideFieldname() + " = ";
		}
		if(isAssignedHost()) {
			s += getHost() + "/";
		}
		s += getContext() + "::";
		s += getMethod() + "(";
		s += getParameterFields().toString() + ")";
		return s;
	}

	public boolean isServiceConstruct() {
		return getMethod().equals("__construct");
	}

}