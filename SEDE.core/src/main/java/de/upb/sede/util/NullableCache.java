package de.upb.sede.util;

public class NullableCache<Content> implements Cache<Content> {

    private OptionalField<Content> content = OptionalField.empty();

    @Override
    public Content access() {
        return content.orElse(null);
    }

    public void set(Content content) {
        this.content = OptionalField.of(content);
    }

    public void unset() {
        content = OptionalField.empty();
    }

    public boolean isNull() {
        return content.isAbsent();
    }

}
