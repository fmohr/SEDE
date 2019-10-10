package de.upb.sede.gateway.engine;

import de.upb.sede.composition.graphs.nodes.IInstructionNode;
import de.upb.sede.composition.graphs.types.TypeClass;

import java.util.List;

public interface ITypeChecker {


    void injectTypeAssumption(String fieldName, TypeClass typeClass);

    void typeCheck(List<IInstructionNode> instructionBlock);

    TypeContext getTypeContext();

}
