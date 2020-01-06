package de.upb.sede.composition.typing;

import de.upb.sede.SDLLookupService;
import de.upb.sede.composition.IFieldType;
import de.upb.sede.composition.InstInput;
import de.upb.sede.composition.InstructionIndexer;

import java.util.List;

public class TCInput implements InstInput {

    private final SDLLookupService lookupService;

    private final InstructionIndexer instructions;

    private final List<IFieldType> initialTypeContext;

    public TCInput(SDLLookupService lookupService, InstructionIndexer instructions, List<IFieldType> initialTypeContext) {
        this.lookupService = lookupService;
        this.instructions = instructions;
        this.initialTypeContext = initialTypeContext;
    }

    public SDLLookupService getLookupService() {
        return lookupService;
    }

    public InstructionIndexer getInstructions() {
        return instructions;
    }

    public List<IFieldType> getInitialTypeContext() {
        return initialTypeContext;
    }
}
