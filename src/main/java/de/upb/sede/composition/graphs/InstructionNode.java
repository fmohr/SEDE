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

	private final int instructionIndex;
	private String fmInstruction;
	private String leftsideFieldname;
	private String host;
	private String context;
	private String method;
	
	/**
	 * Parameters for method or constructor invocation.
	 * The order of the parameters has to be kept unchanged.
	 * May either contain field-names referencing to data or else constants like numbers or strings.
	 * e.g.: ["a1", "b1", "10", "\"a\""]
	 * The first two are fieldnames. The third is a constant number. The fourth is a constant string.
	 * 
	 * 
	 *  The list itself is read-only.
	 */
	private List<String> parameterFields;

	@SuppressWarnings("unchecked")
	public InstructionNode(int instructionIndex, String basedOnInstruction, String context, String method) {
		this.fmInstruction = Objects.requireNonNull(basedOnInstruction);
		leftsideFieldname = unassignedValue;
		host = unassignedValue;
		parameterFields = Collections.EMPTY_LIST;
		this.context = Objects.requireNonNull(context);
		this.method = Objects.requireNonNull(method);
		this.instructionIndex = instructionIndex;
	}

	public String getFmInstruction() {
		return this.fmInstruction;
	}

	/*
	 * standard get, set, isAssigned methods.
	 */
	
	public int getInstructionIndex() {
		return instructionIndex;
	}

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
		if(parameterFields.getClass().getName().equals("java.util.Collections$UnmodifiableRandomAccessList") || 
				parameterFields.getClass().getName().equals("java.util.Collections$EmptyList")	) {
			this.parameterFields = parameterFields;
		}
		else {
			this.parameterFields = Collections.unmodifiableList(parameterFields);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((context == null) ? 0 : context.hashCode());
		result = prime * result + ((fmInstruction == null) ? 0 : fmInstruction.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + instructionIndex;
		result = prime * result + ((leftsideFieldname == null) ? 0 : leftsideFieldname.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((parameterFields == null) ? 0 : parameterFields.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof InstructionNode)) {
			return false;
		}
		InstructionNode other = (InstructionNode) obj;
		if (context == null) {
			if (other.context != null) {
				return false;
			}
		} else if (!context.equals(other.context)) {
			return false;
		}
		if (fmInstruction == null) {
			if (other.fmInstruction != null) {
				return false;
			}
		} else if (!fmInstruction.equals(other.fmInstruction)) {
			return false;
		}
		if (host == null) {
			if (other.host != null) {
				return false;
			}
		} else if (!host.equals(other.host)) {
			return false;
		}
		if (instructionIndex != other.instructionIndex) {
			return false;
		}
		if (leftsideFieldname == null) {
			if (other.leftsideFieldname != null) {
				return false;
			}
		} else if (!leftsideFieldname.equals(other.leftsideFieldname)) {
			return false;
		}
		if (method == null) {
			if (other.method != null) {
				return false;
			}
		} else if (!method.equals(other.method)) {
			return false;
		}
		if (parameterFields == null) {
			if (other.parameterFields != null) {
				return false;
			}
		} else if (!parameterFields.equals(other.parameterFields)) {
			return false;
		}
		return true;
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