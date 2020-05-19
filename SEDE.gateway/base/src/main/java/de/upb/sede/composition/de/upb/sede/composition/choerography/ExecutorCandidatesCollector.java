package de.upb.sede.composition.de.upb.sede.composition.choerography;

import de.upb.sede.composition.*;
import de.upb.sede.exec.ExecutorHandle;
import de.upb.sede.gateway.ExecutorSupplyCoordinator;

import java.util.ArrayList;
import java.util.List;

public class ExecutorCollector extends
    InstWiseCompileStep
        <ExecutorCollector.Input, ExecutorCollector.Output> {

    @Override
    protected Output initializeOutput() {
        return new Output();
    }

    @Override
    public void stepInst() {
        IMethodResolution currentMethod = getInput().getCurrentMethod();
        String serviceUsed = currentMethod.getMethodRef().getServiceRef().getRef().getQualifier();
        List<ExecutorHandle> executorHandles = getInput().getExecutorSupplyCoordinator().supplyExecutor(serviceUsed);
    }

    class Input implements InstInput {

        private final InstructionIndexer indexer;

        private final ICompositionCompilation cc;

        private final ExecutorSupplyCoordinator executorSupplyCoordinator;

        Input(ICompositionCompilation cc, ExecutorSupplyCoordinator executorSupplyCoordinator) {
            this.indexer = new InstructionIndexer(cc.getInstructions());
            this.cc = cc;
            this.executorSupplyCoordinator = executorSupplyCoordinator;
        }

        @Override
        public InstructionIndexer getInstructions() {
            return indexer;
        }

        public ExecutorSupplyCoordinator getExecutorSupplyCoordinator() {
            return executorSupplyCoordinator;
        }

        IMethodResolution getCurrentMethod() {
            Long currentIndex = getCurrentInstruction().getIndex();
            return cc.getInstructionAnalysis().get(currentIndex).getMethodResolution();
        }
    }

    static class Output {

        private final List candidates = new ArrayList();


    }
}
