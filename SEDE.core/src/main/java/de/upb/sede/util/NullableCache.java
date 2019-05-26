package de.upb.sede.util;

public class NullableCache<Content> implements Cache<Content> {

    private WobblyField<Content> content = WobblyField.empty();

    @Override
    public Content access() {
        return content.orElse(null);
    }

    public void unset() {
        content = WobblyField.empty();
    }

    public boolean isNull() {
        return content.isAbsent();
    }

}
