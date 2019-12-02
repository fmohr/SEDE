package de.upb.sede.composition.graphs.typing;

import de.upb.sede.composition.CompStep;


/**
 * Fills in the type journal.
 */
public class TypeChecker implements CompStep<TypeCheckInput, TypeCheckOutput> {


    @Override
    public TypeCheckOutput compose(TypeCheckInput input) {
        TypeCheckStep step;


        TypeCheckOutput output = new TypeCheckOutput();

        step = new TypeCheckStep(output,
            input.getInstructions(),
            input.getLookupService());

        step.checkAll();

        return output;
    }

}
