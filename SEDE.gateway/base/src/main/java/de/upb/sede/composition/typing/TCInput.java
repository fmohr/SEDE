package de.upb.sede.composition.typing;

import de.upb.sede.ISDLLookupService;
import de.upb.sede.composition.IFieldType;
import de.upb.sede.composition.InstructionIndexer;

import java.util.List;

public class TCInput {

    private final ISDLLookupService lookupService;

    private final InstructionIndexer instructions;

    private final List<IFieldType> initialTypeContext;

    public TCInput(ISDLLookupService lookupService, InstructionIndexer instructions, List<IFieldType> initialTypeContext) {
        this.lookupService = lookupService;
        this.instructions = instructions;
        this.initialTypeContext = initialTypeContext;
    }

    public ISDLLookupService getLookupService() {
        return lookupService;
    }

    public InstructionIndexer getInstructions() {
        return instructions;
    }

    public List<IFieldType> getInitialTypeContext() {
        return initialTypeContext;
    }
}
