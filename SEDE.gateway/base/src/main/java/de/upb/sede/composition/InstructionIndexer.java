package de.upb.sede.composition;

import de.upb.sede.composition.graphs.nodes.IInstructionNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InstructionIndexer implements Iterable<IIndexedInstruction>{

    private List<IIndexedInstruction> indexedInstList;


    public InstructionIndexer(List<IInstructionNode> instructions) {
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


    @Override
    public Iterator<IIndexedInstruction> iterator() {
        return indexedInstList.iterator();
    }
}
