package de.upb.sede.composition.graphs.typing;

import de.upb.sede.ISDLLookupService;
import de.upb.sede.composition.InstructionIndexer;

public class TypeCheckInput {

    private final ISDLLookupService lookupService;

    private final InstructionIndexer instructions;

    public TypeCheckInput(ISDLLookupService lookupService, InstructionIndexer instructions) {
        this.lookupService = lookupService;
        this.instructions = instructions;
    }

    public ISDLLookupService getLookupService() {
        return lookupService;
    }

    public InstructionIndexer getInstructions() {
        return instructions;
    }
}
