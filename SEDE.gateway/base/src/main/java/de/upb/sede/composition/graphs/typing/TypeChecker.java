package de.upb.sede.composition.graphs.typing;

import de.upb.sede.ISDLLookupService;
import de.upb.sede.composition.CompStep;
import de.upb.sede.composition.InstructionIndexer;
import de.upb.sede.composition.graphs.nodes.IIndexedInstruction;
import de.upb.sede.composition.graphs.nodes.IInstructionNode;

/**
 * Fills in the type journal.
 */
public class TypeChecker implements CompStep {

    private TypeJournal typeJournal;

    private InstructionIndexer instructions;

    private ISDLLookupService lookupService;

    @Override
    public void compose() {
    }

    private void typeCheckContext(IIndexedInstruction indexedInstruction) {

        IInstructionNode inst = indexedInstruction.getInstruction();
        long index = indexedInstruction.getIndex();
        TypeJournalPage currentPage = typeJournal.getPageAt(index);
        TypeJournalPage nextPage = typeJournal.getPageAfterInst(index);

        if(inst.getContextIsFieldFlag()) {
            /*
             * type check field
             */
            String contextFieldName = inst.getContext();
            FieldType fieldType = currentPage.getFieldType(contextFieldName);

        }
    }

}
