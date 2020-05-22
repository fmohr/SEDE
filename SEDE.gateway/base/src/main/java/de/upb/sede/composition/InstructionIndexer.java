package de.upb.sede.composition;

import de.upb.sede.composition.graphs.nodes.IInstructionNode;

import java.util.*;

public class InstructionIndexer implements Iterable<IIndexedInstruction>{

    private List<IIndexedInstruction> indexedInstList;

    public InstructionIndexer(Iterable<IInstructionNode> instructions) {
        this.indexedInstList = new ArrayList<>();
        long currentIndex = 0;
        for (IInstructionNode instruction : instructions) {
            this.indexedInstList.add(IndexedInstruction.builder().instruction(instruction).index(currentIndex).build());
            currentIndex ++;
        }
    }

    public InstructionIndexer(List<IIndexedInstruction> instructions) {
        this.indexedInstList = new ArrayList<>(instructions);
    }

    public void assertNotEmpty() {
        if(Objects.requireNonNull(indexedInstList, "Instruction List is null").isEmpty()) {
            throw new IllegalArgumentException("The instruction list is empty.");
        }
    }

    public int size() {
        return indexedInstList.size();
    }


    public IInstructionNode get(int index) {
        int listIndex = (int) (index);
        return indexedInstList.get(listIndex).getInstruction();
    }


    public Long getLastIndex() {
        return indexedInstList.get(indexedInstList.size()-1).getIndex();
    }

    public Optional<Integer> getPositionOfInst(long instIndex) {
        int startingIndex = -1;
        for (int i = 0; i < indexedInstList.size(); i++) {
            if(indexedInstList.get(i).getIndex() == instIndex) {
                startingIndex = i;
                break;
            }
        }
        if(startingIndex == -1) {
            return Optional.empty();
        }
        return Optional.of(startingIndex);
    }

    public Iterator<IIndexedInstruction> iteratePrevInst(long instIndex) {
        int startingIndex = getPositionOfInst(instIndex).orElseThrow(
            () -> new IllegalArgumentException("No instruction has index: " + instIndex)
        );
        return new Iterator<>() {
            int index = startingIndex;

            @Override
            public boolean hasNext() {
                return index-1 > 0;
            }

            @Override
            public IIndexedInstruction next() {
                index--;
                return indexedInstList.get(index);
            }
        };
    }

    public Iterator<IIndexedInstruction> iterateFollowingInst(long instIndex) {
        Iterator<IIndexedInstruction> iterator = iterator();
        while(iterator.hasNext()) {
            IIndexedInstruction nextInst = iterator.next();
            if(nextInst.getIndex() == instIndex) {
                return iterator;
            }
        }
        throw new IllegalArgumentException("No instruction has index: "+ instIndex);
    }

    /**
     * Returns true iff there is an instruction with index1 that comes before any other instruction with index2 in the program order.
     * @param index1 index that should come first
     * @param index2 index that should come after
     * @return true if first index is executed before the seconds one.
     */
    public boolean comesBefore(Long index1, Long index2) {
        if (index1.equals(index2)) {
            return false;
        }
        for (IIndexedInstruction inst : this) {
            if(inst.getIndex().equals(index1)) {
                return true;
            }
            if(inst.getIndex().equals(index2)) {
                return false;
            }
        }
        throw new IllegalArgumentException("No instruction with index " + index1 + " or " + index2);
    }

    @Override
    public Iterator<IIndexedInstruction> iterator() {
        return indexedInstList.iterator();
    }

}
