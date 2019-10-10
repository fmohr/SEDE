package de.upb.sede.gateway.engine;

import de.upb.sede.composition.graphs.nodes.IInstructionNode;
import de.upb.sede.composition.graphs.types.TypeClass;

import java.util.List;

public class TypeChecker implements ITypeChecker {

    TypeContext typeContext = new TypeContext();

    public TypeChecker() {
    }

    public TypeChecker(TypeContext typeContext) {
        this.typeContext = typeContext;
    }

    @Override
    public void injectTypeAssumption(String fieldName, TypeClass typeClass) {
        FieldVersion latestField = new FieldVersion(fieldName);
        typeContext.getTypeAssumptions().put(latestField, typeClass);
    }

    @Override
    public void typeCheck(List<IInstructionNode> instructionBlock) {

    }

    @Override
    public TypeContext getTypeContext() {
        return typeContext;
    }
}
