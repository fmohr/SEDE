package de.upb.sede.core;

import javax.annotation.Nullable;
import java.util.Optional;

public enum PrimitiveType {
    NULL, String, Number, Bool;

    public static Optional<PrimitiveType> insensitiveValueOf(String searchName) {
        for (PrimitiveType type : PrimitiveType.values()) {
            if (type.name().equalsIgnoreCase(searchName)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }

}
