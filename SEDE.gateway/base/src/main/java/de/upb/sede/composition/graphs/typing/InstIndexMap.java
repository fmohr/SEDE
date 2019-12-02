package de.upb.sede.composition.graphs.typing;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class InstIndexMap<T> extends HashMap<Long, T> {


    public Consumer<T> setter(long index) {
        return t -> this.put(index, t);
    }

}
