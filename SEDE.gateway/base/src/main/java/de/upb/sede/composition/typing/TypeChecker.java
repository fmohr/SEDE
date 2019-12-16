package de.upb.sede.composition.typing;

import de.upb.sede.composition.CompileStep;


/**
 * Fills in the type journal.
 */
public class TypeChecker implements CompileStep<TCInput, TCOutput> {


    @Override
    public TCOutput step(TCInput input) {
        TypeCheckStep step;


        TCOutput output = new TCOutput();

        step = new TypeCheckStep(output,
            input.getInstructions(),
            input.getLookupService());

        step.checkAll();

        return output;
    }

}
