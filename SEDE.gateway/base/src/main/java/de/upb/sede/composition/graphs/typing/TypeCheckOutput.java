package de.upb.sede.composition.graphs.typing;


public class TypeCheckOutput {

    private final TypeJournal journal;

    private final InstructionMethodResolver methodResolver;

    public TypeCheckOutput(TypeJournal journal,
                           InstructionMethodResolver methodResolver) {
        this.journal = journal;
        this.methodResolver = methodResolver;
    }

    public TypeJournal getJournal() {
        return journal;
    }

    public InstructionMethodResolver getMethodResolver() {
        return methodResolver;
    }
}
