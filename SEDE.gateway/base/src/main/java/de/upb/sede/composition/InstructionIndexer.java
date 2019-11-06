package de.upb.sede.composition;

import de.upb.sede.composition.graphs.nodes.IIndexedInstruction;
import de.upb.sede.composition.graphs.nodes.IInstructionNode;
import de.upb.sede.composition.graphs.nodes.IndexedInstruction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InstructionIndexer implements Iterable<IIndexedInstruction>{

    private List<IIndexedInstruction> instructions;


    public InstructionIndexer(List<IInstructionNode> instructions) {
        this.instructions = new ArrayList<>(instructions.size());

        long currentIndex = 0;
        for (IInstructionNode instruction : instructions) {
            IndexedInstruction.builder().instruction(instruction).index(currentIndex);
            currentIndex ++;
        }
    }

    public int size() {
        return instructions.size();
    }

    public IInstructionNode get(int index) {
        int listIndex = (int) (index);
        return instructions.get(listIndex).getInstruction();
    }


    @Override
    public Iterator<IIndexedInstruction> iterator() {
        return instructions.iterator();
    }
}
