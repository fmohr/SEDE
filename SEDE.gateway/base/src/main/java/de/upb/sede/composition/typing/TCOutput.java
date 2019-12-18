package de.upb.sede.composition.typing;


import de.upb.sede.composition.IMethodCognition;

import java.util.Map;

public class TCOutput {

    private final TypeJournal journal;

    private final IndexMap<IMethodCognition> indexedMethodCog;

    public TCOutput(TypeJournal journal,
                    IndexMap<IMethodCognition> indexedMethodCog) {
        this.journal = journal;
        this.indexedMethodCog = indexedMethodCog;
    }

    TCOutput() {
        this(new TypeJournal(), new IndexMap<>());
    }

    public TypeJournal getJournal() {
        return journal;
    }

    IndexMap<IMethodCognition> getMethodCognitionIndexMap() {
        return indexedMethodCog;
    }

    public Map<Long, IMethodCognition> getMethodCognitionMap() {
        return indexedMethodCog;
    }
}
