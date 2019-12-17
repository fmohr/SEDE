package de.upb.sede.composition.faa;

import de.upb.sede.composition.*;
import de.upb.sede.composition.IIndexedInstruction;
import de.upb.sede.composition.graphs.nodes.IInstructionNode;
import de.upb.sede.composition.graphs.types.TypeClass;
import de.upb.sede.composition.typing.TypeJournal;
import de.upb.sede.composition.typing.TypeJournalPage;
import de.upb.sede.exec.IMethodParameterDesc;
import de.upb.sede.util.TypeUtil;

import java.util.List;

public class FieldAccessAnalyser implements CompileStep<FAAInput, FAAOutput> {

    @Override
    public FAAOutput step(FAAInput input) {
        FAAOutput output = new FAAOutput();
        /*
         * Iterate over all instruction and check for field accesses.
         */
        for(IIndexedInstruction ii : input.getInstructions()) {
            try{
                IMethodCognition methodCognition = input.getInstMethodCognition().get(ii.getIndex());
                if(methodCognition == null) {
                    throw FieldAccessAnalysisException.noMethodCognition();
                }
                checkAssignment(ii, output);
                checkMethodContext(ii, output, methodCognition);
                checkParams(ii, output, methodCognition);
            } catch(FieldAccessAnalysisException faaException) {
                throw FieldAccessAnalysisException.stepError(ii, faaException);
            }
        }

        return output;
    }

    private void checkAssignment(IIndexedInstruction ii, FAAOutput output) {
        IInstructionNode inst = ii.getInstruction();
        Long index = ii.getIndex();
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
    private void checkParams(IIndexedInstruction ii, FAAOutput output, IMethodCognition methodCognition) {
        IInstructionNode inst = ii.getInstruction();
        Long index = ii.getIndex();
        /*
         * Each parameter that is a field is read:
         */
        List<IMethodParameterDesc> methodInput = methodCognition.getSignatureDesc().getInputs();
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
            output.getFAList().add(fieldRead);
            if(!parameterDesc.readOnly()) {
                /*
                 * The field might also be written onto:
                 */
                IFieldAccess fieldWrite = FieldAccess.builder()
                    .field(arg)
                    .index(index)
                    .accessType(IFieldAccess.AccessType.WRITE)
                    .build();
                output.getFAList().add(fieldWrite);
            }
        }
    }

    private void checkMethodContext(IIndexedInstruction ii, FAAOutput output, IMethodCognition methodCognition) {
        /*
         * If the context is a service instance,
         * the method is assumed to read its state (read) and might alter its state too (write)
         */
        IInstructionNode inst = ii.getInstruction();
        Long index = ii.getIndex();
        String context = inst.getContext();
        if(!inst.getContextIsFieldFlag()) {
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

        boolean methodChangesValue = ! methodCognition.getSignatureDesc().isPure();
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
