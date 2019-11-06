package de.upb.sede.composition.graphs.typing;

import de.upb.sede.ISDLLookupService;
import de.upb.sede.composition.CompStep;
import de.upb.sede.composition.InstructionIndexer;
import de.upb.sede.composition.graphs.nodes.IIndexedInstruction;
import de.upb.sede.composition.graphs.nodes.IInstructionNode;

public class TypeChecker implements CompStep {


    private TypingContext typingContext;

    private InstructionIndexer instructions;

    private ISDLLookupService lookupService;

    @Override
    public void compose() {
    }

    private void typeCheck(IIndexedInstruction indexedInstruction) {

        IInstructionNode inst = indexedInstruction.getInstruction();
        long index = indexedInstruction.getIndex();

        inst.getFieldName()
    }

}
