package de.upb.sede.composition;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Supplier;

public class InstOutputIterator<T> implements Iterator<T> {

    private final Map<Long, T> outputMap;

    private Function<T, T> nextOutputGenerator;

    private final List<Long> sortedIndices = new ArrayList<>();

    private final InstructionIndexer indexer;

    private Iterator<IIndexedInstruction> iterator;

    private Long currentIndex = null;

    private IIndexedInstruction currentInstruction = null;

    public InstOutputIterator(Function<T, T> nextOutputGenerator, InstructionIndexer indexer) {
        this.outputMap = new HashMap<>();
        this.nextOutputGenerator = nextOutputGenerator;
        this.iterator = indexer.iterator();
        this.indexer = indexer;
    }

    public InstOutputIterator(Supplier<T> outputGenerator, InstructionIndexer indexer) {
        this.outputMap = new HashMap<>();
        this.nextOutputGenerator = oldOutput -> outputGenerator.get();
        this.iterator = indexer.iterator();
        this.indexer = indexer;
    }

    public void repeatFromBeginning() {
        iterator = indexer.iterator();

        final Function<T, T> oldGen = nextOutputGenerator;
        nextOutputGenerator = lastOutput -> {
            if(outputMap.containsKey(currentInstruction.getIndex())) {
                return outputMap.get(currentInstruction.getIndex());
            } else {
                return oldGen.apply(lastOutput);
            }
        };

    }

    public T next() {
        if(iterator.hasNext()) {
            currentInstruction = iterator.next();
            T newOutput = nextOutputGenerator.apply(outputMap.get(currentIndex));
            currentIndex = currentInstruction.getIndex();
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

    public T getOfIndex(Long instIndex) {
        return outputMap.get(instIndex);
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