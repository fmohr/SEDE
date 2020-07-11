package de.upb.sede.composition.faa;

import de.upb.sede.composition.*;
import de.upb.sede.composition.typing.TCOutput;
import de.upb.sede.util.DeepImmutableCopier;
import de.upb.sede.util.MappedList;

import java.util.*;
import java.util.stream.Collectors;

import static javax.swing.UIManager.get;

public class FieldAnalysisCollector
    extends BlockWiseCompileStep<FieldAnalysisCollector.FACInput, FieldAnalysisCollector.FACOutput> {

    @Override
    protected FACOutput initializeOutput() {
        return new FACOutput();


    }

    @Override
    protected void stepBlock() {
        gatherInitialFields();
        for (IIndexedInstruction inst : this.getInput().indexer) {
            Long instIndex = inst.getIndex();
            TCOutput.FieldTC fieldTC = getInput().tcOutput.get(instIndex).getFieldTC();
            List<IFieldAccess> faList = getInput().fieldAccess.get(instIndex).getFAList();

            for (IFieldAccess fieldAccess : faList) {
                String field = fieldAccess.getField();
                final IFieldType fieldType = FieldType.builder()
                    .fieldname(field)
                    .type(fieldTC.getFieldType(field))
                    .build();
                getOutput().setNewType(inst.getIndex(), fieldType);
            }
            faList.forEach(access -> getOutput().recordFieldAccess(access));

        }

        Comparator<IFieldAccess> comparator = FieldAccessUtil.getComparator(getInput().indexer);
        // sort the field accesses by:
        //  - index
        //  - READ < WRITE < ASSIGN
        for (IFieldAnalysis fieldAccessCollection : getOutput().fieldAnalysisMap.values()) {
            fieldAccessCollection.getFieldAccesses().sort(comparator);
        }
    }

    private void gatherInitialFields() {
        for (IFieldType initialField : getInput().initialContext) {
            getOutput().addInitialField(initialField);
        }
    }


    public static class FACInput {

        private final InstructionIndexer indexer;

        private final List<IFieldType> initialContext;

        private final Map<Long, TCOutput> tcOutput;

        private final Map<Long, IFACOutput> fieldAccess;

        public FACInput(InstructionIndexer indexer, List<IFieldType> initialContext, Map<Long, TCOutput> tcOutput, Map<Long, IFACOutput> fieldAccess) {
            this.indexer = indexer;
            this.initialContext = initialContext;
            this.tcOutput = tcOutput;
            this.fieldAccess = fieldAccess;
        }

    }

    public static class FACOutput {

        private final Map<String, MutableFieldAnalysis> fieldAnalysisMap = new HashMap<>();

        private MutableFieldAnalysis getFieldAnalysis(String fieldname) {
            return this.fieldAnalysisMap.computeIfAbsent(fieldname, f -> {
                MutableFieldAnalysis fieldAnalysis = MutableFieldAnalysis.create();
                fieldAnalysis.setFieldname(fieldname);
                return fieldAnalysis;
            });
        }

        private void addInitialField(IFieldType initialField) {
            MutableFieldAnalysis fieldAnalysis = getFieldAnalysis(initialField.getFieldname());
            fieldAnalysis.setInitialType(initialField.getType());

        }

        private void setNewType(Long index, IFieldType newType) {
            getFieldAnalysis(newType.getFieldname()).putInstTyping(index, newType.getType());
        }

        private void recordFieldAccess(IFieldAccess access) {
            MutableFieldAnalysis fAG = getFieldAnalysis(access.getField());
            fAG.getFieldAccesses().add(access);
        }

        public Map<String, IFieldAnalysis> getFieldAnalysis() {
            List<IFieldAnalysis> collection = fieldAnalysisMap.values().stream()
                .map(DeepImmutableCopier::copyAsImmutable)
                .map(o -> (IFieldAnalysis) o)
                .collect(Collectors.toList());
            return new MappedList<>(collection, IFieldAnalysis::getFieldname);
        }
    }

}
