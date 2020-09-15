package ai.services.composition;

import ai.services.composition.graphs.nodes.WithIndex;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class IndexFactory {

    private AtomicLong currentNextFreeIndex = new AtomicLong(0);

    public IndexFactory() {

    }

    public void setOccupiedIndex(Long index) {
        currentNextFreeIndex.updateAndGet(l -> Math.max(l, index + 1));
    }

    public void setOccupiedIndices(Iterable<WithIndex> occupiedIndices) {
        for (WithIndex occupiedIndex : occupiedIndices) {
            Optional<Long> index = Optional.ofNullable(occupiedIndex.getIndex());
            index.ifPresent(this::setOccupiedIndex);
        }
    }

    public Long create() {
        return currentNextFreeIndex.getAndIncrement();
    }
}
