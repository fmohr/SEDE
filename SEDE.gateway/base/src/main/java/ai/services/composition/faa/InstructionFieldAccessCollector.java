package de.upb.sede.composition.faa;

import de.upb.sede.composition.*;
import de.upb.sede.composition.graphs.nodes.IInstructionNode;
import de.upb.sede.composition.typing.TCOutput;
import de.upb.sede.exec.IMethodDesc;
import de.upb.sede.exec.IMethodParameterDesc;

import java.util.List;

public class InstructionFieldAccessCollector extends InstWiseCompileStep<IFACInput, IFACOutput> {

    public InstructionFieldAccessCollector() {

    }

    @Override
    protected IFACOutput initializeOutput() {
        return new IFACOutput();
    }

    @Override
    public void stepInst() {
        try{
            checkAssignment();
            checkParams();
            checkMethodContext();
        } catch(FieldAccessAnalysisException faaException) {
            throw FieldAccessAnalysisException.stepError(getCurrentInstruction(), faaException);
        }
    }


    private void checkAssignment() {
        IFACOutput output = getInstOutput();
        IInstructionNode inst = getCurrentInstruction().getInstruction();
        Long index = getCurrentInstruction().getIndex();
        if (inst.isAssignment()) {
            /*
             * Instruction is an assignment to a field:
             */
            String assignedField = inst.getFieldName();
            IFieldAccess fieldAssignment = FieldAccess.builder()
                .field(assignedField)
                .accessType(IFieldAccess.AccessType.ASSIGN)
                .index(index).build();
            output.getFAList().add(fieldAssignment);
        }
    }

    private void checkParams() {
        IInstructionNode inst = getCurrentInstruction().getInstruction();
        Long index = getCurrentInstruction().getIndex();
        TCOutput tcOutput = getInput().getTcOutput().get(index);
        IFACOutput current = getInstOutput();
        /*
         * Each parameter that is a field is read:
         */
        List<IMethodParameterDesc> methodInput = tcOutput.getMethodInfo().getMethodDesc().getInputs();
        int inputParamSize = methodInput.size();
        for (int i = 0; i < inputParamSize; i++) {
            String arg = inst.getParameterFields().get(i);
            if(FMCompositionParser.isConstant(arg)) {
                /*
                 * The parameter is a constant. So access is irrelevant..
                 */
                continue;
            }
            IMethodParameterDesc parameterDesc = methodInput.get(i);
            IFieldAccess fieldRead = FieldAccess.builder()
                .field(arg)
                .index(index)
                .accessType(IFieldAccess.AccessType.READ)
                .build();
            getInstOutput().getFAList().add(fieldRead);
            if(!parameterDesc.readOnly()) {
                /*
                 * The field might also be written onto:
                 */
                IFieldAccess fieldWrite = FieldAccess.builder()
                    .field(arg)
                    .index(index)
                    .accessType(IFieldAccess.AccessType.WRITE)
                    .build();
                getInstOutput().getFAList().add(fieldWrite);
            }
        }
    }

    private void checkMethodContext() {
        /*
         * If the context is a service instance,
         * the method is assumed to read its state (read) and might alter its state too (write)
         */
        IInstructionNode inst = getCurrentInstruction().getInstruction();
        Long index = getCurrentInstruction().getIndex();
        IFACOutput output = getOutput().getCurrent();
        String context = inst.getContext();
        IMethodDesc methodDesc = getInput().getTcOutput().get(index).getMethodInfo().getMethodDesc();
        if(!inst.getContextIsFieldFlag() || methodDesc.isContextFree()) {
            /*
             * The context is a service qualifier, like: `a.b.Classifier`
             * There is nothing accessed.
             */
            return;
        }

        IFieldAccess contextRead = FieldAccess.builder()
            .field(context)
            .index(index)
            .accessType(IFieldAccess.AccessType.READ)
            .build();
        output.getFAList().add(contextRead);


        boolean methodChangesValue = ! methodDesc.isPure();
        if(methodChangesValue) {
            IFieldAccess fieldWrite = FieldAccess.builder()
                .field(context)
                .index(index)
                .accessType(IFieldAccess.AccessType.WRITE)
                .build();
            output.getFAList().add(fieldWrite);
        }
    }



}
