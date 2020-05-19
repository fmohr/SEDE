package de.upb.sede.composition.de.upb.sede.composition.choerography;

import de.upb.sede.composition.*;
import de.upb.sede.composition.faa.FieldAccessExplorer;
import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.exec.IExecutorHandle;
import de.upb.sede.gateway.ExecutorSupplyCoordinator;
import de.upb.sede.requests.resolve.beta.Choreography;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExecutorCandidatesCollector extends
    InstWiseCompileStep
        <ExecutorCandidatesCollector.ECCInput, ExecutorCandidatesCollector.ECCOutput> {

    @Override
    protected ECCOutput initializeOutput() {
        return new ECCOutput();
    }

    @Override
    public void stepInst() {
        if(getCurrentInstruction().getInstruction().getContextIsFieldFlag()) {
            chooseExecutorHostingServiceInstance();
        }  else {
            IMethodResolution currentMethod = getInput().getCurrentMethod();
            String serviceUsed = currentMethod.getMethodRef().getServiceRef().getRef().getQualifier();
            List<IExecutorHandle> supportingExecutors = getInput().getExecutorSupplyCoordinator().supplyExecutor(serviceUsed);
            this.getInstOutput().getCandidates().addAll(supportingExecutors);
        }
    }

    @Override
    public void finishStep() {
        // check if at least a single executor is reachable.
        super.finishStep();
        if(!reachableExecutorExists()) {
            throw ChoreographyException.noReachableExecutorAmongCandidates();
        }
    }

    private void chooseExecutorHostingServiceInstance() {
        Long currentIndex = getCurrentInstruction().getIndex();
        String contextField = getCurrentInstruction().getInstruction().getContext();
        Optional<Long> prevAccess = getInput().getFieldAccessExplorer().getPrevAccess(currentIndex,
            fieldAccess -> fieldAccess.getAccessType().isAssignOrWrite() && fieldAccess.getField().equals(contextField));
        if(prevAccess.isPresent()) {
            copyCandidatesOfInst(prevAccess.get());
        } else {
            setToHostOfInitialService();
        }
    }

    private void copyCandidatesOfInst(Long instIndex) {
        ECCOutput prevECCOutput = getOutput().getOfIndex(instIndex);
        if(prevECCOutput == null || prevECCOutput.candidates.isEmpty()) {
            throw new RuntimeException("The previous output has no candidates.");
        }
        this.getInstOutput().candidates.addAll(prevECCOutput.candidates);
    }

    private void setToHostOfInitialService() {
        String contextField = getCurrentInstruction().getInstruction().getContext();
        ServiceInstanceHandle serviceInstanceHandle = getInput()
            .getInitialServices()
            .get(contextField);
        if(!getInput().getExecutorSupplyCoordinator().hasExecutor(serviceInstanceHandle.getId())) {
            throw ChoreographyException.initialServiceHostNotRegistered(contextField, serviceInstanceHandle.getId(), serviceInstanceHandle.getClasspath(), serviceInstanceHandle.getExecutorId());
        }
        IExecutorHandle handle = getInput().getExecutorSupplyCoordinator().getExecutorFor(serviceInstanceHandle.getId());
        getInstOutput().getCandidates().add(handle);
    }

    private boolean reachableExecutorExists() {
        for (IIndexedInstruction inst : getInput().getInstructions()) {
            List<IExecutorHandle> candidates = getOutput().getOfIndex(inst.getIndex()).getCandidates();
            boolean reachableExecutorExists = candidates.stream()
                .anyMatch(executorHandle -> executorHandle.getContactInfo().isReachable());
            if(reachableExecutorExists) {
                return true;
            }
        }
        return false;
    }

    class ECCInput implements InstInput {

        private final InitialServices initialServices;

        private final InstructionIndexer indexer;

        private final ICompositionCompilation cc;

        private final ExecutorSupplyCoordinator executorSupplyCoordinator;

        private final FieldAccessExplorer fieldAccessExplorer;

        ECCInput(InitialServices initialServices, InstructionIndexer indexer, ICompositionCompilation cc, ExecutorSupplyCoordinator executorSupplyCoordinator) {
            this.initialServices = initialServices;
            this.indexer = indexer;
            this.cc = cc;
            this.executorSupplyCoordinator = executorSupplyCoordinator;
            this.fieldAccessExplorer = new FieldAccessExplorer(indexer, cc);
        }

        @Override
        public InstructionIndexer getInstructions() {
            return indexer;
        }

        ExecutorSupplyCoordinator getExecutorSupplyCoordinator() {
            return executorSupplyCoordinator;
        }

        IMethodResolution getCurrentMethod() {
            Long currentIndex = getCurrentInstruction().getIndex();
            return cc.getInstructionAnalysis().get(currentIndex).getMethodResolution();
        }

        FieldAccessExplorer getFieldAccessExplorer() {
            return fieldAccessExplorer;
        }

        InitialServices getInitialServices() {
            return initialServices;
        }
    }

    static class ECCOutput {

        private final List<IExecutorHandle> candidates = new ArrayList<>();

        public List<IExecutorHandle> getCandidates() {
            return candidates;
        }

    }
}
