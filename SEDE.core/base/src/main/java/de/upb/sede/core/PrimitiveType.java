package de.upb.sede.core;

public enum PrimitiveType {
    NULL, String, Number, Bool;

    public static PrimitiveType insensitiveValueOf(String searchName) {
        for (PrimitiveType type : PrimitiveType.values()) {
            if (type.name().equalsIgnoreCase(searchName)) {
                return type;
            }
        }
        throw new RuntimeException("BUG: primitive type '" + searchName + "' not defined.");
    }

}
