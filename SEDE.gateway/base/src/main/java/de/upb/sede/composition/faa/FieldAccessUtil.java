package de.upb.sede.composition.faa;

import de.upb.sede.composition.*;
import de.upb.sede.composition.types.TypeClass;
import de.upb.sede.util.MappedList;
import de.upb.sede.util.MappedListView;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FieldAccessUtil {

    private final InstructionIndexer indexer;

    private final Map<Long, List<IFieldAccess>> instAccesses;

    private final Map<String, IFieldAnalysis> fieldAnalysis;

    public FieldAccessUtil(InstructionIndexer indexer, ICompositionCompilation compilation) {
        this.indexer = indexer;
        this.instAccesses = new MappedListView<>(
            compilation.getStaticInstAnalysis(),
            sia -> sia.getInstruction().getIndex(),
            IStaticInstAnalysis::getInstFieldAccesses);
        this.fieldAnalysis = new MappedList<>(compilation.getFields(), IFieldAnalysis::getFieldname);
    }

    public Optional<Long> getPrevWriteOrAssign(long currentInstIndex, final String field) {
        return getPrevAccess(currentInstIndex, access -> access.getAccessType().isAssignOrWrite() && access.getField().equals(field));
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
        return fieldAnalysis.get(fieldName).getFieldAccesses();
    }

    public Stream<IFieldAnalysis> initialFields() {
        return fieldAnalysis.values().stream()
            .filter(IFieldAnalysis::isInjected);
    }

    public Stream<IFieldAnalysis> allFields() {
        return fieldAnalysis.values().stream();
    }

    public Stream<IFieldAccess> accessesAtInst(final long currentInstIndex) {
        return accessesAtInst(currentInstIndex, field -> true);
    }

    public boolean isInitialField(String field) {
        return initialFields().anyMatch(f -> f.getFieldname().equals(field));
    }

    public IFieldAnalysis getFieldAnalysis(String field) {
        return fieldAnalysis.get(field);
    }

    public TypeClass getFieldTypingAt(Long instIndex, String field) {
        return fieldAnalysis.get(field).getInstTyping().get(instIndex);
    }

    public TypeClass getInitialTyping(String field) {
        return fieldAnalysis.get(field).getInitialType().get();
    }

    public Optional<Long> getPrevFieldAccess(Long instIndex, String fieldname) {
        return getPrevAccess(instIndex, access -> access.getField().equals(fieldname));
    }


    public boolean hasWriteAccess(String fieldname) {
        IFieldAnalysis fieldAnalysis = this.fieldAnalysis.get(fieldname);
        return fieldAnalysis.getFieldAccesses()
            .stream()
            .map(IFieldAccess::getAccessType)
            .anyMatch(IFieldAccess.AccessType::isAssignOrWrite);
    }

    public IFieldAccess lastAccess(String fieldname) {
        return lastAccess(fieldname, iFieldAccess -> true);
    }

    public IFieldAccess lastAccess(String fieldname,
                                          Predicate<IFieldAccess> fieldAccessPredicate) {
        IFieldAnalysis fieldAnalysis = this.fieldAnalysis.get(fieldname);
        Iterator<IFieldAccess> iterator = fieldAnalysis.getFieldAccesses()
            .stream()
            .filter(fieldAccessPredicate)
            .iterator();

        IFieldAccess last = null;
        while(iterator.hasNext()) {
            last = iterator.next();
        }
        return last;
    }

    public TypeClass fieldTypePreInstIndex(String fieldname, Long instIndex) {
        IFieldAnalysis field = fieldAnalysis.get(fieldname);
        if(instIndex == null) {
            return field.getInitialType().orElse(null);
        }
        Iterator<IIndexedInstruction> it = indexer.iteratePrevInst(instIndex);
        while(it.hasNext()) {
            IIndexedInstruction prevInst = it.next();
            if(field.getInstTyping().containsKey(prevInst.getIndex())) {
                return field.getInstTyping().get(prevInst.getIndex());
            }
        }
        return field.getInitialType().orElse(null);
    }

    public TypeClass fieldTypeAfterInstIndex(String fieldname, Long instIndex) {
        IFieldAnalysis field = fieldAnalysis.get(fieldname);
        if(instIndex != null && field.getInstTyping().containsKey(instIndex)) {
            return field.getInstTyping().get(instIndex);
        }
        return fieldTypePreInstIndex(fieldname, instIndex);
    }



    public TypeClass resultingFieldType(IFieldAnalysis field) {
        IFieldAccess lastAccess = lastAccess(field.getFieldname());
        if(lastAccess == null) {
            throw new IllegalArgumentException("Field has not resulting type " + field);
        }
        Long lastAccessInstIndex = lastAccess.getIndex();
        TypeClass typeClass = field.getInstTyping().get(lastAccessInstIndex);
        return Objects.requireNonNull(typeClass, "Field has no typing for the last access index: " + lastAccessInstIndex + " field: " + field);
    }

    public static Comparator<IFieldAccess> getComparator(InstructionIndexer indexer) {
        return (o1, o2) -> {
            if(o1.getIndex().equals(o2.getIndex())) {
                return o1.getAccessType().compareTo(o2.getAccessType());
            } else {
                return indexer.comesBefore(o1.getIndex(), o2.getIndex()) ? -1 : 1;
            }
        };
    }
}
