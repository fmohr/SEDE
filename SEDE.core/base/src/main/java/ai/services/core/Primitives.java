package ai.services.core;

import java.util.Optional;

public enum Primitives {


    NULL, String, Number, Bool;

    public final static Primitives[] VALUES = Primitives.values(); // cache values result

    public static Optional<Primitives> insensitiveValueOf(String searchName) {
        for (Primitives type : VALUES) {
            if (type.name().equalsIgnoreCase(searchName)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }

    public static Optional<Primitives> inferTypeFromValue(Object val) {
        if(val instanceof Number) {
            return Optional.of(Number);
        } else if(val instanceof String) {
            return Optional.of(String);
        } else if(val instanceof Boolean) {
            return Optional.of(Bool);
        } else if(val == null) {
            return Optional.of(NULL);
        } else {
            return Optional.empty();
        }
    }

}
