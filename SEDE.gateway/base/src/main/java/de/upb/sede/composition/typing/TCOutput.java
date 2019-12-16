package de.upb.sede.composition.typing;


import de.upb.sede.composition.IMethodCognition;

public class TCOutput {

    private final TypeJournal journal;

    private final InstIndexMap<IMethodCognition> indexedMethodCog;

    public TCOutput(TypeJournal journal,
                    InstIndexMap<IMethodCognition> indexedMethodCog) {
        this.journal = journal;
        this.indexedMethodCog = indexedMethodCog;
    }

    TCOutput() {
        this(new TypeJournal(), new InstIndexMap<>());
    }

    public TypeJournal getJournal() {
        return journal;
    }

    public InstIndexMap<IMethodCognition> getMethodCognitionMap() {
        return indexedMethodCog;
    }
}
