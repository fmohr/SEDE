package de.upb.sede.composition.graphs;

import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.composition.graphs.types.TypeClass;
import de.upb.sede.util.TypeUtil;

/**
 * This class determines the type for each fieldname.
 */
public class FieldType {
	private final BaseNode producer;
	private final String fieldname;
	private final TypeClass typeClass;
	private final String typeName;
	private final boolean changedState;

	public FieldType(BaseNode producer, String fieldname, TypeClass typeClass, String typeName, boolean changesState) {
		this.producer = producer;
		this.fieldname = fieldname;
		this.typeClass = typeClass;
		this.typeName = typeName;
		this.changedState = changesState;
		if ((isServiceInstance() || isServiceInstanceHandle()) && !changesState) {
//			throw new RuntimeException("Coding error: each new service instance fieldtype has to change its state");
		}
	}

	public BaseNode getProducer() {
		return producer;
	}

	public String getFieldname() {
		return fieldname;
	}

	public TypeClass getTypeClass() {
		return typeClass;
	}

	public String getTypeName() {
		return typeName;
	}

	public boolean isChangedState() {
		return changedState;
	}

	public boolean isServiceInstance() {
		return TypeUtil.isService(typeClass);
	}

	public boolean isServiceInstanceHandle() {
		return TypeUtil.isRefType(typeClass);
	}

	public boolean isSemanticData() {
		return TypeUtil.isData(typeClass);
	}

	public boolean isRealData() {
        return TypeUtil.isData(typeClass);
	}

	public boolean isPrimitive() {
		return TypeUtil.isData(typeClass);
	}

	public String toString() {
		return fieldname + " type=(" + typeClass + ", " + typeName + ")" + (changedState ? "" : "...");
	}

	public FieldType clone(BaseNode newProducer, boolean changesState) {
		return new FieldType(newProducer, getFieldname(), getTypeClass(), getTypeName(), changesState);
	}

}
