package ai.services.composition.typing;

import ai.services.SDLLookupService;
import ai.services.composition.IFieldType;
import ai.services.composition.InstInput;
import ai.services.composition.InstructionIndexer;

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
