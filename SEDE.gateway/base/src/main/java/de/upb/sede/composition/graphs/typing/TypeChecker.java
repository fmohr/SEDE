package de.upb.sede.composition.graphs.typing;

import de.upb.sede.composition.CompStep;


/**
 * Fills in the type journal.
 */
public class TypeChecker implements CompStep<TypeCheckInput, TypeCheckOutput> {


    @Override
    public TypeCheckOutput compose(TypeCheckInput input) {
        TypeCheckStep step;
        TypeJournal journal = new TypeJournal();

        InstructionMethodResolver methodResolver = new InstructionMethodResolver();

        step = new TypeCheckStep(journal,
            methodResolver,
            input.getInstructions(),
            input.getLookupService());

        step.checkAll();

        TypeCheckOutput output = new TypeCheckOutput(journal, methodResolver);

        return output;
    }

}
