package de.upb.sede.composition.graphs;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.upb.sede.exceptions.UnassignedFieldException;

/**
 * 
 * @author aminfaez
 *
 *	This node represents an instruction in a graphComposition.
 */
public final class InstructionNode extends BaseNode {
	
	private static final String unassignedValue = "UNDEFINED";
	
	private String fmInstruction;
    private String leftsideFieldname;
    private String host;
    private String context;
    private String method;
    private List<String> inputFieldnames;
    
	@SuppressWarnings("unchecked")
	public InstructionNode(String basedOnInstruction) { 
    		this.fmInstruction = Objects.requireNonNull(basedOnInstruction);
    		leftsideFieldname = unassignedValue;
    		host = unassignedValue;
    		context = unassignedValue;
    		method = unassignedValue;
    		inputFieldnames = Collections.EMPTY_LIST;
    	}
	
	
	public String getFmInstruction() {
		return this.fmInstruction;
	}
	
	
	/*
	 * stantard get, set, isAssigned methods. 
	 */
	
	public String getLeftSideFieldname() {
		if(!isAssignedLeftSideFieldname()) {
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
		if(!isAssignedHost()) {
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
		if(!isAssignedContext()) {
			throw new UnassignedFieldException(this, "context");
		}
		return context;
	}
	public void setContext(String context) {
		this.context = Objects.requireNonNull(context);
	}
	public boolean isAssignedContext() {
		return context != unassignedValue;
	}
	
	public String getMethod() {
		if(!isAssignedMethod()) {
			throw new UnassignedFieldException(this, "method");
		}
		return method;
	}
	public void setMethod(String method) {
		this.method = Objects.requireNonNull(method);
	}
	public boolean isAssignedMethod() {
		return method != unassignedValue;
	}

	public List<String> getInputFieldnames() {
		return inputFieldnames;
	}
	public void setInputFieldnames(List<String> inputFieldNames) {
		inputFieldnames =  Collections.unmodifiableList(inputFieldNames);
	}
    	
    
}