package de.upb.sede.composition.faa;

import de.upb.sede.composition.*;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;

public class FieldAccessExplorer {

    private final InstructionIndexer indexer;

    private final ICompositionCompilation compilation;

    public FieldAccessExplorer(ICompositionCompilation compilation) {
        this(new InstructionIndexer(compilation.getInstructions()), compilation);
    }

    public FieldAccessExplorer(InstructionIndexer indexer, ICompositionCompilation compilation) {
        this.indexer = indexer;
        this.compilation = compilation;
    }

    public Optional<Long> getPrevAccess(long currentInstIndex, final String field, final IFieldAccess.AccessType accessType) {
        return getPrevAccess(currentInstIndex, access -> access.getAccessType() == accessType && access.getField().equals(field));
    }

    public Optional<Long> getPrevAccess(long currentInstIndex,
                                        Predicate<IFieldAccess> accessTypePredicate) {
        Iterator<IIndexedInstruction> iterator = indexer.iteratePrevInst(currentInstIndex);
        while(iterator.hasNext()) {
            IIndexedInstruction prevInst = iterator.next();
            IStaticInstAnalysis analysis = compilation.getInstructionAnalysis()
                .get(prevInst.getIndex());
            Optional<IFieldAccess> optAccess = analysis.getFieldAccesses().stream()
                .filter(accessTypePredicate)
                .findAny();
            if(optAccess.isPresent()) {
                return Optional.of(prevInst.getIndex());
            }
        }
        return Optional.empty();
    }


}
