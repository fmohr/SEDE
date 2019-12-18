package de.upb.sede.composition.typing;

import java.util.HashMap;
import java.util.function.Consumer;

class IndexMap<T> extends HashMap<Long, T> {

    Consumer<T> setter(long index) {
        return t -> this.put(index, t);
    }

}
