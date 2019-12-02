package de.upb.sede.composition.graphs.typing;


public class TypeCheckOutput {

    private final TypeJournal journal;

    private final InstIndexMap<MethodCognition> indexedMethodCog;

    public TypeCheckOutput(TypeJournal journal,
                           InstIndexMap<MethodCognition> indexedMethodCog) {
        this.journal = journal;
        this.indexedMethodCog = indexedMethodCog;
    }

    TypeCheckOutput() {
        this(new TypeJournal(), new InstIndexMap<>());
    }

    public TypeJournal getJournal() {
        return journal;
    }

    public InstIndexMap<MethodCognition> getMethodCognitionMap() {
        return indexedMethodCog;
    }
}
