package de.upb.sede.util;

public class NullableCache<Content> implements SettableCache<Content> {

    private OptionalField<Content> content = OptionalField.empty();

    public NullableCache(Content content) {
        this.content = OptionalField.of(content);
    }

    public NullableCache() {
    }

    @Override
    public Content access() {
        return content.orElse(null);
    }

    public boolean set(Content content) {
        this.content = OptionalField.of(content);
        return true;
    }

    public void unset() {
        content = OptionalField.empty();
    }

    public boolean isNull() {
        return content.isAbsent();
    }

}
