package ai.services.composition.choerography;

import ai.services.composition.*;
import ai.services.composition.faa.FieldAccessUtil;
import ai.services.exec.IExecutorHandle;
import ai.services.util.MappedList;

import java.util.*;

public class InstExecutorLookaheadRewards extends
        BlockWiseCompileStep<InstExecutorLookaheadRewards.IEAInput,
            InstExecutorLookaheadRewards.IEAOutput> {

    private IIndexedInstruction currentInstruction;

    public InstExecutorLookaheadRewards() {

    }

    @Override
    protected IEAOutput initializeOutput() {
        return new IEAOutput();
    }

    private IIndexedInstruction getCurrentInstruction() {
        return currentInstruction;
    }

    @Override
    protected void stepBlock() {
        for (IIndexedInstruction inst : getInput().indexer) {
            this.currentInstruction = inst;
            rewardExecutors();
        }
        for (IIndexedInstruction inst : getInput().indexer) {
            this.currentInstruction = inst;
            selectInstHost();
        }
    }

    private void selectInstHost() {
        List<IExecutorHandle> candidates = getInput().getCandidates(getCurrentInstruction().getIndex());
        Map<String, IExecutorHandle> candidatesMap = getInput().getCandidatesMap(getCurrentInstruction().getIndex());
        if(candidates.isEmpty()) {
            throw new RuntimeException("BUG: No candidates are present. This needs to be checked before");
        }
        if(candidates.size() == 1) {
            getOutput().selectCandidate(getCurrentInstruction().getIndex(), candidates.get(0));
        }
        // there are multiple options.
        // First consider if the context is bound to another executor:
        else if(getCurrentInstruction().getInstruction().getContextIsFieldFlag()) {
            String contextFieldName = getCurrentInstruction().getInstruction().getContext();
            Optional<Long> prevAccess = getInput().getFieldAccessUtil()
                .getPrevAccess(getCurrentInstruction().getIndex(), access -> access.getField().equals(contextFieldName) && access.getAccessType().isAssignOrWrite());
            if(!prevAccess.isPresent()) {
                throw ChoreographyException.initialServiceMultipleCandidates(contextFieldName, candidates);
            }
            Long lastIndex = prevAccess.get();
            IExecutorHandle serviceHost = getOutput().candidateSelection.get(lastIndex);
            if(serviceHost == null) {
                throw new RuntimeException("BUG");
            }
            if(!candidatesMap.containsKey(serviceHost.getQualifier())) {
                throw new RuntimeException("BUG");
            }
            getOutput().selectCandidate(getCurrentInstruction().getIndex(), serviceHost);
        } else {
            // Context isn't a service. We are free to choose the executor:
            Optional<String> bestExecutorId = getOutput().bestScoredExecutor(getCurrentInstruction().getIndex());
            if(bestExecutorId.isPresent()) {
                IExecutorHandle selection = candidatesMap.get(bestExecutorId.get());
                Objects.requireNonNull(selection);
                getOutput().selectCandidate(getCurrentInstruction().getIndex(), selection);
            } else {
                // pick at random:
                int selectionIndex = (int) (Math.random() * candidates.size());
                IExecutorHandle selection = candidates.get(selectionIndex);
                getOutput().selectCandidate(getCurrentInstruction().getIndex(), selection);
            }
        }
        IExecutorHandle selection = getOutput().candidateSelection.get(getCurrentInstruction().getIndex());
        if(selection == null) {
            throw new RuntimeException("No executor was selected for instruction: " + getCurrentInstruction().getInstruction().getFMInstruction()) ;
        }
    }

    private void rewardExecutors() {
        Long instIndex = getCurrentInstruction().getIndex();
        // consider every field that is being written onto or read from:
        Iterator<IFieldAccess> readAccesses = getInput()
            .getFieldAccessUtil().accessesAtInst(
            instIndex,
            access -> access.getAccessType().isAssignOrWrite())
            .iterator();
        while(readAccesses.hasNext()) {
            IFieldAccess writtenField = readAccesses.next();
            rewardReadsToFutureAccesses(writtenField);
        }
    }

    private void rewardReadsToFutureAccesses(IFieldAccess currentWrittenField) {
        Long instIndex = getCurrentInstruction().getIndex();
        // look into future instruction for reads on this field:
        // we look at all future instruction up until the next write:
        Map<String, IExecutorHandle> candidates = getInput().getCandidatesMap(instIndex);
        List<IFieldAccess> fieldAccesses = getInput()
            .getFieldAccessUtil()
            .accessesToFieldName(currentWrittenField.getField());
        for (IFieldAccess futureAccess : fieldAccesses) {
            Long futureInstIndex = futureAccess.getIndex();
            if(!getInput().indexer.comesBefore(currentWrittenField.getIndex(), futureInstIndex)) {
                // we are not interested in past field accesses.
                continue;
            }
            IFieldAccess.AccessType futureAccessType = futureAccess.getAccessType();
            if(futureAccessType == IFieldAccess.AccessType.READ) {
                rewardIntersection(instIndex, futureAccess.getIndex());
            }
            if(futureAccessType.isAssignOrWrite()) {
                // we are only interested up until the next write.
                break;
            }
        }
    }

    private void rewardIntersection(Long instIndex1, Long instIndex2) {
        List<IExecutorHandle> candidates = getInput().getCandidates(instIndex1);
        Map<String, IExecutorHandle> candidates2 = getInput().getCandidatesMap(instIndex2);
        for (IExecutorHandle candidate : candidates) {
            if(candidates2.containsKey(candidate.getQualifier())) {
                getOutput().rewardCandidate(instIndex1, candidate.getQualifier());
                getOutput().rewardCandidate(instIndex2, candidate.getQualifier());
            }
        }
    }

    static class IEAInput implements InstInput {

        private final InstructionIndexer indexer;

        private final Map<Long, List<IExecutorHandle>> candidates;

        private final FieldAccessUtil fieldAccessUtil;

        IEAInput(InstructionIndexer indexer,
                 Map<Long, List<IExecutorHandle>> candidates,
                 FieldAccessUtil fieldAccessUtil) {
            this.indexer = indexer;
            this.candidates = candidates;
            this.fieldAccessUtil = fieldAccessUtil;
        }

        @Override
        public InstructionIndexer getInstructions() {
            return indexer;
        }

        List<IExecutorHandle> getCandidates(Long instIndex) {
            return candidates.get(instIndex);
        }

        Map<String, IExecutorHandle> getCandidatesMap(Long instIndex) {
            return new MappedList<>(candidates.get(instIndex), IExecutorHandle::getQualifier);
        }

        FieldAccessUtil getFieldAccessUtil() {
            return fieldAccessUtil;
        }
    }

    static class IEAOutput {

        private final Map<Long, Map<String, Integer>> candidateScores = new HashMap<>();

        private final Map<Long, IExecutorHandle> candidateSelection = new HashMap<>();

        private void rewardCandidate(Long index, String executorId) {
            Map<String, Integer> instCandidateScore = candidateScores.computeIfAbsent(index, i -> new HashMap<>());
            instCandidateScore.compute(executorId, (e, score) -> (score == null)  ? 0 : score+1 );
        }

        private Optional<String> bestScoredExecutor(Long index) {
            int bestScore = -1;
            String bestExecutorId = null;
            Map<String, Integer> candidateScore = candidateScores.get(index);
            if(candidateScore == null)
                return Optional.empty();

            for (String s : candidateScore.keySet()) {
                if(candidateScore.get(s) > bestScore) {
                    bestScore = candidateScore.get(s);
                    bestExecutorId = s;
                }
            }
            if(bestScore == -1) {
                return Optional.empty();
            } else {
                return Optional.of(bestExecutorId);
            }
        }

        private void selectCandidate(Long index, IExecutorHandle handle) {
            Objects.requireNonNull(handle);
            candidateSelection.put(index, handle);
        }

        public Map<Long, IExecutorHandle> getCandidateSelection() {
            return candidateSelection;
        }
    }


}
