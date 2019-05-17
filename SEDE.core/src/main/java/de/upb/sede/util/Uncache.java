package de.upb.sede.util;

import java.util.Objects;
import java.util.function.Supplier;

public class Uncache<Content> implements Cache<Content> {

    private Supplier<Content> supplier;

    public Uncache(Supplier<Content> supplier) {
        this.supplier = Objects.requireNonNull(supplier);
    }

    @Override
    public Content access() {
        return supplier.get();
    }
}
