package de.upb.sede.composition.faa;

import de.upb.sede.composition.*;
import de.upb.sede.util.MappedList;
import de.upb.sede.util.MappedListView;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FieldAccessExplorer {

    private final InstructionIndexer indexer;

    private final Map<Long, List<IFieldAccess>> instAccesses;

    private final Map<String, IFieldAccessCollection> fieldAccesses;

    public FieldAccessExplorer(ICompositionCompilation compilation) {
        this(new InstructionIndexer(compilation.getInstructions()), compilation);
    }

    public FieldAccessExplorer(InstructionIndexer indexer, ICompositionCompilation compilation) {
        this.indexer = indexer;
        this.instAccesses = new MappedListView<>(
            compilation.getStaticInstAnalysis(),
            sia -> sia.getInstruction().getIndex(),
            IStaticInstAnalysis::getInstFieldAccesses);
        this.fieldAccesses = new MappedList<>(compilation.getFieldAccesses(), fAC -> fAC.getFieldType().getFieldname());
    }

    public Optional<Long> getPrevAccess(long currentInstIndex, final String field, final IFieldAccess.AccessType accessType) {
        return getPrevAccess(currentInstIndex, access -> access.getAccessType() == accessType && access.getField().equals(field));
    }

    public Optional<Long> getPrevAccess(long currentInstIndex,
                                        Predicate<IFieldAccess> accessTypePredicate) {
        Iterator<IIndexedInstruction> iterator = indexer.iteratePrevInst(currentInstIndex);
        while(iterator.hasNext()) {
            IIndexedInstruction prevInst = iterator.next();
            List<IFieldAccess> fieldAccesses = this.instAccesses
                .get(prevInst.getIndex());
            Optional<IFieldAccess> optAccess = fieldAccesses.stream()
                .filter(accessTypePredicate)
                .findAny();
            if(optAccess.isPresent()) {
                return Optional.of(prevInst.getIndex());
            }
        }
        return Optional.empty();
    }


    public Stream<IFieldAccess> accessesAtInst(final long currentInstIndex,
                                               final Predicate<IFieldAccess> accessTypePredicate) {
        return instAccesses
            .get(currentInstIndex)
            .stream().filter(accessTypePredicate);
    }

    public List<IFieldAccess> accessesToFieldName(String fieldName) {
        return fieldAccesses.get(fieldName).getFieldAccesses();
    }

}
