package de.upb.sede.composition.typing;


import de.upb.sede.composition.InstOutputIterator;
import de.upb.sede.composition.InstructionIndexer;

import java.util.Map;
import java.util.function.Function;

public class TCOutput_2 {

    private final TypeJournal journal;

    private final InstOutputIterator<TCOutput> outputs;

    TCOutput_2(InstructionIndexer indexer) {
        outputs = new InstOutputIterator<>(
            (Function<TCOutput, TCOutput>) TCOutput::new,
            indexer);
        journal = new TypeJournal();
    }

    public TypeJournal getJournal() {
        return journal;
    }


    public InstOutputIterator<TCOutput> typingOutputs() {
        return outputs;
    }

    public Map<Long, MethodCognition> getMethodCognitionMap() {
        return null; //TODO
    }
}
