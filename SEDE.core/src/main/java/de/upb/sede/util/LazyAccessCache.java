package de.upb.sede.util;

import java.io.Serializable;
import java.util.function.Supplier;

public class LazyAccessCache<Content> implements Cache<Content>, Serializable {

    private OptionalField<Supplier<Content>> supplier;

    private OptionalField<Content> content = OptionalField.empty();

    public LazyAccessCache(Supplier<Content> supplier) {
        this.supplier = OptionalField.of(supplier);
    }

    @Override
    public Content access() {
        if(content.isAbsent()) {
            if(supplier.isPresent()) {
                content = OptionalField.of(supplier.get().get());
                supplier = OptionalField.empty();
            } else {
                throw new IllegalStateException("No cache content and no content supplier present. " +
                        "This state shouldn't be reachable.");
            }
        }
        return content.get();
    }

    public void unset() {
        this.content = OptionalField.empty();
    }
}
