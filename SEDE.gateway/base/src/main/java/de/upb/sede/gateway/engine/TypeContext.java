package de.upb.sede.gateway.engine;


import de.upb.sede.composition.graphs.types.TypeClass;

public class TypeContext {

    FieldVersionMap<TypeClass> typeAssumptions = new FieldVersionMap<>();

    public FieldVersionMap<TypeClass> getTypeAssumptions() {
        return typeAssumptions;
    }

    public void setTypeAssumptions(FieldVersionMap<TypeClass> typeAssumptions) {
        this.typeAssumptions = typeAssumptions;
    }
}
