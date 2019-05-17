package de.upb.sede.util;

import java.util.function.Supplier;

public interface Cache<Content> extends Supplier<Content> {
    Content access();

    default Content get() {
        return access();
    }
}
