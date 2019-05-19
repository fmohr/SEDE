package de.upb.sede.util;

import java.io.Serializable;
import java.util.function.Supplier;

public class LazyAccessCache<Content> implements Cache<Content>, Serializable {

    private WobblyField<Supplier<Content>> supplier;

    private WobblyField<Content> content = WobblyField.empty();

    public LazyAccessCache(Supplier<Content> supplier) {
        this.supplier = WobblyField.of(supplier);
    }

    @Override
    public Content access() {
        if(content.isAbsent()) {
            if(supplier.isPresent()) {
                content = WobblyField.of(supplier.get().get());
                supplier = WobblyField.empty();
            } else {
                throw new IllegalStateException("No cache content and no content supplier present. " +
                        "This state shouldn't be reachable.");
            }
        }
        return content.get();
    }

    @Override
    public void set(Content content) {
        this.content = WobblyField.of(content);
    }

    @Override
    public void unset() {
        this.content = WobblyField.empty();
    }
}
