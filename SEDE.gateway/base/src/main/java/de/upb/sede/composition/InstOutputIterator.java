package de.upb.sede.composition;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class InstOutputIterator<T> implements Iterator<T> {

    private final Map<Long, T> outputMap;

    private final Function<T, T> nextOutputGenerator;

    private final List<Long> sortedIndices = new ArrayList<>();

    private final Iterator<IIndexedInstruction> iterator;

    private Long currentIndex = null;

    private IIndexedInstruction currentInstruction = null;

    public InstOutputIterator(Function<T, T> nextOutputGenerator, InstructionIndexer indexer) {
        this.outputMap = new HashMap<>();
        this.nextOutputGenerator = nextOutputGenerator;
        this.iterator = indexer.iterator();
    }

    public InstOutputIterator(Supplier<T> outputGenerator, InstructionIndexer indexer) {
        this.outputMap = new HashMap<>();
        this.nextOutputGenerator = oldOutput -> outputGenerator.get();
        this.iterator = indexer.iterator();
    }

    public T next() {
        if(iterator.hasNext()) {
            IIndexedInstruction currentInst = iterator.next();
            T newOutput = nextOutputGenerator.apply(outputMap.get(currentIndex));
            currentIndex = currentInst.getIndex();
            currentInstruction = currentInst;
            sortedIndices.add(currentIndex);
            outputMap.put(currentIndex, newOutput);
            return newOutput;
        } else {
            return null;
        }
    }

    public IIndexedInstruction getCurrentInstruction() {
        if(currentIndex == null) {
            throw new IllegalStateException("Iterator in bad state. First call next()");
        }
        return currentInstruction;
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public T getCurrent() {
        if(currentIndex == null) {
            throw new IllegalStateException("Iterator in bad state. First call next()");
        }
        return outputMap.get(currentIndex);
    }

    public T getBefore() {
        if(sortedIndices.size() <= 1) {
            throw new IllegalStateException();
        }
        return outputMap.get(sortedIndices.get(sortedIndices.size() -2));
    }

    public Map<Long, T> getFinalOutput() {
        if(hasNext()) {
            throw new IllegalStateException("The compilation is not done yet. Currently at index: " + getCurrentInstruction().getIndex());
        }
        return outputMap;
    }

}
