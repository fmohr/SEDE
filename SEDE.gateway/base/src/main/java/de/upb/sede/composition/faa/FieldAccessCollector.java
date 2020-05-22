package de.upb.sede.composition.faa;

import de.upb.sede.composition.*;
import de.upb.sede.composition.typing.TCOutput;
import de.upb.sede.util.DeepImmutableCopier;

import java.util.*;

import static javax.swing.UIManager.get;

public class FieldAccessCollector
    extends BlockWiseCompileStep<FieldAccessCollector.FACInput, FieldAccessCollector.FACOutput> implements Comparator<IFieldAccess> {

    @Override
    protected FACOutput initializeOutput() {
        return new FACOutput();


    }

    @Override
    protected void stepBlock() {
        for (IIndexedInstruction inst : this.getInput().indexer) {
            Long instIndex = inst.getIndex();
            TCOutput.ContextInfo callContext = getInput().tcOutput.get(instIndex).getContext();
            TCOutput.FieldTC fieldTC = getInput().tcOutput.get(instIndex).getFieldTC();
            if(!callContext.isStatic()) {
                String field = inst.getInstruction().getContext();
                final IFieldType fieldType = FieldType.builder()
                    .fieldname(field)
                    .type(fieldTC.getFieldType(field))
                    .build();
                getInput().fieldAccess.get(instIndex).getFAList()
                    .stream()
                    .filter(access -> access.getField().equals(field))
                    .forEach(access -> getOutput().recordFieldAccess(fieldType, access));
            }

        }

        // sort the field accesses by:
        //  - index
        //  - READ < WRITE < ASSIGN
        for (IFieldAccessCollection fieldAccessCollection : getOutput().getFieldAccessCollection().values()) {
            fieldAccessCollection.getFieldAccesses().sort(this);
        }
    }

    @Override
    public int compare(IFieldAccess o1, IFieldAccess o2) {
        return o1.compareTo(o2);
    }


    public static class FACInput {

        private final InstructionIndexer indexer;

        private final Map<Long, TCOutput> tcOutput;

        private final Map<Long, FAAOutput> fieldAccess;

        public FACInput(InstructionIndexer indexer, Map<Long, TCOutput> tcOutput, Map<Long, FAAOutput> fieldAccess) {
            this.indexer = indexer;
            this.tcOutput = tcOutput;
            this.fieldAccess = fieldAccess;
        }

    }

    public static class FACOutput {

        private final Map<String, MutableFieldAccessCollection> fieldAccessCollection = new HashMap<>();

        private void recordFieldAccess(IFieldType field, IFieldAccess access) {
            MutableFieldAccessCollection fAG = this.fieldAccessCollection.computeIfAbsent(field.getFieldname(), f -> MutableFieldAccessCollection.create());
            fAG.setFieldType(field);
            fAG.getFieldAccesses().add(access);
        }

        public Map<String, IFieldAccessCollection> getFieldAccessCollection() {
            return DeepImmutableCopier.copyAsImmutable(fieldAccessCollection);
        }
    }

}
