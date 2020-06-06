package de.upb.sede.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.SEDEModelStyle;

import java.util.stream.Stream;

public class DeepImmutableCopier {

    private static final ObjectMapper MAPPER = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);



    public static <T> T copyAsImmutable(Object input) {
        if(!isSMStyleAnnotated(input.getClass())) {
            throw new IllegalArgumentException("Cannot convert object of unknown type: " + input.getClass().getName());
        }
        String immutableClassName = getImmutableClassName(input);
        Class<?> immutableClass;
        try {
            immutableClass = input.getClass().forName(immutableClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(
                String.format("Value object %s doesn't have an immutable class with %s.",
                    input.getClass().getSimpleName(), immutableClassName),
                e);
        }
        return (T) copyAsImmutable(input, immutableClass);
    }

    public static <T> T copyAsImmutable(Object input, Class<T> tClass) {
        if(!isSMStyleAnnotated(input.getClass())) {
            throw new IllegalArgumentException("Cannot convert object of unknown type: " + input.getClass().getName());
        }
        if(!isSMStyleAnnotated(tClass)) {
            throw new IllegalArgumentException("Cannot convert to unknown immutable class: " + tClass);
        }
        return MAPPER.convertValue(input, tClass);
    }

    static <T> boolean isSMStyleAnnotated(Class<T> tClass) {
        return Stream.of(tClass.getInterfaces()).anyMatch(i -> i.isAnnotationPresent(SEDEModelStyle.class));
    }


    private static String getImmutableClassName(Object valueObject) {
        String className = valueObject.getClass().getSimpleName();
        if(!className.startsWith("Mutable")) {
            return valueObject.getClass().getName();
        } else {
            // Remove Mutable from the path:
            Package packageObject = valueObject.getClass().getPackage();
            String packageName;
            if(packageObject == null) {
                // in java 8 packageObject is null when a class is in an unnamed package.
                packageName = "";
            } else {
                packageName = packageObject.getName();
            }
            String immutableClassName = className.substring("Mutable".length());
            if(packageName.isEmpty()) {
                return immutableClassName;
            } else {
                return packageName + "." + immutableClassName;
            }
        }
    }

}
