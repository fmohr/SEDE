package de.upb.sede.util;

import java.util.Objects;

public class StaticCache<Content> implements Cache<Content> {

    private final Content content;

    public StaticCache(Content content) {
        this.content = Objects.requireNonNull(content);
    }

    @Override
    public Content access() {
        return content;
    }



}
