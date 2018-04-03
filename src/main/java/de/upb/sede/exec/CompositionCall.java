package de.upb.sede.exec;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import de.upb.sede.exceptions.UnassignedFieldException;

public class CompositionCall implements Serializable {
	private static final long serialVersionUID = 8978650608812641956L;
	private static final List<?> UNDEFINED_OBJECTLIST = new ArrayList<>();
	private static final ExecutionGraph UNDEFINED_GRAPH_COMPOSITION = null;
	public static final CompositionCall UNDEFINED_COMPOSITION_CALL = new CompositionCall();
	
	private List<?> objectList;
	private ExecutionGraph composition;

	public CompositionCall() {
		objectList = UNDEFINED_OBJECTLIST;
		composition = UNDEFINED_GRAPH_COMPOSITION;
	}

	public List<?> getObjectList() {
		if (!isAssignedObjectList()) {
			throw new UnassignedFieldException(this, "objectList");
		}
		return objectList;
	}

	private boolean isAssignedObjectList() {
		return objectList != UNDEFINED_OBJECTLIST;
	}

	public void setObjectList(List<?> objectList) {
		this.objectList = Collections.unmodifiableList(Objects.requireNonNull(objectList));
	}

	public ExecutionGraph getExecutionGraph() {
		if (!isAssignedExecutionGraph()) {
			throw new UnassignedFieldException(this, "composition");
		}
		return composition;
	}

	private boolean isAssignedExecutionGraph() {
		return composition != UNDEFINED_GRAPH_COMPOSITION;
	}

	public void setExecutionGraph(ExecutionGraph composition) {
		this.composition = Objects.requireNonNull(composition);
	}
}
