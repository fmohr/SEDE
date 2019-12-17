package de.upb.sede.composition.typing;

import de.upb.sede.composition.CompileStep;


/**
 * Fills in the type journal.
 */
public class TypeChecker implements CompileStep<TCInput, TCOutput> {


    @Override
    public TCOutput step(TCInput input) {
        TypeCheckerModel stepModel;

        TCOutput output = new TCOutput();

        /*
         * The initial Context may be defined and is not empty.
         * Then inject the injected field types into it
         */
        if(input.getInitialTypeContext() != null && !input.getInitialTypeContext().isEmpty()) {
            TypeJournalPage initialTC = new TypeJournalPage(input.getInitialTypeContext());
            output.getJournal().injectFirstPage(initialTC);
        }

        stepModel = new TypeCheckerModel(output,
            input.getInstructions(),
            input.getLookupService());

        /*
         * Performs a line for line typecheck.
         */
        stepModel.checkAll();

        return output;
    }

}
