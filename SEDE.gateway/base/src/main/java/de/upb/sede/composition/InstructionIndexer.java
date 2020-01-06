package de.upb.sede.composition;

import de.upb.sede.composition.graphs.nodes.IInstructionNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class InstructionIndexer implements Iterable<IIndexedInstruction>{

    private List<IIndexedInstruction> indexedInstList;


    public InstructionIndexer(List<IInstructionNode> instructions) {
        if(Objects.requireNonNull(instructions, "Instruction List is null").isEmpty()) {
            throw new IllegalArgumentException("The given instruction list is empty.");
        }
        this.indexedInstList = new ArrayList<>(instructions.size());
        long currentIndex = 0;
        for (IInstructionNode instruction : instructions) {
            this.indexedInstList.add(IndexedInstruction.builder().instruction(instruction).index(currentIndex).build());
            currentIndex ++;
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

    @Override
    public Iterator<IIndexedInstruction> iterator() {
        return indexedInstList.iterator();
    }
}
