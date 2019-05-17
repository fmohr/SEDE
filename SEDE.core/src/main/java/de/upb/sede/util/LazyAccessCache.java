package de.upb.sede.util;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Supplier;

public class LazyAccessCache<Content> implements Cache<Content>, Serializable {

    private Supplier<Content> supplier;

    private MutableWobblyField<Content> content = MutableWobblyField.empty();

    public LazyAccessCache(Supplier<Content> supplier) {
        this.supplier = Objects.requireNonNull(supplier);
    }

    @Override
    public Content access() {
        if(content.isAbsent()) {
            content.set(supplier.get());
        }
        return content.get();
    }
}
