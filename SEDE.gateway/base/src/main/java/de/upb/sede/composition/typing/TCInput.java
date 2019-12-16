package de.upb.sede.composition.typing;

import de.upb.sede.ISDLLookupService;
import de.upb.sede.composition.InstructionIndexer;

public class TCInput {

    private final ISDLLookupService lookupService;

    private final InstructionIndexer instructions;

    public TCInput(ISDLLookupService lookupService, InstructionIndexer instructions) {
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
