package de.upb.sede.composition.graphs;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.upb.sede.composition.FMCompositionParser;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.ResolvePolicy;
import de.upb.sede.exceptions.UnassignedFieldException;

/**
 * 
 * @author aminfaez
 *
 *  This node represents an instruction in a graphComposition which was parsed from a fmInstruction.
 *  This can either be a method invocation (ServiceInvocationNode) or a Service creation (ServiceCreationNode).
 */
public abstract class InstructionNode extends BaseNode {

	private static final String unassignedValue = "UNDEFINED";

	private String fmInstruction;
	private String leftsideFieldname;
	private String host;
	private String context;
	private String method;
	
	/**
	 * Parameters for method or constructor invocation.
	 * The order of the parameters has to be kept.
	 * May contain field-names referencing to data or constants like numbers or strings.
	 * e.g.: ["a1", "b1", "10", "\"a\""]
	 * The first two are fieldnames. The third is a constant number. The fourth is a constant string.
	 * 
	 * 
	 *  The list itself is read-only.
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

	public void setParameterFields(List<String> parameterFields) {
		parameterFields = Collections.unmodifiableList(parameterFields);
	}
	
	@Override
	boolean changesState(String fieldname, ClassesConfig configuration) {
		if(isAssignedLeftSideFieldname()) {
			return getLeftSideFieldname().equals(fieldname);
		}
		else {
			return false;
		}
	}
	
	void resolveDependency(String field, GraphComposition graph, ClassesConfig configuration) {
		/*
		 * Get all the node that produces/changes the field this node is depending on.
		 */
		List<BaseNode> dependingFromNodes = graph.getChangers(field, configuration);
		
		if(dependingFromNodes.size() > 0) {
			/*
			 * If there is at least one node that changes the state of the field, it means that the data is already available.
			 */
			
		} else {
			
		}
	}

	@Override
	void expand(GraphComposition graph, ClassesConfig configuration, ResolvePolicy policy) {
		/*
		 * Add param dependencies.
		 */
		for(String parameter : parameterFields) {
			/*
			 * Get all the node that produces/changes the parameter this node is depended on.
			 */
			List<BaseNode> dependingFromNodes = graph.getChangers(parameter, configuration);
			
			if(dependingFromNodes.size() > 0) {
				/*
				 * If there is at least one node  
				 */
				
			}
			/*
			 * Connect every node this node is depending on to this one. 
			 */
			dependingFromNodes.forEach(dependsOn -> graph.connectNodes(dependsOn, this));
			
			if(FMCompositionParser.isConstant(parameter)) {
				
			} else {
				
			}
		}
		
	}

}